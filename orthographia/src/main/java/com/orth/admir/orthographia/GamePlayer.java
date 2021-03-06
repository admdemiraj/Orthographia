package com.orth.admir.orthographia;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;

import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import java.util.HashSet;
import java.util.Random;
import java.util.Set;



public class GamePlayer extends AppCompatActivity {

   // private ArrayList<String> wrongWords = new ArrayList<>();
  private Set<String> wrongWords = new HashSet<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    @Override
    public void onPause() {
        super.onPause();
        TextView textView = (TextView) findViewById(R.id.pointBoard_textView);
        saveHighScore(textView.getText().toString());
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game_player);


        Bundle bundle = getIntent().getExtras();
        final String answerNumber;
        //final String answer = "nai";
        final EditText editText = (EditText) findViewById(R.id.editText);
        //  final TextView textView2= (TextView) findViewById((R.id.title_text));


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
            checkAchievement(Integer.parseInt(getHighScore()));
        ImageButton imageButton = (ImageButton) findViewById(R.id.home_image_btn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();

            }
        });
        String points;
        int lives;
        int difficultyCounter;
        int level;

        if (bundle != null) {
            answerNumber = bundle.getString("key");
            points = bundle.getString("points");
            lives = bundle.getInt("lives");
            difficultyCounter = bundle.getInt("difficultyCounter");
            level = bundle.getInt("level");


        } else {
            Random random = new Random();
            answerNumber = String.valueOf(random.nextInt(225 - 0) + 0);
            points = "0";
            lives = 5;
            difficultyCounter = 1;
            level = 1;
        }

        View v = getSupportActionBar().getCustomView();
        TextView titleTxtView = (TextView) v.findViewById(R.id.title_text);
        titleTxtView.setText("ΕΠΙΠΕΔΟ " + level);
        TextView highScoreTextView = (TextView) findViewById(R.id.high_score);

        highScoreTextView.setText("max: "+getHighScore());

            // set tags
            ImageView heartImageView = (ImageView) findViewById(R.id.heart_image_view);
            ImageView difImageView = (ImageView) findViewById(R.id.difficulty_image_view);
            heartImageView.setTag(String.valueOf(lives));
            difImageView.setTag(String.valueOf(difficultyCounter));

            // trial solve button
            //final Button solveButton = (Button) findViewById(R.id.solve_btn);
          //  getSolution(solveButton, editText, answerNumber);

        editText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        updateResources(points, lives, difficultyCounter);

        MediaPlayer mediaPlayer = getVideoAccordingToAnswerNumber(answerNumber);
        playMedia(mediaPlayer);

        checkIfTheAnswerIsCorrect(answerNumber, editText,mediaPlayer);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
