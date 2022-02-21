package com.example.se328_mid1;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    MediaPlayer song = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        song = MediaPlayer.create(this,R.raw.track1);

        ImageView cat = (ImageView) findViewById(R.id.catImg);
        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                song.pause();
                song.start();
            }
        });
    }
}