package com.example.android.sunshine.data;

public class WeatherUnit {
    private long dateTimeMillis;
    private double pressure;
    private int humidity;
    private double windSpeed;
    private double windDirection;
    private double high;
    private double low;

    private int weatherId;

    public WeatherUnit(
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
