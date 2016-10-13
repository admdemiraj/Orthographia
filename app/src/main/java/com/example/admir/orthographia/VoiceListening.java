package com.example.admir.orthographia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


public class VoiceListening extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_listening);
       Button playButton= (Button) findViewById(R.id.play_btn);
        Button checkIfCorrect = (Button) findViewById(R.id.check_if_correct);
        final EditText editText=(EditText) findViewById(R.id.editText);
       final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.mpthreetest);


        Bundle bundle = getIntent().getExtras();
       final String answerNumber;
        final String answer="nai";


        if(bundle != null) {
            answerNumber = bundle.getString("key");

        }else {answerNumber="0";}






        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.start();
            }
            });



       checkIfCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(findWordByNumber(answer,answerNumber).equals(editText.getText().toString())) {

                        Toast.makeText(getBaseContext(), "ΣΩΣΤΑ!!!!" , Toast.LENGTH_SHORT ).show();
                        startVoiceListening("1");
                    }
                }catch (IOException e){
                    throw new RuntimeException(e);
                }

            }
        });




        try{
            Log.i("GGGGGGGGGGGGG", "fffffffffff" );
            Log.d("GGGGGGGGGGGGG", findWordByNumber(answer,answerNumber) );
        }catch (IOException e){
            throw new RuntimeException(e);
        }


    }


    public   String findWordByNumber(String answer,String answerNumber) throws IOException {

        Context context=getApplicationContext();
        InputStream inputStream = context.getResources().openRawResource(R.raw.words);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        while (bufferedReader.readLine()!=null){
           if (bufferedReader.readLine().equals(answerNumber)) {
                answer = bufferedReader.readLine();
               return answer;

           }
        }

        return answer;
    }

    public  void startVoiceListening(String wordNumber){
        Intent intent = new Intent(this, VoiceListening.class);
        Bundle bundle=new Bundle();
        bundle.putString("key",wordNumber);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();

    }



}
