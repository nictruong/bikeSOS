package com.bike_sos;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Nicolas on 10/9/2016.
 */

public class CallAPI extends AsyncTask<String, String, String> {
    public CallAPI() {
        super();
    }

    @Override
    protected String doInBackground(String... params) {
        System.out.println("HELLO");
        System.out.println("HELLO");
        System.out.println("HELLO");
        System.out.println("HELLO");

        String latitude = params[0];
        String longitude = params[1];

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("latitude", latitude)
                .add("longitude", longitude)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.2.13:3000/gpsSignal")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
