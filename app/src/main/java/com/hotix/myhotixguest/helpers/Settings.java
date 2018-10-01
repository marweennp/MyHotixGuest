package com.hotix.myhotixguest.helpers;

import com.hotix.myhotixguest.models.Event;

public class Settings {

    //public static final String BASE_URL = "http://196.203.219.164/";
    //public static final String BASE_URL = "http://192.168.0.110/";
    public static final String BASE_URL = "http://192.168.0.109/";

    // terms of service url
    public static final String TERMS_OF_SERVICE_URL = "https://termsfeed.com/blog/add-i-agree-terms-checkbox/";

    //event global
    public static Event GLOBAL_EVENT = new Event();

    //true if app start from message notification
    public static boolean HAVE_MESSAGE_NOTIFICATION = false;
    //true if app start from complaint notification
    public static boolean HAVE_COMPLAINT_NOTIFICATION = false;

}
