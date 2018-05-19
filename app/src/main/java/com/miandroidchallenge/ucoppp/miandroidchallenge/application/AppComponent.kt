package com.miandroidchallenge.ucoppp.miandroidchallenge.application

import android.app.Application
import android.support.v4.app.Fragment
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment.DeliveryDetailsViewModel
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.DeliveryFragmentViewModel
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity.MainActivity
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.module.ApiModule
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.module.DeliveriesDbModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, DeliveriesDbModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(application: Application)
    fun inject(fragment: Fragment)
    fun inject(deliveryFragmentViewModel: DeliveryFragmentViewModel)
    fun inject(deliveryDetailsViewModel: DeliveryDetailsViewModel)
}