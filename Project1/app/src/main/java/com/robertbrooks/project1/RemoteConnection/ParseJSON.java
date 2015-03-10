/*Robert Brooks
* ParseJSON.java*/

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
            JSONObject mainObj = data.getJSONObject("main");
            // create JSON Array from data JSON object
            JSONArray weatherArray = data.getJSONArray("weather");
            // Create Weather class arrayList
            List<Weather> weatherList = new ArrayList<>();

            JSONObject JSONWeather = weatherArray.getJSONObject(0);
            Weather weather = new Weather();

            int temp = mainObj.getInt("temp");
            double fConv = Math.round((temp - 273.15) * 1.8000 + 32.00);
            int tempInt = (int)(fConv);

            String tempString = Integer.toString(tempInt) + "˚F";
            weather.setTemp(tempString);
            weather.setCurrentCond(JSONWeather.getString("description"));
            weather.setCityName(data.getString("name"));
            weatherList.add(weather);

            return weatherList;
        } catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
