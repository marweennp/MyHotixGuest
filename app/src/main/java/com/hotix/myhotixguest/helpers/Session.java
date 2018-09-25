package com.hotix.myhotixguest.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    // All Shared Preferences Keys
    // Resa
    public static final String KEY_iSResident = "iSResident";
    public static final String KEY_HasHistory = "HasHistory";
    public static final String KEY_EtatResa = "EtatResa";
    public static final String KEY_ResaId = "ResaId";
    public static final String KEY_DateArrivee = "DateArrivee";
    public static final String KEY_DateDepart = "DateDepart";
    public static final String KEY_Chambre = "Chambre";
    public static final String KEY_FactureId = "FactureId";
    public static final String KEY_FactureAnnee = "FactureAnnee";

    //Profile
    public static final String KEY_ClientId = "ClientId";
    public static final String KEY_UserName = "UserName";
    public static final String KEY_UserPassword = "UserPassword ";
    public static final String KEY_Nom = "Nom";
    public static final String KEY_Prenom = "Prenom";
    public static final String KEY_Email = "Email";
    public static final String KEY_Phone = "Phone";
    public static final String KEY_BirthDay = "BirthDay";
    public static final String KEY_Address = "Address";
    public static final String KEY_NationaliteId = "NationaliteId";
    public static final String KEY_NationaliteName = "NationaliteName";

    //Session
    public static final String KEY_IsLoggedIn = "IsLoggedIn";
    public static final String KEY_RememberMe = "RememberMe";

    //FireBase
    public static final String KEY_NewToken = "NewToken";
    public static final String KEY_FCMToken = "FCMToken ";


    // Sharedpref file name
    private static final String PREF_NAME = "GuestPref";
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;


    // Constructor
    public Session(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create new guest session

    public void createNewGuestSession(boolean iSResident,
                                      boolean HasHistory,
                                      boolean IsLoggedIn,
                                      boolean RememberMe,
                                      String UserName,
                                      String UserPassword,
                                      String DateArrivee,
                                      String DateDepart,
                                      String Chambre,
                                      String Email,
                                      String Nom,
                                      String Prenom,
                                      String Phone,
                                      String BirthDay,
                                      String Address,
                                      String NationaliteName,
                                      Integer EtatResa,
                                      Integer ResaId,
                                      Integer ClientId,
                                      Integer FactureId,
                                      Integer FactureAnnee,
                                      Integer NationaliteId) {

        // Booleans
        editor.putBoolean(KEY_iSResident, iSResident);
        editor.putBoolean(KEY_HasHistory, HasHistory);
        editor.putBoolean(KEY_IsLoggedIn, IsLoggedIn);
        editor.putBoolean(KEY_RememberMe, RememberMe);

        // Strings
        editor.putString(KEY_UserName, UserName);
        editor.putString(KEY_UserPassword, UserPassword);
        editor.putString(KEY_DateArrivee, DateArrivee);
        editor.putString(KEY_DateDepart, DateDepart);
        editor.putString(KEY_Chambre, Chambre);
        editor.putString(KEY_Email, Email);
        editor.putString(KEY_Nom, Nom);
        editor.putString(KEY_Prenom, Prenom);
        editor.putString(KEY_Phone, Phone);
        editor.putString(KEY_BirthDay, BirthDay);
        editor.putString(KEY_Address, Address);
        editor.putString(KEY_NationaliteName, NationaliteName);

        // Integers
        editor.putInt(KEY_EtatResa, EtatResa);
        editor.putInt(KEY_ResaId, ResaId);
        editor.putInt(KEY_ClientId, ClientId);
        editor.putInt(KEY_FactureId, FactureId);
        editor.putInt(KEY_FactureAnnee, FactureAnnee);
        editor.putInt(KEY_NationaliteId, NationaliteId);

        // commit changes
        editor.commit();
    }

    /**********************************(  Getters & Setters )*************************************/
    // Booleans
    public boolean getISResident() {
        return pref.getBoolean(KEY_iSResident, false);
    }

    public boolean getHasHistory() {
        return pref.getBoolean(KEY_HasHistory, false);
    }

    public boolean getIsLoggedIn() {
        return pref.getBoolean(KEY_IsLoggedIn, false);
    }

    public boolean getRememberMe() {
        return pref.getBoolean(KEY_RememberMe, false);
    }

    public boolean getNewToken() {
        return pref.getBoolean(KEY_NewToken, false);
    }

    public void setNewToken(boolean newToken) {
        editor.putBoolean(KEY_NewToken, newToken);
        editor.commit();
    }

    // Strings
    public String getUserName() {
        return pref.getString(KEY_UserName, null);
    }

    public String getUserPassword() {
        return pref.getString(KEY_UserPassword, null);
    }

    public String getDateArrivee() {
        return pref.getString(KEY_DateArrivee, null);
    }

    public String getDateDepart() {
        return pref.getString(KEY_DateDepart, null);
    }

    public String getChambre() {
        return pref.getString(KEY_Chambre, null);
    }

    public String getEmail() {
        return pref.getString(KEY_Email, null);
    }

    public String getNom() {
        return pref.getString(KEY_Nom, null);
    }

    public String getPrenom() {
        return pref.getString(KEY_Prenom, null);
    }

    public String getPhone() {
        return pref.getString(KEY_Phone, null);
    }

    public String getBirthDay() {
        return pref.getString(KEY_BirthDay, null);
    }

    public String getAddress() {
        return pref.getString(KEY_Address, null);
    }

    public String getNationaliteName() {
        return pref.getString(KEY_NationaliteName, null);
    }

    public String getFCMToken() {
        return pref.getString(KEY_FCMToken, null);
    }

    public void setFCMToken(String token) {
        editor.putString(KEY_FCMToken, token);
        editor.commit();
    }

    // Integers
    public Integer getEtatResa() {
        return pref.getInt(KEY_EtatResa, 0);
    }

    public Integer getResaId() {
        return pref.getInt(KEY_ResaId, 0);
    }

    public Integer getClientId() {
        return pref.getInt(KEY_ClientId, 0);
    }

    public Integer getFactureId() {
        return pref.getInt(KEY_FactureId, 0);
    }

    public Integer getFactureAnnee() {
        return pref.getInt(KEY_FactureAnnee, 0);
    }

    public Integer getNationaliteId() {
        return pref.getInt(KEY_NationaliteId, 0);
    }

    /**********************************(  _______  )*************************************/
    //Clear session details
    public void clearSessionDetails() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }


}
