package com.example.videotophotos.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.videotophotos.R;

public class MainActivity extends AppCompatActivity {

    private ImageView imgSelectVideo;
    private ImageView imgGallery;
    private ImageView imgSlideshow;
    private ImageView imageView;
    private TextView tvSelectVideo;
    private TextView tvGallery;
    private TextView tvSlideshow;
    private ImageView imgSettings;
    private TextView tvSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPermission();
        tvSelectVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, VideoFolder.class));
            }
        });
        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Gallery.class));
            }
        });
        tvSlideshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, VideoFolder.class));
            }
        });
        tvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, VideoFolder.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                break;
            case R.id.rateUs:
                break;
            case R.id.aboutUs:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        imgSelectVideo = (ImageView) findViewById(R.id.imgSelectVideo);
        imgGallery = (ImageView) findViewById(R.id.imgGallery);
        imgSlideshow = (ImageView) findViewById(R.id.imgSlideshow);
        tvSelectVideo = (TextView) findViewById(R.id.tvSelectVideo);
        tvGallery = (TextView) findViewById(R.id.tvGallery);
        tvSlideshow = (TextView) findViewById(R.id.tvSlideshow);
        imgSettings = (ImageView) findViewById(R.id.imgSettings);
        tvSettings = (TextView) findViewById(R.id.tvSettings);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permision File is Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permision File is Denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
//
//
    public void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                    Toast.makeText(this, "Permission isn't granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(this, "Permission isn't granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }
    }

}