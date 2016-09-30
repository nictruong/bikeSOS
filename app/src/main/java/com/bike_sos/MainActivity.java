package com.bike_sos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Send the SOS signal to the google map
    public void sendSOS(View view) {
        System.out.println("Sending signal");
    }
}
