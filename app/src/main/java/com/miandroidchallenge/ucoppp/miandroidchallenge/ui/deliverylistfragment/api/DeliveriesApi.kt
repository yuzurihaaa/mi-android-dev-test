package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.api

import com.miandroidchallenge.ucoppp.miandroidchallenge.models.DeliveriesModel
import io.reactivex.Observable
import retrofit2.http.GET

interface DeliveriesApi {

    @GET("deliveries")
    fun getDeliveries(): Observable<List<DeliveriesModel>>
}