package com.hotix.myhotixguest.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.models.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    //public static final String BASE_URL = "http://196.203.219.164/";
    public static final String BASE_URL = "http://192.168.0.109/";
    //public static final String BASE_URL = "http://192.168.0.110/";

    //event global
    public static Event GLOBAL_EVENT = new Event();

    //Date formatter from "yyyy-MM-dd'T'hh:mm:ss" to "dd MMM yyyy"
    public static String dateFormater(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date result;
        try {
            result = df.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.format(result).toString();
        } catch (Exception e) {
        }

        return "dd MMM yyyy";
    }

    //Date formatter from "dd/mm/yyyy" to "dd MMM yyyy"
    public static String newDateFormater(String date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date result;
        try {
            result = df.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.format(result).toString();
        } catch (Exception e) {
        }

        return "dd MMM yyyy";
    }

    //Date formatter from "yyyy-MM-dd'T'hh:mm:ss" to "dd MMM yyyy"
    public static String timeFormater(String date) {
        DateFormat df = new SimpleDateFormat("hh:mm:ss");
        Date result;
        try {
            result = df.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            return sdf.format(result).toString();
        } catch (Exception e) {
            return e.toString();
        }
    }

    //Calculate the number of days between two dates "yyyy-MM-dd'T'hh:mm:ss"
    public static String calculateDaysBetween(String startDate, String endDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date start;
        Date end;
        try {
            start = df.parse(startDate);
            end = df.parse(endDate);

            return Long.toString((end.getTime() - start.getTime()) / (24 * 60 * 60 * 1000));
        } catch (Exception e) {
        }

        return "0";
    }

    //Calculate the number of days between two dates "dd/MM/yyyy"
    public static String newCalculateDaysBetween(String startDate, String endDate) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date start;
        Date end;
        try {
            start = df.parse(startDate);
            end = df.parse(endDate);

            return Long.toString((end.getTime() - start.getTime()) / (24 * 60 * 60 * 1000));
        } catch (Exception e) {
        }

        return "0";
    }

    //Calculate the number of days between two dates "yyyy-MM-dd'T'hh:mm:ss"
    public static String fromTodayToDate(String date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String toDay = df.format(Calendar.getInstance().getTime());
        Date start;
        Date end;
        try {
            start = df.parse(toDay);
            end = df.parse(date);
            return Long.toString((end.getTime() - start.getTime()) / (24 * 60 * 60 * 1000));
        } catch (Exception e) {
            return e.toString();
        }

    }

    /*Show a SnackBar with msg*/
    public static void showSnackbar(View view, String msg) {
        final Snackbar snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE);
        snackBar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackBar.dismiss();
            }
        }).show();

    }

    //Date tow colors "dd MMM yyyy" white & colorPrimary
    public static String dateTowColors(String date, Context context) {

        String color1 = "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.white)).substring(2, 8);
        String color2 = "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.colorPrimary)).substring(2, 8);

        String st1 = date.substring(0, 2);
        String st2 = date.substring(3, 6);
        String st3 = date.substring(7, 11);

        String text = "<font color=" + color1 + ">" + st1 + "</font> <font color=" + color2 + "><b>" + st2 + "</b></font>" + "<font color=" + color1 + "> " + st3;

        return text;
    }

    //SigneUp Text TowColors white & colorPrimary
    public static String signeUpTextTowColors(String text1, String text2, Context context) {

        String color1 = "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.white)).substring(2, 8);
        String color2 = "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.colorPrimary)).substring(2, 8);

        String text = "<font color=" + color1 + ">" + text1 + "</font> <font color=" + color2 + "><b>" + text2 + "</b></color>";

        return text;
    }

}
