package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.interfaces

import com.miandroidchallenge.ucoppp.miandroidchallenge.models.DeliveriesModel

interface OnDeliveryCallback {
    fun onClickDelivery(delivery: DeliveriesModel)
    fun onFailCallDelivery()
}