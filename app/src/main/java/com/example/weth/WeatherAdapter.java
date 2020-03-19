package com.example.weth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeatherAdapter extends ArrayAdapter<Weather> {
    public WeatherAdapter(@NonNull Context context, @NonNull List<Weather> weathers) {
        super(context, 0, weathers);
    }
    char degrees = '\u00B0';

    private String formatTemp(double temperature){
        double newTemperature = temperature - 273.15;
        DecimalFormat magnitudeFormat = new DecimalFormat("0");
        return magnitudeFormat.format(newTemperature)+degrees;

    }


   private int getSmallIconResourceForWeatherCondition(int weatherId) {
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




    public View getView (int position, View convertView, ViewGroup parent){

        View listItemView = convertView;

        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item,parent,false);
        }
        Weather currentWeather = getItem(position);




        TextView highTempView = (TextView) listItemView.findViewById(R.id.high_temperature);
        String formattedHighTemp =formatTemp(currentWeather.getmMaxTemperature());
        highTempView.setText(formattedHighTemp);

        TextView lowTempView = (TextView) listItemView.findViewById(R.id.low_temperature);
        String formattedLowTemp =formatTemp(currentWeather.getmMinTemperature());
        lowTempView.setText(formattedLowTemp);

        TextView weatherDescription = (TextView) listItemView.findViewById(R.id.weather_description);
        String description = currentWeather.getmWeatherDescription();
        weatherDescription.setText(description);

        ImageView icon = (ImageView) listItemView.findViewById(R.id.weather_icon);
        icon.setImageResource(getSmallIconResourceForWeatherCondition(currentWeather.getmImageResourceId()));

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        //GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
       // int magnitudeColor = getMagnitudeColor(currentEarthquake.getmMagnitude());

        // Set the color on the magnitude circle
        //magnitudeCircle.setColor(magnitudeColor);

        //TextView LowTempView = (TextView) listItemView.findViewById(R.id.low_temperature);
        //primaryLocationView.setText(primaryLocation);

        //TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.location_offset);
        //locationOffsetView.setText(locationOffset);

        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(currentWeather.getmTimeInMilliseconds());

       TextView dateView = (TextView) listItemView.findViewById(R.id.date);
       String formattedDate = formatDate(dateObject);
       dateView.setText(formattedDate);
//
//        // Find the TextView with view ID time
//        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
//        // Format the time string (i.e. "4:30PM")
//        String formattedTime = formatTime(dateObject);
//        // Display the time of the current earthquake in that TextView
//        timeView.setText(formattedTime);

        return listItemView;
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }
}
