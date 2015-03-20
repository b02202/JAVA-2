/*DetailActivity.java*/
package com.robertbrooks.project3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.robertbrooks.project3.CustomData.CarData;
import com.robertbrooks.project3.Fragments.ListFragment;
import com.robertbrooks.project3.Fragments.ViewFragment;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Bob on 3/19/2015.
 */
public class DetailActivity extends ActionBarActivity {

    public String itemText;
    public String fileName;
    ViewFragment viewFrag;
    ArrayAdapter adapter;
    public static String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // add ViewFragment
        if (savedInstanceState == null) {

            viewFrag = ViewFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.viewFragContainer, viewFrag, ViewFragment.TAG )
                    .commit();

        }
       adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        Intent intent = getIntent();
        fileName = intent.getExtras().getString("fileName");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteData(View v) {

        Intent returnIntent = new Intent(this, ListActivity.class);
        String dirString = getFilesDir().getAbsolutePath();
        File file = new File(dirString, fileName);
        boolean delete = file.delete();
        Log.w(TAG, "Delete Check: " + dirString + "/" + fileName + delete);
        if (delete) {
            Toast.makeText(this, fileName + "Has been successfully deleted", Toast.LENGTH_SHORT).show();
                returnIntent.putExtra("delete", fileName);
                returnIntent.putExtra("initRun", false);
                setResult(RESULT_OK);
                startActivity(returnIntent);
                finish();
            }

        }

    public void shareData(View v) {

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This text is being shared");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "This text is being shared from my app."));



    }

}
