package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miandroidchallenge.ucoppp.miandroidchallenge.R;
import com.miandroidchallenge.ucoppp.miandroidchallenge.databinding.FragmentDeliveryBinding;
import com.miandroidchallenge.ucoppp.miandroidchallenge.models.DeliveriesModel;
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.interfaces.OnDeliveryCallback;
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.interfaces.OnDeliveryChange;
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.adapter.DeliveriesRecyclerViewAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DeliveryFragment extends Fragment
        implements OnDeliveryChange,
        DeliveriesRecyclerViewAdapter.OnItemClickListener {

    private OnDeliveryCallback callback;

    private FragmentDeliveryBinding fragmentDeliveryBinding = null;

    private DeliveryFragmentFactoryViewModel factory;

    private DeliveryFragmentViewModel viewModel;

    private DeliveriesRecyclerViewAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDeliveryCallback) {
            callback = (OnDeliveryCallback) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnDeliveryCallback");
        }
    }

    public static DeliveryFragment newInstance() {

        Bundle args = new Bundle();

        DeliveryFragment fragment = new DeliveryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        fragmentDeliveryBinding = DataBindingUtil
                .inflate(
                        inflater,
                        R.layout.fragment_delivery,
                        container,
                        false);

        factory = new DeliveryFragmentFactoryViewModel(
                getActivity().getApplication(),
                this
        );


        viewModel = ViewModelProviders
                .of(this, factory)
                .get(DeliveryFragmentViewModel.class);

        adapter = new DeliveriesRecyclerViewAdapter(getActivity().getApplication(), this);

        fragmentDeliveryBinding.setViewmodel(viewModel);
        fragmentDeliveryBinding.setAdapter(adapter);

        callApi();

        return fragmentDeliveryBinding.getRoot();
    }

    public void callApi() {
        viewModel.getDeliveries();
    }

    @Override
    public void onSuccess(List<DeliveriesModel> deliveries) {
        if(getActivity() != null){
            getActivity().runOnUiThread(() -> {
                adapter.addDeliveries(deliveries);
            });
        }
    }

    @Override
    public void onErrorLoading() {
        callback.onFailCallDelivery();
    }

    @Override
    public void onItemClick(@NotNull DeliveriesModel delivery) {
        callback.onClickDelivery(delivery);
    }
}
