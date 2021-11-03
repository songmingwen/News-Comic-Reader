package com.song.game.soaring

import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.View.FOCUS_DOWN
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.song.game.soaring.bean.character.Character
import com.song.game.soaring.bean.character.CharacterList
import com.song.game.soaring.bean.wuxue.Wuxue
import com.song.game.soaring.bean.wuxue.WuxueList
import com.song.game.soaring.utils.DamageUtil
import com.song.sunset.R
import com.song.sunset.base.activity.BaseActivity
import com.song.sunset.base.utils.AssetsUtils
import com.song.sunset.utils.JsonUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_soaring_battle.*
import java.lang.StringBuilder
import java.util.LinkedHashMap


@Route(path = "/song/soaring/battle")
class SoaringBattleActivity : BaseActivity() {

    companion object {

        private const val CHARACTER_ID_SELF = "character_id_self"
        private const val CHARACTER_ID_ENEMY = "character_id_enemy"
        private const val WUXUE_ID_SELF = "wuxue_id_self"
        private const val WUXUE_ID_ENEMY = "wuxue_id_enemy"

        const val BATTLE_EXTRA = "battle_extra"

        fun obtainBundle(selfCharacterId: String, enemyCharacterId: String,
                         selfWuxueId: String, enemyWuxueId: String): Bundle {
            val bundle = Bundle()
            bundle.putString(CHARACTER_ID_SELF, selfCharacterId)
            bundle.putString(CHARACTER_ID_ENEMY, enemyCharacterId)
            bundle.putString(WUXUE_ID_SELF, selfWuxueId)
            bundle.putString(WUXUE_ID_ENEMY, enemyWuxueId)
            return bundle
        }

    }

    private lateinit var self: Character
    private lateinit var enemy: Character

    private var selfWuxue: Wuxue? = null
    private var enemyWuxue: Wuxue? = null

