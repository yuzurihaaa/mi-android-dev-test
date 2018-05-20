package com.miandroidchallenge.ucoppp.miandroidchallenge.util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

    private static NetworkInfo getNetworkInfo(Application application) {
        if ((application.getSystemService(Context.CONNECTIVITY_SERVICE) instanceof ConnectivityManager)) {
            ((ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        }

        return null;
    }

    public static boolean isConnected(Application application) {
        return getNetworkInfo(application) != null && getNetworkInfo(application).isConnected();
    }
}
