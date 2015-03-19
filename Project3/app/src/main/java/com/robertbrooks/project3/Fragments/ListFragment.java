package com.robertbrooks.project3.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.robertbrooks.project3.CustomData.CarData;
import com.robertbrooks.project3.DetailActivity;
import com.robertbrooks.project3.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bob on 3/19/2015.
 */
public class ListFragment extends Fragment {

    ListView carList;
    List<CarData> cList;
    ArrayAdapter listAdapter;
    String deleteText;
    ArrayList<CarData> carObjects = new ArrayList<CarData>();

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

        carList = (ListView) view.findViewById(R.id.listView);
        listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);

        populateListView();



        // Set onItemClickListener
        carList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selText = parent.getItemAtPosition(position).toString();
                int selPosition = parent.getSelectedItemPosition() + 1;

                String makeText = carObjects.get(selPosition).getMake();
                String modelText = carObjects.get(selPosition).getModel();
                String colorText = carObjects.get(selPosition).getColor();
                String fileName = carObjects.get(selPosition).getMake()
                        + carObjects.get(selPosition).getModel();
                String passText = "Make: " + makeText +
                        "\n\n" + "Model: " + modelText +
                        "\n\n" + "Color : " + colorText;
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("itemText", passText);
                intent.putExtra("fileName", fileName);
                startActivity(intent);
            }
        });
    }


    // Context menu for listView long-click implementation
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()== R.id.listView) {
            AdapterView.AdapterContextMenuInfo options = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(carObjects.get(options.position).getModel());
            menu.add("delete");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //int menuItemIndex = item.getItemId();
        String ListItemName = carObjects.get(info.position).getMake() + carObjects.get(info.position).getModel();
        Log.d(TAG, "List Item Name = " + ListItemName);
        listAdapter.remove(carObjects.get(info.position).getMake() + " " + carObjects.get(info.position).getModel());
        carObjects.remove(carObjects.get(info.position));

        Bundle bundle = getActivity().getIntent().getExtras();
        bundle.remove(ListItemName);


        String dirString = getActivity().getFilesDir().getAbsolutePath();
        File file = new File(dirString, ListItemName);
        boolean delete = file.delete();
        Log.w(TAG, "Delete Check: " + dirString + ListItemName + delete);
        //Toast.makeText(getActivity(), "Car deleted", Toast.LENGTH_LONG).show();
        listAdapter.notifyDataSetChanged();
        carList.setAdapter(listAdapter);

        //populateListView();
        return true;
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
            registerForContextMenu(carList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
