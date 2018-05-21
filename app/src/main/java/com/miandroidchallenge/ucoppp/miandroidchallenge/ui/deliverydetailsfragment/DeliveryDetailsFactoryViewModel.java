package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

public class DeliveryDetailsFactoryViewModel implements ViewModelProvider.Factory {


    private Application application;

    @Inject
    public DeliveryDetailsFactoryViewModel(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DeliveryDetailsViewModel.class)) {
            return (T) new DeliveryDetailsViewModel(application);
        }
        throw new IllegalArgumentException("Unknown" + modelClass.getName() + "class");
    }
}
