package com.song.sunset.utils.danmaku;

import android.graphics.Color;

import com.song.sunset.beans.DanmakuBean;

import java.util.List;

import master.flame.danmaku.danmaku.model.AlphaValue;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.Duration;
import master.flame.danmaku.danmaku.model.IDanmakus;
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
            long time = bean.getTime();
            BaseDanmaku item = mContext.mDanmakuFactory.createDanmaku(bean.getType(), mContext);
            if (item != null) {
                item.setTime(time);
                item.textSize = bean.getTextSize();
                item.textColor = bean.getColor();
                item.textShadowColor = bean.getColor() <= Color.BLACK ? Color.WHITE : Color.BLACK;
                item.setTimer(mTimer);
                item.flags = mContext.mGlobalFlagValues;
                item.text = bean.getContent();
                item.isLive = bean.isLive();

                if (bean.getType() == BaseDanmaku.TYPE_SPECIAL) {
                    int duration = 5000;
                    item.duration = new Duration(duration);
                    mContext.mDanmakuFactory.fillTranslationData(item, 0,
                            0, 1080, 720, duration, 0, 1, 1);
                    mContext.mDanmakuFactory.fillAlphaData(item, (int) (0.3 * AlphaValue.MAX), (int) (1.0 * AlphaValue.MAX)
                            , duration / 2);
                }
            }
            synchronized (mLock) {
                danmakus.addItem(item);
            }
        }
        return danmakus;
    }
}
