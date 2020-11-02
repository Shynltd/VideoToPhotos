package com.example.videotophotos.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videotophotos.model.MediaFileInfo;
import com.example.videotophotos.R;

import java.util.ArrayList;
import java.util.List;

public class SelectVideoAdapter extends RecyclerView.Adapter<SelectVideoAdapter.SelectVideoHolder> {
    Context context;
    List<MediaFileInfo> mediaFileInfoList;
    OnListener listener;

    public SelectVideoAdapter(Context context, OnListener onListener) {
        this.context = context;
        this.mediaFileInfoList = new ArrayList<>();
        this.listener = onListener;
    }

    @NonNull
    @Override
    public SelectVideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_folder, parent, false);
        return new SelectVideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectVideoHolder holder, final int position) {
       holder.imgThumb.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
               if (listener != null){
                   listener.onClick(position, mediaFileInfoList.get(position).getFilePath(), mediaFileInfoList.get(position).getFileDuration());
               }
           }
       });
        Bitmap bitmap = ThumbnailUtils.extractThumbnail(ThumbnailUtils.createVideoThumbnail(mediaFileInfoList.get(position).getFilePath(), MediaStore.Video.Thumbnails.MINI_KIND), 80, 50);
        if (bitmap != null) {
            holder.imgThumb.setImageBitmap(bitmap);
        }

        holder.tvName.setText(mediaFileInfoList.get(position).getFileName());

    }


    @Override
    public int getItemCount() {
        if (mediaFileInfoList == null) return 0;
        return mediaFileInfoList.size();
    }


    public static class SelectVideoHolder extends RecyclerView.ViewHolder {
        ImageView imgThumb;
        TextView tvName;
        ImageView imgPlay;

        public SelectVideoHolder(@NonNull View itemView) {
            super(itemView);
            imgThumb = (ImageView) itemView.findViewById(R.id.imgThumb);
            imgPlay = (ImageView) itemView.findViewById(R.id.imgPlay);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
        }
    }

    public interface OnListener{
        void onClick(int pos, String path, String dur);
    }

    public void addData(List<MediaFileInfo> infoList){
        int last_size = mediaFileInfoList.size();
        mediaFileInfoList.addAll(infoList);
        notifyItemRangeChanged(last_size, infoList.size());
    }
}


