package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.miandroidchallenge.ucoppp.miandroidchallenge.di.MyApplication
import retrofit2.Retrofit
import javax.inject.Inject

class DeliveryDetailsViewModel(
        application: Application
) : AndroidViewModel(application) {

    @Inject
    lateinit var retrofit: Retrofit

    init {
        (application as MyApplication).appComponent.inject(this)
    }
}


