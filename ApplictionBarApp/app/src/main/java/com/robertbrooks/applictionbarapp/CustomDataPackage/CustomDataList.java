package com.robertbrooks.applictionbarapp.CustomDataPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bob on 3/21/2015.
 */
public class CustomDataList {

    private List<CustomData> dataArray = new ArrayList<CustomData>();

    public List<CustomData> getDataArray() {
        return dataArray;
    }

   /* public CustomDataList(String item) {
        addItem(new CustomData(item));
    }
*/
    private void addItem(CustomData item) {
        dataArray.add(item);
    }
}
