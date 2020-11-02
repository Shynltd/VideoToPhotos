package com.example.videotophotos.activity.ui.take_image;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videotophotos.R;

public class TimePictureFragment extends Fragment {
    private VideoView videoView;
    private SeekBar skTime;
    private TextView tvFirstTime;
    private TextView tvTimeLast;
    private Button btnTake;
    private RecyclerView rvImageCrop;

    private String path;

    public TimePictureFragment(String path) {
        this.path = path;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_picture, container, false);
        initView(view);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        videoView.setVideoPath(path);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });
    }

    private void initView(View view) {
        videoView = (VideoView) view.findViewById(R.id.videoView);
        skTime = (SeekBar) view.findViewById(R.id.skTime);
        tvFirstTime = (TextView) view.findViewById(R.id.tvFirstTime);
        tvTimeLast = (TextView) view.findViewById(R.id.tvTimeLast);
        btnTake = (Button) view.findViewById(R.id.btnTake);
        rvImageCrop = (RecyclerView) view.findViewById(R.id.rvImageCrop);
    }
}