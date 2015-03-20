/*ListFragment.java*/

package com.robertbrooks.project3.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.robertbrooks.project3.CustomData.CarData;
import com.robertbrooks.project3.DetailActivity;
import com.robertbrooks.project3.R;

import java.util.ArrayList;

/**
 * Created by Bob on 3/19/2015.
 */
public class ListFragment extends Fragment {

    ListView carList;
    ArrayList<CarData> cList;
    ArrayAdapter listAdapter;
    ArrayAdapter ad;
    String[] fileNames;
    ArrayList<CarData> carObjects = new ArrayList<CarData>();
    private boolean initialRun = true;

    public static final String TAG = "ListFragment.TAG";

    public static ListFragment newInstance() {
        ListFragment frag = new ListFragment();
        return frag;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_frag, container, false);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final View view = getView();
        fileNames = getActivity().getApplicationContext().fileList();

        carList = (ListView) view.findViewById(R.id.listView);
        listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        cList = new ArrayList<>();


        Intent i = getActivity().getIntent();
        initialRun = i.getBooleanExtra("initRun", true);
            if(initialRun) {
                populateListView();
            } else {
                returnPop();
            }

        // Set onItemClickListener
        carList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (initialRun) {
                    String makeText = carObjects.get(position).getMake();
                    String modelText = carObjects.get(position).getModel();
                    String colorText = carObjects.get(position).getColor();
                    String fileName = carObjects.get(position).getMake()
                            + carObjects.get(position).getModel();
                    String passText = "Make: " + makeText +
                            "\n\n" + "Model: " + modelText +
                            "\n\n" + "Color : " + colorText;
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("itemText", passText);
                    intent.putExtra("fileName", fileName);
                    intent.putExtra("position", position);
                    startActivity(intent);
                } else {
                    String makeText2 = cList.get(position).getMake();
                    String modelText2 = cList.get(position).getModel();
                    String colorText2 = cList.get(position).getColor();
                    String fileName2 = cList.get(position).getMake()
                            + cList.get(position).getModel();
                    String passText2 = "Make: " + makeText2 +
                            "\n\n" + "Model: " + modelText2 +
                            "\n\n" + "Color : " + colorText2;
                    Intent intent2 = new Intent(getActivity(), DetailActivity.class);
                    intent2.putExtra("itemText", passText2);
                    intent2.putExtra("fileName", fileName2);
                    intent2.putExtra("position", position);
                    startActivity(intent2);
                }


            }
        });
    }

    public void openActivity(View v) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        startActivity(intent);
    }

    // populate ListView
    public void populateListView() {

            String keyString;
            // Get Intent Extras and populate listView
            try {
                Bundle extras = getActivity().getIntent().getExtras();

                // loop through extras
                for (String key : extras.keySet()) {
                    Object value = extras.get(key);
                    keyString = key;
                    carObjects = (ArrayList<CarData>) extras.getSerializable(keyString);
                    Log.d(TAG, "Extras: " + String.format("%s %s (%s)", key, value.toString(), value.getClass().getName()));
                    //Log.d(TAG, "CarObjects = " + carObjects.toString());
                }

                for (int i = 0; i < carObjects.size(); i ++) {

                    String carsForList = carObjects.get(i).getMake() + " " + carObjects.get(i).getModel();
                    listAdapter.add(carsForList);
                    Log.d(TAG, "CarObjects i: " + carObjects.get(i).getMake() + " " + carObjects.get(i).getModel());
                }

                carList.setAdapter(listAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    //populate listView after delete
    public void returnPop() {
        CarData carInfo;
        if (fileNames.length != 0) {
            for (String file : fileNames) {
                carInfo = CarData.readFile(file, getActivity());
                cList.add(carInfo);
            }

            for (int i = 0; i < cList.size(); i++) {
                String carS = cList.get(i).getMake() + " " + cList.get(i).getModel();
                listAdapter.add(carS);
            }
            carList.setAdapter(listAdapter);
        }
    }

}
