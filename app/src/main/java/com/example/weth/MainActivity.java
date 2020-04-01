package com.example.weth;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weth.ui.WeatherLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private WetAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mLoadingIndicator;

    private ArrayList<Weather> weathers = new ArrayList<Weather>();
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

        mLoadingIndicator = (ProgressBar) findViewById(R.id.loadingIndicator);

        showView();

        WeatherAsyncTask task = new WeatherAsyncTask();
        task.execute(WEATHER_REQUEST_URL);



    }

    public void showView(){
        mAdapter = new WetAdapter(this,weathers);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvContacts);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

    }

//    @Override
//    public void onItemClick(int position) {
//
//    }


    private class WeatherAsyncTask extends AsyncTask<String,Void,List<Weather>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected List<Weather> doInBackground(String... urls) {
            List<Weather> result = QueryUtils.fetchWeatherData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Weather> weathers) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            mAdapter.setmWeather(null);

            if (weathers != null && !weathers.isEmpty()){
                mAdapter.setmWeather((ArrayList<Weather>) weathers);
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
