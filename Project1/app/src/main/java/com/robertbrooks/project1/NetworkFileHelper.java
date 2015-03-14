package com.robertbrooks.project1;

import android.content.Context;

import com.robertbrooks.project1.CustomData.Weather;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bob on 3/14/2015.
 */
public class NetworkFileHelper {

    // HTTP Manager
    public static String getData(String urlString)
    {
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
            while ((dataLine = reader.readLine()) != null)
            {
                sBuilder.append(dataLine + "\n");
            }
            return sBuilder.toString();

        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        } finally {
            // close Buffered Reader
            if (reader != null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

    // JSON PARSE
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

    // File Storage
    // Private internal storage
    public static void writeToFile(String dataString, Context context) throws FileNotFoundException {
        try {
            FileOutputStream fileOutStream = context.openFileOutput(dataString, Context.MODE_PRIVATE);
            fileOutStream.write(dataString.getBytes());
            fileOutStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readData(String dataString, Context context) throws FileNotFoundException {
        String results = "";


        try {
            FileInputStream fileInputStream = context.openFileInput(dataString);
            results = IOUtils.toString(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static void createJSONFile(JSONArray jData, String fileName, Context context) throws JSONException, IOException {
        JSONArray data = jData;

        String text = data.toString();
        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        fos.write(text.getBytes());
        fos.close();

    }

    public static String readJSONFile(String dataString, Context context) throws FileNotFoundException, JSONException {
        String current = "";

        // add check to see if file is not known
        try {
            FileInputStream fileInputStream = context.openFileInput(dataString);
            String results = IOUtils.toString(fileInputStream);
            JSONArray data = new JSONArray(results);
            for (int i = 0; i < data.length(); i++) {
                current = data.getJSONObject(i).getString("current");
            }

            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return current;
    }
}

