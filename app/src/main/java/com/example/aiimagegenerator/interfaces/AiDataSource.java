package com.example.aiimagegenerator.interfaces;

import com.google.ai.client.generativeai.java.GenerativeModelFutures;

public interface AiDataSource {

    GenerativeModelFutures getAiModel();
}
