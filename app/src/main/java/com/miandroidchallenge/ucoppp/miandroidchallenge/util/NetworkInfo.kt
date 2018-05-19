package com.miandroidchallenge.ucoppp.miandroidchallenge.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

fun getNetworkInfo(application: Application): NetworkInfo? {
    return (application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
}

fun isConnected(application: Application): Boolean = getNetworkInfo(application) != null && getNetworkInfo(application)!!.isConnected