package com.robertbrooks.project3;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.robertbrooks.project3.CustomData.CarData;
import com.robertbrooks.project3.Fragments.AddFragment;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    AddFragment addFrag;
    ArrayList<CarData> cData;
    //public static String fileName = "carList";
    String fileName;
    Bundle bObj;
    private static String TAG = "Project3.TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bObj = new Bundle();
        cData = new ArrayList<CarData>();

        loadData();


        if (savedInstanceState == null) {

            addFrag = AddFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.addFragContainer, addFrag, AddFragment.TAG )
                    .commit();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void saveData(View v) {


        TextView carMake = (TextView) findViewById(R.id.makeText);
        TextView carModel = (TextView) findViewById(R.id.modelText);
        TextView carColor = (TextView) findViewById(R.id.colorText);

        CarData carInfo = new CarData();
        //cData = new ArrayList<>();
        String cMake = carMake.getText().toString();
        String cModel = carModel.getText().toString();
        String cColor = carColor.getText().toString();

        String carFile = cMake + cModel;

        carInfo.setMake(cMake);
        carInfo.setModel(cModel);
        carInfo.setColor(cColor);
        cData.add(carInfo);
        carInfo.saveFile(cData, carFile, this);


        bObj.putSerializable(carFile, cData);


    }


    public void openActivity(View v) {

        Intent intent = new Intent(this, ListActivity.class);

        for (int i = 0; i < cData.size();i++) {
            String keyString = cData.get(i).getMake() + cData.get(i).getModel();
            bObj.putSerializable(keyString, cData);
        }
        intent.putExtras(bObj);
        startActivity(intent);
    }

    public void loadData() {


        CarData carInfo;

        String[] fileNames = getApplicationContext().fileList();

        if (fileNames.length != 0) {

            for (String file : fileNames) {

                carInfo = CarData.readFile(file, this);
                Log.d(TAG, "Line 115:" + carInfo.getMake());
                cData.add(carInfo);

            }

        }

    }





}
