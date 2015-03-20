/*AddFragment.java*/
package com.robertbrooks.project3.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.robertbrooks.project3.R;

/**
 * Created by Bob on 3/19/2015.
 */
public class AddFragment extends Fragment {
    public static final String TAG = "AddFragment.TAG";

    EditText modelText;
    EditText makeText;
    EditText colorText;
    Button saveButton;

    public static AddFragment newInstance() {
        AddFragment frag = new AddFragment();
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_frag, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();

        // References:
        modelText = (EditText) view.findViewById(R.id.modelText);
        makeText = (EditText) view.findViewById(R.id.makeText);
        colorText = (EditText) view.findViewById(R.id.colorText);
        saveButton = (Button) view.findViewById(R.id.saveButton);


    }


}
