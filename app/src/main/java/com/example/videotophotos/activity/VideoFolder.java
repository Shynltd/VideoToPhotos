package com.example.videotophotos.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videotophotos.R;
import com.example.videotophotos.adapter.SelectVideoAdapter;
import com.example.videotophotos.model.MediaFileInfo;

import java.util.ArrayList;
import java.util.List;

public class VideoFolder extends AppCompatActivity {

    private RecyclerView rvList;
    private List<MediaFileInfo> mediaFileInfoList;
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_folder);
        initView();
        type = "video";
        new MediaAsyncTask().execute(type);
    }

    private void parseAllVideo(String type) {
        try {
            String name = null;
            int video_column_index;
            String[] proj = {MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media.DURATION,
                    MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.SIZE};
            Cursor videocursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    proj, null, null, null);
            int count = videocursor.getCount();
            Log.d("No of video", "" + count);
            for (int i = 0; i < count; i++) {
                MediaFileInfo mediaFileInfo = new MediaFileInfo();
                video_column_index = videocursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
                videocursor.moveToPosition(i);
                name = videocursor.getString(video_column_index);

                mediaFileInfo.setFileName(name);

                int column_index = videocursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                int duration = videocursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
                videocursor.moveToPosition(i);
                String filepath = videocursor.getString(column_index);
                String dur = videocursor.getString(duration);
                mediaFileInfo.setFileDuration(dur);
                mediaFileInfo.setFilePath(filepath);
                mediaFileInfo.setFileType(type);
                mediaFileInfoList.add(mediaFileInfo);
            }
            videocursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        rvList = (RecyclerView) findViewById(R.id.rvList);
    }

    public class MediaAsyncTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            int result = 0;
            String type = strings[0];
            mediaFileInfoList = new ArrayList<MediaFileInfo>();
            try {
                if (type.equalsIgnoreCase("video")) {
                    parseAllVideo(type);
                    result = 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = 0;
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer == 1) {
                SelectVideoAdapter selectVideoAdapter = new SelectVideoAdapter(getApplicationContext(), new SelectVideoAdapter.OnListener() {
                    @Override
                    public void onClick(int pos, String path, String dur) {
//                        SupportReplaceFragment supportReplaceFragment = new SupportReplaceFragment();
//                        supportReplaceFragment.replaceFragment(getSupportFragmentManager(),new ListVideoFragment(path), R.id.frame);
                        Intent intent = new Intent(VideoFolder.this, TakeImageActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("path", path);
                        intent.putExtra("bundle", bundle);
                        startActivity(intent);

                    }
                });
                selectVideoAdapter.addData(mediaFileInfoList);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VideoFolder.this);
                rvList.setLayoutManager(linearLayoutManager);
                rvList.setHasFixedSize(true);
                rvList.setAdapter(selectVideoAdapter);
            } else {
                Log.e("TAG", "Failed to fetch data!");
            }
        }
    }
}
