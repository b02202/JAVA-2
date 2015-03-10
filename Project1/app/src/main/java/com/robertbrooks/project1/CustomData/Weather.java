/*Robert Brooks
* Weather.java*/

package com.robertbrooks.project1.CustomData;

import java.io.Serializable;

/**
 * Created by Bob on 3/3/2015.
 */

public class Weather implements Serializable {
    private static long serialVersionUID = 8775654232565833554L;


    private String temp;
    private String CurrentCond;
    private String cityName;
    //private String something;
    private String zip;




    // Getters / Setters
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getCurrentCond() {
        return CurrentCond;
    }

    public void setCurrentCond(String currentCond) {
        CurrentCond = currentCond;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
