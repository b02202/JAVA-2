/*ImagesFragment.java
* Robert Brooks*/

package com.robertbrooks.actionbarspinnerapp.Fragments;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.robertbrooks.actionbarspinnerapp.CustomGridView;
import com.robertbrooks.actionbarspinnerapp.R;

/**
 * Created by Bob on 3/25/2015.
 */
public class ImagesFragment extends Fragment {

    GridView gridView;
    // images array
    int[] images = {
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5,
            R.drawable.pic6,
            R.drawable.pic7,
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5,
            R.drawable.pic6,
            R.drawable.pic7,
    };

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ImagesFragment newInstance(int sectionNumber) {
        ImagesFragment fragment = new ImagesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ImagesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.images_frag_layout, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CustomGridView adapter = new CustomGridView(getActivity(), images);

        gridView = (GridView) getActivity().findViewById(R.id.gridView);
        gridView.setAdapter(adapter);


    }
}
