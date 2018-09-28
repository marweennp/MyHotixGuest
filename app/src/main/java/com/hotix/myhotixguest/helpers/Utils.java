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
import java.util.Random;

public class Utils {

    // Different dictionaries used for random password
    private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";
    private static final String DICTIO = ALPHA_CAPS + ALPHA + NUMERIC + SPECIAL_CHARS ;

    /** Method will generate random string
     * @param len //the length of the random string
     * @return the random password
     */
    public static String generatePassword(int len) {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(DICTIO.length());
            result += DICTIO.charAt(index);
        }
        return result;
    }

    //Date formatter from "yyyy-MM-dd'T'hh:mm:ss" to "dd MMM yyyy"
    public static String dateFormater1(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date result;
        try {
            result = df.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.format(result);
        } catch (Exception e) {
        }

        return "dd MMM yyyy";
    }

    //Date formatter from "dd/MM/yyyy" to "dd MMM yyyy"
    public static String dateFormater2(String date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date result;
        try {
            result = df.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.format(result);
        } catch (Exception e) {
        }

        return "dd MMM yyyy";
    }

    //Date formatter from "dd/MM/yyyy" to "yyyyMMdd"
    public static String dateFormater3(String date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date result;
        try {
            result = df.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.format(result);
        } catch (Exception e) {
        }

        return "19990101";
    }

    //Date formatter from "yyyy-MM-dd'T'hh:mm:ss" to "dd/MM/yyyy"
    public static String dateFormater4(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date result;
        try {
            result = df.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(result);
        } catch (Exception e) {
        }

        return "dd MMM yyyy";
    }

    //Date formatter from "yyyyMMdd" to "yyyy-MM-dd'T'hh:mm:ss"
    public static String dateFormater5(String date) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date result;
        try {
            result = df.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
            return sdf.format(result);
        } catch (Exception e) {
        }

        return "dd MMM yyyy";
    }

    //Time formatter from "hh:mm:ss" to "hh:mm"
    public static String timeFormater1(String date) {
        DateFormat df = new SimpleDateFormat("hh:mm:ss");
        Date result;
        try {
            result = df.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            return sdf.format(result);
        } catch (Exception e) {
            return e.toString();
        }
    }

    //Time formatter from "yyyy-MM-dd'T'hh:mm:ss" to "hh:mm"
    public static String timeFormater2(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date result;
        try {
            result = df.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            return sdf.format(result);
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
        String st3 = date.substring(7, date.length());

        String text = "<font color=" + color1 + ">" + st1 + "</font> <font color=" + color2 + "><b>" + st2 + "</b></font>" + "<font color=" + color1 + "> "+st3;

        return text;
    }

    //Date tow colors "dd MMM" white & colorPrimary
    public static String newDateTowColors(String date, Context context) {

        String color1 = "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.white)).substring(2, 8);
        String color2 = "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.colorPrimary)).substring(2, 8);

        String st1 = date.substring(0, 2);
        String st2 = date.substring(3, 6);
        String st3 = date.substring(7, date.length());

        String text = "<font color=" + color1 + ">" + st1 + "</font> <font color=" + color2 + "><b>" + st2 + "</b></font>" + "<font color=" + color1 + "> ";

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
