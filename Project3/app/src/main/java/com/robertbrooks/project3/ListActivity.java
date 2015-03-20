/*ListActivity.java*/
package com.robertbrooks.project3;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.robertbrooks.project3.Fragments.ListFragment;


/**
 * Created by Bob on 3/19/2015.
 */
public class ListActivity extends ActionBarActivity {

    ListFragment listFrag;
    ListView carList;
    boolean initialRun;
    // ArrayList carList;
    public static String TAG = "ListActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState)         {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list);
       /* getActionBar().setDisplayHomeAsUpEnabled(true);*/
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            carList = (ListView) findViewById(R.id.listView);


            if (savedInstanceState == null) {

                listFrag = ListFragment.newInstance();
                getFragmentManager().beginTransaction()
                        .replace(R.id.listFragContainer, listFrag, ListFragment.TAG )
                        .commit();

            }
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {

            initialRun = false;
        }
    }

    public void openActivity(View v) {
        Intent intent = new Intent(this, DetailActivity.class);
        startActivity(intent);
    }


}
