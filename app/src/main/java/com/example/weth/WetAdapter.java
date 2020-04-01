package com.example.weth;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WetAdapter  extends RecyclerView.Adapter<WetAdapter.ViewHolder>{
    private static final String LOG_TAG = WetAdapter.class.getName();
    private ArrayList<Weather> mWeather;
    private OnItemClickListener mListener;
    public Context mContext;


    //constructor

    public WetAdapter(Context context,ArrayList<Weather> weathers){
        this.mWeather = weathers;
        this.mContext = context;
    }

    public void setmWeather(ArrayList<Weather> mWeather) {
        this.mWeather = mWeather;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View weatherView = inflater.inflate(layoutIdForListItem,parent,false);
        ViewHolder viewHolder = new ViewHolder(weatherView,mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Weather weather = mWeather.get(position);




        TextView description = holder.mDescription;
        description.setText(weather.getmWeatherDescription());

        TextView date = holder.mDate;
        date.setText(weather.getmDate());

        TextView highTempView = holder.mMaxTemp;
        String formattedHighTemp = WeatherUtils.formatTemp(weather.getmMaxTemperature());
        highTempView.setText(formattedHighTemp);

        TextView lowTempView = holder.mMinTemp;
        String formattedLowTemp = WeatherUtils.formatTemp(weather.getmMinTemperature());
        lowTempView.setText(formattedLowTemp);

        ImageView icon = holder.mIcon;
        icon.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));
        icon.setImageResource(WeatherUtils.getSmallIconResourceForWeatherCondition(weather.getmImageResourceId()));
        holder.parentLayout.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,MoreInfoActivity.class);
                i.putExtra("selectedWeather",weather);
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {

        return mWeather.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mIcon;
        public TextView mDate;
        public TextView mMaxTemp;
        public TextView mMinTemp;
        public TextView mDescription;
        OnItemClickListener onItemClickListener;
        ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parent_view);
            this.onItemClickListener = onItemClickListener;
            mIcon = (ImageView) itemView.findViewById(R.id.wet_icon);
            mDate = (TextView) itemView.findViewById(R.id.date);
            mDescription = (TextView) itemView.findViewById(R.id.description);
            mMaxTemp = (TextView) itemView.findViewById(R.id.maxtemp);
            mMinTemp = (TextView) itemView.findViewById(R.id.mintemp);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
            notifyDataSetChanged();


        }
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }


}
