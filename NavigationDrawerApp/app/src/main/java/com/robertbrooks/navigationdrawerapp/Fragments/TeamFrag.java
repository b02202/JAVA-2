/*TeamFrag.jav
  Robert Brooks*/
package com.robertbrooks.navigationdrawerapp.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.robertbrooks.navigationdrawerapp.R;

/**
 * Created by Bob on 3/23/2015.
 */
public class TeamFrag extends Fragment  {
    public static final String TAG = "Team1Fragment.TAG";
    public static final String ARG_SECTION_NUMBER = "section_number";
    private int param1;
    ListView fListView;
    String[] playerList;
    private OnFragmentInteractionListener fListener;

    // Define Interface
   public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);

    }

    public static TeamFrag newInstance(int position) {

        TeamFrag frag = new TeamFrag();
        // Create Bundle and store position
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, position);
        frag.setArguments(args);
        return frag;
    }

    public TeamFrag() {
        //leave empty
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get arguments and set param1 to value
        if (getArguments() != null) {
            param1 = getArguments().getInt(ARG_SECTION_NUMBER);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.team1_frag_layout, container, false);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Enforce Interface
        try {
            fListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    // set fListener to null on detach
    @Override
    public void onDetach() {
        super.onDetach();
        fListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //populateList();
        fListView = (ListView) getActivity().findViewById(R.id.listView);
        Log.d(TAG, "Param1: " + param1);

        // populate listView based on argument
        if (param1 != 0) {
            switch (param1) {
                case 1:
                    playerList = getResources().getStringArray(R.array.team1);
                    break;
                case 2:
                    playerList = getResources().getStringArray(R.array.team2);
                    break;
                case 3:
                    playerList = getResources().getStringArray(R.array.team3);
                    break;
                case 4:
                    playerList = getResources().getStringArray(R.array.team4);
                    break;
                case 5:
                    playerList = getResources().getStringArray(R.array.team5);
                    break;
                case 6:
                    playerList = getResources().getStringArray(R.array.team6);
                    break;
            }
            // populate listView
            populateList(playerList);
        }
    }
    // populate listView
    public void populateList(String[] players) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        for (String player : players) {
            adapter.add(player);
        }
        fListView.setAdapter(adapter);
    }
}