package com.example.android.sunshine.data;

import android.database.Cursor;
import android.icu.util.Calendar;

import com.example.android.sunshine.DetailActivity;
import com.example.android.sunshine.MainActivity;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class WeatherUnit {

    public static List<WeatherUnit> parseCursorToWeatherUnits(Cursor cursor){
        if (!cursor.moveToFirst()) return null;

        List<WeatherUnit> weatherList = new ArrayList<>();
        while (cursor.moveToNext()) {
            long dateInMillis = cursor.getLong(DetailActivity.INDEX_WEATHER_DATE);
            double pressure = cursor.getDouble(DetailActivity.INDEX_WEATHER_PRESSURE);
            int humidity = cursor.getInt(DetailActivity.INDEX_WEATHER_HUMIDITY);
            double windSpeed = cursor.getDouble(DetailActivity.INDEX_WEATHER_WIND_SPEED);
            double windDirecrion = cursor.getDouble(DetailActivity.INDEX_WEATHER_DEGREES);
            double highInCelsius = cursor.getDouble(DetailActivity.INDEX_WEATHER_MAX_TEMP);
            double lowInCelsius = cursor.getDouble(DetailActivity.INDEX_WEATHER_MIN_TEMP);
            int weatherId = cursor.getInt(DetailActivity.INDEX_WEATHER_CONDITION_ID);
            WeatherUnit unit = new WeatherUnit(
                    dateInMillis,
                    pressure,
                    humidity,
                    windSpeed,
                    windDirecrion,
                    highInCelsius,
                    lowInCelsius,
                    weatherId);
            weatherList.add(unit);
        }

        return weatherList;
    }

    private long dateTimeMillis;
    private double pressure;
    private int humidity;
    private double windSpeed;
    private double windDirection;
    private double high;
    private double low;

    private int weatherId;

    private WeatherUnit(
            long dateTimeMillis,
            double pressure,
            int humidity,
            double windSpeed,
            double windDirection,
            double high,
            double low,
            int weatherId){
        this.dateTimeMillis = dateTimeMillis;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.high = high;
        this.low = low;
        this.weatherId = weatherId;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getPressure() {
        return pressure;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public long getDateTimeMillis() {
        return dateTimeMillis;
    }
}
