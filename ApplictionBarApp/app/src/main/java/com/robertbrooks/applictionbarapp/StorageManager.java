package com.robertbrooks.applictionbarapp;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;

/**
 * Created by Bob on 3/21/2015.
 */
public class StorageManager {

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
}
