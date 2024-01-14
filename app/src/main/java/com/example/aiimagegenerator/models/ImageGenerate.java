package com.example.aiimagegenerator.models;

public class ImageGenerate {
    public String prompt;
    public String size;

    public ImageGenerate(String prompt, String size) {
        this.prompt = prompt;
        this.size = size;
    }
}
