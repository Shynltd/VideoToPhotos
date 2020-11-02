package com.example.videotophotos.activity.ui.take_image;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videotophotos.R;
import com.example.videotophotos.adapter.ImageCropAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class QuickPictureFragment extends Fragment {
    private VideoView videoView;
    private SeekBar skTime;
    private TextView tvFirstTime;
    private TextView tvTimeLast;
    private Button btnTake;
    private RecyclerView rvImageCrop;

    private String path;
    private List<Bitmap> bitmapList;
    private MediaMetadataRetriever media;
    private ImageCropAdapter imageCropAdapter;

    public QuickPictureFragment(String path) {
        this.path = path;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_view_video, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        videoView.setVideoPath(path);
        media = new MediaMetadataRetriever();
        media.setDataSource(path);
        bitmapList = new ArrayList<>();
        imageCropAdapter = new ImageCropAdapter(bitmapList, getActivity());
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        rvImageCrop.setLayoutManager(linearLayoutManager);
        rvImageCrop.setHasFixedSize(true);
        rvImageCrop.setAdapter(imageCropAdapter);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                skTime.setMax(videoView.getDuration());
                videoView.start();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                tvTimeLast.setText(simpleDateFormat.format(videoView.getDuration()));
                updateSk();
                skTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        videoView.seekTo(seekBar.getProgress());
                    }
                });
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        videoView.start();
                    }
                });
                btnTake.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bitmapList.add(media.getFrameAtTime(skTime.getProgress()));
                        imageCropAdapter.notifyItemInserted(bitmapList.size() -1 );
                        imageCropAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void initView(View root) {
        videoView = (VideoView) root.findViewById(R.id.videoView);
        skTime = (SeekBar) root.findViewById(R.id.skTime);
        tvFirstTime = (TextView) root.findViewById(R.id.tvFirstTime);
        tvTimeLast = (TextView) root.findViewById(R.id.tvTimeLast);
        btnTake = (Button) root.findViewById(R.id.btnTake);
        rvImageCrop = (RecyclerView) root.findViewById(R.id.rvImageCrop);
    }

    private void updateSk () {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                tvFirstTime.setText(simpleDateFormat.format(videoView.getCurrentPosition()));
                skTime.setProgress(videoView.getCurrentPosition());
                handler.postDelayed(this,900);
            }
        }, 0);
    }
}