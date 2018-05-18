package com.miandroidchallenge.ucoppp.miandroidchallenge.util.adapter

import android.app.Application
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.miandroidchallenge.ucoppp.miandroidchallenge.R
import com.miandroidchallenge.ucoppp.miandroidchallenge.databinding.ItemDeliveryBinding
import com.miandroidchallenge.ucoppp.miandroidchallenge.models.Deliveries

class DeliveriesRecyclerViewAdapter(private val context: Application) : RecyclerView.Adapter<CustomViewHolder>() {

    private var deliveries: MutableList<Deliveries> = mutableListOf()

    fun addDeliveries(deliveriesList: MutableList<Deliveries>) {
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
        holder.bind(deliveries[position])
    }

    override fun getItemCount(): Int {
        return deliveries.size
    }
}

class CustomViewHolder(private val binding: ItemDeliveryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(delivery: Deliveries) {
        binding.delivery = delivery
    }
}