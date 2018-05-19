package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment.interfaces.OnLoadSuccess
import javax.inject.Inject

class DeliveryDetailsFactoryViewModel @Inject constructor(
        val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeliveryDetailsViewModel::class.java)) {
            return DeliveryDetailsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ${modelClass.name} class")
    }
}