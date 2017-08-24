package com.example.android.quakereport;

/**
 * Created by shanma1 on 3/1/17.
 */

public class QuakeFlavor {


    // Magnitude of the Quake

    private double mMagnitude;

    // Location of the Quake

    private String mLocation , mUrl ;

    // Timestamp of the Quake
    private long mTimeInMilliseconds;


    public QuakeFlavor(double vMag , String vLoc, long vDate,String vUrl  ){

        this.mMagnitude = vMag;
        this.mLocation = vLoc;
        this.mTimeInMilliseconds = vDate;
        this.mUrl = vUrl;
    }

    public double getMagnitude(){

        return  mMagnitude;
    }

    public String getLocation(){

        return  mLocation;
    }
    public long getTimestamp(){

        return  mTimeInMilliseconds;
    }
    public String getUrl(){

        return  mUrl;
    }
}
