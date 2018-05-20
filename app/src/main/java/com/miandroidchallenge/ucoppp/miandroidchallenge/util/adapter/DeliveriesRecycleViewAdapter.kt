package com.miandroidchallenge.ucoppp.miandroidchallenge.util.adapter

import android.app.Application
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.miandroidchallenge.ucoppp.miandroidchallenge.R
import com.miandroidchallenge.ucoppp.miandroidchallenge.databinding.ItemDeliveryBinding
import com.miandroidchallenge.ucoppp.miandroidchallenge.models.DeliveriesModel



class DeliveriesRecyclerViewAdapter(private val context: Application, val callback: OnItemClickListener?) : RecyclerView.Adapter<CustomViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(delivery: DeliveriesModel)
    }


    private var deliveries: MutableList<DeliveriesModel> = mutableListOf()

    fun addDeliveries(deliveriesList: MutableList<DeliveriesModel>) {
        this.deliveries = deliveriesList
        notifyDataSetChanged()
    }

    fun clearDeliveries() {
        this.deliveries.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = DataBindingUtil.inflate<ItemDeliveryBinding>(
                LayoutInflater.from(context),
                R.layout.item_delivery,
                parent,
                false
        )

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(deliveries[position], callback)
    }

    override fun getItemCount(): Int {
        return deliveries.size
    }
}

class CustomViewHolder(private val binding: ItemDeliveryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(delivery: DeliveriesModel, callback: DeliveriesRecyclerViewAdapter.OnItemClickListener?) {
        binding.delivery = delivery
        binding.deliveryParent.setOnClickListener {
            callback?.onItemClick(delivery)
        }
    }
}