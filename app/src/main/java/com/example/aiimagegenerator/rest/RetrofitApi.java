package com.example.aiimagegenerator.rest;

import com.example.aiimagegenerator.interfaces.ApiService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitApi {
    private final Retrofit retrofit;
    private static RetrofitApi instance;

    public RetrofitApi() {
        this.retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl("https://api.openai.com/v1/")
                .build();
    }


    public static RetrofitApi getInstance() {
        if (instance == null) {
            synchronized (RetrofitApi.class) {
                instance = new RetrofitApi();
            }
        }
        return instance;
    }
    public ApiService createService(){
        return retrofit.create(ApiService.class);
    }
}
