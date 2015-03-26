/*CurrentForecastFragment
* Robert Brooks*/
package com.robertbrooks.tabapp.Fragments;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class CurrentForecastFragment extends Fragment {
    TextView weatherText;
    HttpManager netCheck;
    Boolean isOnline;
    List<Atask> tasks;

    List<Weather> weatherList;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static CurrentForecastFragment newInstance(int sectionNumber) {
            CurrentForecastFragment fragment = new CurrentForecastFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public CurrentForecastFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.current_frag_layout, container, false);
            return rootView;
        }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        netCheck = new HttpManager(getActivity().getApplicationContext());
        isOnline = netCheck.isOnline();
        // Network Check
        if (isOnline) {
            weatherText = (TextView) getActivity().findViewById(R.id.textView);
            //testText.setText("Current Conditions working");
            String apiString = "http://api.wunderground.com/api/0d340778b98d6d95/conditions/q/NC/Charlotte.json";
            runTask(apiString);
        } else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Your device must be connected to the internet to use this application")
                    .setPositiveButton("OK", null)
                    .show();
        }

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
                weatherList = HttpManager.parse(result);
                if (weatherList != null){
                    for (Weather weather : weatherList) {
                        String Current = (weather.getObservationTime()
                                + "\n\n Current Weather: " + weather.getCurrentWeather()
                                + "\n\n Temperature: " + weather.getTemperature()
                                + "\n\n Relative Humidity: " + weather.getRelativeHumidity()
                                + "\n\n Wind: " + weather.getWind()
                                + "\n\n Dewpoint: " + weather.getDewpoint()
                                + "\n\n Feels Like: " + weather.getFeelsLike()
                                + "\n\n Visibility: " + weather.getVisibility()
                                + "\n\n UV: " + weather.getUV());
                        weatherText.setText(Current);
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