package com.ifpb.loginapp;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginService extends IntentService {

    public LoginService() {
        super("LoginService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);

        try {
            int response = post(email, password);
            intent.putExtra("response", response);
            manager.sendBroadcast(intent);
        } catch (Exception e) {
            manager.sendBroadcast(intent);
        }
    }

    private int post(String email, String password) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://ag-ifpb-sgd-server.herokuapp.com/login")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }

}
