package com.example.weth.ui;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.loader.content.AsyncTaskLoader;

import com.example.weth.MainActivity;
import com.example.weth.QueryUtils;
import com.example.weth.Weather;
import com.example.weth.WetAdapter;

import java.util.ArrayList;
import java.util.List;

public class WeatherLoader extends AsyncTaskLoader<List<Weather>> {

    private String mUrl;
    private static final String LOG_TAG = WeatherLoader.class.getName();



    public WeatherLoader(@NonNull Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    public void deliverResult(@Nullable List<Weather> data) {
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public ArrayList<Weather> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        ArrayList<Weather> weathers = (ArrayList<Weather>) QueryUtils.fetchWeatherData(mUrl);

        Log.e(LOG_TAG,weathers.toString());

        return weathers;
    }
}