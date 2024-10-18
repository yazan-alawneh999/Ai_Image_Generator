package com.example.aiimagegenerator.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aiimagegenerator.callbacks.ResponseCallback;
import com.example.aiimagegenerator.interfaces.ApiService;
import com.example.aiimagegenerator.interfaces.impl.AiDataSourceImpl;
import com.example.aiimagegenerator.models.ImageGenerate;
import com.example.aiimagegenerator.models.ImageGenerateResponse;
import com.example.aiimagegenerator.repository.GemeniRepository;
import com.example.aiimagegenerator.repository.GenerateRepository;
import com.example.aiimagegenerator.repository.impl.GemeniRepositoryImpl;
import com.example.aiimagegenerator.requests.ImageGenerateRequest;
import com.example.aiimagegenerator.rest.APIClient;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AIGenerateViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final GenerateRepository generateRepository;
    private MutableLiveData<Status> generationStatus = new MutableLiveData<>();
    private MutableLiveData<Status> gemeniRespnseStatus = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<String> gemeniResponseSuccessLiveData = new MutableLiveData<>();
    private MutableLiveData<String> gemeniResponseFailerLiveData = new MutableLiveData<>();
    private static final String TAG = "AIGenerateViewModel";
    private MutableLiveData<ImageGenerateResponse> successLiveData = new MutableLiveData<>();
    private final GemeniRepository gemeniRepository;

    public AIGenerateViewModel() {
        generateRepository = new GenerateRepository(APIClient.getClient().create(ApiService.class));
        gemeniRepository = new GemeniRepositoryImpl(new AiDataSourceImpl());
    }

    public void generateImage(ImageGenerateRequest imageGenerate, String header) {
        disposable.add(
                generateRepository
                        .generateImage(imageGenerate, header)
                        .doOnSubscribe(disposable1 -> {
                            generationStatus.postValue(Status.LOADING);
                            Log.d(TAG, "loading ... ");
                        })
                        .subscribeOn(Schedulers.io())
                        .subscribe(imageGenerateResponse -> {
                                    Log.d(TAG, "success ... ");
                                    generationStatus.postValue(Status.SUCCESS);
                                    successLiveData.postValue(imageGenerateResponse);
                                }
                                , throwable -> {
                                    Log.d(TAG, "error ... ");
                                    generationStatus.postValue(Status.ERROR);
                                    errorLiveData.postValue(throwable.getMessage());
                                }
                        ));
    }

    public void getResponse(String query) {
        gemeniRespnseStatus.setValue(Status.LOADING);
        gemeniRepository.getResponse(query, new ResponseCallback() {
            @Override
            public void onResponse(String response) {
                gemeniRespnseStatus.postValue(Status.SUCCESS);
                gemeniResponseSuccessLiveData.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                gemeniRespnseStatus.postValue(Status.ERROR);
                gemeniResponseFailerLiveData.postValue(error);
            }
        });
    }

    public LiveData<Status> getGemeniRespnseStatus() {
        return gemeniRespnseStatus;
    }

    public LiveData<String> getGemeniResponseSuccessLiveData() {
        return gemeniResponseSuccessLiveData;
    }

    public LiveData<String> getGemeniResponseFailerLiveData() {
        return gemeniResponseFailerLiveData;
    }

    public LiveData<ImageGenerateResponse> getSuccessLiveData() {
        return successLiveData;
    }

    public MutableLiveData<Status> getGenerationStatus() {
        return generationStatus;
    }


    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void setErrorLiveData(MutableLiveData<String> errorLiveData) {
        this.errorLiveData = errorLiveData;
    }

    public void setSuccessLiveData(MutableLiveData<ImageGenerateResponse> successLiveData) {
        this.successLiveData = successLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public enum Status {
        SUCCESS,
        LOADING,
        ERROR
    }
}
