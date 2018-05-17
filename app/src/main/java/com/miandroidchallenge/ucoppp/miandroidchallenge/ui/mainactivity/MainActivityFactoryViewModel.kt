package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity.interfaces.OnDeliveriesChange
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivityFactoryViewModel @Inject constructor(val application: Application,
                                                       private val retrofit: Retrofit,
                                                       private val onDeliveriesChange: OnDeliveriesChange
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(application, retrofit, onDeliveriesChange) as T
        }
        throw IllegalArgumentException("Unknown ${modelClass.name} class")
    }
}