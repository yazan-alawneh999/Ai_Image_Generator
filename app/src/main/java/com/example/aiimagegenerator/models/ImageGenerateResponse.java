package com.example.aiimagegenerator.models;

import java.util.List;

public class ImageGenerateResponse {
    private List<String> urlImageGenerates;

    public ImageGenerateResponse(List<String> urlImageGenerates) {
        this.urlImageGenerates = urlImageGenerates;
    }

    public List<String> getUrlImageGenerates() {
        return urlImageGenerates;
    }

    public void setUrlImageGenerates(List<String> urlImageGenerates) {
        this.urlImageGenerates = urlImageGenerates;
    }
}
