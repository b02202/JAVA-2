package com.robertbrooks.project1.Libs;

import android.content.Context;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Bob on 3/4/2015.
 */
public class StorageManager {

    // Private internal storage
    public static void saveData (String _data, Context context) {

        try {
            FileOutputStream fOStream = context.openFileOutput("data.txt", Context.MODE_PRIVATE);
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
        String results = "";
        try {
            FileInputStream fIStream = context.openFileInput("data.txt");
            results = IOUtils.toString(fIStream);
            fIStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }
}


