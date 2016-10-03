package com.bike_sos;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Nicolas on 10/2/2016.
 */

public class MyFirebaseInstanceIDService extends com.google.firebase.iid.FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();

        registerToken(token);
    }

    private void registerToken(String token) {

        System.out.println("TOKEN: " + token);
        System.out.println("TOKEN: " + token);
        System.out.println("TOKEN: " + token);
        System.out.println("TOKEN: " + token);

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token",token)
                .build();

        // URL NOT SETUP YET
        Request request = new Request.Builder()
                .url("http://192.168.2.13:3000")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
