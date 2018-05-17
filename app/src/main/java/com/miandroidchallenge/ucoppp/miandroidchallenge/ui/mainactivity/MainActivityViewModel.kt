package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.util.Log
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity.api.MainActivityApi
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity.interfaces.OnDeliveriesChange
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity.models.Deliveries
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.api.Listener
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.api.RetrofitRequest
import io.reactivex.disposables.Disposable
import retrofit2.Retrofit
import java.util.*

class MainActivityViewModel(application: Application, private val retrofit: Retrofit, private val onDeliveriesChange: OnDeliveriesChange) : AndroidViewModel(application) {

    fun getDeliveries(): Disposable {
        val api: MainActivityApi = retrofit.create(MainActivityApi::class.java)
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
                                }

                                onDeliveriesChange.onSuccess(deliveries)
                            }

                            override fun onError(error: String?) {
                                Log.e("onError", "error")
                            }
                        }
                )
    }
}