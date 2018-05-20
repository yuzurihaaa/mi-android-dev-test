package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.interfaces.OnDeliveryChange;

import javax.inject.Inject;

public class DeliveryFragmentFactoryViewModel implements ViewModelProvider.Factory {

    private Application application;

    private OnDeliveryChange onDeliveryChange;

    @Inject
    public DeliveryFragmentFactoryViewModel(Application application, OnDeliveryChange onDeliveryChange) {
        this.application = application;
        this.onDeliveryChange = onDeliveryChange;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DeliveryFragmentViewModel.class)) {
            return (T) new DeliveryFragmentViewModel(application, onDeliveryChange);
        }
        throw new IllegalArgumentException("Unknown" + modelClass.getName() + "class");
    }
}
