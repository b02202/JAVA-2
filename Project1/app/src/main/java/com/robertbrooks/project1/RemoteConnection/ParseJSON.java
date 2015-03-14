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
            JSONObject baseOBJ = new JSONObject(content);
            // get "data" object
            JSONObject data = baseOBJ.getJSONObject("data");
            // get "current condition array from "data"
            JSONArray currentCArr = data.getJSONArray("current_condition");
            // Create Weather class arrayList
            List<Weather> weatherList = new ArrayList<>();
            // get first object from "current condition" array
            JSONObject JSONWeather = currentCArr.getJSONObject(0);
            // get weatherDesc array from "current_condition" object
            JSONArray weatherDesc = JSONWeather.getJSONArray("weatherDesc");
            // get first object from "weatherDesc" array
            JSONObject descObj = weatherDesc.getJSONObject(0);
            JSONArray request = data.getJSONArray("request");
            JSONObject reqObj = request.getJSONObject(0);
            Weather weather = new Weather();

           // String temp = JSONWeather.getString("temp");
            /*double fConv = Math.round((temp - 273.15) * 1.8000 + 32.00);
            int tempInt = (int)(fConv);*/

            //String tempString = Integer.toString(tempInt) + "˚F";
            weather.setTemp(JSONWeather.getString("temp_F") + "˚F");
            weather.setCurrentCond(descObj.getString("value"));
            weather.setCityName(reqObj.getString("query"));
            weatherList.add(weather);

            return weatherList;
        } catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
