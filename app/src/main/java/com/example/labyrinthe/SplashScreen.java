package com.example.labyrinthe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    TextView textView;
    ProgressBar progressBar;
    Handler handler;
    int progressCount = 0;
//    ImageView img_maze;
    LinearLayout imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        textView = (TextView) findViewById(R.id.textView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        img_maze = findViewById(R.id.img_maze);




        imageView = (LinearLayout) findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_animation);
        imageView.startAnimation(animation);


//        Animation animation_btnPlay = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwize);
//        img_maze.startAnimation(animation_btnPlay);
//        Animation animation_btnPlay = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwize);
//        imageView.startAnimation(animation_btnPlay);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progressbar));
                if (progressBar.getProgress() < 100){
                    progressBar.setProgress(progressCount);
                    progressCount++;
                    handler.postDelayed(this, 150); //100
                    textView.setText("\t\t\t\tPlease wait...\n\tSearching Data game\n\t\t\t\t\t\t "+progressCount+"%");

//                    if (progressBar.getProgress() < 65){
//                        textView.setText("Please wait...\nData recuperation...\n\t\t "+progressCount+"%");
//                    }
//                    else if (progressBar.getProgress() < 88){
//                        textView.setText("Please wait...\nData deploying...\n\t\t "+progressCount+"%");
//                    }


                }



                else {
                    textView.setText("Completed Data");
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 100);
    }
}