package com.song.game.wuxia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.song.game.wuxia.bean.talent.Talent
import com.song.game.wuxia.bean.talent.TotalTalent
import com.song.game.wuxia.holder.TalentHolder
import com.song.sunset.R
import com.song.sunset.base.utils.AssetsUtils
import com.song.sunset.utils.JsonUtil
import com.zhihu.android.sugaradapter.SugarAdapter
import kotlinx.android.synthetic.main.activity_talent.*
import java.util.*

@Route(path = "/song/wuxia/talent")
class WuXiaTalentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talent)
        showTalent()
    }

    private fun showTalent() {
        val adapter = SugarAdapter.Builder
                .with(getRandom())
                .add(TalentHolder::class.java)
                .build()
        talent_recycler_view.adapter = adapter
        talent_recycler_view.layoutManager = LinearLayoutManager(this)
    }

    private fun getRandom(): ArrayList<Talent> {
        val talentListJson = AssetsUtils.getJson("talent_list.json", this)
        val totalTalent = JsonUtil.gsonToBean(talentListJson, TotalTalent::class.java)
        return totalTalent.talentList
    }

}