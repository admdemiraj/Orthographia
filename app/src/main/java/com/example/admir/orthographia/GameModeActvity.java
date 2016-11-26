package com.example.admir.orthographia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameModeActvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode_actvity);
        Button button=(Button) findViewById(R.id.practice_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startVoiceActivity();



            }
        });
    }

    public void startVoiceActivity() {
        Intent intent = new Intent(this, VoiceListening.class);
        startActivity(intent);

    }
}
