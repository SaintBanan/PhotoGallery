package com.example.photogallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.photogallery.api.ServiceAPI;
import com.example.photogallery.model.FlickrPhotos;
import com.example.photogallery.model.Photo;
import com.example.photogallery.view.RVAdapter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoGallery extends AppCompatActivity
{
    List<Photo> photos;

    //Изменить главное меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        //Прослушивать элемент поиска
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //Сработает при отправке введенного текста
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            //Сработает при вводе текста
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

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
                photos = response.body().getPhotos().getPhoto();

                //Создать список с изображениями
                setRVAdapter(createImageUrls(photos));
            }

            @Override
            public void onFailure(Call<FlickrPhotos> call, Throwable t) {
                Toast.makeText(PhotoGallery.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Сформировать список url'ов изображений
    public List<String> createImageUrls(List<Photo> photos) {
        List<String> image_ursl = new ArrayList<>();
        int photo_size = photos.size();

        for (int i = 0; i < photo_size; i++) {
            Photo photo = photos.get(i);
            image_ursl.add(String.format("https://live.staticflickr.com/%s/%s_%s_w.jpg", photo.getServer(), photo.getId(), photo.getSecret()));
        }

        return image_ursl;
    }

    //Создать список и установить адаптер
    public void setRVAdapter(List<String> image_ursl) {
        RVAdapter adapter = new RVAdapter(this, image_ursl);

        RecyclerView list_view = findViewById(R.id.recyclerView);
        GridLayoutManager layout_manager = new GridLayoutManager(this, 3);

        list_view.setLayoutManager(layout_manager);
        list_view.setAdapter(adapter);
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