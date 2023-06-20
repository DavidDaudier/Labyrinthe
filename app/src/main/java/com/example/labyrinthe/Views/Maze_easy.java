package com.example.labyrinthe.Views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.example.labyrinthe.R;

public class Maze_easy extends Activity {

    private Dialog maze_win, maze_quit;
    LinearLayout btn_maybe, btn_stay;
    //LottieAnimationView mazeWin;
    Maze_level_easy maze_level_easy;
    Context context;
    @Nullable AttributeSet attrs;


    //MazeWinAnimation mazeWinAnimation;
    public void onCreate (Bundle bundle) {
        super.onCreate(bundle);
       // maze_level_easy = new Maze_level_easy(context);

//        mazeWinAnimation = findViewById(R.id.mazeWin);
//        mazeWinAnimation.setVisibility(view.VISIBLE);
//        LayoutMazeWinInfo = findViewById(R.id.LayoutMazeWinInfo);
//        PlayAgain = findViewById(R.id.PlayAgain);
        // maze_level_easy = new Maze_level_easy(context, attrs);

//        PlayAgain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                maze_level_easy.creation_labyrinthe();
//            }
//        });

        setContentView(R.layout.maze_level_easy);

//        if (maze_level_easy.entree == maze_level_easy.sortir){
//            Intent intent = new Intent(getApplicationContext(), MazeWinAnimation.class);
//            startActivity(intent);
//        }

    }


//    @Override
//    private void maze_win(){
//        maze_win = new Dialog(Maze_easy.this);
//        maze_win.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
//        maze_win.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        maze_win.setContentView(R.layout.maze_win);
//    }

    @Override
    public void onBackPressed(){
        maze_quit = new Dialog(Maze_easy.this);
        maze_quit.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        maze_quit.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        maze_quit.setContentView(R.layout.maze_quit);

        btn_maybe = maze_quit.findViewById(R.id.btn_maybe);
        btn_stay = maze_quit.findViewById(R.id.btn_stay);
//        difficult_level = maze_levels_dialog.findViewById(R.id.difficult_level);
        ImageView id_close = (ImageView) findViewById(R.id.id_close);

        ((ImageView) maze_quit.findViewById(R.id.id_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maze_quit.dismiss();
            }
        });

        btn_maybe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                        Intent display_easy = new Intent();
//                        startActivity(display_easy);
//                Intent easy_level = new Intent(getApplicationContext(), Maze_easy.class);
//                startActivity(easy_level);
                finish();

            }
        });

        btn_stay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                        Intent display_easy = new Intent();
//                        startActivity(display_easy);
//                Intent easy_level = new Intent(getApplicationContext(), Maze_easy.class);
//                startActivity(easy_level);
                maze_quit.dismiss();
//                finish();
            }
        });





        maze_quit.show();
//        break;
    }


//    @Override
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        //super.onCreate(savedInstanceState);
//
//    }
}
