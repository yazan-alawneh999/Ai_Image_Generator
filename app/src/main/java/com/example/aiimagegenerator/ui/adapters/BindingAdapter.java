package com.example.aiimagegenerator.ui.adapters;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class BindingAdapter {
    @androidx.databinding.BindingAdapter("imageURL")
    public static void bindImage(ImageView view, String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .apply(new RequestOptions().centerCrop()) // Apply any additional options if needed
                    .into(view);
        }
    }
}

