package com.example.android.sunshine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.sunshine.data.DayWeatherData;
import com.example.android.sunshine.data.WeatherUnit;
import com.example.android.sunshine.utilities.SunshineWeatherUtils;

import java.text.SimpleDateFormat;
import java.util.List;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.DailyWeatherViewHolder> {


    List<WeatherUnit> mWeatherList;
    Context mContext;
    DailyWeatherOnClickHandler mClickHandler;
    private ImageView mSelectedFrameView = null;


    @NonNull
    @Override
    public DailyWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hour_weather_list_item, viewGroup, false);
        return new DailyWeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyWeatherViewHolder dailyWeatherViewHolder, int position) {
        WeatherUnit weather = mWeatherList.get(position);

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        long dateInMillis = weather.getDateTimeMillis();
        String time = format.format(dateInMillis);
        dailyWeatherViewHolder.time.setText(time);

        int weatherId = weather.getWeatherId();
        int resId = SunshineWeatherUtils.getSmallArtResourceIdForWeatherCondition(weatherId);
        String weatherDescription = SunshineWeatherUtils.getStringForWeatherCondition(mContext, weatherId);
        dailyWeatherViewHolder.weatherIcon.setImageResource(resId);
        dailyWeatherViewHolder.weatherDescription.setText(weatherDescription);

        String highLowTemp = SunshineWeatherUtils.formatHighLows(mContext, weather.getHigh(), weather.getLow());
        dailyWeatherViewHolder.highLowTemp.setText(highLowTemp);
    }

    @Override
    public int getItemCount() {
        if(mWeatherList != null) {
            return mWeatherList.size();
        }
        return -1;
    }

    public void setList(DayWeatherData dayWeatherData, Context context) {
        mContext = context;
        mWeatherList = dayWeatherData.getmDayWeatherList();
        notifyDataSetChanged();
    }

    public void setList(DayWeatherData dayWeatherData, Context context, DailyWeatherOnClickHandler handler) {
        mContext = context;
        mWeatherList = dayWeatherData.getmDayWeatherList();
        mClickHandler = handler;
        notifyDataSetChanged();
    }

    public interface DailyWeatherOnClickHandler{
        void onClick(WeatherUnit unit);
    }

    class DailyWeatherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView time;
        final ImageView weatherIcon;
        final TextView weatherDescription;
        final TextView highLowTemp;
        final ImageView frameView;


        public DailyWeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
            weatherIcon = (ImageView) itemView.findViewById(R.id.weather_icon);
            weatherDescription = (TextView) itemView.findViewById(R.id.weather_description);
            highLowTemp = (TextView) itemView.findViewById(R.id.high_slash_low_temperature);
            frameView = (ImageView) itemView.findViewById(R.id.frame_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mClickHandler == null) return;
            int position = getAdapterPosition();
            if(mSelectedFrameView != null) {
                mSelectedFrameView.setBackground(mContext.getDrawable(R.drawable.frame));
            }
            mSelectedFrameView = frameView;
            mSelectedFrameView.setBackground(mContext.getDrawable(R.drawable.frame_selected));
            WeatherUnit weatherUnit = mWeatherList.get(position);
            mClickHandler.onClick(weatherUnit);
        }
    }

}
