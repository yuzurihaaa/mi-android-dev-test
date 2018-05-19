package com.miandroidchallenge.ucoppp.miandroidchallenge.util.module

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.miandroidchallenge.ucoppp.miandroidchallenge.util.isConnected
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
internal class ApiModule(var mBaseUrl: String, private val application: Application) {


    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun REWRITE_CACHE_CONTROL_INTERCEPTOR(): Interceptor {
        return Interceptor() {
            val originalResponse = it.proceed(it.request())
            val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
            if (isConnected(application = application)) {
                val maxAge = 6000 // read from cache for 1 minute
                originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .build()
            } else {
                val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
            }
        }
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR())
        client.cache(cache)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build()
    }
}