package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miandroidchallenge.ucoppp.miandroidchallenge.R
import com.miandroidchallenge.ucoppp.miandroidchallenge.databinding.FragmentDeliveryDetailsBinding
import com.miandroidchallenge.ucoppp.miandroidchallenge.models.Deliveries

class DeliveryDetailsFragment : Fragment() {

    lateinit var fragmentDeliveryDetailsBinding: FragmentDeliveryDetailsBinding

    companion object {

        const val KEY_DELIVERY = "delivery"

        fun newInstance(delivery: Deliveries): DeliveryDetailsFragment {
            val fragment = DeliveryDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_DELIVERY, delivery)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentDeliveryDetailsBinding = DataBindingUtil
                .inflate(
                        inflater,
                        R.layout.fragment_delivery_details,
                        container,
                        false)

        val bundle = arguments?.getParcelable<Deliveries>(KEY_DELIVERY)
        Log.e("bundle", bundle?.description)

        return fragmentDeliveryDetailsBinding.root
    }
}