////trial get solution
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

    public void lifeManager() throws IOException {
        ImageView imageView = (ImageView) findViewById(R.id.heart_image_view);

        if (String.valueOf(imageView.getTag()).equals("5")) {
            imageView.setBackgroundResource(R.drawable.lifeboardb);
            imageView.setTag("4");

        } else if (String.valueOf(imageView.getTag()).equals("4")) {
            imageView.setBackgroundResource(R.drawable.lifeboardc);
            imageView.setTag("3");

        } else if (String.valueOf(imageView.getTag()).equals("3")) {
            imageView.setBackgroundResource(R.drawable.lifeboardd);
            imageView.setTag("2");

        } else if (String.valueOf(imageView.getTag()).equals("2")) {
            imageView.setBackgroundResource(R.drawable.lifeboarde);
            imageView.setTag("1");

        } else if (String.valueOf(imageView.getTag()).equals("1")) {
            imageView.setBackgroundResource(R.drawable.lifeboardf);
            imageView.setTag("0");

        } else {
            initiatePopupWindow();
        }


    }

    public int findNumberOfLives() {
        ImageView imageView = (ImageView) findViewById(R.id.heart_image_view);
        if (String.valueOf(imageView.getTag()).equals("5")) {
            return 5;

        } else if (String.valueOf(imageView.getTag()).equals("4")) {
            return 4;

        } else if (String.valueOf(imageView.getTag()).equals("3")) {
            return 3;

        } else if (String.valueOf(imageView.getTag()).equals("2")) {
            return 2;

        } else if (String.valueOf(imageView.getTag()).equals("1")) {
            return 1;

        } else {
            return 0;

        }


    }


    public void difficultyManager() {
        ImageView imageView = (ImageView) findViewById(R.id.difficulty_image_view);
        // System.out.println("image view constant state10:"+getResources().getDrawable(R.drawable.difficulty10).getConstantState().toString());
        if (String.valueOf(imageView.getTag()).equals("1")) {
            imageView.setBackgroundResource(R.drawable.difficulty2);
            imageView.setTag("2");

        } else if (String.valueOf(imageView.getTag()).equals("2")) {
            imageView.setBackgroundResource(R.drawable.difficulty3);
            imageView.setTag("3");

        } else if (String.valueOf(imageView.getTag()).equals("3")) {
            imageView.setBackgroundResource(R.drawable.difficulty4);
            imageView.setTag("4");

        } else if (String.valueOf(imageView.getTag()).equals("4")) {
            imageView.setBackgroundResource(R.drawable.difficulty5);
            imageView.setTag("5");

        } else if (String.valueOf(imageView.getTag()).equals("5")) {
            imageView.setBackgroundResource(R.drawable.difficulty6);
            imageView.setTag("6");

        } else if (String.valueOf(imageView.getTag()).equals("6")) {
            imageView.setBackgroundResource(R.drawable.difficulty7);
            imageView.setTag("7");

        } else if (String.valueOf(imageView.getTag()).equals("7")){
            imageView.setBackgroundResource(R.drawable.difficulty8);
            imageView.setTag("8");

        } else if (String.valueOf(imageView.getTag()).equals("8")) {
            imageView.setBackgroundResource(R.drawable.difficulty9);
            imageView.setTag("9");

        } else if (String.valueOf(imageView.getTag()).equals("9")) {
            imageView.setBackgroundResource(R.drawable.difficulty10);
            imageView.setTag("10");

        } else {
            imageView.setBackgroundResource(R.drawable.difficulty1);
            imageView.setTag("1");
        }


    }




    public int difficultyCounter() {
        ImageView imageView = (ImageView) findViewById(R.id.difficulty_image_view);
        if (String.valueOf(imageView.getTag()).equals("1")) {
            return 11;
            //return 11;

        } else if (String.valueOf(imageView.getTag()).equals("2")) {
            return 2;

        } else if (String.valueOf(imageView.getTag()).equals("3")) {
            return 3;

        } else if (String.valueOf(imageView.getTag()).equals("4")) {
            return 4;

        } else if (String.valueOf(imageView.getTag()).equals("5")) {
            return 5;

        } else if (String.valueOf(imageView.getTag()).equals("6")) {
            return 6;

        } else if (String.valueOf(imageView.getTag()).equals("7")) {
            return 7;

        } else if (String.valueOf(imageView.getTag()).equals("8")) {
            return 8;

        } else if (String.valueOf(imageView.getTag()).equals("9")) {
            return 9;

        } else if (String.valueOf(imageView.getTag()).equals("10")) {
            return 10;

        } else {
            return 11;
        }


    }
    public void setDifficultyAccordingToCounter(int difficultyCounter) {
        ImageView imageView = (ImageView) findViewById(R.id.difficulty_image_view);
        if (difficultyCounter == 1) {
            imageView.setBackgroundResource(R.drawable.difficulty1);
            imageView.setTag("1");
        } else if (difficultyCounter == 2) {
            imageView.setBackgroundResource(R.drawable.difficulty2);
        } else if (difficultyCounter == 3) {
            imageView.setBackgroundResource(R.drawable.difficulty3);
        } else if (difficultyCounter == 4) {
            imageView.setBackgroundResource(R.drawable.difficulty4);
        } else if (difficultyCounter == 5) {
            imageView.setBackgroundResource(R.drawable.difficulty5);
        } else if (difficultyCounter == 6) {
            imageView.setBackgroundResource(R.drawable.difficulty6);
        } else if (difficultyCounter == 7) {
            imageView.setBackgroundResource(R.drawable.difficulty7);
        } else if (difficultyCounter == 8) {
            imageView.setBackgroundResource(R.drawable.difficulty8);
        } else if (difficultyCounter == 9) {
            imageView.setBackgroundResource(R.drawable.difficulty9);
        } else if (difficultyCounter == 10) {
            imageView.setBackgroundResource(R.drawable.difficulty10);
        } else {
            imageView.setBackgroundResource(R.drawable.difficulty1);
            imageView.setTag("1");
        }


    }


    private void initiatePopupWindow() throws IOException {

        final PopupWindow pw;
        final Button checkIfCorrectButton = (Button) findViewById(R.id.check_if_correct);
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        //We need to get the instance of the LayoutInflater, use the context of this activity
        LayoutInflater inflater = (LayoutInflater) GamePlayer.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout
        View layout = inflater.inflate(R.layout.pop_up_layout, (ViewGroup) findViewById(R.id.pop_up_element));
        pw = new PopupWindow(layout, height, width, false);
        checkIfCorrectButton.setEnabled(false);

        TextView textView = (TextView) findViewById(R.id.pointBoard_textView);
        saveHighScore(textView.getText().toString());
        String achievement = checkAchievement(Integer.parseInt(getHighScore()));
        if(achievement != null) {
            ((TextView) pw.getContentView().findViewById(R.id.definition)).setText("ΤΕΛΟΣ ΠΑΙΧΝΙΔΙΟΥ \n ΠΕΤΥΧΑΤΕ ΣΚΟΡ: " + textView .getText().toString()+"\nΛΑΝΘΑΣΜΕΝΗ ΛΕΞΗ: "+wrongWords+" \n ΕΠΙΤΕΥΜΑ: "+achievement);

        }else{
            ((TextView) pw.getContentView().findViewById(R.id.definition)).setText("ΤΕΛΟΣ ΠΑΙΧΝΙΔΙΟΥ \n ΠΕΤΥΧΑΤΕ ΣΚΟΡ: " + textView .getText().toString()+"\nΛΑΝΘΑΣΜΕΝΗ ΛΕΞΗ: "+wrongWords);

        }
        //((TextView) pw.getContentView().findViewById(R.id.definition)).setText("ΤΕΛΟΣ ΠΑΙΧΝΙΔΙΟΥ \n ΠΕΤΥΧΑΤΕ ΣΚΟΡ: " + textView.getText().toString());
        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

        Button cancelButton = (Button) layout.findViewById(R.id.cancel_btn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw.dismiss();
                Random random = new Random();
                String answerNumber = String.valueOf(random.nextInt(225 - 0) + 0);
                checkIfCorrectButton.setEnabled(true);
                StartGamePlayer(answerNumber, "0", 5, 1, 1);

            }
        });
    }


    public void playMedia(final MediaPlayer mediaPlayer) {
        Button playButton = (Button) findViewById(R.id.play_btn);

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
                    String word = findWordByNumber(answerNumber);
                    String answer = editText.getText().toString().trim();
                    if (word.equals(answer)) {
                        mediaPlayerRight.start();
                        Toast.makeText(getBaseContext(), "ΣΩΣΤΑ!!!!", Toast.LENGTH_SHORT).show();
                        difficultyManager();
                        View vr = getSupportActionBar().getCustomView();
                        TextView titleTxtView = (TextView) vr.findViewById(R.id.title_text);
                        String temp = titleTxtView.getText().toString();
                        temp = temp.replaceAll("\\D+", "");
                        System.out.println("TEMP IS : " + temp);
                        int level = Integer.parseInt(temp);
                        int numLives = findNumberOfLives();
                        int difficulty = difficultyCounter();
                        //before starting new activity release previous mediaplayer
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            public void onCompletion(MediaPlayer mp) {
                                mp.release();

                            };
                        });

                        if (difficultyCounter() == 11) {
                            StartGamePlayer(nextAnswerNumberAccordingToLevelDifficulty(level, difficulty), updatePoints(pointManager()), numLives, difficulty, level + 1);
                            finish();
                        } else {
                            StartGamePlayer(nextAnswerNumberAccordingToLevelDifficulty(level, difficulty), updatePoints(pointManager()), numLives, difficulty, level);
                            finish();

                        }

                    } else {
                        if (!editText.getText().toString().equals("")) {
                            mediaPlayerFalse.start();
                            wrongWords.add(findWordByNumber(answerNumber));
                            lifeManager();
                            // difficultyManager();
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }
    private String checkAchievement(int highScore){
        View v = getSupportActionBar().getCustomView();
        ImageButton achievement= (ImageButton) v.findViewById(R.id.achievement_image_btn);

        if(highScore>=10000){
            achievement.setBackgroundResource(R.drawable.achf);
            return "φιλόσοφος";

        }else if(highScore>=8000){
            achievement.setBackgroundResource(R.drawable.ache);
            return "μάγος";

        }else if (highScore>=6000){
            achievement.setBackgroundResource(R.drawable.achd);
            return "φοιτητής";
        }else if(highScore>=4000){
            achievement.setBackgroundResource(R.drawable.achc);
            return "μαθηταράς";
        }else if(highScore>=2000){
            achievement.setBackgroundResource(R.drawable.achb);
            return "διαβαστερός";
        }else {
            return null;
        }
    }

    public int pointManager() {
        TextView titleText = (TextView) findViewById(R.id.title_text);
        String str = titleText.getText().toString();
        String numberOnly = str.replaceAll("[^0-9]", "");
        // System.out.println("numbersv ONLYYYYYYYY :"+numberOnly);
        int level = Integer.parseInt(numberOnly);
        if (level <= 10) {
            if (difficultyCounter() <= 7) {
                return 100;
            } else {
                return 200;
            }
        } else if (level <= 20) {
            if (difficultyCounter() <= 7) {
                return 100;
            } else if (difficultyCounter() <= 9) {
                return 200;
            } else {
                return 400;
            }
        } else if (level <= 30) {
            if (difficultyCounter() <= 6) {
                return 100;
            } else if (difficultyCounter() <= 9) {
                return 200;
            } else {
                return 400;
            }
        } else if (level <= 40) {
            if (difficultyCounter() <= 4) {
                return 100;
            } else if (difficultyCounter() <= 8) {
                return 200;
            } else {
                return 400;
            }
        } else {
            return 400;
        }


    }


    public void updateResources(String points, int lives, int difficultyCounter) {
        TextView textView = (TextView) findViewById(R.id.pointBoard_textView);
        ImageView imageView = (ImageView) findViewById(R.id.heart_image_view);
        textView.setText(points);
        if (lives == 4) {
            imageView.setBackgroundResource(R.drawable.lifeboardb);
        } else if (lives == 3) {
            imageView.setBackgroundResource(R.drawable.lifeboardc);
        } else if (lives == 2) {
            imageView.setBackgroundResource(R.drawable.lifeboardd);
        } else if (lives == 1) {
            imageView.setBackgroundResource(R.drawable.lifeboarde);
        } else if (lives == 0) {
            imageView.setBackgroundResource(R.drawable.lifeboardf);
        } else {
            imageView.setBackgroundResource(R.drawable.lifeboard);
        }
        setDifficultyAccordingToCounter(difficultyCounter);

    }

    public String nextAnswerNumberAccordingToLevelDifficulty(int level, int difficultyCounter) {
        //Check what the picture of level is to see difficulty
        String answerNumber;
        Random random = new Random();

        if (level <= 10) {
            if (difficultyCounter <= 7) {
                answerNumber = String.valueOf(random.nextInt(225 - 0) + 0);
            } else {
                answerNumber = String.valueOf(random.nextInt(357 - 225) + 225);
            }

        } else if (level <= 20) {
            if (difficultyCounter <= 7) {
                answerNumber = String.valueOf(random.nextInt(225 - 0) + 0);
            } else if (difficultyCounter <= 9) {
                answerNumber = String.valueOf(random.nextInt(357 - 225) + 225);
            } else {
                answerNumber = String.valueOf(random.nextInt(428 - 357) + 357);
            }

        } else if (level <= 30) {
            if (difficultyCounter <= 6) {
                answerNumber = String.valueOf(random.nextInt(225 - 0) + 0);
            } else if (difficultyCounter <= 9) {
                answerNumber = String.valueOf(random.nextInt(357 - 225) + 225);
            } else {
                answerNumber = String.valueOf(random.nextInt(428 - 357) + 357);
            }

        } else if (level <= 40) {
            if (difficultyCounter <= 4) {
                answerNumber = String.valueOf(random.nextInt(225 - 0) + 0);
            } else if (difficultyCounter <= 8) {
                answerNumber = String.valueOf(random.nextInt(357 - 225) + 225);
            } else {
                answerNumber = String.valueOf(random.nextInt(428 - 357) + 357);
            }
        } else {
            answerNumber = String.valueOf(random.nextInt(428 - 357) + 357);
        }
        return answerNumber;


    }

    public String updatePoints(int addedPoints) {
        /*I also need a method to find how many points will be added according to dificulty,
       Implementation: Check which specific drawable is in the level imageView and return points accordingly*/
        TextView pointBoard = (TextView) findViewById(R.id.pointBoard_textView);
        String currentPoints = pointBoard.getText().toString();
        int totalPoints = Integer.parseInt(currentPoints) + addedPoints;

        pointBoard.setText((Integer.toString(totalPoints)));
        return Integer.toString(totalPoints);

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
    private void saveHighScore(String data) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int newHigh = Integer.parseInt(data);
        int previousHigh = Integer.parseInt(getHighScore());
        if(newHigh>previousHigh){
            editor.putString("highScoreB", data);
            editor.commit();
        }



    }
    private String getHighScore(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        String highScore = sharedPref.getString("highScoreB", "0000");
        return highScore;
    }

    public void StartGamePlayer(String wordNumber, String points, int lives, int difficultyCounter, int level) {
        Intent intent = new Intent(this, GamePlayer.class);
        Bundle bundle = new Bundle();
        bundle.putString("key", wordNumber);
        bundle.putString("points", points);
        bundle.putInt("lives", lives);
        bundle.putInt("difficultyCounter", difficultyCounter);
        bundle.putInt("level", level);
        intent.putExtras(bundle);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
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
        TextView textView = (TextView) findViewById(R.id.pointBoard_textView);
        saveHighScore(textView.getText().toString());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
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

}
