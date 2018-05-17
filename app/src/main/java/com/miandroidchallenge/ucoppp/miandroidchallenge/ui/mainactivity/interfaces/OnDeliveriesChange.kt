package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity.interfaces

import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity.models.Deliveries

interface OnDeliveriesChange {
    fun onSuccess(deliveries: MutableList<Deliveries>)
}