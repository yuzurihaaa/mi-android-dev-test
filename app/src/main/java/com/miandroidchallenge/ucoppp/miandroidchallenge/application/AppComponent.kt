package com.miandroidchallenge.ucoppp.miandroidchallenge.application

import android.app.Application
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity.MainActivity
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.module.ApiModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(application: Application)
}