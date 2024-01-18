package com.example.aiimagegenerator.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aiimagegenerator.R;
import com.example.aiimagegenerator.databinding.ActivityMain2Binding;
import com.example.aiimagegenerator.models.ImageGenerate;
import com.example.aiimagegenerator.util.FirebaseUtil;
import com.example.aiimagegenerator.viewmodel.AIGenerateViewModel;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMain2Binding activityMain2Binding;

    private String imageUrl;
    private static final String TAG = "yazan";
    public static final MediaType JSON = MediaType.get("application/json");
    OkHttpClient client = new OkHttpClient();

    private AIGenerateViewModel aiGenerateViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMain2Binding = DataBindingUtil.setContentView(this, R.layout.activity_main2);
        aiGenerateViewModel = new ViewModelProvider(this).get(AIGenerateViewModel.class);
        activityMain2Binding.setIsAdmin(true);
        activityMain2Binding.generateBtn.setOnClickListener(v ->
        {
            activityMain2Binding.setIsAdmin(false);
            activityMain2Binding.setIsAdminBackBtn(false);
            activityMain2Binding.setIsLoading(true);
            String prompt = activityMain2Binding.promptEt.getText().toString();
            aiGenerateViewModel.generateImage(new ImageGenerate(prompt, "256x256"), "Bearer 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");
            activityMain2Binding.imageDescription.setText("");
            callApi(prompt);
        });
        aiGenerateViewModel.getGenerateImageResponseLiveData().observe(this,s -> {
//            JSONObject jsonObject = new JSONObject((response.body().string()));
//            String result = jsonObject.getJSONArray("choices").getJSONObject(0).getString("text");
//            runOnUiThread(() -> {
//                activityMain2Binding.imageDescription.setText(result);
//                activityMain2Binding.setIsLoading(false);
//                FirebaseUtil.getCurrentUserRef().get().addOnSuccessListener(dataSnapshot ->
//                {
//                    if (dataSnapshot.hasChild("isAdmin")) {
//                        activityMain2Binding.setIsAdminBackBtn(true);
//                    }
//                });
//            });
        });
        activityMain2Binding.logout.setOnClickListener(v ->
        {
            FirebaseUtil.getAuth().signOut();
            Intent in = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(in);
            finish();
        });
        activityMain2Binding.back.setOnClickListener(v -> {
            activityMain2Binding.setIsAdmin(true);
        });


    }

    private void callApi(String prompt) {
        JSONObject object = new JSONObject();
        try {
            object.put("prompt", prompt);
            object.put("size", "256x256");
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        RequestBody requestBody = RequestBody.create(object.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/images/generations")
                .header("Authorization", "Bearer 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")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(MainActivity.this, "failed to generate image", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject((response.body().string()));
                    imageUrl = jsonObject.getJSONArray("data").getJSONObject(0).getString("url");
                    loadImage(imageUrl, prompt);
                } catch (Exception e1) {
                    Toast.makeText(MainActivity.this, e1.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void callApi1(String prompt) {
        JSONObject object1 = new JSONObject();
        try {
            object1.put("model", "gpt-3.5-turbo-instruct");
            object1.put("prompt", prompt);
            object1.put("max_tokens", 500);
            object1.put("temperature", 0);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        RequestBody requestBody1 = RequestBody.create(object1.toString(), JSON);
        Request request1 = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization", "Bearer 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")
                .post(requestBody1)
                .build();

        client.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "failed to generate image", Toast.LENGTH_SHORT).show();
                    activityMain2Binding.setIsLoading(false);

                });


            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject((response.body().string()));
                    String result = jsonObject.getJSONArray("choices").getJSONObject(0).getString("text");
                    runOnUiThread(() -> {
                        activityMain2Binding.imageDescription.setText(result);
                        activityMain2Binding.setIsLoading(false);
//                        FirebaseUtil.getCurrentUserRef().get().addOnSuccessListener(dataSnapshot ->
//                        {
//                            if (dataSnapshot.hasChild("isAdmin")) {
//                                activityMain2Binding.setIsAdminBackBtn(true);
//                            }
//                        });
                    });
                } catch (Exception e1) {
                    Log.d(TAG, "onResponse: Error : " + e1.getMessage());
                }
            }
        });
    }

    private void loadImage(String imageUrl, String prompt) {
        runOnUiThread(() -> {
            Glide.with(this)
                    .load(imageUrl)
                    .centerCrop()
                    .into(activityMain2Binding.generatedImage);
            callApi1(prompt);
        });

    }
}