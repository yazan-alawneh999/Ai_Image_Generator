package com.example.aiimagegenerator.interfaces;

import com.example.aiimagegenerator.models.ImageGenerate;
import com.example.aiimagegenerator.models.ImageGenerateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @POST("images/generations")
    Call<ImageGenerateResponse> generateImage(@Body ImageGenerate imageGenerate, @Header("Authorization") String header);
}
