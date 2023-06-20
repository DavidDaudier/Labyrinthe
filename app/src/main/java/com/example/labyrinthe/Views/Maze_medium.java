package com.example.labyrinthe.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.labyrinthe.R;

public class Maze_medium extends Activity {

    public void onCreate (Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Maze_level_medium  maze_level_medium = new Maze_level_medium(this, getResources().getLayout(R.layout.maze_level_medium));
//        Cellules cells = (Cellules)extras.get("cells");
//        LabyrintheVue vue = new LabyrintheVue(this, (AttributeSet) cells);
//        Maze maze = (Maze)extras.get("maze");
//        GameView view = new GameView(this,maze);
        setContentView(maze_level_medium);
    }
}
