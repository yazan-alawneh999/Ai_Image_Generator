package com.example.aiimagegenerator.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.aiimagegenerator.R;
import com.example.aiimagegenerator.databinding.ActivityLoginBinding;
import com.example.aiimagegenerator.interfaces.LoginManager;
import com.example.aiimagegenerator.util.AiStoryGeneratorUtil;
import com.example.aiimagegenerator.util.FirebaseUtil;

public class LoginActivity extends AppCompatActivity {
private ActivityLoginBinding activityLoginBinding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);


            activityLoginBinding.setLoginManager(new LoginManager() {
                @Override
                public void login(String email, String password) {
                    if (validateInput())
                    {

                        String em = activityLoginBinding.editTextUsername.getText().toString();
                        String pass = activityLoginBinding.editTextPassword.getText().toString();

                        FirebaseUtil.getAuth()
                                .signInWithEmailAndPassword(em,pass)
                                .addOnSuccessListener(authResult -> {
                                    Intent in = new Intent(getApplicationContext() , MainActivity.class);
                                    startActivity(in);
                                    AiStoryGeneratorUtil.showToast(getApplicationContext(),"logged in successfully");
                                    finish();
                                })
                                .addOnFailureListener(e ->
                                {
                                    AiStoryGeneratorUtil.showToast(getApplicationContext(),e.getMessage());
                                });
                    }


                }

                @Override
                public void createAccountListner() {
                    Intent signupIntent = new Intent(getApplicationContext(), SignupActivity.class);
                    startActivity(signupIntent);
                    finish();

                }
            });
        }


    private boolean validateInput() {
        if ( TextUtils.isEmpty(activityLoginBinding.editTextUsername.getText())){
            activityLoginBinding.editTextUsername.setError("not valid input");
            return false;

        }else if ( TextUtils.isEmpty(activityLoginBinding.editTextUsername.getText())){
            activityLoginBinding.editTextPassword.setError("not valid input");
            return false ;
        }
        activityLoginBinding.editTextUsername.setError(null);
        activityLoginBinding.editTextPassword.setError(null);
        return true ;

    }
}