package com.example.photogallery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.photogallery.model.Photo;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class FullPhoto extends AppCompatActivity {
    private ImageView image_view;
    private Button button;
    Photo photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_photo_activity);

        image_view = findViewById(R.id.full_photo);
        button = findViewById(R.id.add_db_btn);

        Intent intent = getIntent();
        Gson gson = new Gson();

        if (intent == null) {
            closeOnError();
        }

        String gson_str = intent.getStringExtra("photo");

        photo = gson.fromJson(gson_str, Photo.class);

        Picasso.with(this)
                .load(photo.getPhotoUrl())
                .into(image_view);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.full_photo_error_message, Toast.LENGTH_SHORT).show();
    }
}
