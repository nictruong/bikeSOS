package com.bike_sos;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;

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

        // create class object
        GPSTracker gps = new GPSTracker(MainActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Intent intent = new Intent(this, MapsActivity.class);

            intent.putExtra("gpsLat", latitude);
            intent.putExtra("gpsLong", longitude);

            sendGPStoServer(Double.toString(latitude), Double.toString(longitude));

            //**startActivity(intent);
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();*/
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    public void sendGPStoServer(String latitude, String longitude) {
        CallAPI callAPI = new CallAPI();
        String[] coordinates = {latitude, longitude};
        callAPI.execute(coordinates);
    }
}
