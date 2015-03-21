package com.robertbrooks.applictionbarapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.robertbrooks.applictionbarapp.CustomDataPackage.CustomData;
import com.robertbrooks.applictionbarapp.CustomDataPackage.CustomDataList;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    public static String TAG = "MainActivity";

    private static final int REQUEST_CODE = 999;

    ListView listView;
    String dataString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(MainActivity.this, AddActivity.class );
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // refresh List

    /*public void refreshList() {

        // loop through CustomData
        for (final CustomData customData : customDatas) {
            Intent intent = new Intent(MainActivity.this, AddActivity.class );
            intent.putExtra("dataString", customData.dataString);
            startActivityForResult(intent, REQUEST_CODE);
        }

    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            dataString = data.getStringExtra("dataString");
            String action = data.getStringExtra("action");

            if (action.equals("add")) {
                Toast.makeText(this, dataString + " has been passed back from addActivity",
                        Toast.LENGTH_LONG).show();
                Log.d(TAG, dataString + "has been received from addActivity");
                List<CustomData> customDatas = new CustomDataList(dataString).getDataArray();
                refreshList(customDatas);
            }

        }
    }

    // create / Refresh ListView

    public void refreshList(List<CustomData> dList) {

        // Set List View
        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<CustomData> adapter = new ArrayAdapter<CustomData>(
                this, android.R.layout.simple_list_item_1, dList);
        listView.setAdapter(adapter);
    }

}
