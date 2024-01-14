package com.example.aiimagegenerator.repository;

import com.example.aiimagegenerator.interfaces.ApiService;
import com.example.aiimagegenerator.models.ImageGenerate;
import com.example.aiimagegenerator.models.ImageGenerateResponse;
import com.example.aiimagegenerator.rest.RetrofitApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.POST;

public class GenerateRepository {
    private final ApiService apiService;

    public GenerateRepository() {

        this.apiService = RetrofitApi.getInstance().createService();
    }


    public void generateImage(ImageGenerate imageGenerate, String header, Callback<ImageGenerateResponse> callback) {
        apiService.generateImage(imageGenerate,header).enqueue(callback);
    }
}
