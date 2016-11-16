package com.example.admir.orthographia;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;


import java.util.Timer;
import java.util.TimerTask;

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

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {

                //here you can start your Activity B.
startMainActivity();

            }

        }, 4000);


    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
