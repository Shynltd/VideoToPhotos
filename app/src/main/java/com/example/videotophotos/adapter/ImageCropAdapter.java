package com.example.videotophotos.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videotophotos.R;

import java.util.List;

public class ImageCropAdapter extends RecyclerView.Adapter<ImageCropAdapter.ImageCropHolder> {
    List<Bitmap> bitmapList;
    Context context;

    public ImageCropAdapter(List<Bitmap> bitmapList, Context context) {
        this.bitmapList = bitmapList;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageCropHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_image_crop, parent, false);
        return new ImageCropHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageCropHolder holder, int position) {
        holder.imgImage.setImageBitmap(bitmapList.get(position));
    }

    @Override
    public int getItemCount() {
        if (bitmapList == null) return 0;
        return bitmapList.size();
    }

    public static class ImageCropHolder extends RecyclerView.ViewHolder {
        ImageView imgImage;
        public ImageCropHolder(@NonNull View itemView) {
            super(itemView);
            imgImage = itemView.findViewById(R.id.imgImage);
        }
    }
}
