package com.hotix.myhotixguest.helpers;

import com.hotix.myhotixguest.models.CartItem;
import com.hotix.myhotixguest.models.Event;
import com.hotix.myhotixguest.models.Order;
import com.hotix.myhotixguest.models.Pax;
import com.hotix.myhotixguest.models.Produit;
import com.hotix.myhotixguest.models.Slide;
import com.hotix.myhotixguest.models.StartData;

import java.util.ArrayList;

public class Settings {

    //public static final String BASE_URL = "http://196.203.219.164/";
    //public static final String BASE_URL = "http://192.168.0.110/";
    public static final String BASE_URL = "http://192.168.0.109/";

    public static final String BASE_URL_2 = "https://maps.googleapis.com/";

    // Terms of service url
    public static final String TERMS_OF_SERVICE_URL = "https://termsfeed.com/blog/add-i-agree-terms-checkbox/";

    // Web Site URL
    public static final String WEB_SITE_URL = "https://www.leroyal.com/en/AFRICA/hammamet-tunisia";

    // Google MAps API Key
    public static final String G_MAP_API_KEY = "AIzaSyDpqH7ciz4f0tE-jixmz7QTXm8hmNzG9s4";
    public static final String G_PLACES_API_KEY = "AIzaSyBCaBLHuK_snYuADXjOas2XElNtZ1eGIZI";

    //this will be loaded on app startup
    public static StartData GLOBAL_START_DATA = new StartData();

    //GLOBAL_EVENT
    public static Event GLOBAL_EVENT = new Event();

    //GLOBAL_SLIDES and GLOBAL_INFOS
    public static ArrayList<Slide> GLOBAL_SLIDES = new ArrayList<>();
    public static String GLOBAL_INFOS = "";

    //Cart Global
    public static ArrayList<CartItem> GLOBAL_CART = new ArrayList<>();

    //Orde Global
    public static Order GLOBAL_ORDER = new Order();

    //Pax List Global
    public static ArrayList<Pax> GLOBAL_PAX_LIST = new ArrayList<>();

    //true if app start from message notification
    public static boolean HAVE_MESSAGE_NOTIFICATION = false;
    //true if app start from complaint notification
    public static boolean HAVE_COMPLAINT_NOTIFICATION = false;

}
