package com.song.sunset.widget.neural;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author songmingwen
 * @description
 * @since 2019/4/9
 */
public class NeuralParams implements Parcelable {
    /**
     * 连线阀值，小于此值时点与点之间会有连线
     */
    private int mConnectionThreshold;

    /**
     * 点的数量
     */
    private int mElementAmount;

    /**
     * 点的基础速度
     */
    private int mSpeed;

    /**
     * 圆点半径
     */
    private float mRadius;

    /**
     * 点的颜色
     */
    private int mDotColor;

    /**
     * 线的颜色
     */
    private int mLineColor;

    public NeuralParams(int connectionThreshold, int elementAmount, int speed, float radius) {
        mConnectionThreshold = connectionThreshold;
        mElementAmount = elementAmount;
        mSpeed = speed;
        mRadius = radius;
    }

    public int getConnectionThreshold() {
        return mConnectionThreshold;
    }

    public void setConnectionThreshold(int connectionThreshold) {
        mConnectionThreshold = connectionThreshold;
    }

    public int getElementAmount() {
        return mElementAmount;
    }

    public void setElementAmount(int elementAmount) {
        mElementAmount = elementAmount;
    }

    public int getSpeed() {
        return mSpeed;
    }

    public void setSpeed(int speed) {
        mSpeed = speed;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float radius) {
        mRadius = radius;
    }

    public int getDotColor() {
        return mDotColor;
    }

    public void setDotColor(int dotColor) {
        mDotColor = dotColor;
    }

    public int getLineColor() {
        return mLineColor;
    }

    public void setLineColor(int lineColor) {
        mLineColor = lineColor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mConnectionThreshold);
        dest.writeInt(this.mElementAmount);
        dest.writeInt(this.mSpeed);
        dest.writeFloat(this.mRadius);
        dest.writeInt(this.mDotColor);
        dest.writeInt(this.mLineColor);
    }

    protected NeuralParams(Parcel in) {
        this.mConnectionThreshold = in.readInt();
        this.mElementAmount = in.readInt();
        this.mSpeed = in.readInt();
        this.mRadius = in.readFloat();
        this.mDotColor = in.readInt();
        this.mLineColor = in.readInt();
    }

    public static final Parcelable.Creator<NeuralParams> CREATOR = new Parcelable.Creator<NeuralParams>() {
        @Override
        public NeuralParams createFromParcel(Parcel source) {
            return new NeuralParams(source);
        }

        @Override
        public NeuralParams[] newArray(int size) {
            return new NeuralParams[size];
        }
    };
}
