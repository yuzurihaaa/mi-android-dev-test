package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.miandroidchallenge.ucoppp.miandroidchallenge.R
import com.miandroidchallenge.ucoppp.miandroidchallenge.models.Deliveries
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.unselecteddetailsfragment.UnselectedDetailsFragment
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment.DeliveryDetailsFragment
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.DeliveryFragment
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.interfaces.OnDeliveryCallback


class MainActivity : AppCompatActivity(), OnDeliveryCallback {

    val DELIVERY_STATE = "delivery_state"

    var tabletSize: Boolean = false

    var screenChangingId: Int = R.id.fragment_holder

    var deliveries: Deliveries? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabletSize = resources.getBoolean(R.bool.isTablet)

        if (tabletSize) {

            screenChangingId = R.id.fragment_details_holder

            replaceFragment(R.id.fragment_list_holder, DeliveryFragment.newInstance())

            if (savedInstanceState != null) {

                val delivery = savedInstanceState.getParcelable<Deliveries>(DELIVERY_STATE)

                replaceFragment(screenChangingId, DeliveryDetailsFragment.newInstance(delivery))
            } else {

                replaceFragment(screenChangingId, UnselectedDetailsFragment.newInstance())
            }

        } else {

            screenChangingId = R.id.fragment_holder

            replaceFragment(screenChangingId, DeliveryFragment.newInstance())
        }

    }

    override fun onClickDelivery(delivery: Deliveries) {
        deliveries = delivery
        replaceFragmentWithPop(screenChangingId, DeliveryDetailsFragment.newInstance(delivery))
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(DELIVERY_STATE, deliveries)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        deliveries = savedInstanceState?.getParcelable(DELIVERY_STATE)
    }

    private fun replaceFragment(fragmentId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(fragmentId, fragment)
                .commit()
    }

    private fun replaceFragmentWithPop(fragmentId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(fragmentId, fragment)
                .addToBackStack(null)
                .commit()
    }
}
