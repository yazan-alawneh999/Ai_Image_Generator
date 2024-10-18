package com.example.aiimagegenerator.repository;

import com.example.aiimagegenerator.interfaces.ApiService;
import com.example.aiimagegenerator.models.ImageGenerate;
import com.example.aiimagegenerator.models.ImageGenerateResponse;
import com.example.aiimagegenerator.requests.ImageGenerateRequest;
import com.example.aiimagegenerator.rest.RetrofitApi;
import com.google.android.gms.common.api.Api;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.POST;

public class GenerateRepository {
    private final ApiService apiService;

    public GenerateRepository(ApiService apiService) {
        this.apiService = apiService;

    }


    public Single<ImageGenerateResponse> generateImage(ImageGenerateRequest imageGenerate, String header) {
       return apiService.generateImage(imageGenerate, header);
    }
}
