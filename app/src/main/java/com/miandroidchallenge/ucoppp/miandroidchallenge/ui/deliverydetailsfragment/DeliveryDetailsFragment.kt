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
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity.MainActivity
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.getImage
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.getImagePath
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.isConnected
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.saveFile
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.URL


class DeliveryDetailsFragment : Fragment(), OnMapReadyCallback {

    val MARKER_IMAGE_NAME = "MARKER_IMAGE_NAME.png"

    private lateinit var factory: DeliveryDetailsFactoryViewModel

    private lateinit var viewModel: DeliveryDetailsViewModel

    private lateinit var fragmentDeliveryDetailsBinding: FragmentDeliveryDetailsBinding

    private lateinit var googleMap: GoogleMap

    private lateinit var bundle: Deliveries

    private lateinit var marker: LatLng

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

    override fun onDestroyView() {
        super.onDestroyView()
        if (activity is MainActivity) {
            (activity as MainActivity).changeTitle(R.string.things_to_deliver)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Let's make this google map is global var
        this.googleMap = googleMap

        marker = LatLng(
                bundle.location?.lat!!,
                bundle.location?.lng!!
        )

        setCameraPosition(marker = marker)
        loadImage()

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

    private fun loadImage() {
        val url = URL(bundle.imageUrl)

        val image = getImagePath(app = activity?.application!!,
                finalDirectory = MARKER_IMAGE_NAME
        )

        Observable
                .fromCallable({
                    if (!isConnected(activity?.application!!)) {
                        throw Exception("Load local please")
                    }
                    BitmapFactory.decodeStream(url.openConnection().getInputStream())
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ bitmap ->

                    saveFile(
                            app = activity?.application!!,
                            bitmap = bitmap,
                            imageName = MARKER_IMAGE_NAME
                    )

                    googleMap.addMarker(
                            MarkerOptions()
                                    .position(marker)
                                    .title(bundle.description)
                                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap)))
                }, { _ ->
                    googleMap.addMarker(
                            MarkerOptions()
                                    .position(marker)
                                    .title(bundle.description)
                                    .icon(BitmapDescriptorFactory.fromBitmap(getImage(image))))
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