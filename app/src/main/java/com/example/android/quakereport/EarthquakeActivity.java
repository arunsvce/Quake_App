/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.quakereport.Util.QueryUtils;
import com.example.android.quakereport.adapter.QuakeListAdapter;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<QuakeFlavor>>{

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    /**
     * URL for earthquake data from the USGS dataset
     */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=5&limit=20";

    private static final int EARTHQUAKE_LOADER_ID = 1;

    public ListView earthquakeListView;
    private TextView emptyView;
    private ProgressBar mProgressBar;

    private   QuakeListAdapter adapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        mContext = getApplicationContext();

        // Create a fake list of earthquake locations.
//        ArrayList<QuakeFlavor> earthquakes = new ArrayList<QuakeFlavor>();
//        earthquakes.add(new QuakeFlavor("7.2","San Francisco","Feb 2, 2016"));
//        earthquakes.add(new QuakeFlavor("6.1","London","July 20, 2015"));
//        earthquakes.add(new QuakeFlavor("3.9","Tokyo","Nov 10, 2014"));
//        earthquakes.add(new QuakeFlavor("5.4","Mexico","May 3, 2014"));
//        earthquakes.add(new QuakeFlavor("2.8","Moscow","Jan 31, 2013"));
//        earthquakes.add(new QuakeFlavor("4.9","Rio de Janeiro","Aug 19, 2012"));
//        earthquakes.add(new QuakeFlavor("1.5","Paris","Oct 30, 2011"));

//        // Kick off an {@link AsyncTask} to perform the network request
//        EarthQuakeAsyncTask task = new EarthQuakeAsyncTask();
//        task.execute(USGS_REQUEST_URL);

        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = (ListView) findViewById(R.id.list);
        emptyView = (TextView)findViewById(R.id.empty_view);
        mProgressBar = (ProgressBar)findViewById(R.id.loading_spinner) ;
        earthquakeListView.setEmptyView(emptyView);

        emptyView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);

        if(QueryUtils.isInternetConnected(mContext)) {

            getLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this);

        }else{

            mProgressBar.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            emptyView.setText("No Internet Connection");
        }
    }

    /**
     * Update the UI with the given earthquake information.
     */
    private void updateUi(final ArrayList<QuakeFlavor> earthquake) {

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                String Url = earthquake.get(position).getUrl();
                Intent urlIntent = new Intent(Intent.ACTION_VIEW);
                urlIntent.setData(Uri.parse(Url));
                startActivity(urlIntent);

            }
        });


        // Create a new {@link ArrayAdapter} of earthquakes
         adapter = new QuakeListAdapter(
                this, android.R.layout.simple_list_item_1, earthquake);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

    }

    @Override
    public Loader<ArrayList<QuakeFlavor>> onCreateLoader(int id, Bundle args) {

        Log.e(LOG_TAG,"Inside OnCreateLoad : 3");

        return new EarthquakeLoader(EarthquakeActivity.this,USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<QuakeFlavor>> loader, ArrayList<QuakeFlavor> data) {


        Log.e(LOG_TAG,"Inside OnLoadFinished : 4");

        emptyView.setText("No Earthquakes Found");

        mProgressBar.setVisibility(View.INVISIBLE);

        if (data != null && ! data.isEmpty()) {

              updateUi(data);

        }else{

            emptyView.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onLoaderReset(Loader<ArrayList<QuakeFlavor>> loader) {

        Log.e(LOG_TAG, "Inside onLoaderReset: 5");

        adapter.clear();

    }


    private class EarthQuakeAsyncTask extends AsyncTask<String, Void, ArrayList<QuakeFlavor>> {

        @Override
        protected ArrayList<QuakeFlavor> doInBackground(String... urls) {

            ArrayList<QuakeFlavor> earthquakes = QueryUtils.fetchEarthquakeData(urls[0]);

            return earthquakes;
        }

        /**
         * Update the screen with the given earthquake (which was the result of the
         * {@link EarthQuakeAsyncTask}).
         */

        @Override
        protected void onPostExecute(ArrayList<QuakeFlavor> earthquake) {
            if (earthquake == null) {
                return;
            }

            updateUi(earthquake);
        }
    }
}
