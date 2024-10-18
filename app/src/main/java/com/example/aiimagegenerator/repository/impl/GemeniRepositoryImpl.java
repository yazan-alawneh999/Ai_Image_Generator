package com.example.aiimagegenerator.repository.impl;

import androidx.annotation.NonNull;

import com.example.aiimagegenerator.callbacks.ResponseCallback;
import com.example.aiimagegenerator.interfaces.AiDataSource;
import com.example.aiimagegenerator.repository.GemeniRepository;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;

public class GemeniRepositoryImpl implements GemeniRepository {
    private final AiDataSource dataSource;

    public GemeniRepositoryImpl(AiDataSource aiDataSource) {
        this.dataSource = aiDataSource;
    }

    @Override
    public void getResponse(String query, ResponseCallback callback) {
        GenerativeModelFutures aiModel = dataSource.getAiModel();
        Content content = new Content.Builder().addText(query).build();
        Executor executor = Runnable::run;
        ListenableFuture<GenerateContentResponse> response = aiModel.generateContent(content);
        Futures.addCallback(response,
                new FutureCallback<GenerateContentResponse>() {
                    @Override
                    public void onSuccess(GenerateContentResponse result) {
                        callback.onResponse(result.getText());
                    }

                    @Override
                    public void onFailure(@NonNull Throwable t) {
                        callback.onFailure(t.toString());

                    }
                }, executor);
    }
}
