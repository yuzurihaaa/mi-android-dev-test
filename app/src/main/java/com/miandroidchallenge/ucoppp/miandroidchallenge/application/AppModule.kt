package com.miandroidchallenge.ucoppp.miandroidchallenge.application

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class AppModule(private val mApplication: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return mApplication
    }
}