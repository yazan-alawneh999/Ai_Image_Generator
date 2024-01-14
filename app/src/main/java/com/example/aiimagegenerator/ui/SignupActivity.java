package com.example.aiimagegenerator.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.aiimagegenerator.R;
import com.example.aiimagegenerator.models.UserModel;
import com.example.aiimagegenerator.databinding.ActivitySinupBinding;
import com.example.aiimagegenerator.interfaces.SignupManager;
import com.example.aiimagegenerator.util.AiStoryGeneratorUtil;
import com.example.aiimagegenerator.util.FirebaseUtil;
import com.google.android.gms.tasks.OnFailureListener;

public class SignupActivity extends AppCompatActivity {
    private ActivitySinupBinding activitySinupBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySinupBinding = DataBindingUtil.setContentView(this, R.layout.activity_sinup);


        activitySinupBinding.setSignupManager(new SignupManager() {
            @Override
            public void signupBtnListner() {
                if (true) {
                    // create new email
                    FirebaseUtil.getAuth()
                            .createUserWithEmailAndPassword(getUser().getUserEmail(), getUser().getUserPass())
                            .addOnSuccessListener(authResult -> {
                                // save user info
                                FirebaseUtil.getUserRef()
                                        .child(authResult.getUser().getUid())
                                        .setValue(getUser())
                                        .addOnSuccessListener(unused ->
                                        {
                                            AiStoryGeneratorUtil.showToast(getApplicationContext(), "signup successfully");
                                            Intent logIn = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(logIn);
                                            finish();
                                        })
                                        .addOnFailureListener(e -> {
                                            AiStoryGeneratorUtil.showToast(getApplicationContext(), e.getMessage());

                                        });

                            });
                }
            }

            @Override
            public void haveAccountTxtViewListner() {
                Intent logIn = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(logIn);
                finish();
            }
        });

    }


    private UserModel getUser() {
        String name = activitySinupBinding.editTextName.getText().toString();
        String email = activitySinupBinding.editTextEmail.getText().toString();
        String password = activitySinupBinding.editTextPassword.getText().toString();
        return new UserModel(name, email, password);
    }

    private boolean validateInput() {
        if (activitySinupBinding.editTextName.getText() == null || TextUtils.isEmpty(activitySinupBinding.editTextName.getText())) {
            activitySinupBinding.editTextName.setError("not valid input");
            return false;
        } else if (activitySinupBinding.editTextEmail.getText() == null || TextUtils.isEmpty(activitySinupBinding.editTextEmail.getText())) {
            activitySinupBinding.editTextEmail.setError("not valid input");
            return false;

        } else if (activitySinupBinding.editTextPassword.getText() == null || TextUtils.isEmpty(activitySinupBinding.editTextPassword.getText())) {
            activitySinupBinding.editTextPassword.setError("not valid input");
            return false;
        } else if (activitySinupBinding.editTextConfirmPassword.getText() == null || TextUtils.isEmpty(activitySinupBinding.editTextConfirmPassword.getText())) {
            activitySinupBinding.editTextConfirmPassword.setError("not valid input");
            return false;
        } else if (!(activitySinupBinding.editTextPassword.getText().toString().equals(activitySinupBinding.editTextConfirmPassword.getText().toString()))) {
            activitySinupBinding.editTextConfirmPassword.setError("passwords not similar");
            return false;
        }
        activitySinupBinding.editTextName.setError(null);
        activitySinupBinding.editTextEmail.setError(null);
        activitySinupBinding.editTextPassword.setError(null);
        activitySinupBinding.editTextConfirmPassword.setError(null);
        return true;

    }
}


