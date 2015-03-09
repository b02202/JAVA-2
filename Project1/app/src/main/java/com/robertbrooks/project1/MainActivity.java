/*Robert Brooks
* MainActivity.java*/
package com.robertbrooks.project1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.robertbrooks.project1.Fragments.Detail;
import com.robertbrooks.project1.Fragments.Master;


public class MainActivity extends Activity implements Master.OnSubmitClickListener {

    final String TAG = "Project 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Network Check
        if (isOnline() == true) {

            // load Master Fragment to container 2
            if (savedInstanceState == null)
            {
                Master frag = Master.newInstance();

                getFragmentManager().beginTransaction()
                        .replace(R.id.container1, frag, Master.TAG).commit();
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
                        .replace(R.id.container1, frag, Master.TAG).commit();
            }
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
                    .replace(R.id.container2, frag, Detail.TAG)
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
