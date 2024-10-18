package com.example.aiimagegenerator.interfaces;

import android.database.Observable;

import com.example.aiimagegenerator.models.ImageGenerate;
import com.example.aiimagegenerator.models.ImageGenerateResponse;
import com.example.aiimagegenerator.requests.ImageGenerateRequest;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    String BASE_URL = "https://api.openai.com/v1/";
//    String API_KEY = "AIzaSyDLbdA6rDxG518J9cRYXE5RXuTHoetsEXA";

    @Headers("Content-Type: application/json")
    @POST("images/generations")
    Single<ImageGenerateResponse> generateImage(@Body ImageGenerateRequest imageGenerate, @Header("Authorization") String header);
}
