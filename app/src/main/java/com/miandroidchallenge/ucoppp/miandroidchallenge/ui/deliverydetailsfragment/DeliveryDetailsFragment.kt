package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.miandroidchallenge.ucoppp.miandroidchallenge.R
import com.miandroidchallenge.ucoppp.miandroidchallenge.databinding.FragmentDeliveryDetailsBinding
import com.miandroidchallenge.ucoppp.miandroidchallenge.models.Deliveries
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.URL


class DeliveryDetailsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var factory: DeliveryDetailsFactoryViewModel

    private lateinit var viewModel: DeliveryDetailsViewModel

    private lateinit var fragmentDeliveryDetailsBinding: FragmentDeliveryDetailsBinding

    private lateinit var googleMap: GoogleMap

    private lateinit var bundle: Deliveries

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

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fragmentDeliveryDetailsBinding = DataBindingUtil
                .inflate(
                        inflater,
                        R.layout.fragment_delivery_details,
                        container,
                        false)

        bundle = arguments?.getParcelable(KEY_DELIVERY)!!

        fragmentDeliveryDetailsBinding.delivery = bundle

        initArchitecture()

        initMap()

        bundle = arguments?.getParcelable(KEY_DELIVERY)!!

        return fragmentDeliveryDetailsBinding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Let's make this google map is global var
        this.googleMap = googleMap

        val marker = LatLng(
                bundle.location?.lat!!,
                bundle.location?.lng!!
        )

        setCameraPosition(marker = marker)
        loadImage(marker = marker)

    }

    private fun initArchitecture() {
        factory = DeliveryDetailsFactoryViewModel(
                application = activity!!.application
        )

        viewModel = ViewModelProviders
                .of(this, factory)
                .get(DeliveryDetailsViewModel::class.java)
    }

    private fun initMap() {
        val mMapFragment =
                childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mMapFragment.getMapAsync(this)
    }

    private fun loadImage(marker: LatLng) {
        val url = URL(bundle.imageUrl)
        Observable
                .fromCallable({
                    BitmapFactory.decodeStream(url.openConnection().getInputStream())
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ bitmap ->
                    googleMap.addMarker(
                            MarkerOptions()
                                    .position(marker)
                                    .title(bundle.description)
                                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap)))
                })
    }

    private fun setCameraPosition(marker: LatLng) {
        val cameraPosition =
                CameraPosition
                        .builder()
                        .target(marker)
                        .zoom(14f)
                        .build()

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}