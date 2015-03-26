/*SettingsFragment.java
* Robert Brooks*/
package com.robertbrooks.actionbarspinnerapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.robertbrooks.actionbarspinnerapp.R;

/**
 * Created by Bob on 3/25/2015.
 */
public class SettingsFragment extends Fragment {

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
        View v = getView();
        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = myPrefs.edit();
        switch (v.getId()) {
            case R.id.enable_button:

                //String prefs = myPrefs.getString("MOBILE_DATA", "Default");

                editor.putBoolean("MOBILE_DATA", true);
                editor.commit();
                Toast.makeText(getActivity(), "Data over mobile is enabled", Toast.LENGTH_LONG).show();
                break;

            case R.id.disable_button:
                editor.putBoolean("MOBILE_DATA", false);
                editor.commit();
                Toast.makeText(getActivity(), "Data over mobile is disabled", Toast.LENGTH_LONG).show();
                break;
        }






    }

    // data over mobile enable / disable
    public void setMobileData() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);






    }

}
