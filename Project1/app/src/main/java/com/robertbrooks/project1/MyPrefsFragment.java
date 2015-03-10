package com.robertbrooks.project1;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Bob on 3/10/2015.
 */
public class MyPrefsFragment extends PreferenceFragment {


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preferences);
        }
}
