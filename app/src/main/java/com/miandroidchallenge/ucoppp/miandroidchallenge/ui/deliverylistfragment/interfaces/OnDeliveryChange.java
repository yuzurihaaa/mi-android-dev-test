package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.interfaces;

import com.miandroidchallenge.ucoppp.miandroidchallenge.models.DeliveriesModel;

import java.util.List;

public interface OnDeliveryChange {

    void onSuccess(List<DeliveriesModel> deliveries);

    void onErrorLoading();
}
