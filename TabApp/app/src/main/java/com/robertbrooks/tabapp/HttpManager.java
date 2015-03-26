/*HttpManager.java
* Robert Brooks*/
package com.robertbrooks.tabapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.robertbrooks.tabapp.CustomData.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bob on 3/24/2015.
 */
public class HttpManager {


    private Context _context;

    public HttpManager(Context context) {
        this._context = context;
    }

    // Network Check
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {

            return false;
        }
    }

    // Manage HTTP Connection
    public static String getData(String urlString) {
        // Create buffered reader
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            // Create HttpURLConnection with url
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            // Create String Builder
            StringBuilder sBuilder = new StringBuilder();
            // Set Buffered Reader to read input stream from HttpURLConnection
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String dataLine;
            // Loop through input Stream and append String Builder
            while ((dataLine = reader.readLine()) != null) {
                sBuilder.append(dataLine + "\n");
            }
            return sBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            // close Buffered Reader
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

    // JSON Parse
    public static List<Weather> parse(String content) {


        final String TAG = "ParseJSON";
        try {
            // get complete JSON object from Weather Source
            JSONObject baseOBJ = new JSONObject(content);
            // get "data" object
            JSONObject data = baseOBJ.getJSONObject("current_observation");
            // get "current condition array from "data"
            // Create Weather class arrayList
            List<Weather> weatherList = new ArrayList<>();
            // get first object from "current condition" array
            Weather weather = new Weather();
            weather.setObservationTime(data.getString("observation_time"));
            weather.setCurrentWeather(data.getString("weather"));
            weather.setTemperature(data.getString("temperature_string"));
            weather.setRelativeHumidity(data.getString("relative_humidity"));
            weather.setWind(data.getString("wind_string"));
            weather.setDewpoint(data.getString("dewpoint_string"));
            weather.setFeelsLike(data.getString("feelslike_string"));
            weather.setVisibility(data.getString("visibility_mi") + " miles");
            weather.setUV(data.getString("UV"));
            weatherList.add(weather);
            return weatherList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Weather> weekParse(String content) {


        final String TAG = "ParseJSONWeek";
        try {
            // get complete JSON object from Weather Source
            JSONObject baseOBJ = new JSONObject(content);
            // get "forecast" object
            JSONObject data = baseOBJ.getJSONObject("forecast");
            // get forecast object array from "data"
            JSONObject forecast = data.getJSONObject("simpleforecast");
            // get forecastday array
            JSONArray forecastDay = forecast.getJSONArray("forecastday");

            // Create Weather class arrayList
            List<Weather> weatherList = new ArrayList<>();
            // get first object from "current condition" array


            Weather weather = new Weather();

            StringBuilder sb = new StringBuilder();
            //Loop through forecastDay
            for (int i = 0; i < 7; i++) {
                JSONObject day = forecastDay.getJSONObject(i).getJSONObject("date");
                String test = day.getString("weekday") + " -  "
                        + forecastDay.getJSONObject(i).getString("conditions") + "\n"
                        + "High: " + forecastDay.getJSONObject(i).getJSONObject("high").getString("fahrenheit") + "°F "
                        + "| Low: " + forecastDay.getJSONObject(i).getJSONObject("low").getString("fahrenheit") + "°F\n\n";
                sb.append(test);
                Log.d(TAG, "Test String: " + test);
            }

           String sbString = sb.toString();
            Log.d(TAG, "SBSTRING: " + sbString);
            weather.setDay1(sbString);
            weatherList.add(weather);
            return weatherList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    // Hourly JSON Parse
    public static List<Weather> hourlyParse(String content) {


        final String TAG = "ParseJSONWeek";
        try {
            // get complete JSON object from Weather Source
            JSONObject baseOBJ = new JSONObject(content);
            // get "hourly_forecast" array
            JSONArray hourlyForecast = baseOBJ.getJSONArray("hourly_forecast");
            // Create Weather class arrayList
            List<Weather> weatherList = new ArrayList<>();
            Weather weather = new Weather();
            StringBuilder sb = new StringBuilder();
            //Loop through forecastDay
            for (int i = 0; i < hourlyForecast.length(); i++) {
                String test = hourlyForecast.getJSONObject(i).getJSONObject("FCTTIME").getString("civil") + " -  "
                        + hourlyForecast.getJSONObject(i).getString("condition") + "\n"
                        + "Temperature: " + hourlyForecast.getJSONObject(i).getJSONObject("temp").getString("english") + "°F\n\n ";
                sb.append(test);
                Log.d(TAG, "Test String: " + test);
            }

            String sbString = sb.toString();
            Log.d(TAG, "SBSTRING: " + sbString);
            weather.setDay2(sbString);
            weatherList.add(weather);
            return weatherList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

}