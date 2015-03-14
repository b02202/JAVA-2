/*Robert Brooks
* Detail.java*/

package com.robertbrooks.project1.Fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.robertbrooks.project1.R;

/**
 * Created by Bob on 3/2/2015.
 */
public class Detail extends Fragment {

    public static final String TAG = "DisplayFragment.TAG";
    public static final String ARG_TEXT = "DisplayFragment.ARG_TEXT";
    SharedPreferences myPrefs;
    public int bgColor;

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
        myPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int color = myPrefs.getInt("color", getResources().getColor(android.R.color.black));
        bgColor = myPrefs.getInt("detail_color", getResources().getColor(android.R.color.holo_green_light));
        getArguments().putString(ARG_TEXT, text);
        TextView cConditions = (TextView) getView().findViewById(R.id.current_conditions);
        TextView textView = (TextView) getView().findViewById(R.id.textView1);
        textView.setText(text);
        textView.setTextColor(color);
        textView.setBackgroundColor(bgColor);
        cConditions.setTextColor(color);
        cConditions.setBackgroundColor(bgColor);
    }
}
