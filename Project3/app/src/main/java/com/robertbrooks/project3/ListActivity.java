package com.robertbrooks.project3;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;

import com.robertbrooks.project3.CustomData.CarData;
import com.robertbrooks.project3.Fragments.ListFragment;

import java.util.ArrayList;

/**
 * Created by Bob on 3/19/2015.
 */
public class ListActivity extends ActionBarActivity {

    ListFragment addFrag;
    ArrayList<CarData> cData;
    ArrayList<CarData> carsArray;
    // ArrayList carList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
       /* getActionBar().setDisplayHomeAsUpEnabled(true);*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TODO: add carIfo to listView Fragment






        if (savedInstanceState == null) {

            addFrag = ListFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.listFragContainer, addFrag, ListFragment.TAG )
                    .commit();

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



    public void openActivity(View v) {
        Intent intent = new Intent(this, DetailActivity.class);
        startActivity(intent);
    }


}
