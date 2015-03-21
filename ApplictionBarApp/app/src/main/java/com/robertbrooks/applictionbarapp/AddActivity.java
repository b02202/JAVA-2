package com.robertbrooks.applictionbarapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.robertbrooks.applictionbarapp.CustomDataPackage.CustomData;
import com.robertbrooks.applictionbarapp.CustomDataPackage.CustomDataList;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Bob on 3/21/2015.
 */
public class AddActivity extends ActionBarActivity{

    EditText userInput;
    String dataString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userInput = (EditText) findViewById(R.id.editText);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        switch (item.getItemId()) {
            case R.id.action_save:
                // get user Input
                dataString = userInput.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("dataString", dataString);
                intent.putExtra("action", "add");
                setResult(RESULT_OK, intent);
                finish();


                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
