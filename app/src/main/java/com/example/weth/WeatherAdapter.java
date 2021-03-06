package com.example.weth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeatherAdapter extends ArrayAdapter<Weather> {
    public WeatherAdapter(@NonNull Context context, @NonNull List<Weather> weathers) {
        super(context, 0, weathers);
    }




   private int getSmallIconResourceForWeatherCondition(int weatherId) {
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




        TextView highTempView = (TextView) listItemView.findViewById(R.id.maxtemp);
        String formattedHighTemp = WeatherUtils.formatTemp(currentWeather.getmMaxTemperature());
        highTempView.setText(formattedHighTemp);

        TextView lowTempView = (TextView) listItemView.findViewById(R.id.mintemp);
        String formattedLowTemp = WeatherUtils.formatTemp(currentWeather.getmMinTemperature());
        lowTempView.setText(formattedLowTemp);

        TextView weatherDescription = (TextView) listItemView.findViewById(R.id.description);
        String description = currentWeather.getmWeatherDescription();
        weatherDescription.setText(description);

        ImageView icon = (ImageView) listItemView.findViewById(R.id.wet_icon);
        icon.setImageResource(getSmallIconResourceForWeatherCondition(currentWeather.getmImageResourceId()));

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        //GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
       // int magnitudeColor = getMagnitudeColor(currentEarthquake.getmMagnitude());



        //TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.location_offset);


        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(currentWeather.getmDate());

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
