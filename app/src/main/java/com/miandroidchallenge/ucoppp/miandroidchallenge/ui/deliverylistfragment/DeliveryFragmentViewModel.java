package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.miandroidchallenge.ucoppp.miandroidchallenge.database.DeliveryDao;
import com.miandroidchallenge.ucoppp.miandroidchallenge.database.DeliveryDb;
import com.miandroidchallenge.ucoppp.miandroidchallenge.di.MyApplication;
import com.miandroidchallenge.ucoppp.miandroidchallenge.models.DeliveriesModel;
import com.miandroidchallenge.ucoppp.miandroidchallenge.models.LocationModel;
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.api.DeliveryApi;
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.deliverylistfragment.interfaces.OnDeliveryChange;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class DeliveryFragmentViewModel extends AndroidViewModel {

    private OnDeliveryChange onDeliveriesChange;

    public ObservableField<Boolean> isLoading = new ObservableField<Boolean>(false);

    public DeliveryFragmentViewModel(
            @NonNull Application application,
            OnDeliveryChange onDeliveriesChange
    ) {
        super(application);
        this.onDeliveriesChange = onDeliveriesChange;
        if (application instanceof MyApplication) {
            ((MyApplication) application).getAppComponent().inject(this);
        }
    }

    @Inject
    public Retrofit retrofit;

    @Inject
    public DeliveryDao deliveryDao;

    public void getDeliveries() {
        isLoading.set(true);

        DeliveryApi api = retrofit.create(DeliveryApi.class);

        api.getDeliveries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DeliveriesModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        isLoading.set(false);
                    }

                    @Override
                    public void onNext(List<DeliveriesModel> deliveriesModels) {
                        ArrayList<DeliveriesModel> deliveries = new ArrayList<>();
                        for (int i = 0; i < deliveriesModels.size(); i++) {
                            deliveries.add(
                                    new DeliveriesModel(
                                            deliveriesModels.get(i).getDescription(),
                                            deliveriesModels.get(i).getImageUrl(),
                                            deliveriesModels.get(i).getLocation()
                                    )
                            );

                            deliveryDao.insertDelivery(new DeliveryDb(
                                    String.valueOf(i),
                                    deliveriesModels.get(i).getDescription(),
                                    deliveriesModels.get(i).getImageUrl(),
                                    deliveriesModels.get(i).getLocation().getLat(),
                                    deliveriesModels.get(i).getLocation().getLng(),
                                    deliveriesModels.get(i).getLocation().getAddress()));
                        }

                        onDeliveriesChange.onSuccess(deliveries);

                    }

                    @Override
                    public void onError(Throwable e) {
                        onDeliveriesChange.onErrorLoading();
                        Observable.fromCallable(() -> deliveryDao.selectAll())
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe(new Observer<List<DeliveryDb>>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(List<DeliveryDb> deliveryDbs) {
                                        ArrayList<DeliveriesModel> deliveries = new ArrayList<>();
                                        for (int i = 0; i < deliveryDbs.size(); i++) {
                                            deliveries.add(
                                                    new DeliveriesModel(
                                                            deliveryDbs.get(i).getDescription(),
                                                            deliveryDbs.get(i).getImageUrl(),
                                                            new LocationModel(
                                                                    deliveryDbs.get(i).getLongitude(),
                                                                    deliveryDbs.get(i).getLatitude(),
                                                                    deliveryDbs.get(i).getAddress()
                                                            )
                                                    )
                                            );
                                            onDeliveriesChange.onSuccess(deliveries);
                                        }

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
