package com.example.aiimagegenerator.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.aiimagegenerator.R;
import com.example.aiimagegenerator.databinding.ActivitySplashScreenBinding;
import com.example.aiimagegenerator.util.FirebaseUtil;

public class SplashScreenActivity extends AppCompatActivity {

    private ActivitySplashScreenBinding activitySplashScreenBinding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activitySplashScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            if(FirebaseUtil.getAuth().getCurrentUser() != null ){
                intent(MainActivity.class);
            }else {
                intent(LoginActivity.class);
            }
        },4000);
    }

    private  void intent(Class<?> clazz) {
        Intent intent =new Intent(this, clazz);
        startActivity(intent);
        finish();
    }
}