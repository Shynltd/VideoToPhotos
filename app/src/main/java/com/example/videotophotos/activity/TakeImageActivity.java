package com.example.videotophotos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.videotophotos.R;
import com.example.videotophotos.activity.ui.take_image.QuickPictureFragment;
import com.example.videotophotos.activity.ui.take_image.SectionsPagerAdapter;
import com.example.videotophotos.activity.ui.take_image.TimePictureFragment;
import com.google.android.material.tabs.TabLayout;

public class TakeImageActivity extends AppCompatActivity {

    private TabLayout tabs;
    private ViewPager viewPager;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_image);
        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        path = bundle.getString("path");
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        sectionsPagerAdapter.addFragment(new QuickPictureFragment(path), "Quick Picture");
        sectionsPagerAdapter.addFragment(new TimePictureFragment(path), "Time Picture");
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);

    }

    private void initView() {
        tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
    }
}