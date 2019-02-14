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
import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.data.WeatherUnit;
import com.example.android.sunshine.utilities.SunshineWeatherUtils;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.DailyWeatherViewHolder> {


    List<WeatherUnit> mWeatherList;
    Context mContext;

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
        return mWeatherList.size();
    }

    public void setList(DayWeatherData dayWeatherData, Context context) {
        mContext = context;
        mWeatherList = dayWeatherData.getmDayWeatherList();
        notifyDataSetChanged();
    }

    class DailyWeatherViewHolder extends RecyclerView.ViewHolder{

        final TextView time;
        final ImageView weatherIcon;
        final TextView weatherDescription;
        final TextView highLowTemp;;


        public DailyWeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
            weatherIcon = (ImageView) itemView.findViewById(R.id.weather_icon);
            weatherDescription = (TextView) itemView.findViewById(R.id.weather_description);
            highLowTemp = (TextView) itemView.findViewById(R.id.high_slash_low_temperature);
        }
    }

}
