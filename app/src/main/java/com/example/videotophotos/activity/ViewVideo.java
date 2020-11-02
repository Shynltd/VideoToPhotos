package com.example.videotophotos.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videotophotos.R;
import com.example.videotophotos.adapter.ImageCropAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ViewVideo extends AppCompatActivity {

    private VideoView videoView;
    private SeekBar skTime;
    private TextView tvFirstTime;
    private TextView tvTimeLast;
    private RecyclerView rvImageCrop;
    private List<Bitmap> bitmapList;
    private MediaMetadataRetriever media;
    private ImageCropAdapter imageCropAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_video);
        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        final String path = bundle.getString("path");
//        Log.e("path", path);
//        Log.e( "parseUri: ", String.valueOf(Uri.parse(path)));
        videoView.setVideoPath(path);
        media = new MediaMetadataRetriever();
        media.setDataSource(path);
        bitmapList = new ArrayList<>();
        imageCropAdapter = new ImageCropAdapter(bitmapList, getApplicationContext());
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false);
        rvImageCrop.setLayoutManager(linearLayoutManager);
        rvImageCrop.setHasFixedSize(true);
        rvImageCrop.setAdapter(imageCropAdapter);
//        MediaController mediaController = new MediaController(viewVideo.getContext());
//        mediaController.setAnchorView(viewVideo);
//        viewVideo.setMediaController(mediaController);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

                skTime.setMax(videoView.getDuration());
                videoView.start();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                tvTimeLast.setText(simpleDateFormat.format(videoView.getDuration()));
                updateSb();

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
            }
        });

//        Log.e( "uri: ", String.valueOf(uri));
//        imgPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//        viewVideo.start();
//                imgPlay.setVisibility(View.INVISIBLE);
//            }
//        });


        //get Duration
//        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//        retriever.setDataSource(path);
//        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
//        final long timeInMillisec = Long.parseLong(time);
//        retriever.release();
//        //format time
//        SimpleDateFormat formatTime = new SimpleDateFormat("mm:ss");
//        tvLastTime.setText(formatTime.format(timeInMillisec));
//        Log.e( "timeInMillisec: ", String.valueOf(timeInMillisec));
//        Log.e("duration: ", String.valueOf(viewVideo.getDuration()));
//        sbTime.setMax((int) timeInMillisec);
//        sbTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                viewVideo.seekTo(sbTime.getProgress());
//                Log.e( "onStopTrackingTouch: ", String.valueOf(sbTime.getProgress()));
//            }
//        });
//        updateSb();
    }

    private void initView() {
        videoView = (VideoView) findViewById(R.id.videoView);
        skTime = (SeekBar) findViewById(R.id.skTime);
        tvFirstTime = (TextView) findViewById(R.id.tvFirstTime);
        tvTimeLast = (TextView) findViewById(R.id.tvTimeLast);
        rvImageCrop = (RecyclerView) findViewById(R.id.rvImageCrop);
    }
    private void updateSb () {
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
//    public int getDuration (){
//        if (isInPla)
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.camera, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.camera:
                Toast.makeText(this, "chụp", Toast.LENGTH_SHORT).show();
                bitmapList.add(media.getFrameAtTime(skTime.getProgress()));
                imageCropAdapter.notifyItemInserted(bitmapList.size() -1 );
                imageCropAdapter.notifyDataSetChanged();

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}