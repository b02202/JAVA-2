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

    public static List<Weather> parse(String content)
    {
        final String TAG = "DEV-4";
        try{
            // get complete JSON object from Weather Map api
            JSONObject data = new JSONObject(content).getJSONObject("data");
            // create JSON Array from data JSON object
            JSONArray children = data.getJSONArray("children");
            // Create Weather class arrayList
            List<Weather> weatherList = new ArrayList<>();
            // Loop through JSON Array
            for (int i = 0; i < children.length(); i++) {
                // Create JSON object from "data" object in children JSON Array
                JSONObject obj = children.getJSONObject(i).getJSONObject("data");
                // create Weather instance
                Weather weather = new Weather();

                Log.i(TAG, "OBJ = " + obj);

                // Populate Weather data
                weather.setTemp(obj.getString("title"));
                weather.setForecast(obj.getString("author"));
                weather.setSomething(obj.getString("domain"));

                // add to weatherList ArrayList
                weatherList.add(weather);
            }
            return weatherList;
        } catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
