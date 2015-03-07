package com.robertbrooks.project1.Libs;

import android.content.Context;

import com.robertbrooks.project1.Fragments.Master;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Bob on 3/4/2015.
 */
public class StorageManager {

    // Private internal storage
    public static void saveData (String _data, Context context) throws JSONException {

        JSONArray jData = new JSONArray();
        JSONObject zip;

        zip = new JSONObject();
        zip.put("zip", _data);

        try {
            FileOutputStream fOStream = context.openFileOutput("zips", Context.MODE_PRIVATE);
            fOStream.write(_data.getBytes());
            fOStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Load Data
    public static String loadData(Context context) {
        ArrayList<String> dataArray = new ArrayList<>();
        String results;
        try {
            FileInputStream fIStream = context.openFileInput("zips");
            BufferedInputStream bStream = new BufferedInputStream(fIStream);
            StringBuffer sB = new StringBuffer();
            while (bStream.available() != 0) {
                char ch = (char) bStream.read();

            }
            results = IOUtils.toString(fIStream);
            dataArray.add(results);
            fIStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataArray.toString();
    }
}


