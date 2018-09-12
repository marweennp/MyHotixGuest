package com.hotix.myhotixguest.helpers;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.hotix.myhotixguest.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    //public static final String BASE_URL = "http://196.203.219.164/HNGAPI/api/";
    public static final String BASE_URL = "http://192.168.0.109/HNGAPI/api/";

    //Date formatter from "yyyy-MM-dd'T'hh:mm:ss" to "dd MMM yyyy"
    public static String dateFormater(String date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date result;
        try {
            result = df.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.format(result).toString();
        }catch (Exception e){}

        return "dd MMM yyyy";
    }

    //Date formatter from "dd/mm/yyyy" to "dd MMM yyyy"
    public static String newDateFormater(String date){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date result;
        try {
            result = df.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.format(result).toString();
        }catch (Exception e){}

        return "dd MMM yyyy";
    }

    //Calculate the number of days between two dates "yyyy-MM-dd'T'hh:mm:ss"
    public static String calculateDaysBetween(String startDate, String endDate ){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date start;
        Date end;
        try {
            start = df.parse(startDate);
            end = df.parse(endDate);

            return Long.toString((end.getTime() - start.getTime())/ (24 * 60 * 60 * 1000));
        }catch (Exception e){}

        return "0";
    }

    //Calculate the number of days between two dates "dd/MM/yyyy"
    public static String newCalculateDaysBetween(String startDate, String endDate ){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date start;
        Date end;
        try {
            start = df.parse(startDate);
            end = df.parse(endDate);

            return Long.toString((end.getTime() - start.getTime())/ (24 * 60 * 60 * 1000));
        }catch (Exception e){}

        return "0";
    }

    /*Show a SnackBar with msg*/
    public static void showSnackbar (View view, String msg) {
        final Snackbar snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE);
            snackBar.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackBar.dismiss();
                }
            }).show();

    }


}
