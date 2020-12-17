package com.example.photogallery.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photogallery.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    private List<String> image_urls;
    private Context context;

    public RVAdapter(Context context) {
        this.context = context;
        this.image_urls = new ArrayList<>();
    }

    public RVAdapter(Context context, List<String> image_urls) {
        this.context = context;
        this.image_urls = image_urls;
    }

    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapter.ViewHolder holder, int position) {
        //Получить асинхронно изображение по url'у
        Picasso.with(context)
                .load(image_urls.get(position))
                .into(holder.image_view);
    }

    @Override
    public int getItemCount() {
        return image_urls.size();
    }

    //Установить список url'ов изображений
    public void setContent(List<String> image_urls) {
        this.image_urls = image_urls;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_view;

        ViewHolder(View view) {
            super(view);
            image_view = view.findViewById(R.id.imageView);
        }
    }
}
