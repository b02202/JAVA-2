/*HourlyFragment
* Robert Brooks*/
package com.robertbrooks.tabapp.Fragments;

import android.app.AlertDialog;
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
public class HourlyFragment extends Fragment {
    List<Weather> weatherList;
    TextView hourlyText;
    HttpManager netCheck;
    Boolean isOnline;
    public String TAG = "HourlyFragment";

    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HourlyFragment newInstance(int sectionNumber) {
        HourlyFragment fragment = new HourlyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public HourlyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hourly_frag_layout, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        netCheck = new HttpManager(getActivity().getApplicationContext());
        isOnline = netCheck.isOnline();
        // Network Check
        if (isOnline) {

            hourlyText = (TextView) getActivity().findViewById(R.id.hourly_text);
            String hourlyUrl = "http://api.wunderground.com/api/0d340778b98d6d95/hourly/q/NC/Charlotte.json";
            runTask(hourlyUrl);
        } else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Your device must be connected to the internet to use this device")
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
                weatherList = HttpManager.hourlyParse(result);
                if (weatherList != null){
                    for (Weather weather : weatherList) {
                        String Current = (weather.getDay2());
                        Log.d(TAG, Current);
                        hourlyText.setText(Current);
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

