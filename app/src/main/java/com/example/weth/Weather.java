package com.example.weth;

public class Weather {

    private double mMaxTemperature;
    private double mMinTemperature;
    private long mTimeInMilliseconds;
    private int mImageResourceId;
    private String mWeatherDescription;
    private int mPressure;
    private int mHumidity;


    public Weather(double mMaxTemperature, double mMinTemperature, String mWeatherDescription, int mHumidity, int mPressure, int mImageResourceId,long mTimeInMilliseconds) {
        this.mMaxTemperature = mMaxTemperature;
        this.mMinTemperature = mMinTemperature;

        this.mTimeInMilliseconds = mTimeInMilliseconds;
        this.mWeatherDescription = mWeatherDescription;
        this.mHumidity = mHumidity;
        this.mPressure= mPressure;
        this.mImageResourceId = mImageResourceId;
    }

    public double getmMaxTemperature() {
        return mMaxTemperature;
    }

    public void setmMaxTemperature(double mMaxTemperature) {
        this.mMaxTemperature = mMaxTemperature;
    }

    public double getmMinTemperature() {
        return mMinTemperature;
    }

    public void setmMinTemperature(double mMinTemperature) {
        this.mMinTemperature = mMinTemperature;
    }

    public long getmTimeInMilliseconds() {
       return mTimeInMilliseconds;}



    public void setmTimeInMilliseconds(long mTimeInMilliseconds) {
        this.mTimeInMilliseconds = mTimeInMilliseconds;
    }

    public int getmImageResourceId(){
        return mImageResourceId;
    }

    public void setmImageResourceId(int mImageResourceId) {
        this.mImageResourceId = mImageResourceId;
    }

    public String getmWeatherDescription() {
        return mWeatherDescription;
    }

    public void setmWeatherDescription(String mWeatherDescription) {
        this.mWeatherDescription = mWeatherDescription;
    }

    public int getmPressure() {
        return mPressure;
    }

    public void setmPressure(int mPressure) {
        this.mPressure = mPressure;
    }

    public int getmHumidity() {
        return mHumidity;
    }

    public void setmHumidity(int mHumidity) {
        this.mHumidity = mHumidity;
    }
}
