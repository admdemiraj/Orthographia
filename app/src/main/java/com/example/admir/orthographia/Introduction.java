package com.example.admir.orthographia;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;


import static com.example.admir.orthographia.R.raw.intro;
//import static com.example.admir.orthographia.R.raw.introteliko;

public class Introduction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        VideoView v = (VideoView) findViewById(R.id.videoView);
        v.setVideoURI(Uri.parse(String.format("android.resource://%s/%d", getPackageName(), intro)));
        v.start();
    }
}
