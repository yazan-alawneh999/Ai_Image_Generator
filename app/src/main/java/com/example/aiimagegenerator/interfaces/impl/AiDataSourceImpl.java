package com.example.aiimagegenerator.interfaces.impl;

import com.example.aiimagegenerator.interfaces.AiDataSource;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.BlockThreshold;
import com.google.ai.client.generativeai.type.GenerationConfig;
import com.google.ai.client.generativeai.type.HarmCategory;
import com.google.ai.client.generativeai.type.SafetySetting;

import java.util.Collections;

public class AiDataSourceImpl implements AiDataSource {
    private static final String KEY_API = "AIzaSyDLbdA6rDxG518J9cRYXE5RXuTHoetsEXA";
    @Override
    public GenerativeModelFutures getAiModel() {
        SafetySetting harassmentSafety = new SafetySetting(HarmCategory.HARASSMENT
        , BlockThreshold.ONLY_HIGH);

        GenerationConfig.Builder configBuilder = new GenerationConfig.Builder();
        configBuilder.temperature = 0.9f;
        configBuilder.topK = 16;
        configBuilder.topP = 0.1f;

        GenerationConfig generationConfig = configBuilder.build();

        GenerativeModel gm = new GenerativeModel("gemini-prod",
                 KEY_API,
                generationConfig,
                Collections.singletonList(harassmentSafety));

        return GenerativeModelFutures.from(gm);
    }
}
