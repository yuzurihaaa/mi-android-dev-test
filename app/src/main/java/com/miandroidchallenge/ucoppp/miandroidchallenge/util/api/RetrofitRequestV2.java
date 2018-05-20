package com.miandroidchallenge.ucoppp.miandroidchallenge.util.api;

import android.app.Application;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RetrofitRequestV2 {

    private Application application;

    private RetrofitRequestV2 instance;

    public synchronized RetrofitRequestV2 getInstance(Application application) {
        if (instance == null) {
            instance = new RetrofitRequestV2(application);
        }

        return instance;
    }

    public interface Listener<T> {

        void onPreRequest();

        void onResponse(T object);

        void onError();

    }

    public RetrofitRequestV2(Application application) {
        this.application = application;
    }

    public <T> void makeJonRequest(Observable<T> request, final Listener listener) {
        request.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
