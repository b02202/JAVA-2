package com.robertbrooks.project1;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Bob on 3/10/2015.
 */
public class MyPrefsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MyPrefsFragment())
                .commit();
    }
}
