package com.example.weth;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.weth.QueryUtils;
import com.example.weth.R;
import com.example.weth.Weather;
import com.example.weth.WeatherAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WeatherAdapter mAdapter;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public static final String LOG_TAG = MainActivity.class.getName();

    private static final String WEATHER_REQUEST_URL = "http://api.openweathermap.org/data/2.5/forecast?q=Nairobi&APPID=5d806c0e06aa6a4bd5794917a9fe4690";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                } else {

                }
            }
        };

        ListView weatherListView = (ListView) findViewById(R.id.list);

        mAdapter = new WeatherAdapter(this,new ArrayList<Weather>());

        weatherListView.setAdapter(mAdapter);

        weatherListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Weather currentWeather = mAdapter.getItem(position);

                double maxTemp =currentWeather.getmMaxTemperature();
                double minTemp = currentWeather.getmMinTemperature();
                String description = currentWeather.getmWeatherDescription();
                int humidity = currentWeather.getmHumidity();
                int pressure = currentWeather.getmPressure();
               int imageResourceId = currentWeather.getmImageResourceId();

                Bundle extras = new Bundle();

                extras.putDouble("maxTemp",maxTemp);
                extras.putDouble("minTemp",minTemp);
                extras.putString("description",description);
                extras.putInt("humidity",humidity);
                extras.putInt("pressure",pressure);
                extras.putInt("imageResource",imageResourceId);

                Intent i = new Intent(MainActivity.this,MoreInfoActivity.class);
                i.putExtras(extras);
                startActivity(i);

            }
        });

        FetchWeatherTask task = new FetchWeatherTask();
        task.execute(WEATHER_REQUEST_URL);
    }


    public class FetchWeatherTask extends AsyncTask<String,Void,List<Weather>> {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected List<Weather> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null){
                return null;
            }
            List<Weather> result = QueryUtils.fetchWeatherData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Weather> data) {
            mAdapter.clear();
            if (data != null && !data.isEmpty()){
                mAdapter.addAll(data);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if (id == R.id.action_logout){
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

}
