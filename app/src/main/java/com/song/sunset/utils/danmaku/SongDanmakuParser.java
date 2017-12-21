package com.song.sunset.utils.danmaku;

import android.graphics.Color;

import com.song.sunset.beans.DanmakuBean;

import java.util.List;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;

/**
 * Created by Song on 2017/4/18 0018.
 * E-mail: z53520@qq.com
 */

public class SongDanmakuParser extends BaseDanmakuParser {

    private List<DanmakuBean> mList = null;
    private Danmakus danmakus = new Danmakus();
    private final Object mLock;

    public SongDanmakuParser(List<DanmakuBean> list) {
        this.mList = list;
        mLock = danmakus.obtainSynchronizer();
    }

    @Override
    protected IDanmakus parse() {
        if (mList == null || mList.size() <= 0) {
            return new Danmakus();
        }
        for (DanmakuBean bean : mList) {
            BaseDanmaku item = mContext.mDanmakuFactory.createDanmaku(bean.getType(), mContext);
            if (item != null) {
                item.setTime(bean.getTime());
                item.textSize = bean.getTextSize();
                item.textColor = bean.getColor();
                item.textShadowColor = bean.getColor() <= Color.BLACK ? Color.WHITE : Color.BLACK;
                item.setTimer(mTimer);
                item.flags = mContext.mGlobalFlagValues;
                item.text = bean.getContent();
                item.isLive = bean.isLive();
            }
            synchronized (mLock) {
                danmakus.addItem(item);
            }
        }
        return danmakus;
    }
}
