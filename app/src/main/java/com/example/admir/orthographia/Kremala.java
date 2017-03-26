package com.example.admir.orthographia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Kremala extends AppCompatActivity {
    /**
     * We give the user the whole alphabet when in kremala mode and only the vowels in the hidden word mode
     */
    private static ArrayList<String> alphabet = new ArrayList<>(Arrays.asList("α", "β", "γ", "δ", "ε", "ζ", "η", "θ", "ι", "κ", "λ", "μ", "ν", "ξ", "ο", "π", "ρ", "σ", "τ", "υ", "φ", "χ", "ψ", "ω"));
    private static ArrayList<String> vowels = new ArrayList<>(Arrays.asList("α", "ε", "ι", "η", "ο", "υ", "ω", "ει", "οι", "ευ", "ου", "αι", "υι", "αυ"));
    private static ArrayList<String> allKremalaWords;
    private static ArrayList<String> allKremalaWordsNoVowels = new ArrayList<>();
    private static ArrayList<String> allKremalaWordsNormalized = new ArrayList<>();
    /**
     * We have a linear layout for the kremala word and a tableLayout for the letters of the alphabet,
     * they both get initialized in the onCreate method
     */
    private LinearLayout letterLinearLayout;
    private TableLayout alphabetTableLayout;
    @Override
    public void onStop() {
        super.onStop();
        TextView textView = (TextView) findViewById(R.id.title_text);
        saveHighScore(textView.getText().toString());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }
    @Override
    public void onPause() {
        super.onPause();
        TextView textView = (TextView) findViewById(R.id.title_text);
        saveHighScore(textView.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kremala);
        /**Get the view of the layouts of the kremala word and the alphabet*/
        letterLinearLayout = (LinearLayout) findViewById(R.id.letters_linear_layout);
        alphabetTableLayout = (TableLayout) findViewById(R.id.alphabet_table_layout);


        /** Ininitializing the actionbar with all the appropriate ImageButtons*/
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        View v = getSupportActionBar().getCustomView();
        TextView titleTxtView = (TextView) v.findViewById(R.id.title_text);
        titleTxtView.setText("000");
        TextView highScoreTextView = (TextView) findViewById(R.id.high_score);

        highScoreTextView.setText("max: "+getHighScore());

        /**Creating an onClickListener for the home ImageButton and using a method to go to the
         *  MainActivity*/
        ImageButton homeImageButton = (ImageButton) findViewById(R.id.home_image_btn);
        homeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
        initializeResources();
        int wordPosition = randomGenerator("easy");
        String word = allKremalaWordsNoVowels.get(wordPosition);
        initializeLetters(word);//change
        initializeVowels(word.length());
        checkIfCorrect(word, wordPosition);
    }

    /**
     * Method that starts the MainActivity
     */
    private void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    /**
     * Method that reads the file with the words and saves them in an array list so we can use them
     * for the kremala word
     */
    private void initializeResources() {
        Context context = getApplicationContext();
        InputStream inputStream = context.getResources().openRawResource(R.raw.words_lower);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        allKremalaWords = new ArrayList<>();
        int count = 0;
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                //add the word to an array list allKremalaWords. we only need the words and not their number
                if (count % 2 != 0) {
                    allKremalaWords.add(line);
                }
                //normalize words
                line = line.replaceAll("ή", "η");
                line = line.replaceAll("ά", "α");
                line = line.replaceAll("ί", "ι");
                line = line.replaceAll("έ", "ε");
                line = line.replaceAll("ό", "ο");
                line = line.replaceAll("ύ", "υ");
                line = line.replaceAll("ώ", "ω");
                if (count % 2 != 0) {
                    allKremalaWordsNormalized.add(line);
                }

                System.out.println(allKremalaWordsNormalized);
                //remove vowels and add them to array list allKremalaWordsNoVowels
                for (int i = vowels.size() - 1; i >= 0; i--) {
                    line = line.replaceAll(vowels.get(i), "_");
                }
                if (count % 2 != 0) {
                    allKremalaWordsNoVowels.add(line);
                }
                line = bufferedReader.readLine();
                count++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method that creates a button of fixed size for each letter of the kremala word
     */
    private void initializeLetters(String word) {
        int wordLength = word.length();
        for (int i = 0; i < wordLength; i++) {
            Button letterButton = new Button(this);
            letterButton.setLayoutParams(new LinearLayout.LayoutParams(70, 80));
            letterButton.setBackground(getResources().getDrawable(R.drawable.letters_btn_layout));
            letterLinearLayout.addView(letterButton);
            letterButton.setText(String.valueOf(word.charAt(i)));
            letterButton.setGravity(Gravity.CENTER);
            //letterButton.setTypeface(Typeface.DEFAULT_BOLD);
            //letterButton.setTextColor(Color.parseColor("#a4c639"));
            letterButton.setTextColor(Color.BLUE);
            letterButton.setTextSize(18);


            if (i == 0) {
                letterButton.setId(R.id.word_btn_1);
                if (letterButton.getText().equals("_")) {
                    wordListener(letterButton);
                    letterButton.setTextColor(Color.RED);
                    letterButton.setTextSize(20);
                }
            } else if (i == 1) {
                letterButton.setId(R.id.word_btn_2);
                if (letterButton.getText().equals("_")) {
                    wordListener(letterButton);
                    letterButton.setTextColor(Color.RED);
                    letterButton.setTextSize(20);
                }
            } else if (i == 2) {
                letterButton.setId(R.id.word_btn_3);
                if (letterButton.getText().equals("_")) {
                    wordListener(letterButton);
                    letterButton.setTextColor(Color.RED);
                    letterButton.setTextSize(20);
                }
            } else if (i == 3) {
                letterButton.setId(R.id.word_btn_4);
                if (letterButton.getText().equals("_")) {
                    wordListener(letterButton);
                    letterButton.setTextColor(Color.RED);
                    letterButton.setTextSize(20);
                }
            } else if (i == 4) {
                letterButton.setId(R.id.word_btn_5);
                if (letterButton.getText().equals("_")) {
                    wordListener(letterButton);
                    letterButton.setTextColor(Color.RED);
                    letterButton.setTextSize(20);
                }
            } else if (i == 5) {
                letterButton.setId(R.id.word_btn_6);
                if (letterButton.getText().equals("_")) {
                    wordListener(letterButton);
                    letterButton.setTextColor(Color.RED);
                    letterButton.setTextSize(20);
                }
            } else if (i == 6) {
                letterButton.setId(R.id.word_btn_7);
                if (letterButton.getText().equals("_")) {
                    wordListener(letterButton);
                    letterButton.setTextColor(Color.RED);
                    letterButton.setTextSize(20);
                }
            } else if (i == 7) {
                letterButton.setId(R.id.word_btn_8);
                wordListener(letterButton);
                if (letterButton.getText().equals("_")) {
                    letterButton.setTextColor(Color.RED);
                    letterButton.setTextSize(20);
                }
            } else if (i == 8) {
                letterButton.setId(R.id.word_btn_9);
                if (letterButton.getText().equals("_")) {
                    wordListener(letterButton);
                    letterButton.setTextColor(Color.RED);
                    letterButton.setTextSize(20);
                }
            } else if (i == 9) {
                letterButton.setId(R.id.word_btn_10);
                if (letterButton.getText().equals("_")) {
                    wordListener(letterButton);
                    letterButton.setTextColor(Color.RED);
                    letterButton.setTextSize(20);
                }
            } else if (i == 10) {
                letterButton.setId(R.id.word_btn_11);
                if (letterButton.getText().equals("_")) {
                    wordListener(letterButton);
                    letterButton.setTextColor(Color.RED);
                    letterButton.setTextSize(20);
                }
            } else if (i == 11) {
                letterButton.setId(R.id.word_btn_12);
                if (letterButton.getText().equals("_")) {
                    wordListener(letterButton);
                    letterButton.setTextColor(Color.RED);
                    letterButton.setTextSize(20);
                }
            } else if (i == 12) {
                letterButton.setId(R.id.word_btn_13);
                if (letterButton.getText().equals("_")) {
                    wordListener(letterButton);
                    letterButton.setTextColor(Color.RED);
                    letterButton.setTextSize(20);
                }
            } else if (i == 13) {
                letterButton.setId(R.id.word_btn_14);
                if (letterButton.getText().equals("_")) {
                    wordListener(letterButton);
                    letterButton.setTextColor(Color.RED);
                    letterButton.setTextSize(20);
                }
            } else if (i == 14) {
                letterButton.setId(R.id.word_btn_15);
                if (letterButton.getText().equals("_")) {
                    wordListener(letterButton);
                    letterButton.setTextColor(Color.RED);
                    letterButton.setTextSize(20);
                }
            } else if (i == 15) {
                letterButton.setId(R.id.word_btn_16);
                if (letterButton.getText().equals("_")) {
                    wordListener(letterButton);
                    letterButton.setTextColor(Color.RED);
                    letterButton.setTextSize(20);
                }
            } else {
                letterButton.setId(R.id.word_btn_17);
                if (letterButton.getText().equals("_")) {
                    wordListener(letterButton);
                    letterButton.setTextColor(Color.RED);
                    letterButton.setTextSize(20);
                }
            }


        }
    }

    /**
     * Method that creates a button of fixed sized for each  of the vowels and displays the
     * letter in the button
     */
    private void initializeVowels(int wordSize) {
        //create two table rows and add them to the tableLayout
        TableRow tableRow1 = new TableRow(this);
        TableRow tableRow2 = new TableRow(this);
        alphabetTableLayout.addView(tableRow1);
        alphabetTableLayout.addView(tableRow2);
        for (int i = 0; i < vowels.size(); i++) {
            Button alphabetButton = new Button(this);
            //Put the first 6 letters of the vowels in the first row and the rest in the second
            if (i < Math.ceil(vowels.size() / 2)) {
                alphabetButton.setLayoutParams(new TableRow.LayoutParams(90, 80));
                alphabetButton.setText(vowels.get(i));
                tableRow1.addView(alphabetButton);
            } else {
                alphabetButton.setLayoutParams(new TableRow.LayoutParams(90, 80));
                alphabetButton.setText(vowels.get(i));
                tableRow2.addView(alphabetButton);
            }
            if (i == 0) {
                alphabetButton.setId(R.id.alphabet_btn_1);
            } else if (i == 1) {
                alphabetButton.setId(R.id.alphabet_btn_2);
            } else if (i == 2) {
                alphabetButton.setId(R.id.alphabet_btn_3);
            } else if (i == 3) {
                alphabetButton.setId(R.id.alphabet_btn_4);
            } else if (i == 4) {
                alphabetButton.setId(R.id.alphabet_btn_5);
            } else if (i == 5) {
                alphabetButton.setId(R.id.alphabet_btn_6);
            } else if (i == 6) {
                alphabetButton.setId(R.id.alphabet_btn_7);
            } else if (i == 7) {
                alphabetButton.setId(R.id.alphabet_btn_8);
            } else if (i == 8) {
                alphabetButton.setId(R.id.alphabet_btn_9);
            } else if (i == 9) {
                alphabetButton.setId(R.id.alphabet_btn_10);
            } else if (i == 10) {
                alphabetButton.setId(R.id.alphabet_btn_11);
            } else if (i == 11) {
                alphabetButton.setId(R.id.alphabet_btn_12);
            } else if (i == 12) {
                alphabetButton.setId(R.id.alphabet_btn_13);
            } else if (i == 13) {
                alphabetButton.setId(R.id.alphabet_btn_14);
            }

            alpabetButtonsListener(alphabetButton, wordSize);
        }

    }

    /**
     * Method that checks of the answer of the user is correct
     */
    private void checkIfCorrect(final String word, final int wordPosition) {
        final Button checkIfCorrect = (Button) findViewById(R.id.check_if_correct_btn);
        final MediaPlayer mediaPlayerRight = MediaPlayer.create(this, R.raw.correct);


        checkIfCorrect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String answer = "";
                for (int i = 1; i <= word.length(); i++) {
                    int id = getResources().getIdentifier("word_btn_" + i, "id", getPackageName());
                    Button button = (Button) findViewById(id);
                    answer = answer + button.getText().toString();
                }

                // if the word is correct play the correct sound
                if (allKremalaWordsNormalized.get(wordPosition).toString().equals(answer)) {
                    mediaPlayerRight.start();
                    LinearLayout letterLinearLayout = (LinearLayout) findViewById(R.id.letters_linear_layout);
                    letterLinearLayout.removeAllViews();

                    int wordPosition = randomGenerator("easy");
                    String word = allKremalaWordsNoVowels.get(wordPosition);
                    initializeLetters(word);
                    for (int i = 1; i <= 14; i++) {
                        int id = getResources().getIdentifier("alphabet_btn_" + i, "id", getPackageName());
                        Button button = (Button) findViewById(id);
                        answer = answer + button.getText().toString();
                        alpabetButtonsListener(button, word.length());
                    }
pointManager("easy");
                    checkIfCorrect(word,wordPosition);

                }else{ // if the answer is incorrect
                    changeDummy();
                }
            }
        });
    }

    /**
     * Method thatremoves the previous letters that are on the screen in order to display the new word
     */
    private void pointManager(String difficulty) {
        View v = getSupportActionBar().getCustomView();
        TextView titleTxtView = (TextView) v.findViewById(R.id.title_text);
        if (difficulty.equals("easy")) {
            System.out.println("points:"+titleTxtView.getText().toString());
            int points = Integer.parseInt(titleTxtView.getText().toString());
            points =points + 500;
            titleTxtView.setText(String.valueOf(points));
        } else if (difficulty.equals("medium")) {

        } else {

        }

    }
