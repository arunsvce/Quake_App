package com.example.android.quakereport.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.quakereport.QuakeFlavor;
import com.example.android.quakereport.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by shanma1 on 3/1/17.
 */

public class QuakeListAdapter extends ArrayAdapter<QuakeFlavor> {


    private static final String LOG_TAG =  QuakeListAdapter.class.getSimpleName();

    private String fullLocation;
    private String offset;
    private String location;



    public QuakeListAdapter(Context context, int resource, ArrayList<QuakeFlavor> quakeItems) {

        super(context, resource, quakeItems);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.quake_list_item, parent, false);

        }
        QuakeFlavor flavors = getItem(position);

        if (flavors!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long

            TextView magTextView = (TextView) convertView.findViewById(R.id.quake_magnitude);
            DecimalFormat formatter = new DecimalFormat("0.0");
            String magnitude = formatter.format(flavors.getMagnitude());
            magTextView.setText(magnitude);

            GradientDrawable magnitudeCircle = (GradientDrawable) magTextView.getBackground();
            int magnitudeColor = getMagnitudeColor(flavors.getMagnitude());
            magnitudeCircle.setColor(magnitudeColor);

            TextView offTextView = (TextView) convertView.findViewById(R.id.quake_offset);
            TextView locTextView = (TextView) convertView.findViewById(R.id.quake_location);

            fullLocation =  flavors.getLocation();

            if(fullLocation.contains("of")){

                String[] loc_parts = fullLocation.split("(?<=of)");
                offset = loc_parts[0];
                location = loc_parts[1];

            }else{

                offset = "Near the";
                location = fullLocation;
            }
            offTextView.setText(offset);
            locTextView.setText(location);

//            Log.e(TAG, loc_parts[0]);
//            Log.e(TAG, loc_parts[1]);
            //locTextView.setText(flavors.getLocation());


            Date dateObject = new Date(flavors.getTimestamp());
            TextView dateTextView = (TextView) convertView.findViewById(R.id.quake_date);
            dateTextView.setText(formatDate(dateObject));

            TextView timeTextView = (TextView) convertView.findViewById(R.id.quake_time);
            timeTextView.setText(formatTime(dateObject));

        }

        return convertView;
    }

//    public int getMagnitudeColor(double magValue){
//
//        if(magValue >= 0 && magValue <= 2  ){
//
//            myColor = getContext().getColor(R.color.magnitude1);
//            return myColor;
//
//        }else if(magValue > 2 && magValue <= 3 ){
//
//            myColor = getContext().getColor(R.color.magnitude2);
//            return myColor;
//
//        }else if(magValue > 3 && magValue <= 4 ){
//
//            myColor = getContext().getColor(R.color.magnitude3);
//            return myColor;
//
//        }else if(magValue > 4 && magValue <= 5 ){
//
//            myColor = getContext().getColor(R.color.magnitude4);
//            return myColor;
//
//        }else if(magValue > 5 && magValue <= 6 ){
//
//            myColor = getContext().getColor(R.color.magnitude5);
//            return myColor;
//
//        }else if(magValue > 6 && magValue <= 7 ){
//
//            myColor = getContext().getColor(R.color.magnitude6);
//            return myColor;
//
//        }else if(magValue > 7 && magValue <= 8 ){
//
//            myColor = getContext().getColor(R.color.magnitude7);
//            return myColor;
//
//        }else if(magValue > 8 && magValue <= 9 ){
//
//            myColor = getContext().getColor(R.color.magnitude8);
//            return myColor;
//
//        }else if(magValue > 9 && magValue <= 10 ){
//
//            myColor = getContext().getColor(R.color.magnitude9);
//            return myColor;
//        }
//        else{
//
//            myColor = getContext().getColor(R.color.magnitude10plus);
//            return myColor;
//        }
//
//    }

    private int getMagnitudeColor(double magnitude){

        int magnitudeResId;
        int magnitudeFloor = (int)Math.floor(magnitude);

        switch (magnitudeFloor){

            case 0:
            case 1:
                magnitudeResId = R.color.magnitude1;
                break;
            case 2:
                magnitudeResId = R.color.magnitude2;
                break;
            case 3:
                magnitudeResId = R.color.magnitude3;
                break;
            case 4:
                magnitudeResId = R.color.magnitude4;
                break;
            case 5:
                magnitudeResId = R.color.magnitude5;
                break;
            case 6:
                magnitudeResId = R.color.magnitude6;
                break;
            case 7:
                magnitudeResId = R.color.magnitude7;
                break;
            case 8:
                magnitudeResId = R.color.magnitude8;
                break;
            case 9:
                magnitudeResId = R.color.magnitude9;
                break;
            default:
                magnitudeResId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(),magnitudeResId);

    }

    public String formatDate(Date dateObj){


        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");

        return dateFormat.format(dateObj);
    }

    public String formatTime(Date timeObj){

        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

        return timeFormat.format(timeObj);
    }
}

