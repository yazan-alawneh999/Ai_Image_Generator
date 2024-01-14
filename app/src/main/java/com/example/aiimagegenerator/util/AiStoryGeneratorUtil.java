package com.example.aiimagegenerator.util;

import android.content.Context;
import android.widget.Toast;

public class AiStoryGeneratorUtil {
    public static void showToast(Context context , String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
