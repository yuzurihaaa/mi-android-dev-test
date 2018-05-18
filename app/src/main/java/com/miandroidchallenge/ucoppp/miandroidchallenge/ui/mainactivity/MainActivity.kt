package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.miandroidchallenge.ucoppp.miandroidchallenge.R
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.DeliveryFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_holder, DeliveryFragment.newInstance())
                .commit()

    }
}