/** Method that changes the dummy when the user makes a mistake*/
    private void changeDummy(){
        ImageView dummyImgView = (ImageView) findViewById(R.id.dummy_imgview);
        if(String.valueOf(dummyImgView.getTag()).equals("f") ){
            dummyImgView.setImageResource(R.drawable.dummye);
            dummyImgView.setTag("e");
        }else if(String.valueOf(dummyImgView.getTag()).equals("e")){
            dummyImgView.setImageResource(R.drawable.dummyad);
            dummyImgView.setTag("d");
        }else if(String.valueOf(dummyImgView.getTag()).equals("d")){
            dummyImgView.setImageResource(R.drawable.dummyc);
            dummyImgView.setTag("c");
        }else if(String.valueOf(dummyImgView.getTag()).equals("c")){
            dummyImgView.setImageResource(R.drawable.dummyb);
            dummyImgView.setTag("b");
        }else if(String.valueOf(dummyImgView.getTag()).equals("b")){
            dummyImgView.setImageResource(R.drawable.dummya);
            dummyImgView.setTag("a");
        }else{
            try {
                initiatePopupWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void initiatePopupWindow() throws IOException {

        final PopupWindow pw;


        //We need to get the instance of the LayoutInflater, use the context of this activity
        LayoutInflater inflater = (LayoutInflater) Kremala.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout
        View layout = inflater.inflate(R.layout.pop_up_layout, (ViewGroup) findViewById(R.id.pop_up_element));
        pw = new PopupWindow(layout, 1100, 550, true);
        TextView titleTxtView = (TextView) findViewById(R.id.title_text);
        saveHighScore(titleTxtView.getText().toString());
        ((TextView) pw.getContentView().findViewById(R.id.definition)).setText("ΤΕΛΟΣ ΠΑΙΧΝΙΔΙΟΥ \n ΠΕΤΥΧΑΤΕ ΣΚΟΡ: " + titleTxtView .getText().toString());
        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
        Button cancelButton = (Button) layout.findViewById(R.id.cancel_btn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw.dismiss();
                //reset all resources nad start a new game
                LinearLayout letterLinearLayout = (LinearLayout) findViewById(R.id.letters_linear_layout);
                letterLinearLayout.removeAllViews();

                int wordPosition = randomGenerator("easy");
                String word = allKremalaWordsNoVowels.get(wordPosition);
                initializeLetters(word);
                for (int i = 1; i <= 14; i++) {
                    int id = getResources().getIdentifier("alphabet_btn_" + i, "id", getPackageName());
                    Button button = (Button) findViewById(id);
                    String answer ="";
                    answer = answer + button.getText().toString();
                    alpabetButtonsListener(button, word.length());
                }
                checkIfCorrect(word,wordPosition);
                ImageView dummyImgView = (ImageView) findViewById(R.id.dummy_imgview);
                    dummyImgView.setImageResource(R.drawable.dummyf);
                    dummyImgView.setTag("f");
                TextView titleTxtView = (TextView) findViewById(R.id.title_text);
                titleTxtView.setText("000");


            }
        });
    }
    private void saveHighScore(String data) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int newHigh = Integer.parseInt(data);
        int previousHigh = Integer.parseInt(getHighScore());
        if(newHigh>previousHigh){
            editor.putString("highScoreC", data);
            editor.commit();
        }




    }
    private String getHighScore(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        String highScore = sharedPref.getString("highScoreC", "000");
        return highScore;
    }
    /**
     * Method that sets the alphabet button to gray and unclickable when they get pressed and puts
     * the letter of that button in the correct box of the answer
     */
    private void alpabetButtonsListener(final Button alphabetButton, final int wordSize) {
        final Button button1 = (Button) findViewById(R.id.word_btn_1);
        final Button button2 = (Button) findViewById(R.id.word_btn_2);
        final Button button3 = (Button) findViewById(R.id.word_btn_3);
        final Button button4 = (Button) findViewById(R.id.word_btn_4);
        final Button button5 = (Button) findViewById(R.id.word_btn_5);
        final Button button6 = (Button) findViewById(R.id.word_btn_6);
        final Button button7 = (Button) findViewById(R.id.word_btn_7);
        final Button button8 = (Button) findViewById(R.id.word_btn_8);
        final Button button9 = (Button) findViewById(R.id.word_btn_9);
        final Button button10 = (Button) findViewById(R.id.word_btn_10);
        final Button button11 = (Button) findViewById(R.id.word_btn_11);
        final Button button12 = (Button) findViewById(R.id.word_btn_12);
        final Button button13 = (Button) findViewById(R.id.word_btn_13);
        final Button button14 = (Button) findViewById(R.id.word_btn_14);
        final Button button15 = (Button) findViewById(R.id.word_btn_15);
        final Button button16 = (Button) findViewById(R.id.word_btn_16);
        final Button button17 = (Button) findViewById(R.id.word_btn_17);

        alphabetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO crete the action of the alphabet buttons
                System.out.println("Button text is:- " + alphabetButton.getText().toString());
                //use them in kremala to make button gray  and unusable
                //  alphabetButton.setClickable(false);
                // alphabetButton.setEnabled(false);
                if (wordSize == 1) {
                    if (button1.getText().equals("_") && wordSize >= 1) {
                        button1.setText(alphabetButton.getText().toString());
                    }
                } else if (wordSize == 2) {
                    if (button1.getText().equals("_") && wordSize >= 1) {
                        button1.setText(alphabetButton.getText().toString());
                    } else if (button2.getText().equals("_")) {
                        button2.setText(alphabetButton.getText().toString());
                    }
                } else if (wordSize == 3) {
                    if (button1.getText().equals("_") && wordSize >= 1) {
                        button1.setText(alphabetButton.getText().toString());
                    } else if (button2.getText().equals("_")) {
                        button2.setText(alphabetButton.getText().toString());
                    } else if (button3.getText().equals("_")) {
                        button3.setText(alphabetButton.getText().toString());
                    }
                } else if (wordSize == 4) {
                    if (button1.getText().equals("_") && wordSize >= 1) {
                        button1.setText(alphabetButton.getText().toString());
                    } else if (button2.getText().equals("_")) {
                        button2.setText(alphabetButton.getText().toString());
                    } else if (button3.getText().equals("_")) {
                        button3.setText(alphabetButton.getText().toString());
                    } else if (button4.getText().equals("_")) {
                        button4.setText(alphabetButton.getText().toString());
                    }
                } else if (wordSize == 5) {
                    if (button1.getText().equals("_") && wordSize >= 1) {
                        button1.setText(alphabetButton.getText().toString());
                    } else if (button2.getText().equals("_")) {
                        button2.setText(alphabetButton.getText().toString());
                    } else if (button3.getText().equals("_")) {
                        button3.setText(alphabetButton.getText().toString());
                    } else if (button4.getText().equals("_")) {
                        button4.setText(alphabetButton.getText().toString());
                    } else if (button5.getText().equals("_")) {
                        button5.setText(alphabetButton.getText().toString());
                    }
                } else if (wordSize == 6) {
                    if (button1.getText().equals("_") && wordSize >= 1) {
                        button1.setText(alphabetButton.getText().toString());
                    } else if (button2.getText().equals("_")) {
                        button2.setText(alphabetButton.getText().toString());
                    } else if (button3.getText().equals("_")) {
                        button3.setText(alphabetButton.getText().toString());
                    } else if (button4.getText().equals("_")) {
                        button4.setText(alphabetButton.getText().toString());
                    } else if (button5.getText().equals("_")) {
                        button5.setText(alphabetButton.getText().toString());
                    } else if (button6.getText().equals("_")) {
                        button6.setText(alphabetButton.getText().toString());
                    }
                } else if (wordSize == 7) {
                    if (button1.getText().equals("_") && wordSize >= 1) {
                        button1.setText(alphabetButton.getText().toString());
                    } else if (button2.getText().equals("_")) {
                        button2.setText(alphabetButton.getText().toString());
                    } else if (button3.getText().equals("_")) {
                        button3.setText(alphabetButton.getText().toString());
                    } else if (button4.getText().equals("_")) {
                        button4.setText(alphabetButton.getText().toString());
                    } else if (button5.getText().equals("_")) {
                        button5.setText(alphabetButton.getText().toString());
                    } else if (button6.getText().equals("_")) {
                        button6.setText(alphabetButton.getText().toString());
                    } else if (button7.getText().equals("_")) {
                        button7.setText(alphabetButton.getText().toString());
                    }
                } else if (wordSize == 8) {
                    if (button1.getText().equals("_") && wordSize >= 1) {
                        button1.setText(alphabetButton.getText().toString());
                    } else if (button2.getText().equals("_")) {
                        button2.setText(alphabetButton.getText().toString());
                    } else if (button3.getText().equals("_")) {
                        button3.setText(alphabetButton.getText().toString());
                    } else if (button4.getText().equals("_")) {
                        button4.setText(alphabetButton.getText().toString());
                    } else if (button5.getText().equals("_")) {
                        button5.setText(alphabetButton.getText().toString());
                    } else if (button6.getText().equals("_")) {
                        button6.setText(alphabetButton.getText().toString());
                    } else if (button7.getText().equals("_")) {
                        button7.setText(alphabetButton.getText().toString());
                    } else if (button8.getText().equals("_")) {
                        button8.setText(alphabetButton.getText().toString());
                    }
                } else if (wordSize == 9) {
                    if (button1.getText().equals("_") && wordSize >= 1) {
                        button1.setText(alphabetButton.getText().toString());
                    } else if (button2.getText().equals("_")) {
                        button2.setText(alphabetButton.getText().toString());
                    } else if (button3.getText().equals("_")) {
                        button3.setText(alphabetButton.getText().toString());
                    } else if (button4.getText().equals("_")) {
                        button4.setText(alphabetButton.getText().toString());
                    } else if (button5.getText().equals("_")) {
                        button5.setText(alphabetButton.getText().toString());
                    } else if (button6.getText().equals("_")) {
                        button6.setText(alphabetButton.getText().toString());
                    } else if (button7.getText().equals("_")) {
                        button7.setText(alphabetButton.getText().toString());
                    } else if (button8.getText().equals("_")) {
                        button8.setText(alphabetButton.getText().toString());
                    } else if (button9.getText().equals("_")) {
                        button9.setText(alphabetButton.getText().toString());
                    }
                } else if (wordSize == 10) {
                    if (button1.getText().equals("_") && wordSize >= 1) {
                        button1.setText(alphabetButton.getText().toString());
                    } else if (button2.getText().equals("_")) {
                        button2.setText(alphabetButton.getText().toString());
                    } else if (button3.getText().equals("_")) {
                        button3.setText(alphabetButton.getText().toString());
                    } else if (button4.getText().equals("_")) {
                        button4.setText(alphabetButton.getText().toString());
                    } else if (button5.getText().equals("_")) {
                        button5.setText(alphabetButton.getText().toString());
                    } else if (button6.getText().equals("_")) {
                        button6.setText(alphabetButton.getText().toString());
                    } else if (button7.getText().equals("_")) {
                        button7.setText(alphabetButton.getText().toString());
                    } else if (button8.getText().equals("_")) {
                        button8.setText(alphabetButton.getText().toString());
                    } else if (button9.getText().equals("_")) {
                        button9.setText(alphabetButton.getText().toString());
                    } else if (button10.getText().equals("_")) {
                        button10.setText(alphabetButton.getText().toString());
                    }
                } else if (wordSize == 11) {
                    if (button1.getText().equals("_") && wordSize >= 1) {
                        button1.setText(alphabetButton.getText().toString());
                    } else if (button2.getText().equals("_")) {
                        button2.setText(alphabetButton.getText().toString());
                    } else if (button3.getText().equals("_")) {
                        button3.setText(alphabetButton.getText().toString());
                    } else if (button4.getText().equals("_")) {
                        button4.setText(alphabetButton.getText().toString());
                    } else if (button5.getText().equals("_")) {
                        button5.setText(alphabetButton.getText().toString());
                    } else if (button6.getText().equals("_")) {
                        button6.setText(alphabetButton.getText().toString());
                    } else if (button7.getText().equals("_")) {
                        button7.setText(alphabetButton.getText().toString());
                    } else if (button8.getText().equals("_")) {
                        button8.setText(alphabetButton.getText().toString());
                    } else if (button9.getText().equals("_")) {
                        button9.setText(alphabetButton.getText().toString());
                    } else if (button10.getText().equals("_")) {
                        button10.setText(alphabetButton.getText().toString());
                    } else if (button11.getText().equals("_")) {
                        button11.setText(alphabetButton.getText().toString());
                    }
                } else if (wordSize == 12) {
                    if (button1.getText().equals("_") && wordSize >= 1) {
                        button1.setText(alphabetButton.getText().toString());
                    } else if (button2.getText().equals("_")) {
                        button2.setText(alphabetButton.getText().toString());
                    } else if (button3.getText().equals("_")) {
                        button3.setText(alphabetButton.getText().toString());
                    } else if (button4.getText().equals("_")) {
                        button4.setText(alphabetButton.getText().toString());
                    } else if (button5.getText().equals("_")) {
                        button5.setText(alphabetButton.getText().toString());
                    } else if (button6.getText().equals("_")) {
                        button6.setText(alphabetButton.getText().toString());
                    } else if (button7.getText().equals("_")) {
                        button7.setText(alphabetButton.getText().toString());
                    } else if (button8.getText().equals("_")) {
                        button8.setText(alphabetButton.getText().toString());
                    } else if (button9.getText().equals("_")) {
                        button9.setText(alphabetButton.getText().toString());
                    } else if (button10.getText().equals("_")) {
                        button10.setText(alphabetButton.getText().toString());
                    } else if (button11.getText().equals("_")) {
                        button11.setText(alphabetButton.getText().toString());
                    } else if (button12.getText().equals("_")) {
                        button12.setText(alphabetButton.getText().toString());
                    }
                } else if (wordSize == 13) {
                    if (button1.getText().equals("_") && wordSize >= 1) {
                        button1.setText(alphabetButton.getText().toString());
                    } else if (button2.getText().equals("_")) {
                        button2.setText(alphabetButton.getText().toString());
                    } else if (button3.getText().equals("_")) {
                        button3.setText(alphabetButton.getText().toString());
                    } else if (button4.getText().equals("_")) {
                        button4.setText(alphabetButton.getText().toString());
                    } else if (button5.getText().equals("_")) {
                        button5.setText(alphabetButton.getText().toString());
                    } else if (button6.getText().equals("_")) {
                        button6.setText(alphabetButton.getText().toString());
                    } else if (button7.getText().equals("_")) {
                        button7.setText(alphabetButton.getText().toString());
                    } else if (button8.getText().equals("_")) {
                        button8.setText(alphabetButton.getText().toString());
                    } else if (button9.getText().equals("_")) {
                        button9.setText(alphabetButton.getText().toString());
                    } else if (button10.getText().equals("_")) {
                        button10.setText(alphabetButton.getText().toString());
                    } else if (button11.getText().equals("_")) {
                        button11.setText(alphabetButton.getText().toString());
                    } else if (button12.getText().equals("_")) {
                        button12.setText(alphabetButton.getText().toString());
                    } else if (button13.getText().equals("_")) {
                        button13.setText(alphabetButton.getText().toString());
                    }
                } else if (wordSize == 14) {
                    if (button1.getText().equals("_") && wordSize >= 1) {
                        button1.setText(alphabetButton.getText().toString());
                    } else if (button2.getText().equals("_")) {
                        button2.setText(alphabetButton.getText().toString());
                    } else if (button3.getText().equals("_")) {
                        button3.setText(alphabetButton.getText().toString());
                    } else if (button4.getText().equals("_")) {
                        button4.setText(alphabetButton.getText().toString());
                    } else if (button5.getText().equals("_")) {
                        button5.setText(alphabetButton.getText().toString());
                    } else if (button6.getText().equals("_")) {
                        button6.setText(alphabetButton.getText().toString());
                    } else if (button7.getText().equals("_")) {
                        button7.setText(alphabetButton.getText().toString());
                    } else if (button8.getText().equals("_")) {
                        button8.setText(alphabetButton.getText().toString());
                    } else if (button9.getText().equals("_")) {
                        button9.setText(alphabetButton.getText().toString());
                    } else if (button10.getText().equals("_")) {
                        button10.setText(alphabetButton.getText().toString());
                    } else if (button11.getText().equals("_")) {
                        button11.setText(alphabetButton.getText().toString());
                    } else if (button12.getText().equals("_")) {
                        button12.setText(alphabetButton.getText().toString());
                    } else if (button13.getText().equals("_")) {
                        button13.setText(alphabetButton.getText().toString());
                    } else if (button14.getText().equals("_")) {
                        button14.setText(alphabetButton.getText().toString());
                    }
                } else if (wordSize == 15) {
                    if (button1.getText().equals("_") && wordSize >= 1) {
                        button1.setText(alphabetButton.getText().toString());
                    } else if (button2.getText().equals("_")) {
                        button2.setText(alphabetButton.getText().toString());
                    } else if (button3.getText().equals("_")) {
                        button3.setText(alphabetButton.getText().toString());
                    } else if (button4.getText().equals("_")) {
                        button4.setText(alphabetButton.getText().toString());
                    } else if (button5.getText().equals("_")) {
                        button5.setText(alphabetButton.getText().toString());
                    } else if (button6.getText().equals("_")) {
                        button6.setText(alphabetButton.getText().toString());
                    } else if (button7.getText().equals("_")) {
                        button7.setText(alphabetButton.getText().toString());
                    } else if (button8.getText().equals("_")) {
                        button8.setText(alphabetButton.getText().toString());
                    } else if (button9.getText().equals("_")) {
                        button9.setText(alphabetButton.getText().toString());
                    } else if (button10.getText().equals("_")) {
                        button10.setText(alphabetButton.getText().toString());
                    } else if (button11.getText().equals("_")) {
                        button11.setText(alphabetButton.getText().toString());
                    } else if (button12.getText().equals("_")) {
                        button12.setText(alphabetButton.getText().toString());
                    } else if (button13.getText().equals("_")) {
                        button13.setText(alphabetButton.getText().toString());
                    } else if (button14.getText().equals("_")) {
                        button14.setText(alphabetButton.getText().toString());
                    } else if (button15.getText().equals("_")) {
                        button15.setText(alphabetButton.getText().toString());
                    }
                } else if (wordSize == 16) {
                    if (button1.getText().equals("_") && wordSize >= 1) {
                        button1.setText(alphabetButton.getText().toString());
                    } else if (button2.getText().equals("_")) {
                        button2.setText(alphabetButton.getText().toString());
                    } else if (button3.getText().equals("_")) {
                        button3.setText(alphabetButton.getText().toString());
                    } else if (button4.getText().equals("_")) {
                        button4.setText(alphabetButton.getText().toString());
                    } else if (button5.getText().equals("_")) {
                        button5.setText(alphabetButton.getText().toString());
                    } else if (button6.getText().equals("_")) {
                        button6.setText(alphabetButton.getText().toString());
                    } else if (button7.getText().equals("_")) {
                        button7.setText(alphabetButton.getText().toString());
                    } else if (button8.getText().equals("_")) {
                        button8.setText(alphabetButton.getText().toString());
                    } else if (button9.getText().equals("_")) {
                        button9.setText(alphabetButton.getText().toString());
                    } else if (button10.getText().equals("_")) {
                        button10.setText(alphabetButton.getText().toString());
                    } else if (button11.getText().equals("_")) {
                        button11.setText(alphabetButton.getText().toString());
                    } else if (button12.getText().equals("_")) {
                        button12.setText(alphabetButton.getText().toString());
                    } else if (button13.getText().equals("_")) {
                        button13.setText(alphabetButton.getText().toString());
                    } else if (button14.getText().equals("_")) {
                        button14.setText(alphabetButton.getText().toString());
                    } else if (button15.getText().equals("_")) {
                        button15.setText(alphabetButton.getText().toString());
                    } else if (button16.getText().equals("_")) {
                        button16.setText(alphabetButton.getText().toString());
                    }
                } else if (wordSize == 17) {
                    if (button1.getText().equals("_") && wordSize >= 1) {
                        button1.setText(alphabetButton.getText().toString());
                    } else if (button2.getText().equals("_")) {
                        button2.setText(alphabetButton.getText().toString());
                    } else if (button3.getText().equals("_")) {
                        button3.setText(alphabetButton.getText().toString());
                    } else if (button4.getText().equals("_")) {
                        button4.setText(alphabetButton.getText().toString());
                    } else if (button5.getText().equals("_")) {
                        button5.setText(alphabetButton.getText().toString());
                    } else if (button6.getText().equals("_")) {
                        button6.setText(alphabetButton.getText().toString());
                    } else if (button7.getText().equals("_")) {
                        button7.setText(alphabetButton.getText().toString());
                    } else if (button8.getText().equals("_")) {
                        button8.setText(alphabetButton.getText().toString());
                    } else if (button9.getText().equals("_")) {
                        button9.setText(alphabetButton.getText().toString());
                    } else if (button10.getText().equals("_")) {
                        button10.setText(alphabetButton.getText().toString());
                    } else if (button11.getText().equals("_")) {
                        button11.setText(alphabetButton.getText().toString());
                    } else if (button12.getText().equals("_")) {
                        button12.setText(alphabetButton.getText().toString());
                    } else if (button13.getText().equals("_")) {
                        button13.setText(alphabetButton.getText().toString());
                    } else if (button14.getText().equals("_")) {
                        button14.setText(alphabetButton.getText().toString());
                    } else if (button15.getText().equals("_")) {
                        button15.setText(alphabetButton.getText().toString());
                    } else if (button16.getText().equals("_")) {
                        button16.setText(alphabetButton.getText().toString());
                    } else if (button17.getText().equals("_")) {
                        button17.setText(alphabetButton.getText().toString());
                    }
                }


            }
        });
    }

    /**
     * Method that has listener for each word button and resets the text of the button to "_"
     */
    private void wordListener(final Button wordButton) {
        wordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                wordButton.setText("_");
            }
        });
    }

    /**
     * Method that selects a random word according to difficulty
     */
    private int randomGenerator(String difficulty) {
        Random random = new Random();

        if (difficulty.equals("easy")) {
            return random.nextInt(30 - 0 + 1) + 0;
        } else if (difficulty.equals("medium")) {
            return random.nextInt(60 - 30 + 1) + 30;
        } else {
            return random.nextInt(90 - 60 + 1) + 60;
        }
    }
}
