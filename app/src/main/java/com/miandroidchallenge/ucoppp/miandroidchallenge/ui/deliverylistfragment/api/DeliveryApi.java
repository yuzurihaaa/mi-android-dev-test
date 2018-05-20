package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.api;

import com.miandroidchallenge.ucoppp.miandroidchallenge.models.DeliveriesModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface DeliveryApi {

    @GET("deliveries")
    Observable<List<DeliveriesModel>> getDeliveries();
}
