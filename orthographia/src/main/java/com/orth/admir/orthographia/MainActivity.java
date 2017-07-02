package com.orth.admir.orthographia;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
        Resources res =this.getResources();
// Change locale settings in the app.
        String language = "el_GR";
        String country = "GR";
        Locale locale = new Locale(language , country);
        Configuration configEn = new Configuration();
        configEn.locale = locale;
        getApplicationContext().getResources().updateConfiguration(configEn, null);

       // System.out.println("OOOOOOOOOOOOOOOOOOOOOOOO"+locale.getCountry()+locale.getDisplayLanguage());
         */
        final MediaPlayer click = MediaPlayer.create(this, R.raw.click);

        Button button=(Button) findViewById(R.id.play_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.start();
                startGameModeActivity();



            }
        });

        Button creditsButton = (Button) findViewById(R.id.credits_btn);
        creditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.start();
                startCreditsActivity();



            }
        });

        Button instuctionsButton = (Button) findViewById(R.id.instructions_btn);
        instuctionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.start();
                starInstructionsActivity();



            }
        });
    }

    /** Called when the user clicks the Send button */


    public void startGameModeActivity() {
        Intent intent = new Intent(this, GameModeActvity.class);
        startActivity(intent);

    }
    public void startCreditsActivity() {
        Intent intent = new Intent(this, CreditsActivity.class);
        startActivity(intent);

    }
    public void starInstructionsActivity() {
        Intent intent = new Intent(this, InstructionsActivity.class);
        startActivity(intent);

    }
    public void startIntroduction() {
        Intent intent = new Intent(this, Introduction.class);
        startActivity(intent);

    }

}
