package com.hotix.myhotixguest.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    // All Shared Preferences Keys
    public static final String KEY_iSResident = "iSResident";
    public static final String KEY_HasHistory = "HasHistory";
    public static final String KEY_DateArrivee = "DateArrivee";
    public static final String KEY_DateDepart = "DateDepart";
    public static final String KEY_Chambre = "Chambre";
    public static final String KEY_Email = "Email";
    public static final String KEY_Nom = "Nom";
    public static final String KEY_Prenom = "Prenom";
    public static final String KEY_EtatResa = "EtatResa";
    public static final String KEY_ResaId = "ResaId";
    public static final String KEY_ClientId = "ClientId";
    public static final String KEY_FactureId = "FactureId";
    public static final String KEY_FactureAnnee = "FactureAnnee";
    public static final String KEY_IsLoggedIn = "IsLoggedIn";
    public static final String KEY_RememberMe = "RememberMe";


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

    public void createNewGuestSession(boolean iSResident, boolean HasHistory, boolean IsLoggedIn, boolean RememberMe, String DateArrivee, String DateDepart, String Chambre, String Email, String Nom, String Prenom, Integer EtatResa, Integer ResaId, Integer ClientId, Integer FactureId, Integer FactureAnnee) {

        // Booleans
        editor.putBoolean(KEY_iSResident, iSResident);
        editor.putBoolean(KEY_HasHistory, HasHistory);
        editor.putBoolean(KEY_IsLoggedIn, IsLoggedIn);
        editor.putBoolean(KEY_RememberMe, RememberMe);

        // Strings
        editor.putString(KEY_DateArrivee, DateArrivee);
        editor.putString(KEY_DateDepart, DateDepart);
        editor.putString(KEY_Chambre, Chambre);
        editor.putString(KEY_Email, Email);
        editor.putString(KEY_Nom, Nom);
        editor.putString(KEY_Prenom, Prenom);

        // Integers
        editor.putInt(KEY_EtatResa, EtatResa);
        editor.putInt(KEY_ResaId, ResaId);
        editor.putInt(KEY_ClientId, ClientId);
        editor.putInt(KEY_FactureId, FactureId);
        editor.putInt(KEY_FactureAnnee, FactureAnnee);

        // commit changes
        editor.commit();
    }

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

    // Strings
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

    //Clear session details
    public void clearSessionDetails() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }


}
