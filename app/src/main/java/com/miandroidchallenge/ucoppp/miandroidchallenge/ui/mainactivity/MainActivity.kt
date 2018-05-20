package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.miandroidchallenge.ucoppp.miandroidchallenge.R
import com.miandroidchallenge.ucoppp.miandroidchallenge.models.DeliveriesModel
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment.DeliveryDetailsFragment
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.DeliveryFragment
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.interfaces.OnDeliveryCallback
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.unselecteddetailsfragment.UnselectedDetailsFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnDeliveryCallback {

    val DELIVERY_STATE = "delivery_state"

    var isTablet: Boolean = false

    private var screenChangingId: Int = R.id.fragment_list_holder

    var deliveries: DeliveriesModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeTitle(R.string.things_to_deliver)

        isTablet = resources.getBoolean(R.bool.isTablet)

        val delivery = savedInstanceState?.getParcelable<DeliveriesModel>(DELIVERY_STATE)

        if (isTablet) {

            screenChangingId = R.id.fragment_details_holder

            replaceFragment(R.id.fragment_list_holder, DeliveryFragment.newInstance())

            if (savedInstanceState != null && delivery != null) {

                replaceFragment(screenChangingId, DeliveryDetailsFragment.newInstance(delivery))
            } else {

                replaceFragment(screenChangingId, UnselectedDetailsFragment.newInstance())
            }

        } else {
            replaceFragment(R.id.fragment_list_holder, DeliveryFragment.newInstance())
            if (savedInstanceState != null && delivery != null) {

                replaceFragmentWithPop(R.id.fragment_list_holder, DeliveryDetailsFragment.newInstance(delivery))
            }
        }

        button_retry.setOnClickListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragment_list_holder)
            if (fragment is DeliveryFragment) {
                (fragment as DeliveryFragment).callApi()
            }

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_list_holder)
        if (fragment is DeliveryFragment) {
            deliveries = null
        }
    }

    fun changeTitle(title: Int) {
        supportActionBar?.setTitle(title)
    }

    override fun onFailCallDelivery() {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.let(this::handleAlert)
        alertDialog.show()
    }

    private fun handleAlert(alertDialog: AlertDialog) {
        alertDialog.setTitle("Sorry!")
        alertDialog.setMessage("We failed to get the data")
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", { dialog, which ->
            dialog.dismiss()
        })
    }

    override fun onClickDelivery(delivery: DeliveriesModel) {
        deliveries = delivery
        if (isTablet) {
            replaceFragment(screenChangingId, DeliveryDetailsFragment.newInstance(delivery))
        } else {
            changeTitle(R.string.delivery_details)
            replaceFragmentWithPop(screenChangingId, DeliveryDetailsFragment.newInstance(delivery))
        }
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
