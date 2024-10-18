package com.example.aiimagegenerator.callbacks;

public interface ResponseCallback {

    void onResponse(String response);

    void onFailure(String error);
}
