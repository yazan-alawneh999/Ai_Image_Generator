package com.example.aiimagegenerator.repository;

import com.example.aiimagegenerator.callbacks.ResponseCallback;

public interface GemeniRepository {
    void getResponse(String query, ResponseCallback callback);
}
