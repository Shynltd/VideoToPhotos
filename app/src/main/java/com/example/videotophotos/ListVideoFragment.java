package com.example.videotophotos;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.videotophotos.adapter.ImageCropAdapter;

import java.util.ArrayList;
import java.util.List;


public class ListVideoFragment extends Fragment {
    private VideoView videoView;
    private RecyclerView rvImageCrop;

    // TODO: Rename and change types of parameters
    private String path;

    public ListVideoFragment(String path) {
        this.path = path;
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        videoView.setVideoPath(path);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                final List<Bitmap> bitmapList = new ArrayList<>();
                final ImageCropAdapter imageCropAdapter =new ImageCropAdapter(bitmapList,getContext());
                final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false);
                rvImageCrop.setLayoutManager(linearLayoutManager);
                rvImageCrop.setHasFixedSize(true);
                rvImageCrop.setAdapter(imageCropAdapter);

               final MediaMetadataRetriever media = new MediaMetadataRetriever();
                media.setDataSource(path);

                Log.d("TAG11", "getDuration: " + videoView.getDuration());
                final MediaController mediaController = new MediaController(getContext());
                mediaController.setAnchorView(videoView);
                mediaController.setPrevNextListeners(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        long time = (long) videoView.getCurrentPosition();

                        bitmapList.add(media.getFrameAtTime(time,MediaMetadataRetriever.OPTION_CLOSEST));


                        imageCropAdapter.notifyItemInserted(bitmapList.size() - 1);
                        imageCropAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), ""+time, Toast.LENGTH_SHORT).show();

                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "pre", Toast.LENGTH_SHORT).show();
                    }
                });
                videoView.setMediaController(mediaController);
                videoView.start();
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        videoView.start();
                    }
                });
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
                    }
                });
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_video, container, false);
        videoView = (VideoView) view.findViewById(R.id.videoView);
        rvImageCrop = (RecyclerView) view.findViewById(R.id.rvImageCrop);
        return view;
    }
}