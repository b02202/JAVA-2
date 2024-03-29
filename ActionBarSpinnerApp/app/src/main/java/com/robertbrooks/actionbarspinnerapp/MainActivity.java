/*MainActivity.java
* Robert Brooks*/
package com.robertbrooks.actionbarspinnerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import com.robertbrooks.actionbarspinnerapp.Fragments.FeaturedFragment;
import com.robertbrooks.actionbarspinnerapp.Fragments.ImagesFragment;
import com.robertbrooks.actionbarspinnerapp.Fragments.NewsFragment;
import com.robertbrooks.actionbarspinnerapp.Fragments.SettingsFragment;


public class MainActivity extends ActionBarActivity implements ActionBar.OnNavigationListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new String[]{
                                getString(R.string.title_section1),
                                getString(R.string.title_section2),
                                getString(R.string.title_section3),
                                getString(R.string.title_section4),
                        }),
                this);


        // get data over mobile shared Preference
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean mobileData = prefs.getBoolean("MOBILE_DATA", true);
        // run wifiCheck if data over mobile is disabled
        if (!mobileData) {
            wifiCheck();
        }


    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getSupportActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getSupportActionBar().getSelectedNavigationIndex());
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        Fragment fragObj = null;

        switch (position) {
            case 0:
                fragObj = FeaturedFragment.newInstance(position + 1);
                break;
            case 1:
                fragObj = NewsFragment.newInstance(position + 1);
                break;
            case 2:
                fragObj = ImagesFragment.newInstance(position + 1);
                break;
            case 3:
                fragObj = SettingsFragment.newInstance(position + 1);
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragObj)
                .commit();
        return true;
    }

    // wifi check
    public void wifiCheck() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!wifi.isConnected()) {
            new AlertDialog.Builder(this)
                    .setTitle("Data over mobile turned off")
                    .setMessage("Please enable data over mobile or connect wifi.")
                    .setPositiveButton("OK", null)
                    .show();
        }


    }

}
