package com.hotix.myhotixguest.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings {

    //Shared Preferences Keys
    // Booleans
    public static final String KEY_firstStart = "firstStart";
    public static final String KEY_receiveNotification = "receiveNotification";

    // Integers
    public static final String KEY_nearbyRadius = "nearbyRadius";


    // Sharedpref file name
    private static final String PREF_NAME = "MyGuestSettings";
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;


    // Constructor
    public Settings(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**********************************(  Getters & Setters )**************************************/
    // Booleans
    public boolean getFirstStart() {
        return pref.getBoolean(KEY_firstStart, true);
    }

    public void setFirstStart(boolean firstStart) {
        editor.putBoolean(KEY_firstStart, firstStart);
        editor.commit();
    }

    public boolean getReceiveNotification() {
        return pref.getBoolean(KEY_receiveNotification, true);
    }

    public void setReceiveNotification(boolean receiveNotification) {
        editor.putBoolean(KEY_receiveNotification, receiveNotification);
        editor.commit();
    }

    // Integers
    public Integer getNearbyRadius() {
        return pref.getInt(KEY_nearbyRadius, 500);
    }

    public void setNearbyRadius(Integer nearbyRadius) {
        editor.putInt(KEY_nearbyRadius, nearbyRadius);
        editor.commit();
    }

    /*****************************************(  _______  )****************************************/
    //Clear Settings details
    public void clearSettingsDetails() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

}
