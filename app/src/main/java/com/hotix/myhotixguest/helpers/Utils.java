package com.hotix.myhotixguest.helpers;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.hotix.myhotixguest.R;

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
    private static final String DICTIO = ALPHA_CAPS + ALPHA + NUMERIC + SPECIAL_CHARS;

    /**
     * Method will generate random string
     *
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

        String text = "<font color=" + color1 + ">" + st1 + "</font> <font color=" + color2 + "><b>" + st2 + "</b></font>" + "<font color=" + color1 + "> " + st3;

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

    /**
     ***********************************************************************************************
     */

    /**
     * String Empty Or Null (String)
     * EX stringEmptyOrNull("hello")
     *
     * @param str //the String to check for null or empty value
     * @return true if the String is !null & !empty false if not
     */
    public static boolean stringEmptyOrNull(String str) {

        if(str != null && !str.isEmpty()) { return false; }
        return true;
    }

    /**
     * Date formatter (String, String, String)
     * EX dateFormater("2000-01-01'T'00:00:00", "yyyy-MM-dd'T'hh:mm:ss", "dd MMM yyyy")
     *
     * @param date       //the original date to format
     * @param fromFormat //the original date string format EX "yyyy-MM-dd'T'hh:mm:ss"
     * @param toFormat   //the string format to transform to EX "dd MMM yyyy"
     * @return the String Date
     */
    public static String dateFormater(String date, String fromFormat, String toFormat) {
        SimpleDateFormat sdf_from = new SimpleDateFormat(fromFormat);
        SimpleDateFormat sdf_to = new SimpleDateFormat(toFormat);
        Date result;
        String dateResult = "";
        try {
            result = sdf_from.parse(date);
            dateResult = sdf_to.format(result);
        } catch (Exception e) {
        }
        return dateResult;
    }

    /**
     * Date Colored (String, String, String)
     * EX dateFormater("2000-01-01'T'00:00:00", "yyyy-MM-dd'T'hh:mm:ss", "dd MMM yyyy")
     *
     * @param date       //the original date to format
     * @param col_1      //coler 1 default #757575
     * @param col_1      //coler 2 default #424242
     * @param fromFormat //the original date string format EX "yyyy-MM-dd'T'hh:mm:ss"
     * @param full       //if tru return "dd MMM yyyy" else return "dd MMM "
     * @return the String Colered Date
     */
    public static String dateColored(String date, String col_1, String col_2, String fromFormat, boolean full) {

        String color1 = "#9E9E9E";//default color
        String color2 = "#757575";//default color

        SimpleDateFormat sdf_from = new SimpleDateFormat(fromFormat);
        SimpleDateFormat sdf_d = new SimpleDateFormat("dd");
        SimpleDateFormat sdf_m = new SimpleDateFormat("MMM");
        SimpleDateFormat sdf_y = new SimpleDateFormat("yyyy");
        Date result;
        String dateResult = "";
        String st_d = "";
        String st_m = "";
        String st_y = "";

        if (!stringEmptyOrNull(col_1)) { color1 = col_1; }

        if (!stringEmptyOrNull(col_2)) { color2 = col_2; }

        try {
            result = sdf_from.parse(date);
            st_d = sdf_d.format(result);
            st_m = sdf_m.format(result);
            st_y = sdf_y.format(result);
        } catch (Exception e) {
        }

        if (full) {
            dateResult = "<font color=" + color1 + ">" + st_d + "</font> <font color=" + color2 + "><b>" + st_m + "</b></font>" + "<font color=" + color1 + "> " + st_y;
        } else {
            dateResult = "<font color=" + color1 + ">" + st_d + "</font> <font color=" + color2 + "><b>" + st_m + "</b></font>";
        }


        return dateResult;
    }

}
