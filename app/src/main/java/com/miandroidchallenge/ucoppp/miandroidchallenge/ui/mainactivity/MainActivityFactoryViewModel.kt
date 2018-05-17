package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.reactivex.disposables.Disposable
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivityFactoryViewModel @Inject constructor(val application: Application,
                                                       private val retrofit: Retrofit
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(application, retrofit) as T
        }
        throw IllegalArgumentException("Unknown ${modelClass.name} class")
    }
}