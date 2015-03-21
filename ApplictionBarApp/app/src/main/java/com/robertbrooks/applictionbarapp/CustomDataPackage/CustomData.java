package com.robertbrooks.applictionbarapp.CustomDataPackage;

/**
 * Created by Bob on 3/21/2015.
 */
public class CustomData {
    public String dataString;

    public CustomData(String id) {
        this.dataString = id;
    }

    @Override
    public String toString() {
        return dataString;
    }
}
