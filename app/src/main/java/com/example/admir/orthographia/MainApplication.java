package com.example.admir.orthographia;

/**
 * Created by admir on 27/10/2016.
 */
import android.app.Application;
public class MainApplication extends Application {

    LocaleHelper localeHelper=new LocaleHelper();
    @Override
    public void onCreate() {
        super.onCreate();
        localeHelper.onCreate(this, "en");
    }
}