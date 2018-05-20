package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.interfaces

import com.miandroidchallenge.ucoppp.miandroidchallenge.models.Deliveries

interface OnDeliveryCallback {
    fun onClickDelivery(delivery: Deliveries)
    fun onFailCallDelivery()
}