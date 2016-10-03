package com.bike_sos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    String Token;
    boolean thread_running = true;

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

        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}
