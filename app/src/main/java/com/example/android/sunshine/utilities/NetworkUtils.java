/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.android.sunshine.data.SunshinePreferences;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the weather servers.
 */
public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static boolean isDataAvalable = false;


    private static final String OPEN_WEATHER_MAP_URL =
            "https://api.openweathermap.org/data/2.5";

    private static final String WEATHER = "weather";
    private static final String FORECAST = "forecast";

    private static final String FORECAST_BASE_URL = Uri.parse(OPEN_WEATHER_MAP_URL).buildUpon()
            .appendPath(FORECAST).toString();

    private static final String WEATHER_BASE_URL = Uri.parse(OPEN_WEATHER_MAP_URL).buildUpon()
            .appendPath(WEATHER).toString();


    private static final String API_KEY_PARAM = "APPID";
    private static final String API_KEY = "<My key>";
    /* The format we want our API to return */
    private static final String format = "json";
    /* The units we want our API to return */
    private static final String units = "metric";
    /* The query parameter allows us to provide a location string to the API */
    private static final String QUERY_PARAM = "q";

    private static final String LAT_PARAM = "lat";
    private static final String LON_PARAM = "lon";

    /* The format parameter allows us to designate whether we want JSON or XML from our API */
    private static final String FORMAT_PARAM = "mode";
    /* The units parameter allows us to designate whether we want metric units or imperial units */
    private static final String UNITS_PARAM = "units";




    public static URL getUrl(Context context) {
        if (SunshinePreferences.isLocationLatLonAvailable(context)) {
            double[] preferredCoordinates = SunshinePreferences.getLocationCoordinates(context);
            double latitude = preferredCoordinates[0];
            double longitude = preferredCoordinates[1];
            return buildUrlWithLatitudeLongitude(latitude, longitude);
        } else {
            String locationQuery = SunshinePreferences.getPreferredWeatherLocation(context);
            return buildUrlWithLocationQuery(locationQuery);
        }
    }

    /**
     * Builds the URL used to talk to the weather server using latitude and longitude of a
     * location.
     *
     * @param latitude  The latitude of the location
     * @param longitude The longitude of the location
     * @return The Url to use to query the weather server.
     */
    private static URL buildUrlWithLatitudeLongitude(Double latitude, Double longitude) {
        Uri weatherQueryUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .appendQueryParameter(LAT_PARAM, String.valueOf(latitude))
                .appendQueryParameter(LON_PARAM, String.valueOf(longitude))
                .appendQueryParameter(FORMAT_PARAM, format)
                .appendQueryParameter(UNITS_PARAM, units)
                .build();

        try {
            URL weatherQueryUrl = new URL(weatherQueryUri.toString());
            Log.v(TAG, "URL: " + weatherQueryUrl);
            return weatherQueryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Builds the URL used to talk to the weather server using a location. This location is based
     * on the query capabilities of the weather provider that we are using.
     *
     * @param locationQuery The location that will be queried for.
     * @return The URL to use to query the weather server.
     */
    private static URL buildUrlWithLocationQuery(String locationQuery) {
        Uri weatherQueryUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .appendQueryParameter(QUERY_PARAM, locationQuery)
                .appendQueryParameter(FORMAT_PARAM, format)
                .appendQueryParameter(UNITS_PARAM, units)
                .build();

        try {
            URL weatherQueryUrl = new URL(weatherQueryUri.toString());
            Log.v(TAG, "URL: " + weatherQueryUrl);
            return weatherQueryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response, null if no response
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
                isDataAvalable = true;
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }

    public static boolean checkIfDataAvalable(){
        return isDataAvalable;
    }
}
