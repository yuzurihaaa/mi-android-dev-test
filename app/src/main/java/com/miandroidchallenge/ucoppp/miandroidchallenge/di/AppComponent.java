package com.miandroidchallenge.ucoppp.miandroidchallenge.di;

import android.app.Application;
import android.support.v4.app.Fragment;

import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverydetailsfragment.DeliveryDetailsViewModel;
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.DeliveryFragmentViewModel;
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.module.ApiModuleV2;
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.module.DeliveriesDbModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiModuleV2.class, DeliveriesDbModule.class})
public interface AppComponent {

    void inject(Application application);
    void inject(DeliveryDetailsViewModel deliveryDetailsViewModel);
    void inject(DeliveryFragmentViewModel deliveryFragmentViewModel);
    void inject(Fragment fragment);
}
