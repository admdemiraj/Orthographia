package com.example.admir.orthographia;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class MultipleChoice extends AppCompatActivity {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_multiple_choice);


        Bundle bundle = getIntent().getExtras();
        final String answerNumber;
        //final String answer = "nai";
        final EditText editText = (EditText) findViewById(R.id.editText);
        //  final TextView textView2= (TextView) findViewById((R.id.title_text));




        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        ImageButton imageButton=(ImageButton) findViewById(R.id.home_image_btn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();

            }
        });












        //  getSupportActionBar().setDisplayShowTitleEnabled(false);
        //  getSupportActionBar().setDisplayShowCustomEnabled(true);
        //  getSupportActionBar().setDisplayUseLogoEnabled(false);
        //  getSupportActionBar().setDisplayShowHomeEnabled(false);


        //ActionBar mActionBar = getActionBar();
//        mActionBar.setDisplayShowHomeEnabled(false);
        //  mActionBar.setDisplayShowTitleEnabled(true);
        // LayoutInflater mInflater = LayoutInflater.from(this);

        //View mCustomView = mInflater.inflate(R.layout.pop_up_layout, null);



        String points;
        int lives;
        int difficultyCounter;
        int level;

        if (bundle != null) {
            answerNumber = bundle.getString("key");
            points= bundle.getString("points");
            lives = bundle.getInt("lives");
            difficultyCounter=bundle.getInt("difficultyCounter");
            level=bundle.getInt("level");


        } else {
            answerNumber = "0";
            points="0";
            lives=5;
            difficultyCounter=1;
            level=1;
        }

        View v = getSupportActionBar().getCustomView();
        TextView titleTxtView = (TextView) v.findViewById(R.id.title_text);
        titleTxtView.setText("ΕΠΙΠΕΔΟ "+level);

     //   try {
          //  final TextView textView = (TextView) findViewById(R.id.textView2);
         //   textView.setText(findWordByNumber(answerNumber));
    //    } catch (IOException e) {
         //   throw new RuntimeException(e);
      //  }
      //  editText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});



     //   updateResources(points,lives,difficultyCounter);

     //   playMedia(getVideoAccordingToAnswerNumber(answerNumber));


        //  changeDifficulty(easyButton, mediumButton, hardButton);


        // setDifficultyForTheNewLevel(answerNumber, easyButton, mediumButton, hardButton);


     //   checkIfTheAnswerIsCorrect(answerNumber, editText);
