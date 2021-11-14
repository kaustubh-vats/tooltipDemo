package com.kaustubhvats.tooltipdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;



public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    ImageButton imageButton;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageButton = findViewById(R.id.imageButton);
        animation = AnimationUtils.loadAnimation(this,R.anim.slide_left);
        animation.setDuration(700);
        imageButton.startAnimation(animation);
        imageButton.setOnClickListener(view -> {
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.button_press);
            mp.start();
            mediaPlayer.stop();
            MediaPlayer mediaPlayer1 = MediaPlayer.create(getApplicationContext(), R.raw.level_update);
            mediaPlayer1.start();
            Intent intent = new Intent(this, GamePlay.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer = MediaPlayer.create(this,R.raw.music_ingredients_level);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        new Handler(Looper.getMainLooper()).postDelayed((Runnable) () -> {
            DialogCustom dialogCustom = new DialogCustom();
            Dialog dialog = dialogCustom.showDialog(MainActivity.this);
            Button dialogButton = (Button) dialog.findViewById(R.id.button);
            dialogButton.setOnClickListener(v -> dialog.dismiss());
            dialog.show();
        },800);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}