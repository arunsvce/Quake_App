package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.android.quakereport.Util.QueryUtils;

import java.util.ArrayList;

/**
 * Created by shanma1 on 4/5/17.
 */

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<QuakeFlavor>>{

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=5&limit=20";

    private String mUrl;

    public EarthquakeLoader(Context context, String url) {

        super(context);
        mUrl = url;
    }

    @Override

    protected void onStartLoading(){

        Log.e(LOG_TAG,"Inisde onStartLoading: 2");

        forceLoad();
    }
    @Override
    public ArrayList<QuakeFlavor> loadInBackground() {

        Log.e(LOG_TAG,"Inisde LoadInBackground: 1");

        if(mUrl == null){

            return null;
        }
        ArrayList<QuakeFlavor> earthquakes = QueryUtils.fetchEarthquakeData(USGS_REQUEST_URL);

        return earthquakes;
    }



}
