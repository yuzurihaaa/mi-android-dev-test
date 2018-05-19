package com.miandroidchallenge.ucoppp.miandroidchallenge.application

import android.app.Activity
import android.app.Application
import android.app.Fragment
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.module.ApiModule
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.module.DeliveriesDbModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import android.support.v4.app.Fragment as SupportFragment
import javax.inject.Inject


class MyApplication : Application(), HasActivityInjector, HasFragmentInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var frameworkFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var frameworkSupportFragmentInjector: DispatchingAndroidInjector<SupportFragment>

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .apiModule(
                        ApiModule(
                                mBaseUrl = "https://staging.massiveinfinity.com/api/",
                                application = this
                        ))
                .deliveriesDbModule(DeliveriesDbModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = dispatchingAndroidInjector

    override fun fragmentInjector(): AndroidInjector<Fragment> = frameworkFragmentInjector

    override fun supportFragmentInjector(): AndroidInjector<SupportFragment> = frameworkSupportFragmentInjector
}