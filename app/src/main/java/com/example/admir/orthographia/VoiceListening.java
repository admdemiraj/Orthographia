package com.example.admir.orthographia;

import android.app.ActionBar;
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

import static android.R.attr.id;
import static android.R.attr.layout;
import static android.R.string.cancel;
import static com.example.admir.orthographia.R.layout.activity_voice_listening;
import static com.example.admir.orthographia.R.layout.pop_up_layout;
import android.view.ViewGroup.LayoutParams;

public class VoiceListening extends AppCompatActivity {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        ActionBar bar = getActionBar();




//for image
     //  bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bar));

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


/*

        Resources res =this.getResources();
// Change locale settings in the app.
String language_code="el";
        DisplayMetrics dm = res.getDisplayMetrics();

        android.content.res.Configuration conf = res.getConfiguration();
        System.out.println("Defaulffffffffffffffffffffft language name (default): " +
               conf.locale.getDisplayLanguage());
       conf.locale = new Locale(language_code.toLowerCase());
        res.updateConfiguration(conf, dm);
        System.out.println("Defaulttttttt language name (default): " +
                conf.locale.getDisplayLanguage());


*/
     /*   btnBack.setOnClickListener(new OnClickListener(){

            private void onClick(){
                Intent intent = new Intent(VoiceListening.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

*/


        playMedia(getVideoAccordingToAnswerNumber(answerNumber));

        changeDifficulty(easyButton, mediumButton, hardButton);


        setDifficultyForTheNewLevel(answerNumber, easyButton, mediumButton, hardButton);


        checkIfTheAnswerIsCorrect(answerNumber, editText);

        getSolution(solveButton, editText, answerNumber);

        goToNextWord(nextButton);
      /*  try {
            for(int i=0;i<findDedinitionByNumber("0").size();i++){

                definition.setText(findDedinitionByNumber("0").get(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        findWordsMeaning(explainButton);

        try {
            Log.i("GGGGGGGGGGGGG", "fffffffffff");
            Log.d("GGGGGGGGGGGGG", findWordByNumber(answerNumber));
            for(int i=0;i<findDedinitionByNumber("0").size();i++){
            Log.d("OOOOOOOOOOO", findDedinitionByNumber("0").get(i).toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
    private void initiatePopupWindow() throws IOException {

           final PopupWindow pw;



            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) VoiceListening.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.pop_up_layout,
                    (ViewGroup) findViewById(R.id.pop_up_element));
            // create a 300px width and 470px height PopupWindow
            pw = new PopupWindow(layout, 1100, 550, true);

        ((TextView)pw.getContentView().findViewById(R.id.definition)).setText("hello there");
        StringBuilder string = new StringBuilder();
        for(int i=0;i<findDedinitionByNumber("5").size();i++){
string.append(findDedinitionByNumber("5").get(i)+"\n");

           // ((TextView)pw.getContentView().findViewById(R.id.definition)).setText(findDedinitionByNumber("0").get(i));
        }
        ((TextView)pw.getContentView().findViewById(R.id.definition)).setText(string);
       // TextView definition=(TextView) findViewById(R.id.definition);
       // definition.setText("kk");
            // display the popup in the center
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

            //TextView mResultText = (TextView) layout.findViewById(R.id.server_status_text);
            Button cancelButton = (Button) layout.findViewById(R.id.cancel_btn);
            //cancelButton.setOnClickListener(new View.OnClickListener()){}
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pw.dismiss();

                }
            });
                }




    public void findWordsMeaning(Button explainButton) {



        explainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    initiatePopupWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(getBaseContext(),"0",Toast.LENGTH_LONG).show();


                //popupWindow.showAtLocation(mainLayout, Gravity.BOTTOM, 10, 10);
              //  popupWindow.update(50, 50, 320, 90);

               /* Toast.makeText(getBaseContext(), "\"κίβδηλος -η -ο\n" +
                        "\n" +
                        "   1. (για νόμισμα, ιδιαίτερα μεταλλικό) που είναι προϊόν παραχάραξης, μη γνήσιος, πλαστός\n" +
                        "\n" +
                        "        εντόπισαν κίβδηλο κέρμα των 2 ευρώ\n" +
                        "\n" +
                        "    2.(μεταφορικά) για οτιδήποτε παρουσιάζει εξωτερικά μια ψευδή και παραπλανητική εικόνα ενώ στην πραγματικότητα στερείται αξίας\n" +
                        "\"\n", Toast.LENGTH_SHORT).show();

                */
                //Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
               // Intent intent = new Intent(Intent.ACTION_VIEW, uri);
               // startActivity(intent);
                //finish();

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
    public ArrayList<String> findDedinitionByNumber(String answerNumber) throws IOException {

        ArrayList<String> answerDefinition = new ArrayList<String>();

        Context context = getApplicationContext();
        InputStream inputStream = context.getResources().openRawResource(R.raw.definitions);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder string = new StringBuilder();
        while (bufferedReader.readLine() != null) {
            if (bufferedReader.readLine().equals(answerNumber)) {
               do {
                   answerDefinition.add(bufferedReader.readLine());


               }while(!answerDefinition.contains("#"));

            }
        }

        return answerDefinition;
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
