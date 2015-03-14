/*Robert Brooks
* Master.java*/

package com.robertbrooks.project1.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.robertbrooks.project1.CustomData.Weather;
import com.robertbrooks.project1.Libs.StorageManager;
import com.robertbrooks.project1.R;
import com.robertbrooks.project1.RemoteConnection.HttpManager;
import com.robertbrooks.project1.RemoteConnection.ParseJSON;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bob on 3/2/2015.
 */
public class Master extends Fragment implements View.OnClickListener {

    public static final String TAG = "MasterFragment.TAG";
    List<Weather> wList;

    private OnSubmitClickListener SListener;
    public EditText mUserInput;
    public Button mSaveButton;
    public ListView mListView;
    public String selText;
    public ProgressBar mPB;
    public TextView listText;
    List<ATask> tasks;
    SharedPreferences myPrefs;

    public static Master newInstance() {
        Master frag = new Master();
        return frag;
    }

    // Define Interface
    public interface OnSubmitClickListener {

        public void populateDisplay(String text);

    }

    // onAttach
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Enforce Interface
        if (activity instanceof OnSubmitClickListener) {
            SListener = (OnSubmitClickListener) activity;
        } else {
            throw new IllegalArgumentException("Containing activity must implement the onSubmitClickListener");
        }
    }
    // onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.master_layout, container, false);
        return view;
    }

    // onActivityCreated


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        // References:
        mSaveButton = (Button) view.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(this);
        mUserInput = (EditText) view.findViewById(R.id.editText);
        mListView = (ListView) view.findViewById(R.id.list_view);
        listText = (TextView) view.findViewById(R.id.list_style);
        mPB = (ProgressBar) view.findViewById(R.id.progressBar);
        mPB.setVisibility(View.INVISIBLE);
        // Create ArrayList for AsyncTasks
        tasks = new ArrayList<>();

        // Get Filenames:
        getFilenames();
        // add listener to listView
        addListener();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.save_button:
                if (isOnline()) {
                    // zip code validation
                    if (zipCheck(mUserInput.getText().toString()))
                    {
                        try {
                            // write to internal file
                            StorageManager.writeToFile(mUserInput.getText().toString(), getActivity().getApplicationContext());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getActivity(), "Zip Code has been saved", Toast.LENGTH_LONG).show();
                        mUserInput.setText("");
                        getFilenames();

                    } else {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("OOPS!")
                                .setMessage("Please enter a valid zip code")
                                .setPositiveButton("OK", null)
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("OOPS!")
                            .setMessage("You must be connected to the internet to add a zip code")
                            .setPositiveButton("OK", null)
                            .show();
                }
                break;
        }
    }

    // custom functions

    // get filenames and display in listView
    public void getFilenames() {

       // ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_adapter);
        ArrayAdapter adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1) {
          @Override
        public View getView(int position, View convertView, ViewGroup parent) {
              myPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
              int colorPref = myPrefs.getInt("color", getResources().getColor(android.R.color.black));
              View view = super.getView(position, convertView, parent);
              TextView text = (TextView) view.findViewById(android.R.id.text1);
              if (text != null) {
                  text.setTextColor(colorPref);
              }
              return view;
          }

        };


        String[] fileNames = getActivity().getApplicationContext().fileList();
        List<Weather> list = new ArrayList<>();
        Weather weather = new Weather();

        // Add filenames to Arraylist
        for (int i = 0; i < fileNames.length; i++) {
            Log.d("Filename", fileNames[i]);
            weather.setZip(fileNames[i]);

            list.add(weather);
            String data = list.get(i).getZip();
            //String temp = list.get(i).getTemp();
            // add data to adapter
            adapter2.add(data);
            adapter2.notifyDataSetChanged();
        }
        mListView.setAdapter(adapter2);
        // set pref
        setPref();



    }

    // add listener to listView
    public void addListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Position: " + position);
                selText = (String) parent.getItemAtPosition(position);
                if (isOnline()) {
                    // Create String Query
                    // http://api2.worldweatheronline.com/free/v2/weather.ashx?q=22152&format=json&num_of_days=0&key=a5516dc9365b98e1faea4e7759fb9
                   // String baseUrl = "http://api.openweathermap.org/data/2.5/weather?q=";
                    String baseUrl1 = "http://api2.worldweatheronline.com/free/v2/weather.ashx?q=";
                    String queryString = selText;
                    String baseUrl2 = "&format=json&num_of_days=0&key=a5516dc9365b98e1faea4e7759fb9";
                    //String testUrl = "http://api.openweathermap.org/data/2.5/weather?q=charlotte";

                    String searchString = baseUrl1 + queryString + baseUrl2;
                    // Run AsyncTask
                    runTask(searchString);

                }
                else {
                    // Load data from local storage
                    loadSData();
                }

                Log.d(TAG, "Position Text: " + selText);
            }
        });
    }

    // Network Check
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {

            return false;
        }
    }

    // AsyncTask
    private class ATask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // Set progress bar to visible
            if (tasks.size() == 0)
            {
                mPB.setVisibility(View.VISIBLE);
            }
            // Add task to ArrayList
            tasks.add(this);
        }

        @Override
        protected String doInBackground(String... params) {
            // Get String from HttpURLConnection
            String content = HttpManager.getData(params[0]);
            return content;
        }
        @Override
        protected void onPostExecute(String result) {
            // Parse JSON
            if (result != null) {
                wList = ParseJSON.parse(result);
                // create JSONArray and JSON Object to store current conditions data
                JSONArray data = new JSONArray();
                JSONObject zip;
                if (wList != null) {
                    for (Weather weather : wList) {

                        String currentC = (weather.getCityName() + ", USA \n \n" + weather.getTemp() + "\n" + "\n" + weather.getCurrentCond());
                        zip = new JSONObject();
                        try {
                            zip.put("current", currentC);
                            data.put(zip);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        new AlertDialog.Builder(getActivity())
                                .setTitle(currentC)
                                .setMessage(currentC)
                                .setPositiveButton("OK", null)
                                .show();
                        // Save to JSONArray to internal storage
                        try {
                            StorageManager.createJSONFile(data, selText, getActivity().getApplicationContext());
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                        // populate textView with current condition
                        SListener.populateDisplay(currentC);
                    }

                }
            } else {
                loadSData();
                new AlertDialog.Builder(getActivity())
                        .setTitle("Open Weather Map Not Available")
                        .setMessage("Sorry for the inconvenience. Please try again later. ")
                        .setPositiveButton("OK", null)
                        .show();

            }
            tasks.remove(this);
            if (tasks.size() == 0)
            {
                mPB.setVisibility(View.INVISIBLE);
            }
        }
    }

    // Run AsyncTask
    public void runTask(String urlSearch)
    {
        ATask task = new ATask();
        task.execute(urlSearch);
    }

    // Zip Code Check
   public boolean zipCheck(String zip) {
        String regex = "^\\d{5}(-\\d{4})?$";
        Pattern pattern = Pattern.compile(regex);
       Matcher matcher = pattern.matcher(zip);
       if (matcher.matches()) {
           return true;
       } else {
           return false;
       }
   }

    // Shared Preferences:
    public void setPref() {


        myPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        int colorPref = myPrefs.getInt("color", getResources().getColor(android.R.color.black));
        if (listText != null) {
            listText.setTextColor(colorPref);
            mSaveButton.setTextColor(colorPref);
            mUserInput.setTextColor(colorPref);
            mUserInput.setHintTextColor(colorPref);
            int lColor = listText.getCurrentTextColor();
            Log.i(TAG, "List Color =" + lColor );
        }
    }

    // getList
    public void setListTextColor(int PrefColor) {
        //SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //int colorPref = myPrefs.getInt("color", getResources().getColor(android.R.color.black));
        if (listText != null) {
            listText.setTextColor(PrefColor);
            int lColor = listText.getCurrentTextColor();
            Log.i(TAG, "List Color =" + lColor );
        }
        getFilenames();
        //setPref();
    }

    // Load saved data
    public void loadSData() {
        try {
            String lData = null;
            try {
                lData = StorageManager.readJSONFile(selText, getActivity().getApplicationContext());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "Populated by saved json: " + lData);
            SListener.populateDisplay(lData);
            getFilenames();
            setPref();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Toast.makeText(getActivity(), "Displaying weather conditions from last successful search", Toast.LENGTH_LONG).show();
    }
}
