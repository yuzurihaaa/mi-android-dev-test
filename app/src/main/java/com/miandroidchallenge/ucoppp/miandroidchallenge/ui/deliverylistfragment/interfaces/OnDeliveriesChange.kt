package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.interfaces

import com.miandroidchallenge.ucoppp.miandroidchallenge.models.Deliveries

interface OnDeliveriesChange {
    fun onSuccess(deliveries: MutableList<Deliveries>)
}