package com.example.admir.orthographia;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VoiceListening extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_listening);
       Button play_btn = (Button) findViewById(R.id.play_btn);
       final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.mpthreetest);

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.start();
            }
            });

    }





}
