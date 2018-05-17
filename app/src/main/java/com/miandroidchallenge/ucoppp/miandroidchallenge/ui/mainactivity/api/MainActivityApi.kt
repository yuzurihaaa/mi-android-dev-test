package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity.api

import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity.models.Deliveries
import io.reactivex.Observable
import retrofit2.http.GET

interface MainActivityApi {

    @GET("deliveries")
    fun getDeliveries(): Observable<List<Deliveries>>
}