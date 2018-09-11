package com.paktalin.vocabularynotebook.app_setup;

import android.app.Application;

import com.firebase.client.Firebase;

public class VocabularyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
