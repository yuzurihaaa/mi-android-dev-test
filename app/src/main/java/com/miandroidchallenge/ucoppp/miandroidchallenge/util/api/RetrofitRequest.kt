package com.miandroidchallenge.ucoppp.miandroidchallenge.util.api

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

interface Listener<in T> {
    fun onPreRequest()

    fun onResponse(`object`: T)

    fun onError(error: String?)

}


@Singleton
class RetrofitRequest(application: Application) {

    var networkInfo: NetworkInfo? = (application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

    fun isConnected(): Boolean = networkInfo != null && networkInfo!!.isConnected


    fun <T> makeJSONRequest(request: Observable<T>, listener: Listener<T>?): Disposable {

        var networkError = ""

        if (!isConnected()) {
            networkError = "No internet connection"
        }

        listener?.onPreRequest()

        return request.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->

                            listener?.onResponse(result)

                        },
                        { error -> listener?.onError(if (networkError.isNotEmpty()) networkError else error.message) }
                )
    }
}