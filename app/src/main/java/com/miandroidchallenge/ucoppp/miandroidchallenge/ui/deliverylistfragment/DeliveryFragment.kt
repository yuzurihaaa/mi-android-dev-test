package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
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
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.interfaces.OnDeliveryCallback
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.adapter.DeliveriesRecyclerViewAdapter

class DeliveryFragment : Fragment(), OnDeliveriesChange, DeliveriesRecyclerViewAdapter.OnItemClickListener {

    lateinit var onDeliveryCallback: OnDeliveryCallback

    companion object {
        fun newInstance(): DeliveryFragment = DeliveryFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnDeliveryCallback) {
            onDeliveryCallback = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnDeliveryCallback")
        }
    }


    lateinit var fragmentDeliveryBinding: FragmentDeliveryBinding

    private lateinit var factory: DeliveryFragmentFactoryViewModel

    private lateinit var viewModel: DeliveryFragmentViewModel

    private val deliveryAdapter by lazy {
        DeliveriesRecyclerViewAdapter(activity?.application!!, this)
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

        viewModel = ViewModelProviders
                .of(this, factory)
                .get(DeliveryFragmentViewModel::class.java)

        fragmentDeliveryBinding.adapter = deliveryAdapter

        return fragmentDeliveryBinding.root
    }

    override fun onItemClick(delivery: Deliveries) {
        onDeliveryCallback.onClickDelivery(delivery)
    }

    override fun onSuccess(deliveries: MutableList<Deliveries>) {
        deliveryAdapter.addDeliveries(deliveries)
    }
}