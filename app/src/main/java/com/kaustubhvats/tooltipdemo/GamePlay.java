package com.kaustubhvats.tooltipdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GamePlay extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    ImageView tooltip1, tooltip2, tooltip3, srcLayout, destLayout, auxilary1, auxilary2, hand;
    Animation slideLeft, slideRight, slideDown, slideRight1, handAnim;
    int [][] gameBoard = {{4,5,2,3,1},
                          {1,4,1,2,4},
                          {2,3,5,2,2},
                          {2,1,3,1,3},
                          {1,5,2,2,1}};
    GridLayout gridLayout;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        mediaPlayer = MediaPlayer.create(this, R.raw.music_jelly_level);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        tooltip1 = findViewById(R.id.tooltip1);
        tooltip2 = findViewById(R.id.tooltip2);
        tooltip3 = findViewById(R.id.tooltip3);
        hand = findViewById(R.id.hand);
        gridLayout = findViewById(R.id.grid_layout);
        slideLeft = AnimationUtils.loadAnimation(this,R.anim.slide_left);
        slideRight = AnimationUtils.loadAnimation(this, R.anim.slide_right);
        slideRight1 = AnimationUtils.loadAnimation(this, R.anim.slide_right);
        slideDown = AnimationUtils.loadAnimation(this,R.anim.slide_down);
        handAnim = AnimationUtils.loadAnimation(this,R.anim.hand_anim);
        slideLeft.setDuration(200);
        slideRight.setDuration(200);
        slideDown.setDuration(200);
        slideRight1.setDuration(200);
        handAnim.setDuration(700);

        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (40 * scale + 0.5f);
        int margins = (int) (5 * scale + 0.5f);
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                ImageView candyImage = new ImageView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(pixels,pixels);
                layoutParams.setMargins(margins,margins,margins,margins);
                candyImage.setLayoutParams(layoutParams);
                if(gameBoard[i][j]==1)
                    candyImage.setImageResource(R.drawable.candy1);
                else if(gameBoard[i][j]==2)
                    candyImage.setImageResource(R.drawable.candy2);
                else if(gameBoard[i][j]==3)
                    candyImage.setImageResource(R.drawable.candy3);
                else if(gameBoard[i][j]==4)
                    candyImage.setImageResource(R.drawable.candy4);
                else
                    candyImage.setImageResource(R.drawable.candy5);

                if(i==0 && j==2){
                    srcLayout = candyImage;
                }
                else if(i==0 && j==3){
                    destLayout = candyImage;
                }
                else if(i==1 && j==3){
                    auxilary1 = candyImage;
                }
                else if(i==2 && j==3){
                    auxilary2 = candyImage;
                }
                gridLayout.addView(candyImage);
            }
        }
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            tooltip1.setVisibility(View.VISIBLE);
            tooltip1.startAnimation(slideLeft);
        },400);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            tooltip1.startAnimation(slideRight);
            hand.setVisibility(View.VISIBLE);
            hand.startAnimation(handAnim);
            handAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    hand.startAnimation(handAnim);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            slideRight.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public void onAnimationEnd(Animation animation) {
                    tooltip1.setVisibility(View.INVISIBLE);
                    tooltip2.setVisibility(View.VISIBLE);
                    tooltip2.startAnimation(slideLeft);
                    srcLayout.setOnClickListener(view -> {
                        Toast.makeText(GamePlay.this, "Swipe", Toast.LENGTH_SHORT).show();
                    });
                    srcLayout.setOnTouchListener((view, event) -> {
                        switch(event.getAction())
                        {
                            case MotionEvent.ACTION_DOWN:
                                x1 = event.getX();
                                break;
                            case MotionEvent.ACTION_UP:
                                x2 = event.getX();
                                float deltaX = x2 - x1;
                                if (deltaX > MIN_DISTANCE)
                                {
                                    updateUI();
                                }
                                break;
                        }
                        return false;
                    });
                    destLayout.setOnClickListener(view -> Toast.makeText(GamePlay.this, "Swipe", Toast.LENGTH_SHORT).show());
                    destLayout.setOnTouchListener((view, event) -> {
                        switch(event.getAction())
                        {
                            case MotionEvent.ACTION_DOWN:
                                x1 = event.getX();
                                break;
                            case MotionEvent.ACTION_UP:
                                x2 = event.getX();
                                float deltaX = x1 - x2;
                                if (deltaX > MIN_DISTANCE)
                                {
                                    updateUI();
                                }
                                break;
                        }
                        return false;
                    });;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        },3000);
    }

    private void updateUI() {
        handAnim.setAnimationListener(null);
        hand.setVisibility(View.INVISIBLE);
        destLayout.setOnTouchListener(null);
        srcLayout.setOnTouchListener(null);
        srcLayout.setImageResource(R.drawable.candy3);
        destLayout.setImageResource(R.drawable.candy2);

        auxilary2.setImageResource(R.drawable.candy2);
        auxilary1.setImageResource(R.drawable.candy3);
        destLayout.setImageResource(R.drawable.candy2);

        auxilary2.setVisibility(View.INVISIBLE);
        auxilary1.setVisibility(View.INVISIBLE);
        destLayout.setVisibility(View.INVISIBLE);

        MediaPlayer mediaPlayer2 = MediaPlayer.create(GamePlay.this,R.raw.combo_sound);
        mediaPlayer2.start();
        MediaPlayer mediaPlayer3 = MediaPlayer.create(GamePlay.this,R.raw.candy_land1);
        MediaPlayer mediaPlayer4 = MediaPlayer.create(GamePlay.this,R.raw.candy_land1);
        MediaPlayer mediaPlayer5 = MediaPlayer.create(GamePlay.this,R.raw.candy_land1);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            mediaPlayer3.start();
            auxilary2.startAnimation(slideDown);
            auxilary2.setVisibility(View.VISIBLE);
        },300);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            mediaPlayer4.start();
            auxilary1.startAnimation(slideDown);
            auxilary1.setVisibility(View.VISIBLE);
        },600);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            mediaPlayer5.start();
            destLayout.startAnimation(slideDown);
            destLayout.setVisibility(View.VISIBLE);
        },900);
        tooltip2.startAnimation(slideRight);
        slideRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tooltip2.setVisibility(View.INVISIBLE);
                tooltip3.setVisibility(View.VISIBLE);
                tooltip3.startAnimation(slideLeft);
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    tooltip3.startAnimation(slideRight1);
                    tooltip3.setVisibility(View.INVISIBLE);
                },3000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!mediaPlayer.isPlaying()){
            mediaPlayer = MediaPlayer.create(this, R.raw.music_jelly_level);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
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