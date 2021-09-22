package com.song.game.soaring

import android.os.Bundle
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

@Route(path = "/song/soaring/battle")
class SoaringBattleActivity : BaseActivity() {

    companion object {
        const val CHARACTER_ID = "character_id"
    }

    private lateinit var self: Character
    private lateinit var enemy: Character

    private var selfWuxue: Wuxue? = null
    private var enemyWuxue: Wuxue? = null

    private var finish = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soaring_battle)
        val characterId = intent.getStringExtra(CHARACTER_ID)

        val characterListStr = AssetsUtils.getJson("character_list.json", this)
        val characterList = JsonUtil.gsonToBean(characterListStr, CharacterList::class.java)

        val wuxueListStr = AssetsUtils.getJson("wuxue.json", this)
        val wuxueList = JsonUtil.gsonToBean(wuxueListStr, WuxueList::class.java)

        characterList.characterList.forEach {
            if (TextUtils.equals(it.name, "乔峰")) {
                self = it
            } else if (TextUtils.equals(it.name, "虚竹")) {
                enemy = it
            }
        }

        wuxueList.wuxueList.forEach {
            if (TextUtils.equals(it.name, "降龙十八掌")) {
                selfWuxue = it
            } else if (TextUtils.equals(it.name, "天山六阳掌")) {
                enemyWuxue = it
            }
        }

        enemy.currentHealth = enemy.health
        enemy_health.max = enemy.health.toInt()
        enemy_health.progress = enemy.health.toInt()

        self.currentHealth = self.health
        self_health.max = self.health.toInt()
        self_health.progress = self.health.toInt()

        attack.setOnClickListener {
            if (!finish) {
                startBattle()
            }
        }
    }

    private fun startBattle() {
        val disposable = Observable.create<Boolean> { emitter ->
            showMessage("一招" + (selfWuxue?.name ?: "普攻") + "向敌人打去")
            if (DamageUtil.isHit(self, enemy, selfWuxue)) {
                val isPhysicalCritical = DamageUtil.isPhysicalCritical(self, enemy, selfWuxue)
                val isSpellsCritical = DamageUtil.isSpellsCritical(self, enemy, selfWuxue)
                val damage = DamageUtil.getDamage(self, enemy, selfWuxue, isPhysicalCritical, isSpellsCritical)

                val extraMessage = if (isPhysicalCritical) "暴击" else ""
                showMessage("对敌人造成 " + damage + " 点" + extraMessage + "伤害")
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
                showMessage("被敌人躲过了")
                emitter.onNext(true)
            }
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .map {
                    if (it) {
                        showMessage("敌人一招" + (enemyWuxue?.name ?: "普攻") + "向我攻来")
                        if (DamageUtil.isHit(enemy, self, enemyWuxue)) {
                            val isPhysicalCritical = DamageUtil.isPhysicalCritical(enemy, self, enemyWuxue)
                            val isSpellsCritical = DamageUtil.isSpellsCritical(enemy, self, enemyWuxue)
                            val damage = DamageUtil.getDamage(enemy, self, enemyWuxue, isPhysicalCritical, isSpellsCritical)

                            val extraMessage = if (isPhysicalCritical) "暴击" else ""
                            showMessage("敌人对我造成 " + damage + " 点" + extraMessage + "伤害")
                            self.currentHealth -= damage
                            if (self.currentHealth <= 0) {
                                self.currentHealth = 0f
                            }
                            self_health.progress = self.currentHealth.toInt()
                            if (self.currentHealth == 0f) {
                                showMessage("你被击败了")
                                finishBattle()
                            }
                        } else {
                            showMessage("躲过了敌人的攻击")
                        }
                    } else {
                        showMessage("敌人被击败")
                        finishBattle()
                    }
                }.subscribe {
                    showMessage("------- song -------")
                    message_scroll.post { message_scroll.fullScroll(FOCUS_DOWN) }
                }
    }

    private fun finishBattle() {
        finish = true
        //装备掉落
    }

    private fun showMessage(message: String) {
        val text = TextView(this)
        text.text = message
        message_content.addView(text)
    }
}