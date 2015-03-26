/*Robert Brooks
* MainActivity.java*/
package com.robertbrooks.project1;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.robertbrooks.project1.Fragments.Detail;
import com.robertbrooks.project1.Fragments.Master;


public class MainActivity extends ActionBarActivity implements Master.OnSubmitClickListener {

    final String TAG = "Project 1";
    private static final int REQUEST_CODE = 9119;
    public String prefString;

    TextView detailText;
    TextView editText;
    TextView listText;
    TextView currentText;
    Button saveButton;
    Master frag;
    SharedPreferences myPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create sharedPreference for fragment background colors:
        myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        int masterColor = getResources().getColor(android.R.color.holo_blue_light);
        int detailColor = getResources().getColor(android.R.color.holo_green_light);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putInt("master_color", masterColor);
        editor.putInt("detail_color", detailColor);
        editor.commit(); // apply() can be used to write in background



        // Network Check
        NetworkFileHelper nFH = new NetworkFileHelper(getApplicationContext());
        Boolean isOnline = nFH.isOnline();
        if (isOnline) {
            // load Master Fragment to container 1
            if (savedInstanceState == null)
            {
                frag = Master.newInstance();

                getFragmentManager().beginTransaction()
                        .replace(R.id.container2, frag, Master.TAG)
                        .commit();
            }
        } else {
            // No internet connection alert
            new AlertDialog.Builder(this)
                    .setTitle("No Internet Connection")
                    .setMessage("If this is the first time using this application, an internet connection must be present. If you have run this application before your saved data will display")
                    .setPositiveButton("Continue in offline mode", null)
                    .show();
            if (savedInstanceState == null)
            {
                frag = Master.newInstance();

                getFragmentManager().beginTransaction()
                        .replace(R.id.container2, frag, Master.TAG).commit();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClass(this, MyPrefsActivity.class );
            startActivityForResult(intent, 9119); // turn number into constant
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        detailText = (TextView)findViewById(R.id.textView1);
        currentText = (TextView)findViewById(R.id.current_conditions);
        listText = (TextView) findViewById(android.R.id.text1);
        saveButton = (Button)findViewById(R.id.save_button);
        editText = (EditText)findViewById(R.id.editText);

        // Shared Prefs
        if(REQUEST_CODE == 9119) {
           myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            prefString = myPrefs.getString("PREF_LIST", "Default");
            int selectedColor = 0;
            if (prefString.equals("Default")) {
                selectedColor = getResources().getColor(android.R.color.black);
            } else if (prefString.equals("Red")) {
                selectedColor = getResources().getColor(android.R.color.holo_red_dark);
            } else if (prefString.equals("Blue")) {
                selectedColor = getResources().getColor(android.R.color.holo_blue_dark);
            } else if (prefString.equals("Green")) {
                selectedColor = getResources().getColor(android.R.color.holo_green_dark);
            }
            setTextColor(selectedColor, prefString);
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.putInt("color", selectedColor);
            editor.commit(); // apply() can be used to write in background

            Log.i(TAG, "Current Color Color: " + selectedColor);
        }
    }

    @Override
    public void populateDisplay(String text){
        Log.i(TAG, "Displaying: " + text);
        // Load detail fragment to container 1
        Detail frag1 = (Detail) getFragmentManager().findFragmentByTag(Detail.TAG);

        if(frag1 == null)
        {
            frag1 = Detail.newInstance(text);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container1, frag1, Detail.TAG)
                    .commit();
        } else {
            frag1.setDisplayText(text);
        }
    }




    // Set Text Color
    public void setTextColor(int colorInt, String prefValue) {

        if (prefString.equals(prefValue)) {
             if (detailText != null & currentText != null) {
                 detailText.setTextColor(colorInt);

                 currentText.setTextColor(colorInt);
             }

            if (frag != null) {
                frag.setListTextColor(colorInt);
            }
                saveButton.setTextColor(colorInt);
                editText.setTextColor(colorInt);
                editText.setHintTextColor(colorInt);
            Toast.makeText(this, "Text Color Preference: " + prefString, Toast.LENGTH_LONG).show();
        }
    }
}