package com.robertbrooks.project1;

import android.app.Activity;
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

        if (savedInstanceState == null)
        {
            Master frag = Master.newInstance();

            getFragmentManager().beginTransaction()
                    .replace(R.id.container1, frag, Master.TAG).commit();
        }
    }

    @Override
    public void populateDisplay(String text){
        Log.i(TAG, "Dipslaying" + text);

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


}
