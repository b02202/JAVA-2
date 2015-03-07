package com.robertbrooks.project1.RemoteConnection;

import android.util.Log;

import com.robertbrooks.project1.CustomData.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bob on 3/3/2015.
 */
public class ParseJSON {

    public static List<Weather> parse(String content) {

        final String TAG = "ParseJSON";
        try{
            // get complete JSON object from Weather Source
            JSONObject data = new JSONObject(content);
            // create JSON Array from data JSON object
            JSONArray weatherArray = data.getJSONArray("weather");
            // Create Weather class arrayList
            List<Weather> weatherList = new ArrayList<>();

            JSONObject JSONWeather = weatherArray.getJSONObject(0);
            Weather weather = new Weather();
            weather.setTemp(JSONWeather.getString("description"));
            Log.i(TAG, "OBJ = " + JSONWeather.get("description"));
            weatherList.add(weather);

            return weatherList;
        } catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
