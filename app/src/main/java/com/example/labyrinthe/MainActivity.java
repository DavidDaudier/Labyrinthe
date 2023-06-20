package com.example.labyrinthe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.labyrinthe.Views.Maze_easy;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Dialog maze_levels_dialog;
    private LinearLayout difficult_level, medium_level, easy_level, maze_level;
    ImageView img_maze, btn_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        LinearLayout btnPlay = (LinearLayout)findViewById(R.id.btnPlay);
//        LinearLayout text_play = (LinearLayout) findViewById(R.id.text_play);
        ImageView btn_play = (ImageView) findViewById(R.id.btn_play);
        ImageView text_play = (ImageView) findViewById(R.id.text_play);
        //maze_level = (LinearLayout) findViewById(R.id.maze_level);

        img_maze = findViewById(R.id.img_maze);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_animation);
        btn_play.startAnimation(animation);
        Animation animation_text_play = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_animation);
        text_play.startAnimation(animation);
        Animation animation_btnPlay = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwize);
        img_maze.startAnimation(animation_btnPlay);

//        Animation animation_btnPlay = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwize);
//        btnPlay.startAnimation(animation_btnPlay);
//        TextView btnExit = (TextView)findViewById(R.id.btnExit);

        btnPlay.setOnClickListener(this);
//        btnExit.setOnClickListener(this);
//        id_close.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
//            case R.id.btnExit:
//                finish();
//                break;

            case R.id.btnPlay:
//                Animation animation_level = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_maze_levels);
//                maze_level.startAnimation(animation_level);

                maze_levels_dialog = new Dialog(MainActivity.this);
                maze_levels_dialog.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
                maze_levels_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                maze_levels_dialog.setContentView(R.layout.maze_levels_dialog);

//                maze_levels_dialog.getWindow().getAttributes().windowAnimations = R.anim.zoom_in_maze_levels;

                easy_level = maze_levels_dialog.findViewById(R.id.easy_level);
                medium_level = maze_levels_dialog.findViewById(R.id.medium_level);
                difficult_level = maze_levels_dialog.findViewById(R.id.difficult_level);
                ImageView id_close = (ImageView) findViewById(R.id.id_close);

                ((ImageView) maze_levels_dialog.findViewById(R.id.id_close)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        maze_levels_dialog.dismiss();
                    }
                });

                easy_level.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent easy_level = new Intent(getApplicationContext(), Maze_easy.class);
                            startActivity(easy_level);
                            maze_levels_dialog.dismiss();
                    }
                });


                maze_levels_dialog.show();
                break;

        }
    }
}