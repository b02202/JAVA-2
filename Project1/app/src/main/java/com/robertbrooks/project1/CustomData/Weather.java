package com.robertbrooks.project1.CustomData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bob on 3/3/2015.
 */

/*public class Weather implements Serializable {
    private static long serialVersionUID = 8775654232565833554L;  //-7791154359356162736L;*/

public class Weather {
   /* private static long serialVersionUID = 8775654232565833554L;  //-7791154359356162736L;*/


    private String temp;
    private String forecast;
    private String something;
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

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public String getSomething() {
        return something;
    }

    public void setSomething(String something) {
        this.something = something;
    }


}
