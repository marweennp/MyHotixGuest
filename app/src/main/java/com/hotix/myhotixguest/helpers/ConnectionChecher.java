package com.hotix.myhotixguest.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.hotix.myhotixguest.R;

public class ConnectionChecher {

    public static Boolean online = false;

    /*Connection Checker*/
    public static boolean checkNetwork(View view, final Context context) {
        Snackbar snackBar = Snackbar.make(view, R.string.no_internet, Snackbar.LENGTH_INDEFINITE);
        Snackbar snackBarServ = Snackbar.make(view, R.string.unreachable_server, Snackbar.LENGTH_INDEFINITE);
        if (!isNetworkAvailable(context)) {
            snackBar.setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkNetwork(view, context); //recursive call.
                }
            }).show();
            return false;
        } else if (!isOnline()) {
            snackBarServ.setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkNetwork(view, context); //recursive call.
                }
            }).show();
            return false;
        }
        snackBar.dismiss();
        return true;
    }

    /*Android Network Availability*/
    private static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    /* Check if Serveur Online (ping) */
    private static boolean isOnline() {
        try {
            return (Runtime.getRuntime().exec("ping -c 1 google.com").waitFor() == 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}

