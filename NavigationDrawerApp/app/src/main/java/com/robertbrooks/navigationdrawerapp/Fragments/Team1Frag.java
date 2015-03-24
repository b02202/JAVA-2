package com.robertbrooks.navigationdrawerapp.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class Team1Frag extends Fragment  {
    public static final String TAG = "Team1Fragment.TAG";
    public static final String ARG_SECTION_NUMBER = "section_number";
    private int param1;
    //public static final String ARG_TEXT = "Team1Fragment.ARG_TEXT";
    ListView fListView;
    String[] playerList;
    private OnFragmentInteractionListener fListener;

   public interface OnFragmentInteractionListener {
        public void onFragmentInteratction(Uri uri);

    }

    public static Team1Frag newInstance(int position) {

        Team1Frag frag = new Team1Frag();

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, position);
        frag.setArguments(args);
        return frag;
    }

    public Team1Frag () {
        //leave empty
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            param1 = getArguments().getInt(ARG_SECTION_NUMBER);

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.team1_frag_layout, container, false);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

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
        //playerList = getResources().getStringArray(R.array.team1);
        Log.d(TAG, "Param1: " + param1);

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
