package com.miandroidchallenge.ucoppp.miandroidchallenge.application

import android.app.Activity
import android.app.Application
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.module.ApiModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class MyApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    val appComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .apiModule(ApiModule("https://staging.massiveinfinity.com/api/"))
                .build()
    }

    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = dispatchingAndroidInjector
}