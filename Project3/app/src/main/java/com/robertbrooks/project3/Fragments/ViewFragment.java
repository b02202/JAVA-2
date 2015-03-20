/*ViewFragment.java*/
package com.robertbrooks.project3.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.robertbrooks.project3.R;

/**
 * Created by Bob on 3/19/2015.
 */
public class ViewFragment extends Fragment {


    TextView detailText;
    Button deleteCar;
    String itemText;
    //String fileName;

    public  static final String TAG = "ViewFragment.TAG";

    public static ViewFragment newInstance() {
        ViewFragment frag = new ViewFragment();
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_frag, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final View view = getView();

        detailText = (TextView) view.findViewById(R.id.detailText);
        deleteCar = (Button) view.findViewById(R.id.deleteButton);
        Intent intent = getActivity().getIntent();
        itemText = intent.getExtras().getString("itemText");
        detailText.setText(itemText);

    }

}
