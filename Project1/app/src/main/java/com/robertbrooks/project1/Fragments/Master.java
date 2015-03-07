package com.robertbrooks.project1.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.Toast;

import com.robertbrooks.project1.CustomData.Weather;
import com.robertbrooks.project1.Libs.StorageManager;
import com.robertbrooks.project1.MainActivity;
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

// https://www.youtube.com/watch?v=TdC0OCtkHwQ&feature=youtu.be
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
        //EditText mUserInput = (EditText) view.findViewById(R.id.editText);
        mSaveButton = (Button) view.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(this);
        mUserInput = (EditText) view.findViewById(R.id.editText);
        mListView = (ListView) view.findViewById(R.id.list_view);

        // Get Filenames:
        getFilenames();
        addListener();



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.save_button:
                if (isOnline()) {
                    try {
                        StorageManager.writeToFile(mUserInput.getText().toString(), getActivity().getApplicationContext());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    SListener.populateDisplay("Save worked");
                    getFilenames();
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
    public void getFilenames() {
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        String[] fileNames = getActivity().getApplicationContext().fileList();
        List<Weather> list = new ArrayList<>();
        Weather weather = new Weather();

        for (int i = 0; i < fileNames.length; i++) {
            Log.d("Filename", fileNames[i]);
            weather.setZip(fileNames[i]);

            list.add(weather);
            String data = list.get(i).getZip();
            String temp = list.get(i).getTemp();
            adapter.add(data);
            SListener.populateDisplay(temp);

        }

        mListView.setAdapter(adapter);
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
                    String baseUrl = "http://api.openweathermap.org/data/2.5/weather?q=";
                    String queryString = selText;
                    String searchString = baseUrl + queryString;
                    // Run AsyncTask
                    runTask(searchString);

                }
                else {
                    // Load data from local storage
                    try {
                        String lData = null;
                        try {
                            lData = StorageManager.readJSONFile(selText, getActivity().getApplicationContext());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "Populated by saved json: " + lData);
                        SListener.populateDisplay(lData);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getActivity(), "Displaying weather conditions from last successful search", Toast.LENGTH_LONG).show();
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
        protected String doInBackground(String... params) {
            // Get String from HttpURLConnection
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            // Parse JSON
            wList = ParseJSON.parse(result);

            // create JSONArray and JSON Object to store current conditions data
            JSONArray data = new JSONArray();
            JSONObject zip;
            if (wList != null) {
                for (Weather weather : wList) {

                    String currentC = weather.getTemp();
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
        }
    }

    // Run AsyncTask
    public void runTask(String urlSearch)
    {
        ATask task = new ATask();
        task.execute(urlSearch);
    }
}
