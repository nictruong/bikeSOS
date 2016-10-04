package com.bike_sos;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    String Token;
    boolean thread_running = true;

    private final int REQUEST_PERMISSION_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Intent exampleIntent = new Intent(this, ExampleService.class);
        startService(exampleIntent);*/

        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (thread_running) {
                    Token = FirebaseInstanceId.getInstance().getToken();

                    if (Token != null) {
                        System.out.println("Device token is " + Token);

                        thread_running = false;
                    }else{
                        System.out.println("Token not loaded");
                    }

                    try{
                        Thread.sleep(5000);
                    }catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t.start();
    }

    // Send the SOS signal to the google map
    public void sendSOS(View view) {
        System.out.println("Sending signal");

        ActivityCompat.requestPermissions(
                this,
                new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSION_LOCATION);

        GPSTracker gpsTracker = new GPSTracker(this);
        String gpsLat = String.valueOf(gpsTracker.getLatitude());
        String gpsLong = String.valueOf(gpsTracker.getLongitude());

        /*OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token", token)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.2.13:3000")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Intent intent = new Intent(this, MapsActivity.class);

        intent.putExtra("gpsLat", gpsLat);
        intent.putExtra("gpsLong", gpsLong);

        startActivity(intent);
    }
}
