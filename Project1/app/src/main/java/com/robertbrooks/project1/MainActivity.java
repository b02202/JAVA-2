/*Robert Brooks
* MainActivity.java*/
package com.robertbrooks.project1;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.robertbrooks.project1.Fragments.Detail;
import com.robertbrooks.project1.Fragments.Master;


public class MainActivity extends ActionBarActivity implements Master.OnSubmitClickListener {

    final String TAG = "Project 1";
    private static final int REQUEST_CODE = 9119;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Network Check
        if (isOnline() == true) {

            // load Master Fragment to container 1
            if (savedInstanceState == null)
            {
                Master frag = Master.newInstance();

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
                Master frag = Master.newInstance();

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

        if(requestCode == 9119) {
            SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            boolean pref1 = myPrefs.getBoolean("pref1", false);
            Toast.makeText(this, "Preference: " + pref1, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void populateDisplay(String text){
        Log.i(TAG, "Displaying: " + text);
        // Load detail fragment to container 2
        Detail frag = (Detail) getFragmentManager().findFragmentByTag(Detail.TAG);

        if(frag == null)
        {
            frag = Detail.newInstance(text);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container1, frag, Detail.TAG)
                    .commit();
        } else {
            frag.setDisplayText(text);
        }
    }



    // Network Check
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {

            return false;
        }
    }


}
