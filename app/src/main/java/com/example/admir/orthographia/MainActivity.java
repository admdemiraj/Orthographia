package com.example.admir.orthographia;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources res =this.getResources();
// Change locale settings in the app.
        String language = "el_GR";
        String country = "GR";
        Locale locale = new Locale(language , country);
        Configuration configEn = new Configuration();
        configEn.locale = locale;
        getApplicationContext().getResources().updateConfiguration(configEn, null);

        System.out.println("OOOOOOOOOOOOOOOOOOOOOOOO"+locale.getCountry()+locale.getDisplayLanguage());

        Button button=(Button) findViewById(R.id.button7);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   startIntroduction();



            }
        });


    }

    /** Called when the user clicks the Send button */
    public void startVoiceActivity(View view) {
        Intent intent = new Intent(this, VoiceListening.class);
        startActivity(intent);

    }

    public void startIntroduction() {
        Intent intent = new Intent(this, Introduction.class);
        startActivity(intent);

    }

}
