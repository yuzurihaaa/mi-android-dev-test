package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableField
import com.miandroidchallenge.ucoppp.miandroidchallenge.database.DeliveryDao
import com.miandroidchallenge.ucoppp.miandroidchallenge.database.DeliveryDb
import com.miandroidchallenge.ucoppp.miandroidchallenge.di.MyApplication
import com.miandroidchallenge.ucoppp.miandroidchallenge.models.DeliveriesModel
import com.miandroidchallenge.ucoppp.miandroidchallenge.models.LocationModel
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.api.DeliveriesApi
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.interfaces.OnDeliveriesChange
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject

class DeliveryFragmentViewModel(
        application: Application,
        private val onDeliveriesChange: OnDeliveriesChange) : AndroidViewModel(application) {

    var isLoading = ObservableField<Boolean>(false)


    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var deliveriesDao: DeliveryDao

    init {
        (application as MyApplication).appComponent.inject(this)
    }

    fun getDeliveries(): Disposable {
        isLoading.set(true)
        val api: DeliveriesApi = retrofit.create(DeliveriesApi::class.java)

        return api.getDeliveries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ `object`: List<DeliveriesModel>? ->
                    isLoading.set(false)
                    val deliveries = ArrayList<DeliveriesModel>()
                    for (i in 0 until `object`!!.size) {
                        deliveries.add(DeliveriesModel(`object`[i].description,
                                `object`[i].imageUrl,
                                `object`[i].location))

                        Observable.fromCallable({
                            deliveriesDao.insertDelivery(DeliveryDb(
                                    i.toString(),
                                    `object`[i].description,
                                    `object`[i].imageUrl,
                                    `object`[i].location?.lat,
                                    `object`[i].location?.lng,
                                    `object`[i].location?.address

                            ))

                        }).subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe({
                                }, { error ->
                                })
                    }

                    onDeliveriesChange.onSuccess(deliveries)
                }, {
                    Observable.fromCallable({
                        deliveriesDao.selectAll()

                    }).subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                            .subscribe({ t ->
                                val deliveries = ArrayList<DeliveriesModel>()
                                t.map {
                                    deliveries.add(DeliveriesModel(it.description, it.imageUrl, LocationModel(it.longitude, it.latitude, it.address)))
                                }
                                onDeliveriesChange.onSuccess(deliveries)
                            }, { _ ->
                                onDeliveriesChange.onErrorLoading()
                            })
                })
    }
}