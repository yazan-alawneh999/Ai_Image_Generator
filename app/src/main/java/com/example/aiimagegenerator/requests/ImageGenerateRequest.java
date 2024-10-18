package com.example.aiimagegenerator.requests;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class ImageGenerateRequest {


    @SerializedName("model")
    private String model;
    @SerializedName("prompt")
    private String prompt;
    @SerializedName("n")
    private int n;
    @SerializedName("size")
    private String size;

    public ImageGenerateRequest() {

    }

    public ImageGenerateRequest(String model, String prompt, int n, String size) {
        this.model = model;
        this.prompt = prompt;
        this.n = n;
        this.size = size;
    }

    public ImageGenerateRequest(String prompt) {
        this.prompt = prompt;
        this.n = 1;
        this.size = "512x512";
        this.model = "dall-e-3";
    }

    public ImageGenerateRequest(String prompt, int n) {
        this.prompt = prompt;
        this.n = n;
        this.size = "512x512";
        this.model = "dall-e-3";
    }

    public ImageGenerateRequest(String prompt, int n, String size) {
        this.prompt = prompt;
        this.n = n;
        this.size = size;
        this.model = "dall-e-3";
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageGenerateRequest that = (ImageGenerateRequest) o;
        return n == that.n && Objects.equals(model, that.model) && Objects.equals(prompt, that.prompt) && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, prompt, n, size);
    }
}
