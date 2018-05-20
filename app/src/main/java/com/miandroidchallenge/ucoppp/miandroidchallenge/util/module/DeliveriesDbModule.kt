package com.miandroidchallenge.ucoppp.miandroidchallenge.util.module

import com.miandroidchallenge.ucoppp.miandroidchallenge.database.DeliveriesDatabase
import com.miandroidchallenge.ucoppp.miandroidchallenge.di.MyApplication
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