package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miandroidchallenge.ucoppp.miandroidchallenge.R
import com.miandroidchallenge.ucoppp.miandroidchallenge.application.MyApplication
import com.miandroidchallenge.ucoppp.miandroidchallenge.databinding.FragmentDeliveryBinding
import com.miandroidchallenge.ucoppp.miandroidchallenge.models.Deliveries
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.interfaces.OnDeliveriesChange
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.adapter.DeliveriesRecyclerViewAdapter
import retrofit2.Retrofit
import javax.inject.Inject

class DeliveryFragment : Fragment(), OnDeliveriesChange {

    companion object {
        fun newInstance(): DeliveryFragment = DeliveryFragment()
    }


    lateinit var fragmentDeliveryBinding: FragmentDeliveryBinding

    private lateinit var factory: DeliveryFragmentFactoryViewModel

    private lateinit var viewModel: DeliveryFragmentViewModel

    private val deliveryAdapter by lazy {
        DeliveriesRecyclerViewAdapter(activity?.application!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as MyApplication)
                .appComponent
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fragmentDeliveryBinding = DataBindingUtil
                .inflate(
                        inflater,
                        R.layout.fragment_delivery,
                        container,
                        false)

        factory = DeliveryFragmentFactoryViewModel(
                application = activity!!.application,
                onDeliveriesChange = this
        )

        viewModel = ViewModelProviders.of(this, factory).get(DeliveryFragmentViewModel::class.java)

        fragmentDeliveryBinding.adapter = deliveryAdapter

        return fragmentDeliveryBinding.root
    }

    override fun onSuccess(deliveries: MutableList<Deliveries>) {
        deliveryAdapter.addDeliveries(deliveries)
    }
}