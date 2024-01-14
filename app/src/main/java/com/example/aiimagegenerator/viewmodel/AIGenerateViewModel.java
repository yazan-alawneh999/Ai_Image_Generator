package com.example.aiimagegenerator.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aiimagegenerator.models.ImageGenerate;
import com.example.aiimagegenerator.models.ImageGenerateResponse;
import com.example.aiimagegenerator.repository.GenerateRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AIGenerateViewModel extends ViewModel {
    private final GenerateRepository generateRepository;
    private final MutableLiveData<ImageGenerateResponse>  generateImageResponseLiveData = new MutableLiveData<>();

    public AIGenerateViewModel() {
        generateRepository = new GenerateRepository();
    }

    public void generateImage(ImageGenerate imageGenerate, String header) {
        generateRepository.generateImage(imageGenerate, header, new Callback<ImageGenerateResponse>() {
            @Override
            public void onResponse(Call<ImageGenerateResponse> call, Response<ImageGenerateResponse> response) {
                generateImageResponseLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ImageGenerateResponse> call, Throwable t) {

            }
        });
    }

    public LiveData<ImageGenerateResponse> getGenerateImageResponseLiveData() {
        return generateImageResponseLiveData;
    }
}
