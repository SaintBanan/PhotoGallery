package com.example.photogallery;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.photogallery.api.ServiceAPI;
import com.example.photogallery.model.FlickrPhotos;

import java.io.FileInputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoGallery extends AppCompatActivity
{
    FlickrPhotos photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        //Прочитать с локального файла api_key для запросов на flickr.com
        String flickr_api_key = readFile("flickr_api_key.txt");

        //Асинхронный запрос на flickr.com
        ServiceAPI.getFlickrAPI().getRecent(flickr_api_key).enqueue(new Callback<FlickrPhotos>() {
            @Override
            public void onResponse(Call<FlickrPhotos> call, Response<FlickrPhotos> response) {
                photos = response.body();
            }

            @Override
            public void onFailure(Call<FlickrPhotos> call, Throwable t) {
                Toast.makeText(PhotoGallery.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Открыть файл
    public String readFile(String file_name) {
        String data = "";

        try {
            FileInputStream fin = openFileInput(file_name);
            byte[] bytes = new byte[fin.available()];

            fin.read(bytes);
            data = new String(bytes);

            if (fin != null) fin.close();
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return data;
    }
}