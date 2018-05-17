package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.miandroidchallenge.ucoppp.miandroidchallenge.R
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity.extensions.injectDagger
import io.reactivex.disposables.Disposable
import retrofit2.Retrofit
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var retrofit: Retrofit

    lateinit var viewModel: MainActivityViewModel

    lateinit var viewModelFactory: MainActivityFactoryViewModel

    lateinit var disposable: Disposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDagger()

        viewModelFactory = MainActivityFactoryViewModel(application, retrofit)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)

        disposable = viewModel.getDeliveries()

    }

    override fun onPause() {
        super.onPause()
        disposable.dispose()
    }
}
