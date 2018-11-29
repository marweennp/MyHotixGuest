package com.hotix.myhotixguest.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings {

    //Shared Preferences Keys
    // Booleans
    public static final String KEY_firstStart = "firstStart";
    public static final String KEY_receiveNotification = "receiveNotification";
    public static final String KEY_CONFIGURED = "configured";
    public static final String KEY_PUBLIC_IP_ENABLED = "publicIpEnabled";
    public static final String KEY_LOCAL_IP_ENABLED = "localIpEnabled";
    public static final String KEY_LOCAL_IP_DEFAULT = "localIpDefault";

    //String
    public static final String KEY_PUBLIC_IP = "publicIp";
    public static final String KEY_LOCAL_IP = "localIp";
    public static final String KEY_PUBLIC_BASE_URL = "publicBaseUrl";
    public static final String KEY_LOCAL_BASE_URL = "localBaseUrl";

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

    public boolean getReceiveNotification() { return pref.getBoolean(KEY_receiveNotification, true); }

    public void setReceiveNotification(boolean receiveNotification) {
        editor.putBoolean(KEY_receiveNotification, receiveNotification);
        editor.commit();
    }

    public boolean getConfigured() {
        return pref.getBoolean(KEY_CONFIGURED, false);
    }

    public void setConfigured(boolean configured) {
        editor.putBoolean(KEY_CONFIGURED, configured);
        editor.commit();
    }

    public boolean getPublicIpEnabled() {
        return pref.getBoolean(KEY_PUBLIC_IP_ENABLED, false);
    }

    public void setPublicIpEnabled(boolean publicIpEnabled) {
        editor.putBoolean(KEY_PUBLIC_IP_ENABLED, publicIpEnabled);
        editor.commit();
    }

    public boolean getLocalIpEnabled() {
        return pref.getBoolean(KEY_LOCAL_IP_ENABLED, false);
    }

    public void setLocalIpEnabled(boolean localIpEnabled) {
        editor.putBoolean(KEY_LOCAL_IP_ENABLED, localIpEnabled);
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

    public boolean getLocalIpDefault() {
        return pref.getBoolean(KEY_LOCAL_IP_DEFAULT, true);
    }

    public void setLocalIpDefault(boolean localIpDefault) {
        editor.putBoolean(KEY_LOCAL_IP_DEFAULT, localIpDefault);
        editor.commit();
    }

    // Strings
    public String getPublicIp() {
        return pref.getString(KEY_PUBLIC_IP, "xxx.xxx.xxx.xxx");
    }

    public void setPublicIp(String publicIp) {
        editor.putString(KEY_PUBLIC_IP, publicIp);
        editor.commit();
    }

    public String getLocalIp() {
        return pref.getString(KEY_LOCAL_IP, "xxx.xxx.xxx.xxx");
    }

    public void setLocalIp(String localIp) {
        editor.putString(KEY_LOCAL_IP, localIp);
        editor.commit();
    }

    public String getPublicBaseUrl() {
        return pref.getString(KEY_PUBLIC_BASE_URL, "http://xxx.xxx.xxx.xxx/");
    }

    public void setPublicBaseUrl(String publicBaseUrl) {
        editor.putString(KEY_PUBLIC_BASE_URL, publicBaseUrl);
        editor.commit();
    }

    public String getLocalBaseUrl() {
        return pref.getString(KEY_LOCAL_BASE_URL, "http://xxx.xxx.xxx.xxx/");
    }

    public void setLocalBaseUrl(String localBaseUrl) {
        editor.putString(KEY_LOCAL_BASE_URL, localBaseUrl);
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
