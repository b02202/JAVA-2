package com.robertbrooks.project1.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.robertbrooks.project1.CustomData.Weather;
import com.robertbrooks.project1.R;
import com.robertbrooks.project1.RemoteConnection.HttpManager;
import com.robertbrooks.project1.RemoteConnection.ParseJSON;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bob on 3/2/2015.
 */
public class Master extends Fragment implements View.OnClickListener {

    public static final String TAG = "MasterFragment.TAG";

    private OnSubmitClickListener SListener;

    public Spinner mSpinner;
    public Button mSubmit;
    public String returnString;
    public ArrayList<String> infoArray = new ArrayList<>();
    public List<aSyncTask> rTasks;
    public List<Weather> wList;
    public ProgressBar pb;

    public static Master newInstance()
    {
        Master frag = new Master();
        return frag;
    }

    // Define Interface

    public interface OnSubmitClickListener
    {
        public void populateDisplay(String text);

    }

    //onAttach
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Enforce Interface

        if (activity instanceof OnSubmitClickListener)
        {
            SListener = (OnSubmitClickListener) activity;
        } else {
            throw new IllegalArgumentException("Containing activity must implement the onSubmitClickListener");
        }
    }

    // OnCreateView
    @Nullable
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

        mSubmit = (Button) view.findViewById(R.id.button);
        mSubmit.setOnClickListener(this);
        rTasks = new ArrayList<>();
        //String[] dropItems = getResources().getStringArray(R.array.items);

        mSpinner = (Spinner) view.findViewById(R.id.spinner);

        // add listener to spinner
        addListener();

        // Create Spinner Array Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item);

        infoArray.add("Search 1");
        infoArray.add("Search 2");
        infoArray.add("Search 3");
        infoArray.add("Search 4");
        infoArray.add("Search 5");


        for (int i = 0; i < infoArray.size(); i++)
        {
            adapter.add(infoArray.get(i));
            mSpinner.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View v) {

        updateDetail();
    }

    // Get selected text for query
    public String getSelText(int selPosition)
    {
        String selString = infoArray.get(selPosition);
        setPopText(selString);
        return selString;

    }
    // set returnString also used to set query
    public void setPopText(String data) {
       returnString = data;

    }

    // add listener to spinner
    public void addListener() {
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getSelText(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // update detail view
    public void updateDetail() {
        SListener.populateDisplay(returnString);
    }
     // AsyncTask
    private class aSyncTask extends AsyncTask<String, String, String> {

        // Pre Execute
        @Override
        protected void onPreExecute() {
            // Handle Progress Bar
            if (rTasks.size() == 0)
            {
                pb.setVisibility(View.VISIBLE);
            }
        }
        // doInBackground
        @Override
        protected String doInBackground(String... params) {
            // String from HttpURLConnection
            String content = HttpManager.getData(params[0]);
            return content;
        }
        // onPostExecute
        @Override
        protected void onPostExecute(String result) {
            // Parse JSON
            wList = ParseJSON.parse(result);
            // Update TextView
            updateDetail();
            // remove task
            rTasks.remove(this);

            // PB to invisible
            if (rTasks.size() == 0)
            {
                pb.setVisibility(View.INVISIBLE);
            }
        }
    }
}
