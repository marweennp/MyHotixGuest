package com.hotix.myhotixguest.helpers;

import com.hotix.myhotixguest.models.CartItem;
import com.hotix.myhotixguest.models.Event;
import com.hotix.myhotixguest.models.HotelInfos;
import com.hotix.myhotixguest.models.Order;
import com.hotix.myhotixguest.models.Pax;
import com.hotix.myhotixguest.models.RestaurantReservation;
import com.hotix.myhotixguest.models.Reveil;
import com.hotix.myhotixguest.models.Slide;
import com.hotix.myhotixguest.models.StartData;

import java.util.ArrayList;

public class ConstantConfig {

    /********************** *****************( Final )************************  *******************/

    // Google maps api url
    public static final String BASE_URL_2 = "https://maps.googleapis.com/";

    // HNGAPI version
    public static final String API_VERSION = "v0";
    //public static final String API_VERSION = "v1";

    // Terms of service url
    public static final String TERMS_OF_SERVICE_URL = "https://termsfeed.com/blog/add-i-agree-terms-checkbox/";

    // Google MAps API Key
    public static final String G_MAP_API_KEY = "AIzaSyDpqH7ciz4f0tE-jixmz7QTXm8hmNzG9s4";
    public static final String G_PLACES_API_KEY = "AIzaSyBCaBLHuK_snYuADXjOas2XElNtZ1eGIZI";

    //HOTEL CONFIG BASE URL
    public static final String CONFIG_BASE_URL = "http://41.228.21.123:99/"; // HOTIX Public
    //public static final String CONFIG_BASE_URL = "http://192.168.0.6:99/"; // HOTIX Local

    //FINAL APP ID
    public static final String FINAL_APP_ID = "1"; // MyGuest

    //FINAL HOTEL CODE
    public static final String FINAL_HOTEL_CODE = "9999"; // 9999 HotixDev server
    //public static final String FINAL_HOTEL_CODE = "9998"; // 9998 MT-PC
    //public static final String FINAL_HOTEL_CODE = "9997"; // 9997 MB-PC
    //public static final String FINAL_HOTEL_CODE = "9996"; // 9997 PC-JALAL
    //public static final String FINAL_HOTEL_CODE = "9995"; // 9997 PC-SAIF
    //public static final String FINAL_HOTEL_CODE = "3350"; // 3350 PALM CLUB HOTEL
    //public static final String FINAL_HOTEL_CODE = "1865"; // 1865 LE SULTAN HOTEL
    //public static final String FINAL_HOTEL_CODE = "3350"; // 3350 CONCORDE HOTEL
    //public static final String FINAL_HOTEL_CODE = "1701"; // 1701 LE ROYAL HOTEL HAMMAMET

    /***************************************(Non Finol )*******************************************/

    //BASE URL
    public static String BASE_URL = "";

    // Notification ON/OFF
    public static boolean RECEIVE_NOTIFICATION = true;

    //this will be loaded on app startup
    public static StartData GLOBAL_START_DATA = new StartData();

    //GLOBAL_EVENT
    public static Event GLOBAL_EVENT = new Event();

    //GLOBAL_REVEIL
    public static Reveil GLOBAL_REVEIL = new Reveil();

    //GLOBAL_RESTO_RESA
    public static RestaurantReservation GLOBAL_RESTO_RESA = new RestaurantReservation();

    //GLOBAL_SLIDES and GLOBAL_INFOS
    public static ArrayList<Slide> GLOBAL_SLIDES = new ArrayList<>();
    public static String GLOBAL_INFOS = "";

    //Cart Global
    public static ArrayList<CartItem> GLOBAL_CART = new ArrayList<>();

    //Orde Global
    public static Order GLOBAL_ORDER = new Order();

    //Pax List Global
    public static ArrayList<Pax> GLOBAL_PAX_LIST = new ArrayList<>();

    //Hotel Infos
    public static HotelInfos GLOBAL_HOTEL_INFOS = new HotelInfos();

    //true if app start from message notification
    public static boolean HAVE_MESSAGE_NOTIFICATION = false;
    //true if app start from complaint notification
    public static boolean HAVE_COMPLAINT_NOTIFICATION = false;

}
