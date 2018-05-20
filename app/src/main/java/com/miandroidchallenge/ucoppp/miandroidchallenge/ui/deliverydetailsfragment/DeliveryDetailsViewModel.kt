package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.graphics.Bitmap
import com.miandroidchallenge.ucoppp.miandroidchallenge.di.MyApplication
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment.api.DeliveryDetailsApi
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.api.Listener
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.api.RetrofitRequestV2
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
        RetrofitRequestV2(getApplication()).getInstance(getApplication())
                .makeJonRequest(api.getDeliveriesImage(imageUrl),
                        object : Listener<Bitmap>, RetrofitRequestV2.Listener<Bitmap> {
                            override fun onError(error: String?) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onError() {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onResponse(`object`: Bitmap) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onPreRequest() {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                        })
    }
}


