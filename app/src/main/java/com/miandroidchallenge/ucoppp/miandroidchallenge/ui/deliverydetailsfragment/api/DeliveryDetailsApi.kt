package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment.api

import android.graphics.Bitmap
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

interface DeliveryDetailsApi {

    @GET
    fun getDeliveriesImage(@Url url: String): Observable<Bitmap>
}