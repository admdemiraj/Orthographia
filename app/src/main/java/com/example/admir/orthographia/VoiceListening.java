package com.example.admir.orthographia;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.ActionBar;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;



public class VoiceListening extends AppCompatActivity {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_voice_listening);


        Bundle bundle = getIntent().getExtras();
        final String answerNumber;
        //final String answer = "nai";
        final Button easyButton = (Button) findViewById(R.id.easy_btn);
        final Button mediumButton = (Button) findViewById(R.id.medium_btn);
        final Button hardButton = (Button) findViewById(R.id.hard_btn);
        final EditText editText = (EditText) findViewById(R.id.editText);
        final Button solveButton = (Button) findViewById(R.id.solve_btn);
        final Button nextButton = (Button) findViewById(R.id.next_btn);
        final Button explainButton = (Button) findViewById(R.id.explain_btn);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        getSupportActionBar().setCustomView(R.layout.actionbar_layout);



        if (bundle != null) {
            answerNumber = bundle.getString("key");

        } else {
            answerNumber = "0";
        }


        try {
            final TextView textView = (TextView) findViewById(R.id.textView2);
            textView.setText(findWordByNumber(answerNumber));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        editText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});





        playMedia(getVideoAccordingToAnswerNumber(answerNumber));

        changeDifficulty(easyButton, mediumButton, hardButton);


        setDifficultyForTheNewLevel(answerNumber, easyButton, mediumButton, hardButton);


        checkIfTheAnswerIsCorrect(answerNumber, editText);

        getSolution(solveButton, editText, answerNumber);

        goToNextWord(nextButton);


        findWordsMeaning(explainButton,"25");



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public MediaPlayer getVideoAccordingToAnswerNumber(String answerNumber) {
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.ofelimos);
        final MediaPlayer mediaPlayer2 = MediaPlayer.create(this, R.raw.mpthreetest);
        final MediaPlayer mediaPlayer3 = MediaPlayer.create(this, R.raw.phgh);

        switch (answerNumber) {

            case "1":
                return mediaPlayer2;
            case "2":
                return mediaPlayer;
            case "3":
                return mediaPlayer2;
            case "4":
                return mediaPlayer;
            case "5":
                return mediaPlayer2;
            case "6":
                return mediaPlayer;
            case "7":
                return mediaPlayer2;
            case "8":
                return mediaPlayer;
            case "9":
                return mediaPlayer2;
            case "10":
                return mediaPlayer;
            case "11":
                return mediaPlayer2;
            case "12":
                return mediaPlayer;
            case "13":
                return mediaPlayer2;
            case "14":
                return mediaPlayer;
            default:
                return mediaPlayer3;
        }
    }
    private void initiatePopupWindow(String answerNumber) throws IOException {

           final PopupWindow pw;



            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) VoiceListening.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.pop_up_layout,
                    (ViewGroup) findViewById(R.id.pop_up_element));
            // create a 300px width and 470px height PopupWindow
            pw = new PopupWindow(layout, 1100, 550, true);
        StringBuilder string = new StringBuilder();
        ((TextView)pw.getContentView().findViewById(R.id.definition)).setText(findDedinitionByNumber(answerNumber));
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
            Button cancelButton = (Button) layout.findViewById(R.id.cancel_btn);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pw.dismiss();

                }
            });
                }




    public void findWordsMeaning(Button explainButton, final String answerNumber) {



        explainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    initiatePopupWindow(answerNumber);
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        });


    }


    public void goToNextWord(Button nextButton) {
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                startVoiceListening(sellectRandomWordAccordingToDifficulty());
                finish();

            }
        });

    }


    public void getSolution(final Button solveButton, final EditText editText, final String answerNumber) {

        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    editText.setText(findWordByNumber(answerNumber));
                } catch (IOException e) {
                    throw new RuntimeException(e);

                }
            }
        });

    }


    public void setDifficultyForTheNewLevel(String aswerNumber, Button easyButton, Button mediumButton, Button hardButton) {
        ArrayList<String> easy = new ArrayList<String>();
        ArrayList<String> medium = new ArrayList<String>();
        ArrayList<String> hard = new ArrayList<String>();

        for (int i = 0; i <= 224; i++) {
            easy.add(String.valueOf(i));
            medium.add(String.valueOf(i + 224));
            hard.add(String.valueOf(i + 357));

        }
        if (hard.contains(aswerNumber)) {
            hardButton.setPressed(true);

        } else if (medium.contains(aswerNumber)) {
            mediumButton.setPressed(true);

        } else {
            easyButton.setPressed(true);

        }

    }

    public String sellectRandomWordAccordingToDifficulty() {
        final Button easyButton = (Button) findViewById(R.id.easy_btn);
        final Button mediumButton = (Button) findViewById(R.id.medium_btn);
        final Button hardButton = (Button) findViewById(R.id.hard_btn);
        Random random = new Random();
        String answerNumber;

        if (hardButton.isPressed()) {
            answerNumber = String.valueOf(random.nextInt(428 - 357) + 357);
        } else if (mediumButton.isPressed()) {
            answerNumber = String.valueOf(random.nextInt(357 - 225) + 225);
        } else {
            answerNumber = String.valueOf(random.nextInt(225 - 0) + 0);
        }

        return answerNumber;
    }


    public void changeDifficulty(final Button easyButton, final Button mediumButton, final Button hardButton) {


        easyButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                easyButton.setPressed(true);
                mediumButton.setPressed(false);
                hardButton.setPressed(false);
                startVoiceListening(sellectRandomWordAccordingToDifficulty());
                finish();
                return true;
            }
        });
        mediumButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mediumButton.setPressed(true);
                easyButton.setPressed(false);
                hardButton.setPressed(false);
                startVoiceListening(sellectRandomWordAccordingToDifficulty());
                finish();
                return true;
            }
        });

        hardButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hardButton.setPressed(true);
                mediumButton.setPressed(false);
                easyButton.setPressed(false);
                startVoiceListening(sellectRandomWordAccordingToDifficulty());
                finish();
                return true;
            }
        });

    }


    public void playMedia(final MediaPlayer mediaPlayer) {
        Button playButton = (Button) findViewById(R.id.play_btn);

        //final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.ofelimos);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.start();
            }
        });

    }

    public void checkIfTheAnswerIsCorrect(final String answerNumber, final EditText editText) {
        Button checkIfCorrect = (Button) findViewById(R.id.check_if_correct);

        final MediaPlayer mediaPlayerRight = MediaPlayer.create(this, R.raw.correct);
        final MediaPlayer mediaPlayerFalse = MediaPlayer.create(this, R.raw.correct);
        checkIfCorrect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    if (findWordByNumber(answerNumber).equals(editText.getText().toString())) {

                        mediaPlayerRight.start();
                        Toast.makeText(getBaseContext(), "ΣΩΣΤΑ!!!!", Toast.LENGTH_SHORT).show();
                        startVoiceListening(sellectRandomWordAccordingToDifficulty());
                        finish();

                    }else{
                        mediaPlayerFalse.start();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }
    public String findDedinitionByNumber(String answerNumber) throws IOException {

       // ArrayList<String> answerDefinition = new ArrayList<String>();
        StringBuilder string = new StringBuilder();
        Context context = getApplicationContext();
        InputStream inputStream = context.getResources().openRawResource(R.raw.definitions);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        while (bufferedReader.readLine() != null) {
            if (bufferedReader.readLine().equals(answerNumber)) {
             //   string.append(""+bufferedReader.readLine().toString()+"\n");
               do {
                       string.append(bufferedReader.readLine().toString()+"\n");
               }while(!string.toString().contains("#"));
            }
        }
        return string.toString().substring(0,string.toString().length()-2);
    }





    public String findWordByNumber(String answerNumber) throws IOException {
        String answer = "smthng";
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
       intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
       // getWindow().setWindowAnimations(0);
        startActivity(intent);

       finish();
       // finish();
      //  overridePendingTransition( 0, 0);
      //  startActivity(getIntent());
       // overridePendingTransition( 0, 0);

    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("VoiceListening Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
