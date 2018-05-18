package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.miandroidchallenge.ucoppp.miandroidchallenge.R
import com.miandroidchallenge.ucoppp.miandroidchallenge.models.Deliveries
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment.DeliveryDetailsFragment
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.DeliveryFragment
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.interfaces.OnDeliveryCallback


class MainActivity : AppCompatActivity(), OnDeliveryCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_holder, DeliveryFragment.newInstance())
                .commit()

    }

    override fun onClickDelivery(delivery: Deliveries) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_holder, DeliveryDetailsFragment.newInstance(delivery))
                .addToBackStack(null)
                .commit()
    }
}
