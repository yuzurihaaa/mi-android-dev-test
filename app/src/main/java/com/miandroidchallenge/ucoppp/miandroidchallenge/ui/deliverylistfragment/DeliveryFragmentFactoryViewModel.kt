package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.interfaces.OnDeliveriesChange
import javax.inject.Inject

class DeliveryFragmentFactoryViewModel @Inject constructor(val application: Application,
                                                           private val onDeliveriesChange: OnDeliveriesChange
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeliveryFragmentViewModel::class.java)) {
            return DeliveryFragmentViewModel(application, onDeliveriesChange) as T
        }
        throw IllegalArgumentException("Unknown ${modelClass.name} class")
    }
}