package com.example.labyrinthe.Views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.labyrinthe.R;


public class MazeWinAnimation extends Activity {
    LottieAnimationView mazeWinAnimation;
    LinearLayout LayoutMazeWinInfo;
    Button PlayAgain;
    Maze_level_easy maze_level_easy;

//    public Context context;
//    public AttributeSet attrs;

    public MazeWinAnimation() {
        // Required empty public constructor
    }

    public void onCreate (Bundle bundle) {
        super.onCreate(bundle);
        PlayAgain = findViewById(R.id.PlayAgain);
        mazeWinAnimation.setVisibility(View.VISIBLE);

        PlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maze_level_easy.creation_labyrinthe();
            }
        });

        setContentView(R.layout.fragment_maze_win_animation);
    }

//    @Override
//    public void setContentView(View view) {
//        (super.setContentView(view);)
//    }
    //    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_maze_win_animation, container, false);
//    }

//    @Override
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        //super.onCreate(savedInstanceState);
//        mazeWinAnimation = view.findViewById(R.id.mazeWin);
//        mazeWinAnimation.setVisibility(view.VISIBLE);
//        LayoutMazeWinInfo = view.findViewById(R.id.LayoutMazeWinInfo);
//        PlayAgain = view.findViewById(R.id.PlayAgain);
//        maze_level_easy = new Maze_level_easy(context, attrs);
//
//        PlayAgain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                maze_level_easy.creation_labyrinthe();
//            }
//        });
//    }

}