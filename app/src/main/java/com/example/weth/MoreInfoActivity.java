package com.example.weth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MoreInfoActivity extends AppCompatActivity {
    char degrees = '\u00B0';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);



        if (getIntent().hasExtra("selectedWeather")){
            Weather weather = getIntent().getParcelableExtra("selectedWeather");

                double maxTemperature = weather.getmMaxTemperature();
                double minTemperature = weather.getmMinTemperature();
               int imageRes = weather.getmImageResourceId();

                Integer humidity =weather.getmHumidity();
                int pressure = weather.getmPressure();
                String description = weather.getmWeatherDescription();

            Log.e("moreinfo",String.valueOf(imageRes));

                TextView desc =  (TextView) findViewById(R.id.description);
                desc.setText(description);

                ImageView icon  =  (ImageView) findViewById(R.id.wet_icon);
                icon.setImageResource(getIconResourceForWeatherCondition(imageRes));

                TextView humid =  (TextView) findViewById(R.id.humidity);
                humid.setText(String.valueOf(humidity)+ " %");

                TextView press = (TextView)  findViewById(R.id.pressure);
                press.setText(String.valueOf(pressure)+" hPa");

                TextView maxT = (TextView) findViewById(R.id.maxtemp);
                maxT.setText(formatTemp(maxTemperature));

                TextView minT =  (TextView) findViewById(R.id.mintemp);
                minT.setText(formatTemp(minTemperature));
            }
        }

    private String formatTemp(double temperature){
        double newTemperature = temperature - 273.15;
        DecimalFormat magnitudeFormat = new DecimalFormat("0");
        return magnitudeFormat.format(newTemperature)+degrees;

    }





    private int getIconResourceForWeatherCondition(int weatherId) {
        /*
         * Based on weather code data found at:
         * See http://bugs.openweathermap.org/projects/api/wiki/Weather_Condition_Codes
         */
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.ic_storm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.ic_light_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.ic_rain;
        } else if (weatherId == 511) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.ic_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.ic_fog;
        } else if (weatherId == 761 || weatherId == 781) {
            return R.drawable.ic_storm;
        } else if (weatherId == 800) {
            return R.drawable.ic_clear;
        } else if (weatherId == 801) {
            return R.drawable.ic_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.ic_cloudy;
        }
        return -1;
    }
}