String correctAnswer="hh";

        try {
           correctAnswer= initializeButtons(findWords("0"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        checkIfAnsweIsCorrect(correctAnswer);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

public String initializeButtons(String[] allWords){
    Button multiple1=(Button) findViewById(R.id.multiple_1_btn);
    Button multiple2=(Button) findViewById(R.id.multiple_2_btn);
    Button multiple3=(Button) findViewById(R.id.multiple_3_btn);
    Button multiple4=(Button) findViewById(R.id.multiple_4_btn);

    ArrayList<String> arrayList=new ArrayList<>();
    arrayList.add(allWords[0]);
    arrayList.add(allWords[1]);
    arrayList.add(allWords[2]);
    arrayList.add(allWords[3]);
    Collections.shuffle(arrayList);
    multiple1.setText(arrayList.get(0));
    multiple2.setText(arrayList.get(1));
    multiple3.setText(arrayList.get(2));
    multiple4.setText(arrayList.get(3));
    return allWords[0];

}


public void checkIfAnsweIsCorrect(final String correctAnswer){
    final Button multiple1=(Button) findViewById(R.id.multiple_1_btn);
    Button multiple2=(Button) findViewById(R.id.multiple_2_btn);
    Button multiple3=(Button) findViewById(R.id.multiple_3_btn);
    Button multiple4=(Button) findViewById(R.id.multiple_4_btn);

    multiple1.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
        if(multiple1.getText().toString().equals(correctAnswer)){
            Toast.makeText(getBaseContext(), "ΣΩΣΤΑ!!!!", Toast.LENGTH_SHORT).show();
            difficultyManager();

            View vr = getSupportActionBar().getCustomView();
            TextView titleTxtView = (TextView) vr.findViewById(R.id.title_text);
            String temp=titleTxtView.getText().toString();
            temp= temp.replaceAll("\\D+","");
            int level=Integer.parseInt(temp);
            int numLives=findNumberOfLives();
            int difficulty=difficultyCounter();

            if(difficultyCounter()==11){
                startMultipleChoice(nextAnswerNumberAccordingToLevelDifficulty(level,difficulty), updatePoints(pointManager()),numLives,difficulty,level+1);
                finish();
            }else {
                startMultipleChoice(nextAnswerNumberAccordingToLevelDifficulty(level,difficulty), updatePoints(pointManager()),numLives,difficulty,level+1);
finish();
            }

        }else {
            Toast.makeText(getBaseContext(), "ΛΑΘΟΣ!!!!", Toast.LENGTH_SHORT).show();
            try {
                lifeManager();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        }
    });

    multiple2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


        }
    });
    multiple3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


        }
    });
    multiple4.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


        }
    });

}



    public void lifeManager() throws IOException {
        ImageView imageView= (ImageView) findViewById(R.id.heart_image_view);
        if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.lifeboard).getConstantState().toString())){
            imageView.setBackgroundResource(R.drawable.lifeboardb);

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.lifeboardb).getConstantState().toString())){
            imageView.setBackgroundResource(R.drawable.lifeboardc);

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.lifeboardc).getConstantState().toString())){
            imageView.setBackgroundResource(R.drawable.lifeboardd);

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.lifeboardd).getConstantState().toString())){
            imageView.setBackgroundResource(R.drawable.lifeboarde);

        }else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.lifeboarde).getConstantState().toString())){
            imageView.setBackgroundResource(R.drawable.lifeboardf);

        }
        else{
            initiatePopupWindow();
        }



    }

    public int findNumberOfLives(){
        ImageView imageView= (ImageView) findViewById(R.id.heart_image_view);
        if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.lifeboard).getConstantState().toString())){
            return 5;

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.lifeboardb).getConstantState().toString())){
            return 4;

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.lifeboardc).getConstantState().toString())){
            return 3;

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.lifeboardd).getConstantState().toString())){
            return 2;

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.lifeboarde).getConstantState().toString())){
            return 1;

        }else{
            return 0;

        }



    }
    public void setWordToRed(String answerNumber) throws IOException, InterruptedException {
        EditText editText=(EditText) findViewById(R.id.editText);
        editText.setText(findWordByNumber(answerNumber).toString());
        editText.setTextColor(Color.red(1));
        editText.setTypeface(null, Typeface.BOLD_ITALIC);




    }

    public void difficultyManager(){
        ImageView imageView= (ImageView) findViewById(R.id.difficulty_image_view);
        if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.difficulty1).getConstantState().toString())){
            imageView.setBackgroundResource(R.drawable.difficulty2);

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.difficulty2).getConstantState().toString())){
            imageView.setBackgroundResource(R.drawable.difficulty3);

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.difficulty3).getConstantState().toString())){
            imageView.setBackgroundResource(R.drawable.difficulty4);

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.difficulty4).getConstantState().toString())){
            imageView.setBackgroundResource(R.drawable.difficulty5);

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.difficulty5).getConstantState().toString())){
            imageView.setBackgroundResource(R.drawable.difficulty6);

        } else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.difficulty6).getConstantState().toString())){
            imageView.setBackgroundResource(R.drawable.difficulty7);

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.difficulty7).getConstantState().toString())){
            imageView.setBackgroundResource(R.drawable.difficulty8);

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.difficulty8).getConstantState().toString())){
            imageView.setBackgroundResource(R.drawable.difficulty9);

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.difficulty9).getConstantState().toString())){
            imageView.setBackgroundResource(R.drawable.difficulty10);

        }else {
            imageView.setBackgroundResource(R.drawable.difficulty1);
        }


    }
    public int difficultyCounter(){
        ImageView imageView= (ImageView) findViewById(R.id.difficulty_image_view);
        if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.difficulty1).getConstantState().toString())){
            return 11;

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.difficulty2).getConstantState().toString())){
            return 2;

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.difficulty3).getConstantState().toString())){
            return 3;

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.difficulty4).getConstantState().toString())){
            return 4;

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.difficulty5).getConstantState().toString())){
            return 5;

        } else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.difficulty6).getConstantState().toString())){
            return 6;

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.difficulty7).getConstantState().toString())){
            return 7;

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.difficulty8).getConstantState().toString())){
            return 8;

        }
        else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.difficulty9).getConstantState().toString())){
            return 9;

        } else if(imageView.getBackground().getConstantState().toString().equals(getResources().getDrawable(R.drawable.difficulty10).getConstantState().toString())){
            return 10;

        }else {
            return 11;
        }


    }
    public void setDifficultyAccordingToCounter(int difficultyCounter){
        ImageView imageView= (ImageView) findViewById(R.id.difficulty_image_view);
        if(difficultyCounter==1){
            imageView.setBackgroundResource(R.drawable.difficulty1);
        }else if(difficultyCounter==2){
            imageView.setBackgroundResource(R.drawable.difficulty2);
        }else if(difficultyCounter==3){
            imageView.setBackgroundResource(R.drawable.difficulty3);
        }else if(difficultyCounter==4){
            imageView.setBackgroundResource(R.drawable.difficulty4);
        }else if(difficultyCounter==5){
            imageView.setBackgroundResource(R.drawable.difficulty5);
        }else if(difficultyCounter==6){
            imageView.setBackgroundResource(R.drawable.difficulty6);
        }else if(difficultyCounter==7){
            imageView.setBackgroundResource(R.drawable.difficulty7);
        }else if(difficultyCounter==8){
            imageView.setBackgroundResource(R.drawable.difficulty8);
        }else if(difficultyCounter==9){
            imageView.setBackgroundResource(R.drawable.difficulty9);
        }else if(difficultyCounter==10){
            imageView.setBackgroundResource(R.drawable.difficulty10);
        }else {
            imageView.setBackgroundResource(R.drawable.difficulty1);
        }



    }


    private void initiatePopupWindow() throws IOException {

        final PopupWindow pw;



        //We need to get the instance of the LayoutInflater, use the context of this activity
        LayoutInflater inflater = (LayoutInflater) MultipleChoice.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout
        View layout = inflater.inflate(R.layout.pop_up_layout,
                (ViewGroup) findViewById(R.id.pop_up_element));
        // create a 300px width and 470px height PopupWindow
        pw = new PopupWindow(layout, 1100, 550, true);
        // StringBuilder string = new StringBuilder();
        //////changed
        //  StringBuilder string2 = new StringBuilder();

        //  for(int i=0;i<findDedinitionByNumber(answerNumber).size();i++){
        //    string2.append(findDedinitionByNumber(answerNumber).get(i).toString()+"\n");
        // }
        TextView textView=(TextView) findViewById(R.id.pointBoard_textView);


        ((TextView)pw.getContentView().findViewById(R.id.definition)).setText("ΤΕΛΟΣ ΠΑΙΧΝΙΔΙΟΥ \n ΠΕΤΥΧΑΤΕ ΣΚΟΡ: "+textView.getText().toString());
        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
        Button cancelButton = (Button) layout.findViewById(R.id.cancel_btn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw.dismiss();
                startMultipleChoice("0","0",5,1,1);

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
                    String word=findWordByNumber(answerNumber);
                    if (word.equals(editText.getText().toString()) ) {

                        mediaPlayerRight.start();
                        Toast.makeText(getBaseContext(), "ΣΩΣΤΑ!!!!", Toast.LENGTH_SHORT).show();

                        // Toast toast = Toast.makeText(getBaseContext(), "100", Toast.LENGTH_SHORT);
                        //toast.show();
                        // toast.setGravity(Gravity.TOP|Gravity.RIGHT, 40, 0);


                        // StartGamePlayer(sellectRandomWordAccordingToDifficulty());


                        difficultyManager();
                        View vr = getSupportActionBar().getCustomView();
                        TextView titleTxtView = (TextView) vr.findViewById(R.id.title_text);
                        String temp=titleTxtView.getText().toString();
                        temp= temp.replaceAll("\\D+","");
                        System.out.println("TEMP IS : "+temp);
                        int level=Integer.parseInt(temp);
                        int numLives=findNumberOfLives();
                        int difficulty=difficultyCounter();

                        if(difficultyCounter()==11){
                            startMultipleChoice(nextAnswerNumberAccordingToLevelDifficulty(level,difficulty), updatePoints(pointManager()),numLives,difficulty,level+1);
                            finish();
                        }else{
                            startMultipleChoice(nextAnswerNumberAccordingToLevelDifficulty(level,difficulty), updatePoints(pointManager()),numLives,difficulty,level);
                            finish();

                        }

                    }else{
                        if( !editText.getText().toString().equals("") ) {
                            mediaPlayerFalse.start();
                            lifeManager();
                            difficultyManager();
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }



    public int pointManager(){
        TextView titleText=(TextView) findViewById(R.id.title_text);
        String str=titleText.getText().toString();
        String numberOnly= str.replaceAll("[^0-9]", "");
        // System.out.println("numbersv ONLYYYYYYYY :"+numberOnly);
        int level=Integer.parseInt(numberOnly);
        if(level<=10){
            if (difficultyCounter()<=7){
                return 100;
            }else{
                return 200;
            }
        }else if(level<=20){
            if (difficultyCounter()<=7){
                return 100;
            }else if(difficultyCounter()<=9){
                return 200;
            }else{
                return 400;
            }
        }else if(level<=30){
            if (difficultyCounter()<=6){
                return 100;
            }else if(difficultyCounter()<=9){
                return 200;
            }else{
                return 400;
            }
        }else if(level<=40){
            if (difficultyCounter()<=4){
                return 100;
            }else if(difficultyCounter()<=8){
                return 200;
            }else{
                return 400;
            }
        }else{
            return 400;
        }


    }




    public void updateResources(String points,int lives,int difficultyCounter){
        TextView textView=(TextView) findViewById(R.id.pointBoard_textView);
        ImageView imageView=(ImageView) findViewById(R.id.heart_image_view);
        textView.setText(points);
        if(lives==4){
            imageView.setBackgroundResource(R.drawable.lifeboardb);
        }
        else if(lives==3){
            imageView.setBackgroundResource(R.drawable.lifeboardc);
        }
        else if(lives==2){
            imageView.setBackgroundResource(R.drawable.lifeboardd);
        }
        else if(lives==1){
            imageView.setBackgroundResource(R.drawable.lifeboarde);
        }
        else if(lives==0){
            imageView.setBackgroundResource(R.drawable.lifeboardf);
        }
        else{
            imageView.setBackgroundResource(R.drawable.lifeboard);
        }
        setDifficultyAccordingToCounter(difficultyCounter);

    }
    public String nextAnswerNumberAccordingToLevelDifficulty(int level,int difficultyCounter){
        //Check what the picture of level is to see difficulty
        String answerNumber;
        Random random = new Random();

        if(level<=10){
            if(difficultyCounter<=7){
                answerNumber = String.valueOf(random.nextInt(225 - 0) + 0);
            }else{
                answerNumber = String.valueOf(random.nextInt(357 - 225) + 225);
            }

        }else if(level<=20){
            if(difficultyCounter<=7){
                answerNumber = String.valueOf(random.nextInt(225 - 0) + 0);
            }else if(difficultyCounter<=9){
                answerNumber = String.valueOf(random.nextInt(357 - 225) + 225);
            }else{
                answerNumber = String.valueOf(random.nextInt(428 - 357) + 357);
            }

        }else if(level<=30){
            if(difficultyCounter<=6){
                answerNumber = String.valueOf(random.nextInt(225 - 0) + 0);
            }else if(difficultyCounter<=9){
                answerNumber = String.valueOf(random.nextInt(357 - 225) + 225);
            }else{
                answerNumber = String.valueOf(random.nextInt(428 - 357) + 357);
            }

        } else if(level<=40){
            if(difficultyCounter<=4){
                answerNumber = String.valueOf(random.nextInt(225 - 0) + 0);
            }else if(difficultyCounter<=8){
                answerNumber = String.valueOf(random.nextInt(357 - 225) + 225);
            }else{
                answerNumber = String.valueOf(random.nextInt(428 - 357) + 357);
            }

        }else{
            answerNumber = String.valueOf(random.nextInt(428 - 357) + 357);

        }
        return answerNumber;


    }

    public String updatePoints(int addedPoints){
        /*I also need a method to find how many points will be added according to dificulty,
       Implementation: Check which specific drawable is in the level imageView and return points accordingly*/
        TextView pointBoard= (TextView) findViewById(R.id.pointBoard_textView);
        String currentPoints= pointBoard.getText().toString();
        int totalPoints=Integer.parseInt(currentPoints)+addedPoints;

        pointBoard.setText((Integer.toString(totalPoints)));
        return Integer.toString(totalPoints);

    }


    public String[] findWords(String answerNumber) throws IOException {

        ArrayList<String> allWordsSeperated = new ArrayList<String>();
        //StringBuilder string = new StringBuilder();
        Context context = getApplicationContext();
        InputStream inputStream = context.getResources().openRawResource(R.raw.words_wrong);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        String allWords="noyhing,noting2";
       // string.append("q"+"s"+"\n");
        while ((line= bufferedReader.readLine() )!= null) {
            // System.out.println("ANSWERNUMBER: "+answerNumber+"BUFFER "+bufferedReader.readLine().toString());


            System.out.println("IF LINE :"+line);
            if (line.equals(answerNumber)) {
               allWords=bufferedReader.readLine();
            }
        }
        String[] split = allWords.split(",");
        System.out.println("all splits :"+split[0]+" "+split[1]+split[2]+split[3]);
        return split;
    }


    public String findDedinitionByNumber(String answerNumber) throws IOException {

        ArrayList<String> answerDefinition = new ArrayList<String>();
        StringBuilder string = new StringBuilder();
        Context context = getApplicationContext();
        InputStream inputStream = context.getResources().openRawResource(R.raw.definitions);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      /*  while (bufferedReader.readLine() != null) {
            if (bufferedReader.readLine().equals(answerNumber)) {
                answerDefinition.add(answerNumber);

              //  do{
                 //  answerDefinition.add(bufferedReader.readLine());
              //  }while(answerDefinition.contains("#"));
            }
        }
        return answerDefinition;*/
        String line;
        string.append("q"+"s"+"\n");
        while ((line= bufferedReader.readLine() )!= null) {
            // System.out.println("ANSWERNUMBER: "+answerNumber+"BUFFER "+bufferedReader.readLine().toString());


            System.out.println("IF LINE :"+line);
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



    public void startMultipleChoice(String wordNumber,String points,int lives,int difficultyCounter,int level) {
        Intent intent = new Intent(this, MultipleChoice.class);
        Bundle bundle = new Bundle();
        bundle.putString("key", wordNumber);
        bundle.putString("points", points);
        bundle.putInt("lives", lives);
        bundle.putInt("difficultyCounter", difficultyCounter);
        bundle.putInt("level", level);

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
    public void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
