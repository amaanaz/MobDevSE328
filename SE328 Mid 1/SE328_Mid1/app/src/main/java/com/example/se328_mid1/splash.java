package com.example.se328_mid1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final TextView counter = (TextView) findViewById(R.id.Counter);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for(int i = 9; i <= 0; i++){
                    counter.setText("Time Left: "+ (i)+  "secs");
                }

                finish();
                startActivity(new Intent(splash.this, MainActivity.class));
            }
        };
        Timer opening = new Timer();
        opening.schedule(timerTask, 9000);
    }
}