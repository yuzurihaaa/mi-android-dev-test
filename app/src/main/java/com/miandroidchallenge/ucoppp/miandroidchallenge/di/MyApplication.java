package com.miandroidchallenge.ucoppp.miandroidchallenge.di;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import com.miandroidchallenge.ucoppp.miandroidchallenge.util.module.ApiModuleV2;
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.module.DeliveryDbModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MyApplication extends Application
        implements HasActivityInjector, HasFragmentInjector, HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjectorActivity;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjectorFragment;

    @Inject
    DispatchingAndroidInjector<android.support.v4.app.Fragment> dispatchingAndroidInjectorSupportFragment;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiModuleV2(new ApiModuleV2(
                        "https://staging.massiveinfinity.com/api/",
                        this
                ))
                .deliveryDbModule(new DeliveryDbModule(this))
                .build();
    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjectorActivity;
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return dispatchingAndroidInjectorFragment;
    }

    @Override
    public AndroidInjector<android.support.v4.app.Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjectorSupportFragment;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
