package com.robertbrooks.project1.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robertbrooks.project1.R;

/**
 * Created by Bob on 3/2/2015.
 */
public class Detail extends Fragment {

    public static final String TAG = "DisplayFragment.TAG";
    public static final String ARG_TEXT = "DisplayFragment.ARG_TEXT";

    public static Detail newInstance(String text)
    {
        Detail frag = new Detail();

         // Create Bundle and store text

        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        frag.setArguments(args);

        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Restore saved text
        Bundle args = getArguments();
        if(args != null && args.containsKey(ARG_TEXT))
        {
            setDisplayText(args.getString(ARG_TEXT));
        }

    }

    public void setDisplayText(String text)
    {
        getArguments().putString(ARG_TEXT, text);
        TextView textView = (TextView) getView().findViewById(R.id.textView);
        textView.setText(text);
    }
}