    private var finish = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soaring_battle)

        val bundle = intent.getBundleExtra(BATTLE_EXTRA)

        if (bundle == null) {
            finish()
            return
        }

        init(bundle)

        attack.setOnClickListener {
            if (!finish) {
                startBattle()
            }
        }
    }

    private fun init(bundle: Bundle) {
        val selfCharacterId = bundle.getString(CHARACTER_ID_SELF)
        val enemyCharacterId = bundle.getString(CHARACTER_ID_ENEMY)
        val selfWuxueId = bundle.getString(WUXUE_ID_SELF)
        val enemyWuxueId = bundle.getString(WUXUE_ID_ENEMY)

        val characterListStr = AssetsUtils.getJson("character_list.json", this)
        val characterList = JsonUtil.gsonToBean(characterListStr, CharacterList::class.java)

        val wuxueListStr = AssetsUtils.getJson("wuxue.json", this)
        val wuxueList = JsonUtil.gsonToBean(wuxueListStr, WuxueList::class.java)

        characterList.characterList.forEach {
            if (TextUtils.equals(it.name, selfCharacterId)) {
                self = it
            } else if (TextUtils.equals(it.name, enemyCharacterId)) {
                enemy = it
            }
        }

        wuxueList.wuxueList.forEach {
            if (TextUtils.equals(it.name, selfWuxueId)) {
                selfWuxue = it
            } else if (TextUtils.equals(it.name, enemyWuxueId)) {
                enemyWuxue = it
            }
        }

        enemy.currentHealth = enemy.health
        enemy_health.max = enemy.health.toInt()
        enemy_health.progress = enemy.health.toInt()

        self.currentHealth = self.health
        self_health.max = self.health.toInt()
        self_health.progress = self.health.toInt()
    }

    private fun startBattle() {
        val disposable = Observable.create<Boolean> { emitter ->

            showMessage(getColorfulText(LinkedHashMap<String, String>().apply {
                this["一招 "] = "#9E9E9E"
                this[selfWuxue?.name ?: "普攻"] = getStringColor(selfWuxue?.level ?: 1)
                this[" 向敌人打去"] = "#9E9E9E"
            }))

            if (DamageUtil.isHit(self, enemy, selfWuxue)) {
                val isPhysicalCritical = DamageUtil.isPhysicalCritical(self, enemy, selfWuxue)
                val isSpellsCritical = DamageUtil.isSpellsCritical(self, enemy, selfWuxue)
                val damage = DamageUtil.getDamage(self, enemy, selfWuxue, isPhysicalCritical, isSpellsCritical)

                val extraMessage = if (isPhysicalCritical) " 暴击 " else ""

                showMessage(getColorfulText(LinkedHashMap<String, String>().apply {
                    this["对敌人造成 "] = "#9E9E9E"
                    this[damage.toString()] = "#FF3300"
                    this[" 点"] = "#9E9E9E"
                    this[extraMessage] = "#FFD700"
                    this["伤害"] = "#9E9E9E"
                }))

                enemy.currentHealth -= damage
                if (enemy.currentHealth <= 0) {
                    enemy.currentHealth = 0f
                }
                enemy_health.progress = enemy.currentHealth.toInt()
                if (enemy.currentHealth == 0f) {
                    emitter.onNext(false)
                } else {
                    emitter.onNext(true)
                }
            } else {
                showMessage(getColorfulText(LinkedHashMap<String, String>().apply {
                    this["被敌人躲过了 "] = "#9E9E9E"
                }))
                emitter.onNext(true)
            }
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .map {
                    if (it) {
                        showMessage(getColorfulText(LinkedHashMap<String, String>().apply {
                            this["敌人一招 "] = "#9E9E9E"
                            this[enemyWuxue?.name ?: "普攻"] = getStringColor(enemyWuxue?.level ?: 1)
                            this[" 向我攻来"] = "#9E9E9E"
                        }))

                        if (DamageUtil.isHit(enemy, self, enemyWuxue)) {
                            val isPhysicalCritical = DamageUtil.isPhysicalCritical(enemy, self, enemyWuxue)
                            val isSpellsCritical = DamageUtil.isSpellsCritical(enemy, self, enemyWuxue)
                            val damage = DamageUtil.getDamage(enemy, self, enemyWuxue, isPhysicalCritical, isSpellsCritical)

                            val extraMessage = if (isPhysicalCritical) " 暴击 " else ""
                            showMessage(getColorfulText(LinkedHashMap<String, String>().apply {
                                this["对我造成 "] = "#9E9E9E"
                                this[damage.toString()] = "#FF3300"
                                this[" 点"] = "#9E9E9E"
                                this[extraMessage] = "#FFD700"
                                this["伤害"] = "#9E9E9E"
                            }))
                            self.currentHealth -= damage
                            if (self.currentHealth <= 0) {
                                self.currentHealth = 0f
                            }
                            self_health.progress = self.currentHealth.toInt()
                            if (self.currentHealth == 0f) {
                                showMessage(getColorfulText(LinkedHashMap<String, String>().apply {
                                    this["你被击败了"] = "#000000"
                                }))
                                finishBattle()
                            }
                        } else {
                            showMessage(getColorfulText(LinkedHashMap<String, String>().apply {
                                this["躲过了敌人的攻击"] = "#9E9E9E"
                            }))
                        }
                    } else {
                        showMessage(getColorfulText(LinkedHashMap<String, String>().apply {
                            this["击败 "] = "#1D222C"
                            this[enemy.name] = "#3377FF"
                        }))
                        finishBattle()
                    }
                }.subscribe {
                    showMessage("------- song -------")
                    message_scroll.post { message_scroll.fullScroll(FOCUS_DOWN) }
                }
    }

    private fun getStringColor(wuxue: Int): String {
        return when (wuxue) {
            1 -> "#1D222C"
            2 -> "#77FF77"
            3 -> "#3377FF"
            4 -> "#9933FF"
            5 -> "#FF3300"
            6 -> "#FFD700"
            else -> "#1D222C"
        }
    }

    private fun getColorfulText(map: LinkedHashMap<String, String>): CharSequence {
        val stringBuilder = StringBuilder()
        for (entry in map) {
            stringBuilder.append("<font color=\"" + entry.value + "\">").append(entry.key).append("</font>")
        }
        return Html.fromHtml(stringBuilder.toString())
    }

    private fun finishBattle() {
        finish = true
        //装备掉落
    }

    private fun showMessage(message: CharSequence) {
        val text = TextView(this)
        text.text = message
        message_content.addView(text)
    }
}