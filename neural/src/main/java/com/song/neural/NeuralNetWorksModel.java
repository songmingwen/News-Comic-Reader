package com.song.neural;

import android.util.Log;

import java.util.ArrayList;

/**
 * @author songmingwen
 * @description
 * @since 2019/4/3
 */
class NeuralNetWorksModel {

    private static final String TAG = NeuralNetWorksModel.class.getSimpleName();

    private ArrayList<Dot> mDots = new ArrayList<>();

    private ArrayList<Line> mLines = new ArrayList<>();

    public NeuralNetWorksModel() {
    }

    ArrayList<Dot> createDotsList(int amount, float width, float height, float dD, float radius) {
        mDots.clear();
        for (int i = 0; i < amount; i++) {
            mDots.add(new Dot().obtain(width, height, dD, radius));
        }
        return mDots;
    }

    ArrayList<Line> getLinesList() {
        return mLines;
    }

    ArrayList<Dot> getDotsList() {
        return mDots;
    }

    void addDotToList(float width, float height, float x, float y, float dX, float dY, float radius) {
        mDots.add(new Dot().obtain(width, height, x, y, dX, dY, radius));
    }

    /**
     * 刷新下一个点及线的位置
     * @param connection_threshold 连线阈值
     */
    void next(float connection_threshold) {

        for (Dot dot : mDots) {
            dot.next();
        }

        mLines.clear();

        for (int i = 0; i < mDots.size() - 1; i++) {
            for (int j = i + 1; j < mDots.size(); j++) {
                Dot dot = mDots.get(i);
                Dot dot2 = mDots.get(j);
                if (Math.abs(dot.getX() - dot2.getX()) >= connection_threshold || Math.abs(dot.getY() - dot2.getY()) >= connection_threshold) {
                    continue;
                }
                double length = Math.sqrt(Math.pow(dot.getX() - dot2.getX(), 2) + Math.pow(dot.getY() - dot2.getY(), 2));
                if (length <= connection_threshold) {
                    Line line = new Line(dot.getX(), dot.getY(), dot2.getX(), dot2.getY(), connection_threshold);
                    mLines.add(line);
                    dot.addLineAmount();
                    dot2.addLineAmount();
                }
            }
        }
    }

    void clear() {
        mDots.clear();
    }
}
