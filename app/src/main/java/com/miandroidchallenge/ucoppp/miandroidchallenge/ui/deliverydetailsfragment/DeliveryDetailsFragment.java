package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.miandroidchallenge.ucoppp.miandroidchallenge.R;
import com.miandroidchallenge.ucoppp.miandroidchallenge.databinding.FragmentDeliveryDetailsBinding;
import com.miandroidchallenge.ucoppp.miandroidchallenge.models.DeliveriesModel;
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity.MainActivity;
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.ImageUtil;
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.NetworkUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DeliveryDetailsFragment extends Fragment implements OnMapReadyCallback {

    private static final String MARKER_IMAGE_NAME = "MARKER_IMAGE_NAME.png";
    private static final String KEY_DELIVERY = " delivery";
    private FragmentDeliveryDetailsBinding binding;

    private DeliveryDetailsViewModel viewmodel;

    private DeliveryDetailsFactoryViewModel factory;

    private GoogleMap googleMap;

    private DeliveriesModel deliveriesModel;

    private LatLng marker;

    public static DeliveryDetailsFragment newInstance(
            DeliveriesModel deliveriesModel
    ) {

        Bundle args = new Bundle();
        args.putParcelable(KEY_DELIVERY, deliveriesModel);
        DeliveryDetailsFragment fragment = new DeliveryDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(
                        inflater,
                        R.layout.fragment_delivery_details,
                        container,
                        false);

        deliveriesModel = getArguments().getParcelable(KEY_DELIVERY);

        binding.setDelivery(deliveriesModel);

        initArchitecture();

        initMap();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).changeTitle(R.string.things_to_deliver);
        }
    }

    private void initArchitecture() {
        factory = new DeliveryDetailsFactoryViewModel(
                getActivity().getApplication()
        );

        viewmodel = ViewModelProviders
                .of(this, factory)
                .get(DeliveryDetailsViewModel.class);
    }

    private void initMap() {
        SupportMapFragment mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mMapFragment != null) {
            mMapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        marker = new LatLng(
                deliveriesModel.getLocation().getLat(),
                deliveriesModel.getLocation().getLng()
        );

        setCameraPosition(marker);
        loadImage();
    }

    private void setCameraPosition(LatLng marker) {
        CameraPosition cameraPosition = CameraPosition
                .builder()
                .target(marker)
                .zoom(14f)
                .build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void loadImage() {
        try {
            URL url = new URL(deliveriesModel.getImageUrl());

            String imagePath = ImageUtil.getImagePath(
                    getActivity().getApplication(),
                    MARKER_IMAGE_NAME);

            Observable.fromCallable((Callable<Bitmap>) () -> {
                if (!NetworkUtil.isConnected(getActivity().getApplication())) {
                    throw new Exception("Load local please");
                }
                return BitmapFactory.decodeStream(url.openConnection().getInputStream());
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Bitmap>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Bitmap bitmap) {

                            ImageUtil.saveFile(
                                    getActivity().getApplication(),
                                    bitmap,
                                    MARKER_IMAGE_NAME
                            );

                            googleMap.addMarker(
                                    new MarkerOptions()
                                            .position(marker)
                                            .title(deliveriesModel.getDescription())
                                            .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));

                        }

                        @Override
                        public void onError(Throwable e) {
                            googleMap.addMarker(
                                    new MarkerOptions()
                                            .position(marker)
                                            .title(deliveriesModel.getDescription())
                                            .icon(BitmapDescriptorFactory.fromBitmap(ImageUtil.getImage(imagePath))));
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
