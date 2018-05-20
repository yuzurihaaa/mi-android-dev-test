package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.interfaces

import com.miandroidchallenge.ucoppp.miandroidchallenge.models.DeliveriesModel

interface OnDeliveriesChange {
    fun onSuccess(deliveries: MutableList<DeliveriesModel>)
    fun onErrorLoading()
}