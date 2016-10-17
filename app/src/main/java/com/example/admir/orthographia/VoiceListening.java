package com.example.admir.orthographia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


public class VoiceListening extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_listening);
        Bundle bundle = getIntent().getExtras();
        final String answerNumber;
        final String answer = "nai";
        final Button easyButton = (Button) findViewById(R.id.easy_btn);
        final Button mediumButton = (Button) findViewById(R.id.medium_btn);
        final Button hardButton = (Button) findViewById(R.id.hard_btn);

        if (bundle != null) {
            answerNumber = bundle.getString("key");

        } else {
            answerNumber = "0";
        }


        playMedia();

        changeDifficulty();


      setDifficultyForTheNewLevel(answerNumber,easyButton,mediumButton,hardButton);


        checkIfTheAnswerIsCorrect(answer, answerNumber);

        try{
            Log.i("GGGGGGGGGGGGG", "fffffffffff" );
            Log.d("GGGGGGGGGGGGG", findWordByNumber(answer,answerNumber) );
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }

public void setDifficultyForTheNewLevel(String aswerNumber,Button easyButton,Button mediumButton,Button hardButton){
    ArrayList<String> easy = new ArrayList<String>();
    ArrayList<String> medium = new ArrayList<String>();
    ArrayList<String> hard = new ArrayList<String>();

    for(int i=1;i<=5;i++){
        easy.add(String.valueOf(i));
        medium.add(String.valueOf(i+5));
        hard.add(String.valueOf(i+10));

    }
if(hard.contains(aswerNumber)){
    hardButton.setPressed(true);
}else if(medium.contains(aswerNumber)){
    mediumButton.setPressed(true);
}else {
    easyButton.setPressed(true);
}

}

    public String sellectRandomWordAccordingToDifficulty(){
        final Button easyButton = (Button) findViewById(R.id.easy_btn);
        final Button mediumButton = (Button) findViewById(R.id.medium_btn);
        final Button hardButton = (Button) findViewById(R.id.hard_btn);
        Random random = new Random();
        String answerNumber;

        if(hardButton.isPressed()){
            answerNumber=String.valueOf(random.nextInt(16 - 11) + 11);
        }
else if(mediumButton.isPressed()){
            answerNumber=String.valueOf(random.nextInt(11 - 6) + 6);
        }else{
            answerNumber=String.valueOf(random.nextInt(6 - 1) + 1);
        }

        return answerNumber;
    }



    public void changeDifficulty() {
        final Button easyButton = (Button) findViewById(R.id.easy_btn);
        final Button mediumButton = (Button) findViewById(R.id.medium_btn);
        final Button hardButton = (Button) findViewById(R.id.hard_btn);

        easyButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                easyButton.setPressed(true);
                mediumButton.setPressed(false);
                hardButton.setPressed(false);
                return true;
            }
        });
        mediumButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mediumButton.setPressed(true);
                easyButton.setPressed(false);
                hardButton.setPressed(false);
                return true;
            }
        });

        hardButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hardButton.setPressed(true);
                mediumButton.setPressed(false);
                easyButton.setPressed(false);
                return true;
            }
        });

    }


    public void playMedia() {
        Button playButton = (Button) findViewById(R.id.play_btn);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.mpthreetest);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.start();
            }
        });

    }

    public void checkIfTheAnswerIsCorrect(final String answer, final String answerNumber) {
        Button checkIfCorrect = (Button) findViewById(R.id.check_if_correct);
        final EditText editText = (EditText) findViewById(R.id.editText);

        checkIfCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (findWordByNumber(answer, answerNumber).equals(editText.getText().toString())) {

                        Toast.makeText(getBaseContext(), "ΣΩΣΤΑ!!!!", Toast.LENGTH_SHORT).show();
                        startVoiceListening(sellectRandomWordAccordingToDifficulty());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }


    public String findWordByNumber(String answer, String answerNumber) throws IOException {

        Context context = getApplicationContext();
        InputStream inputStream = context.getResources().openRawResource(R.raw.words);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        while (bufferedReader.readLine() != null) {
            if (bufferedReader.readLine().equals(answerNumber)) {
                answer = bufferedReader.readLine();
                return answer;

            }
        }

        return answer;
    }


    public void startVoiceListening(String wordNumber) {
        Intent intent = new Intent(this, VoiceListening.class);
        Bundle bundle = new Bundle();
        bundle.putString("key", wordNumber);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();

    }


}
