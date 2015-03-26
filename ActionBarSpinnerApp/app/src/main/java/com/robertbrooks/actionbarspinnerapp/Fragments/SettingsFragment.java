/*SettingsFragment.java
* Robert Brooks*/
package com.robertbrooks.actionbarspinnerapp.Fragments;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.robertbrooks.actionbarspinnerapp.R;

/**
 * Created by Bob on 3/25/2015.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {
    static final String TAG = "SettingsFragment.TAG";
    Button enable;
    Button disable;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SettingsFragment newInstance(int sectionNumber) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_frag_layout, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();

        enable = (Button) view.findViewById(R.id.enable_button);
        enable.setOnClickListener(this);
        disable = (Button) view.findViewById(R.id.disable_button);
        disable.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        // disable / enable data over mobile
        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = myPrefs.edit();
        switch (v.getId()) {
            case R.id.enable_button:
                editor.putBoolean("MOBILE_DATA", true);
                editor.commit();
                Toast.makeText(getActivity(), "Data over mobile is enabled", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Enable button pressed");
                break;

            case R.id.disable_button:
                editor.putBoolean("MOBILE_DATA", false);
                editor.commit();
                Toast.makeText(getActivity(), "Data over mobile is disabled", Toast.LENGTH_LONG).show();
                break;
        }
    }
}