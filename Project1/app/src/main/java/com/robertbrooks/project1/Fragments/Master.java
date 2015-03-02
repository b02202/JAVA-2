package com.robertbrooks.project1.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.robertbrooks.project1.R;

/**
 * Created by Bob on 3/2/2015.
 */
public class Master extends Fragment implements View.OnClickListener {

    public static final String TAG = "MasterFragment.TAG";

    private OnSubmitClickListener SListener;

    public Spinner mSpinner;
    public Button mSubmit;

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
        mSpinner = (Spinner) view.findViewById(R.id.spinner);

        return view;
    }

    // onActivityCreated


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();

        mSubmit = (Button) view.findViewById(R.id.button);
        mSubmit.setOnClickListener(this);

        mSpinner = (Spinner) view.findViewById(R.id.spinner);
        // Create Spinner Array Adapter

        String[] dropItems = getResources().getStringArray(R.array.items);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, dropItems);

        mSpinner.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

        SListener.populateDisplay("This is working");

    }

}
