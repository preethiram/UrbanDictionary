package com.example.urbandictionary.di

import android.app.Application
import android.content.Context
import com.example.urbandictionary.api.AppServices
import com.example.urbandictionary.utils.*
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : AppServices{

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(okHttpClient)

            .build().create(AppServices::class.java)
    }

    @Provides
    @Singleton
    fun getContext(application: Application): Context {

        return application
    }

    @Provides
    @Singleton
    fun provideOkHttpClent(application: Application) :OkHttpClient{

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val cacheDir = File(application.cacheDir,UUID.randomUUID().toString())
        val cache = Cache(cacheDir, 10*1024*1024)

        return OkHttpClient.Builder()
            .cache(cache)
            .readTimeout(60,TimeUnit.SECONDS)
            .connectTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(60,TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor{
                chain ->
                val newbuilder = chain.request().newBuilder()
                    .addHeader(RAPID_API_HOST_KEY, RAPID_API_HOST_VALUE)
                    .addHeader(RAPID_API_KEY, RAPID_API_KEY_VALUE).build()
                chain.proceed(newbuilder)
            }
            .build()

    }
}