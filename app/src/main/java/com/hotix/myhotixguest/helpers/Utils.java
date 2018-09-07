package com.hotix.myhotixguest.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    //public static final String BASE_URL = "http://196.203.219.164/HNGAPI/api/";
    public static final String BASE_URL = "http://192.168.0.109/HNGAPI/api/";

    public static String dateFormater(String date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date result;
        try {
            result = df.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.format(result);
        }catch (Exception e){}

        return "dd MMM yyyy";
    }


}
