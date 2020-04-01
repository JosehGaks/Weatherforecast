package com.example.weth;

import android.os.Parcel;
import android.os.Parcelable;

public class Weather implements Parcelable {

    private double mMaxTemperature;
    private double mMinTemperature;
    private String mDate;
    private int mImageResourceId;
    private String mWeatherDescription;
    private int mPressure;
    private int mHumidity;


    public Weather(double mMaxTemperature, double mMinTemperature, String mWeatherDescription, int mHumidity, int mPressure, int mImageResourceId,String mDate) {
        this.mMaxTemperature = mMaxTemperature;
        this.mMinTemperature = mMinTemperature;

        this.mDate = mDate;
        this.mWeatherDescription = mWeatherDescription;
        this.mHumidity = mHumidity;
        this.mPressure= mPressure;
        this.mImageResourceId = mImageResourceId;
    }

    protected Weather(Parcel in) {
        mMaxTemperature = in.readDouble();
        mMinTemperature = in.readDouble();
        mDate = in.readString();
        mImageResourceId = in.readInt();
        mWeatherDescription = in.readString();
        mPressure = in.readInt();
        mHumidity = in.readInt();
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

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

    public String getmDate() {
       return mDate;}



    public void setmDate(String mDate) {
        this.mDate = mDate;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mMaxTemperature);
        dest.writeDouble(mMinTemperature);
        dest.writeString(mDate);
        dest.writeInt(mImageResourceId);
        dest.writeString(mWeatherDescription);
        dest.writeInt(mPressure);
        dest.writeInt(mHumidity);
    }
}
