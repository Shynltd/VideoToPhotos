package com.example.videotophotos.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.videotophotos.R;
import com.example.videotophotos.activity.gallery.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class Gallery extends AppCompatActivity {

    private TextView title;
    private TabLayout tabs;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        initView();
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(Gallery.this, getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);

    }

    private void initView() {
        title = (TextView) findViewById(R.id.title);
        tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
    }
}