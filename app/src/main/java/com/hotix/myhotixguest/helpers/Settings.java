package com.hotix.myhotixguest.helpers;

import com.hotix.myhotixguest.models.Event;
import com.hotix.myhotixguest.models.Pax;
import com.hotix.myhotixguest.models.Produit;
import com.hotix.myhotixguest.models.StartData;

import java.util.ArrayList;

public class Settings {

    //public static final String BASE_URL = "http://196.203.219.164/";
    //public static final String BASE_URL = "http://192.168.0.110/";
    public static final String BASE_URL = "http://192.168.0.109/";

    // terms of service url
    public static final String TERMS_OF_SERVICE_URL = "https://termsfeed.com/blog/add-i-agree-terms-checkbox/";

    //this will be loaded on app startup
    public static StartData GLOBAL_START_DATA = new StartData();

    //event global
    public static Event GLOBAL_EVENT = new Event();

    //Cart Global
    public static ArrayList<Produit> GLOBAL_CART = new ArrayList<>();

    //Pax List Global
    public static ArrayList<Pax> GLOBAL_PAX_LIST = new ArrayList<>();

    //true if app start from message notification
    public static boolean HAVE_MESSAGE_NOTIFICATION = false;
    //true if app start from complaint notification
    public static boolean HAVE_COMPLAINT_NOTIFICATION = false;

}
