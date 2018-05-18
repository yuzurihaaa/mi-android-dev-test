package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.util.Log
import com.miandroidchallenge.ucoppp.miandroidchallenge.application.MyApplication
import com.miandroidchallenge.ucoppp.miandroidchallenge.database.DeliveriesDao
import com.miandroidchallenge.ucoppp.miandroidchallenge.models.Deliveries
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.api.DeliveriesApi
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.interfaces.OnDeliveriesChange
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.api.Listener
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.api.RetrofitRequest
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.util.ArrayList
import javax.inject.Inject

class DeliveryFragmentViewModel(
        application: Application,
        private val onDeliveriesChange: OnDeliveriesChange) : AndroidViewModel(application) {

    @Inject
    lateinit var retrofit: Retrofit

    init {
        (application as MyApplication).appComponent.inject(this)
        getDeliveries()
    }

    private fun getDeliveries(): Disposable {
        val api: DeliveriesApi = retrofit.create(DeliveriesApi::class.java)
        return RetrofitRequest(getApplication())
                .makeJSONRequest(
                        api.getDeliveries(),
                        object : Listener<List<Deliveries>> {
                            override fun onPreRequest() {
                                Log.e("onPreRequest", "onPreRequest")
                            }

                            override fun onResponse(`object`: List<Deliveries>) {
                                val deliveries = ArrayList<Deliveries>()
                                for (i in 0 until `object`.size) {
                                    deliveries.add(Deliveries(`object`[i].description,
                                            `object`[i].imageUrl,
                                            `object`[i].location))

                                    Log.e("delivery", `object`[i].imageUrl)
                                }

                                onDeliveriesChange.onSuccess(deliveries)
                            }

                            override fun onError(error: String?) {
                                Log.e("onError", error)
                            }
                        }
                )
    }
}