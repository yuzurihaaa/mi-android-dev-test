package com.miandroidchallenge.ucoppp.miandroidchallenge.util.module

import com.miandroidchallenge.ucoppp.miandroidchallenge.application.MyApplication
import com.miandroidchallenge.ucoppp.miandroidchallenge.database.DeliveriesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class DeliveriesDbModule(val application: MyApplication) {


    @Provides
    @Singleton
    fun provideDeliveriesDatabase() = DeliveriesDatabase.getInstance(application)

    @Provides
    @Singleton
    fun provideDeliverisDao() = provideDeliveriesDatabase().DeliveriesDao()
}