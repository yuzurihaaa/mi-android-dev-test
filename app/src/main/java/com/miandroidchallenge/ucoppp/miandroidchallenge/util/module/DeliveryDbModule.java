package com.miandroidchallenge.ucoppp.miandroidchallenge.util.module;

import android.app.Application;

import com.miandroidchallenge.ucoppp.miandroidchallenge.database.DeliveryDao;
import com.miandroidchallenge.ucoppp.miandroidchallenge.database.DeliveryDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DeliveryDbModule {

    private Application application;

    public DeliveryDbModule(Application application){
        this.application = application;
    }

    @Provides
    @Singleton
    public DeliveryDatabase provideDeliveriesDatabase() {
        return DeliveryDatabase.getAppDatabase(application);
    }

    @Provides
    @Singleton
    public DeliveryDao provideDeliveryDao(DeliveryDatabase deliveryDatabase) {
        return deliveryDatabase.deliveryDao();
    }
}
