/*Robert Brooks
* StorageManager.java*/

package com.robertbrooks.project1.Libs;

import android.content.Context;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Bob on 3/4/2015.
 */
public class StorageManager {

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


