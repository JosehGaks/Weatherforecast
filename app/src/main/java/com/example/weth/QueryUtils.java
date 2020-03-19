package com.example.weth;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private static final String LOG_TAG = com.example.weth.MainActivity.class.getName();;

    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    private static String makeHttpRequest(URL url)throws IOException {
        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection  = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else{
                Log.e(LOG_TAG,"Error response code: "+ urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG,"Problem retrieving the earthquake JSON results.", e);
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line =reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<Weather> extractFeatureFromJson(String weatherJson){
        if (TextUtils.isEmpty(weatherJson)){
            return null;
        }
        List<Weather> weathers = new ArrayList<>();

        long localDate = System.currentTimeMillis();
        long utcDate = WeatherDateUtils.getUTCDateFromLocal(localDate);
        long startDay = WeatherDateUtils.normalizeDate(utcDate);

        try {
            JSONObject baseJsonResponse = new JSONObject(weatherJson);
            JSONArray weatherArray = baseJsonResponse.getJSONArray("list");
            for (int i = 0; i < weatherArray.length(); i++){


                JSONObject currentWeather = weatherArray.getJSONObject(i);

                //long time = currentWeather.getLong("dt");
                long time = currentWeather.getLong("dt");

                JSONObject details = currentWeather.getJSONObject("main");
                double temp_min = details.getDouble("temp_min");
                double temp_max = details.getDouble("temp_max");
                int pressure = details.getInt("pressure");
                int humidity = details.getInt("humidity");



                JSONObject weatherObject = currentWeather.getJSONArray("weather").getJSONObject(0);

                String description  = weatherObject.getString("description");
                String id = weatherObject.getString("id");
               String icon = weatherObject.getString("icon");

               int newId = Integer.parseInt(id);


                Weather weather = new Weather(temp_max,temp_min,description,humidity,pressure,newId,time);
                weathers.add(weather);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weathers;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<Weather> fetchWeatherData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Weather}s
        List<Weather> weathers = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Weather}s
        return weathers;
    }
}
