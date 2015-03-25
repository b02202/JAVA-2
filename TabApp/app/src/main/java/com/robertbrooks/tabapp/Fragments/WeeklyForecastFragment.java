/*WeeklyForecastFragment.java
* Robert Brooks*/
package com.robertbrooks.tabapp.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robertbrooks.tabapp.CustomData.Weather;
import com.robertbrooks.tabapp.HttpManager;
import com.robertbrooks.tabapp.R;

import java.util.List;

/**
 * Created by Bob on 3/24/2015.
 */
public class WeeklyForecastFragment extends Fragment {
    List<Weather> weatherList;
    TextView weeklyText;

    private static final String ARG_SECTION_NUMBER = "section_number";
    static String TAG = "Weekly: ";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static WeeklyForecastFragment newInstance(int sectionNumber) {
        WeeklyForecastFragment fragment = new WeeklyForecastFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public WeeklyForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weekly_frag_layout, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        weeklyText = (TextView) getActivity().findViewById(R.id.weekly_text);
        String forecastUrl = "http://api.wunderground.com/api/0d340778b98d6d95/forecast10day/q/NC/Charlotte.json";
        runTask(forecastUrl);

    }

    // AsyncTask
    private class Atask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            // Parse
            if (result != null) {
                weatherList = HttpManager.weekParse(result);
                if (weatherList != null){
                    for (Weather weather : weatherList) {
                        String Current = (weather.getDay1());
                        Log.d(TAG, Current);
                        weeklyText.setText(Current);
                    }
                }
            }
        }
    }

    // Run Async Task
    public void runTask(String urlSearch) {
        Atask task = new Atask();
        task.execute(urlSearch);
    }
}
