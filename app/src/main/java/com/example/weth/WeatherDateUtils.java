package com.example.weth;

import android.content.Context;
import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class WeatherDateUtils {
    public static final long SECOND_IN_MILLIS = 1000;
    public static final long MINUTE_IN_MILLIS = SECOND_IN_MILLIS * 60;
    public static final long HOUR_IN_MILLIS = MINUTE_IN_MILLIS * 60;
    public static final long DAY_IN_MILLIS = HOUR_IN_MILLIS * 24;
    public Context context;

    public static long getDayNumber(long date){
        TimeZone tz = TimeZone.getDefault();
        long gmtOffset = tz.getOffset(date);
        return (date + gmtOffset)/DAY_IN_MILLIS;
    }

    public static long normalizeDate(long date){
        long retValNew = date/DAY_IN_MILLIS * DAY_IN_MILLIS;
        return retValNew;
    }

    public static long getLocalDateFromUTC(long utcDate){
        TimeZone tz = TimeZone.getDefault();
        long gmtOffset = tz.getOffset(utcDate);
        return utcDate - gmtOffset;
    }

    public static long getUTCDateFromLocal(Long localDate){
        TimeZone tz = TimeZone.getDefault();
        long gmtOffset = tz.getOffset(localDate);
        return localDate + gmtOffset;
    }

    public static String getFriendlyDateString(Context context,long dateInMillis,boolean showFullDate){

        long localDate = getLocalDateFromUTC(dateInMillis);
        long dayNumber = getDayNumber(localDate);
        long currentDayNumber = getDayNumber(System.currentTimeMillis());

        if (dayNumber == currentDayNumber || showFullDate){

            String dayName = getDayName(context,localDate);
            String readableDate = getReadableDateString(context,localDate);

            if(dayNumber - currentDayNumber < 2){
                String localizedDayName = new SimpleDateFormat("EEEE").format(localDate);
                return readableDate.replace(localizedDayName,dayName);
            }else {
                return readableDate;
            }
        }else if (dayNumber < currentDayNumber + 7){
            return getDayName(context,localDate);

        }else{
            int flags = DateUtils.FORMAT_SHOW_DATE
                    | DateUtils.FORMAT_NO_YEAR
                    | DateUtils.FORMAT_ABBREV_ALL
                    | DateUtils.FORMAT_SHOW_WEEKDAY;

            return DateUtils.formatDateTime(context, localDate, flags);
        }
    }


    private static  String getReadableDateString(Context context, long timeInMillis){
        int flags = DateUtils.FORMAT_SHOW_DATE
                | DateUtils.FORMAT_NO_YEAR
                | DateUtils.FORMAT_SHOW_WEEKDAY;

        return DateUtils.formatDateTime(context,timeInMillis,flags);
    }


    private static String getDayName(Context context, long dateInMillis) {
        /*
         * If the date is today, return the localized version of "Today" instead of the actual
         * day name.
         */
        long dayNumber = getDayNumber(dateInMillis);
        long currentDayNumber = getDayNumber(System.currentTimeMillis());
        if (dayNumber == currentDayNumber){
            return "Today";
        } else if (dayNumber == currentDayNumber + 1){
            return "Tomorrow";
        }else{
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
            return dayFormat.format(dateInMillis);
        }

    }

}
