package com.example.android.sunshine.data;

import android.util.Log;

import com.example.android.sunshine.utilities.SunshineDateUtils;

import java.util.ArrayList;
import java.util.List;

public class DayWeatherData {


    private List<WeatherUnit> mDayWeatherList;
    private static boolean today;

    public static List<DayWeatherData> initializeWeatherList(List<WeatherUnit> weatherList){
        today = true;
        List<DayWeatherData> summaryWeatherList = new ArrayList<>();
        if(weatherList.size() < 1) return null;
        long date = weatherList.get(0).getDateTimeMillis();
        long normilizedDate = SunshineDateUtils.normalizeDate(date);
        List<WeatherUnit> list = new ArrayList<>();

        for(WeatherUnit unit: weatherList){
            long unitDate = unit.getDateTimeMillis();
            long unitNormilizedDate = SunshineDateUtils.normalizeDate(unitDate);
            if(unitNormilizedDate == normilizedDate + SunshineDateUtils.DAY_IN_MILLIS){
                DayWeatherData daySummary = getSummaryData(list, normilizedDate);
                summaryWeatherList.add(daySummary);
                normilizedDate = unitNormilizedDate;
                list = new ArrayList<>();
                today = false;
            }else if(unitNormilizedDate == normilizedDate){
                list.add(unit);
            }
        }
        summaryWeatherList.add(getSummaryData(list, normilizedDate));
        Log.d("DayWeatherDataTag", "WeatherUnit list size "  + list.size());
        return summaryWeatherList;
    }

    private static DayWeatherData getSummaryData(List<WeatherUnit> list, long dayInMillis) {
        if(list == null || list.isEmpty()) return null;

        int closestWeatherId = list.get(0).getWeatherId();
        double pressureSummary = 0;
        int humiditySummary = 0;
        double maxWindSpeed = list.get(0).getWindSpeed();
        double closestWindDirection = list.get(0).getWindDirection();
        double maxHigh = list.get(0).getHigh();
        double minLow = list.get(0).getLow();

        for(WeatherUnit item: list){
            pressureSummary += item.getPressure();
            humiditySummary += item.getHumidity();
            if(item.getWindSpeed() > maxWindSpeed){
                maxWindSpeed = item.getWindSpeed();
            }
            if (item.getHigh() > maxHigh){
                maxHigh = item.getHigh();
            }
            if (item.getLow() < minLow){
                minLow = item.getLow();
            }
        }

        if(!today) {list = null;}

        return new DayWeatherData(
                dayInMillis,
                pressureSummary,
                humiditySummary,
                maxWindSpeed,
                closestWindDirection,
                maxHigh, minLow,
                closestWeatherId,
                list);
    }


    private long dayInMillis;
    private double pressureSummary;
    private int humiditySummary;
    private double maxWindSpeed;
    private double closestWindDirection;
    private double maxHigh;
    private double minLow;

    private int closestWeatherId;

    private DayWeatherData(
            long dayInMillis,
            double pressureSummary,
            int humiditySummary,
            double maxWindSpeed,
            double closestWindDirection,
            double maxHigh,
            double minLow,
            int closestWeatherId,
            List<WeatherUnit> dayWeatherList){

        this.dayInMillis = dayInMillis;
        this.pressureSummary = pressureSummary;
        this.humiditySummary = humiditySummary;
        this.maxWindSpeed = maxWindSpeed;
        this.closestWindDirection = closestWindDirection;
        this.maxHigh = maxHigh;
        this.minLow = minLow;
        this.closestWeatherId = closestWeatherId;
        this.mDayWeatherList = dayWeatherList;

    }

    public double getClosestWindDirection() {
        return closestWindDirection;
    }

    public double getMaxHigh() {
        return maxHigh;
    }

    public double getMaxWindSpeed() {
        return maxWindSpeed;
    }

    public double getMinLow() {
        return minLow;
    }

    public double getPressureSummary() {
        return pressureSummary;
    }

    public int getClosestWeatherId() {
        return closestWeatherId;
    }

    public int getHumiditySummary() {
        return humiditySummary;
    }

    public List<WeatherUnit> getmDayWeatherList() {
        return mDayWeatherList;
    }

    public long getDayInMillis() {
        return dayInMillis;
    }
}
