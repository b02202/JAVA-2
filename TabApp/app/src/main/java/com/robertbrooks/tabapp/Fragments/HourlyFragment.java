package com.robertbrooks.tabapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robertbrooks.tabapp.R;

/**
 * Created by Bob on 3/24/2015.
 */
public class HourlyFragment extends Fragment {

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

        TextView testText = (TextView) getActivity().findViewById(R.id.hourly_text);
        testText.setText("Hourly Conditions working");

    }
}
