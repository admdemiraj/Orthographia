package com.orth.admir.orthographia;


import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageButton;
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
        final Button easyButton = (Button) findViewById(R.id.easy_btn);
        final Button mediumButton = (Button) findViewById(R.id.medium_btn);
        final Button hardButton = (Button) findViewById(R.id.hard_btn);
        final EditText editText = (EditText) findViewById(R.id.editText);
        final Button solveButton = (Button) findViewById(R.id.solve_btn);
        final Button nextButton = (Button) findViewById(R.id.next_btn);
        final Button explainButton = (Button) findViewById(R.id.explain_btn);


      getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        View v = getSupportActionBar().getCustomView();
        TextView titleTxtView = (TextView) v.findViewById(R.id.title_text);
        titleTxtView.setText("ΠΡΟΠΟΝΗΣΗ");
        /**Creating an onClickListener for the home ImageButton and using a method to go to the
         *  MainActivity*/
        ImageButton homeImageButton = (ImageButton) findViewById(R.id.home_image_btn);
        homeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });








        if (bundle != null) {
            answerNumber = bundle.getString("key");

        } else {
            Random random = new Random();
            answerNumber = String.valueOf(random.nextInt(225 - 0) + 0);
        }


        //try {
            //final TextView textView = (TextView) findViewById(R.id.textView2);
          //  textView.setText(findWordByNumber(answerNumber));
       // } catch (IOException e) {
         //   throw new RuntimeException(e);
       // }
        editText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});




MediaPlayer mediaPlayer = getVideoAccordingToAnswerNumber(answerNumber);
        playMedia(mediaPlayer);
        findWordsMeaning(explainButton,answerNumber);

        changeDifficulty(easyButton, mediumButton, hardButton);


        setDifficultyForTheNewLevel(answerNumber, easyButton, mediumButton, hardButton);


        checkIfTheAnswerIsCorrect(answerNumber, editText,mediaPlayer);

        getSolution(solveButton, editText, answerNumber);

        goToNextWord(nextButton);






        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public MediaPlayer getVideoAccordingToAnswerNumber(String answerNumber) {
            MediaPlayer mediaPlayer = new MediaPlayer();
            String soundName = "file" + answerNumber;
            int sound_id = this.getResources().getIdentifier(soundName, "raw",
                    this.getPackageName());
            if (sound_id != 0) {
                mediaPlayer = MediaPlayer.create(this, sound_id);
                try { mediaPlayer.setDataSource(this,Uri.parse(soundName)); } catch (Exception e) {}
                try { mediaPlayer.prepare(); } catch (Exception e) {}
            }
        return mediaPlayer;

    }
    private void initiatePopupWindow(final String answerNumber) throws IOException {

           final PopupWindow pw;


        final Button easyButton = (Button) findViewById(R.id.easy_btn);
        final Button mediumButton = (Button) findViewById(R.id.medium_btn);
        final Button hardButton = (Button) findViewById(R.id.hard_btn);
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) VoiceListening.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.pop_up_layout,
                    (ViewGroup) findViewById(R.id.pop_up_element));
            // create a 300px width and 470px height PopupWindow
            pw = new PopupWindow(layout, 1100, 550, true);
        ((TextView)pw.getContentView().findViewById(R.id.definition)).setText(findDedinitionByNumber(answerNumber));
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
            Button cancelButton = (Button) layout.findViewById(R.id.cancel_btn);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pw.dismiss();
                    //this called is to fix bug that canceled difficulty when seeing the meaning of the word
                    setDifficultyForTheNewLevel(answerNumber, easyButton, mediumButton, hardButton);


                }
            });
                }




    public void findWordsMeaning(Button explainButton, final String answerNumber) {
        final MediaPlayer book = MediaPlayer.create(this, R.raw.book);


        explainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.start();
                try {
                    initiatePopupWindow(answerNumber);
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        });


    }
    /**
     * Method that starts the MainActivity
     */
    private void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);


    }

    public void goToNextWord(Button nextButton) {
        final MediaPlayer click = MediaPlayer.create(this, R.raw.click);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                click.start();


                startVoiceListening(sellectRandomWordAccordingToDifficulty());
                finish();

            }
        });

    }


    public void getSolution(final Button solveButton, final EditText editText, final String answerNumber) {
        final MediaPlayer lamp = MediaPlayer.create(this, R.raw.lamp);
        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lamp.start();
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
                //mediaPlayer.start();
                onPrepared(mediaPlayer);

            }
        });

    }
    public void onPrepared(MediaPlayer player) {
        player.start();
    }
    public void checkIfTheAnswerIsCorrect(final String answerNumber, final EditText editText,final MediaPlayer mediaPlayer) {
        Button checkIfCorrect = (Button) findViewById(R.id.check_if_correct);

        final MediaPlayer mediaPlayerRight = MediaPlayer.create(this, R.raw.correct);
        final MediaPlayer mediaPlayerFalse = MediaPlayer.create(this, R.raw.wrong);
        checkIfCorrect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    if (findWordByNumber(answerNumber).equals(editText.getText().toString())) {

                        mediaPlayerRight.start();
                        Toast.makeText(getBaseContext(), "ΣΩΣΤΑ!!!!", Toast.LENGTH_SHORT).show();
                        //release previous media player before starting new activity
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            public void onCompletion(MediaPlayer mp) {
                                mp.release();

                            };
                        });
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
        StringBuilder string = new StringBuilder();
        Context context = getApplicationContext();
        InputStream inputStream = context.getResources().openRawResource(R.raw.definitions);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
string.append("-"+"\n");
        while ((line= bufferedReader.readLine() )!= null) {
            if (line.equals(answerNumber)) {
                do {
                    string.append(bufferedReader.readLine().toString() + "\n");
                } while (!string.toString().contains("#"));
                break;
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
