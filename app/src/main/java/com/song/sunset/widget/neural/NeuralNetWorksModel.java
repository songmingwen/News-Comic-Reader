package com.song.sunset.widget.neural;

import java.util.ArrayList;

/**
 * @author songmingwen
 * @description
 * @since 2019/4/3
 */
class NeuralNetWorksModel {

    private ArrayList<Dot> mDots = new ArrayList<>();

    private ArrayList<Line> mLines = new ArrayList<>();

    private static final NeuralNetWorksModel ourInstance = new NeuralNetWorksModel();

    static NeuralNetWorksModel getInstance() {
        return ourInstance;
    }

    private NeuralNetWorksModel() {
    }

    ArrayList<Dot> getDotList(int amount, float width, float height, float dD, float radius) {
        mDots.clear();
        for (int i = 0; i < amount; i++) {
            mDots.add(obtain(width, height, dD, radius));
        }
        return mDots;
    }

    ArrayList<Line> getLinesList() {
        return mLines;
    }

    private Dot obtain(float width, float height, float dD, float radius) {
        return new Dot().obtain(width, height, dD, radius);
    }

    void next(float connection_threshold) {

        for (Dot dot : mDots) {
            dot.next();
        }

        mLines.clear();

        for (int i = 0; i < mDots.size() - 1; i++) {
            for (int j = i + 1; j < mDots.size(); j++) {
                Dot dot = mDots.get(i);
                Dot dot2 = mDots.get(j);
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
