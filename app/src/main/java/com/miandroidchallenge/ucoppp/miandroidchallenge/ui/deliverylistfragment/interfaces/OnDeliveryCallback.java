package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.interfaces;

import com.miandroidchallenge.ucoppp.miandroidchallenge.models.DeliveriesModel;

public interface OnDeliveryCallback {

    void onClickDelivery(DeliveriesModel delivery);
    void onFailCallDelivery();
}
