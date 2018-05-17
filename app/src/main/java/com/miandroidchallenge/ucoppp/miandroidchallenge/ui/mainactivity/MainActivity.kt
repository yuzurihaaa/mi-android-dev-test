package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.miandroidchallenge.ucoppp.miandroidchallenge.R
import com.miandroidchallenge.ucoppp.miandroidchallenge.databinding.ActivityMainBinding
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity.extensions.injectDagger
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity.interfaces.OnDeliveriesChange
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity.models.Deliveries
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.adapter.DeliveriesRecyclerViewAdapter
import io.reactivex.disposables.Disposable
import retrofit2.Retrofit
import javax.inject.Inject


class MainActivity : AppCompatActivity(), OnDeliveriesChange {
    @Inject
    lateinit var retrofit: Retrofit

    private lateinit var viewModel: MainActivityViewModel

    private lateinit var viewModelFactory: MainActivityFactoryViewModel

    private lateinit var disposable: Disposable

    private val activityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private val deliveryAdapter by lazy {
        DeliveriesRecyclerViewAdapter(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectDagger()

        viewModelFactory = MainActivityFactoryViewModel(application, retrofit, this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)

        disposable = viewModel.getDeliveries()

        activityMainBinding.adapter = deliveryAdapter

    }

    override fun onPause() {
        super.onPause()
        disposable.dispose()
    }

    override fun onSuccess(deliveries: MutableList<Deliveries>) {
        deliveryAdapter.addDeliveries(deliveries)
    }

}
