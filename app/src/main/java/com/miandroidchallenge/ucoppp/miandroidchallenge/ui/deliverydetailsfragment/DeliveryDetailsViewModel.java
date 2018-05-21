package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.miandroidchallenge.ucoppp.miandroidchallenge.di.MyApplication;

public class DeliveryDetailsViewModel extends AndroidViewModel {

    public DeliveryDetailsViewModel(@NonNull Application application) {
        super(application);

        if (application instanceof MyApplication) {
            ((MyApplication) application).getAppComponent().inject(this);
        }
    }
}
