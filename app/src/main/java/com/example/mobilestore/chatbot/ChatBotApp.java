package com.example.mobilestore.chatbot;

import android.app.Application;
import com.androidnetworking.AndroidNetworking;

public class ChatBotApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }
}
