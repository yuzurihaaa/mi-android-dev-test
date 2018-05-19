package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.graphics.Bitmap
import android.util.Log
import com.miandroidchallenge.ucoppp.miandroidchallenge.application.MyApplication
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment.api.DeliveryDetailsApi
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment.interfaces.OnLoadSuccess
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.api.Listener
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.api.RetrofitRequest
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

    fun getImage(imageUrl: String) {
        val api: DeliveryDetailsApi = retrofit.create(DeliveryDetailsApi::class.java)
        RetrofitRequest(getApplication())
                .makeJSONRequest(api.getDeliveriesImage(imageUrl),
                        object : Listener<Bitmap> {
                            override fun onPreRequest() {
                                Log.e("onPreRequest", "onPreRequest")
                            }

                            override fun onResponse(`object`: Bitmap) {
                                Log.e("onResponse", `object`.toString())
                            }

                            override fun onError(error: String?) {
                                Log.e("onError", "onError")
                            }

                        })
    }
}
