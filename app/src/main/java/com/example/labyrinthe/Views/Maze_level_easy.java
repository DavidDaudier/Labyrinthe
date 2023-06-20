package com.example.labyrinthe.Views;


import static androidx.core.content.ContextCompat.startActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.labyrinthe.R;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Maze_level_easy extends View {

    private Dialog maze_win;
    private Paint murs_colorer, dessiner_entree, dessiner_sortir;
    private Cellules[][] cellules;
    private Random mur_aleatoire;
    private static final int Colonnes = 10, Lignes = 16;
    private static final float size_mur = 4;
    private enum Direction{DEVANT, DERRIERE, DROIT, GAUCHE}
    public Cellules joueur, entree;
    public Cellules sortir;
    private float taille_cellules, marge_vertical, marge_horizontal;

    private SensorManager sensorManager;
    private Sensor sensor;
    LottieAnimationView mazeWin;
    //public Context context;

    MazeWinAnimation mazeWinAnimation;
    //public View contentView;

    public void getSystemService(String sensorService) {

    }

    public Maze_level_easy(Context context) {
        super(context);
//        this.context = context;
//        LayoutInflater inflater = LayoutInflater.from(context);
//        contentView = inflater.inflate(R.layout.fragment_maze_win_animation, this, false);
    }

    public Maze_level_easy(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //this.context = context;

        murs_colorer = new Paint();
        mazeWinAnimation = new MazeWinAnimation();
        murs_colorer.setColor(Color.parseColor("#0091ea"));
        murs_colorer.setStrokeWidth(size_mur);

        dessiner_entree = new Paint();
        dessiner_entree.setColor(Color.RED);

        dessiner_sortir = new Paint();
        dessiner_sortir.setColor(Color.GREEN);

        mur_aleatoire = new Random();

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sensor_register();
//        maze_win();

//        LayoutInflater inflater = LayoutInflater.from(context);
//        contentView = inflater.inflate(R.layout.fragment_maze_win_animation, this, false);

        creation_labyrinthe();


    }



//    private void maze_win(){
//        maze_win = new Dialog(M);
//        maze_win.requestWindowFeature(getWindowVisibility());
//        maze_win.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        maze_win.setContentView(R.layout.maze_win);
//    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
                float rx = event.values[0], ry = event.values[1];
                if (rx > 3.0f){
                    deplacement_joueur(Direction.DROIT); // deplacer a droite
                }else if (rx < -3.0f){
                    deplacement_joueur(Direction.GAUCHE);
                }
                if (ry > 3.0f){
                    deplacement_joueur(Direction.DEVANT);
                }else if (ry < -3.0f){
                    deplacement_joueur(Direction.DERRIERE);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    // methode registerSensor
    public void sensor_register(){
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //methode unregisterSensor
    public void sensor_unregister(){
        sensorManager.unregisterListener(sensorEventListener);
    }




    protected void onDraw(Canvas canvas){
//        canvas.drawPicture(Picture picture);
        canvas.drawColor(Color.parseColor("#1b1d2a"));
        int largeur_cellule = getWidth();
        int hauteur_cellule = getHeight();
//        int largeur_cellule = 700;
//        int hauteur_cellule = 400;
        float marge = taille_cellules / 4;

        if ((largeur_cellule / hauteur_cellule) < (Colonnes / Lignes)){
            taille_cellules = largeur_cellule / (Colonnes + 1);
        }else{
            taille_cellules = hauteur_cellule / (Lignes + 1);
        }

        marge_horizontal = (largeur_cellule - Colonnes * taille_cellules) / 2;
        marge_vertical = (hauteur_cellule - Lignes * taille_cellules) / 2;
        canvas.translate(marge_horizontal, marge_vertical);

        for (int i=0; i<Colonnes; i++){
            for (int j=0; j<Lignes; j++){
                if(cellules[i][j].mur_devant){
                    canvas.drawLine(
                            i * taille_cellules,
                            j * taille_cellules,
                            (i+1) * taille_cellules,
                            j * taille_cellules,
                            murs_colorer
                    );
                }
                if(cellules[i][j].mur_gauche){
                    canvas.drawLine(
                            i * taille_cellules,
                            j * taille_cellules,
                            i * taille_cellules,
                            (j+1) * taille_cellules,
                            murs_colorer
                    );
                }
                if(cellules[i][j].mur_derriere){
                    canvas.drawLine(
                            i * taille_cellules,
                            (j+1) * taille_cellules,
                            (i+1) * taille_cellules,
                            (j+1) * taille_cellules,
                            murs_colorer
                    );
                }
                if(cellules[i][j].mur_droite){
                    canvas.drawLine(
                            (i+1) * taille_cellules,
                            j * taille_cellules,
                            (i+1) * taille_cellules,
                            (j+1) * taille_cellules,
                            murs_colorer
                    );
                }
            }

            //Dessiner Adversaire
            canvas.drawRect(
                    entree.colonne * taille_cellules + marge,
                    entree.ligne * taille_cellules + marge,
                    (entree.colonne+1) * taille_cellules - marge,
                    (entree.ligne+1) * taille_cellules - marge,
                    dessiner_entree
            );

            // Dessiner Sortir
            canvas.drawRect(
                    sortir.colonne * taille_cellules + marge,
                    sortir.ligne * taille_cellules + marge,
                    (sortir.colonne+1) * taille_cellules - marge,
                    (sortir.ligne+1) * taille_cellules - marge,
                    dessiner_sortir
            );
        }


//        View rootView = inflater.inflate(R.layout.fragment_maze_win_animation, this, false);

    }


    private void deplacement_joueur(Direction direction){
        switch (direction){

            case DEVANT:
                if (!entree.mur_devant){
                    entree = cellules[entree.colonne][entree.ligne-1];
                }
                break;

            case DERRIERE:
                if (!entree.mur_derriere){
                    entree = cellules[entree.colonne][entree.ligne+1];
                }
                break;

            case GAUCHE:
                if (!entree.mur_gauche){
                    entree = cellules[entree.colonne-1][entree.ligne];
                }
                break;

            case DROIT:
                if (!entree.mur_droite){
                    entree = cellules[entree.colonne+1][entree.ligne];
                }
                break;
        }
        sortir();
        invalidate();
    }



    private void sortir(){
        if (entree == sortir){


        //FragmentManager fragmentManager = getSupportFragmentManager();
//            Intent intentWin = new Intent(this.getContext(), MazeWinAnimation.class);
//
//            mazeWinAnimation = new MazeWinAnimation();

//            mazeWin = findViewById(R.id.mazeWin);
//            mazeWin.setVisibility(VISIBLE);
            //maze_win = new Dialog(Maze_level_easy.this.getContext());
            //maze_win.requestWindowFeature(getWindowVisibility());
//            maze_win.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            maze_win.setContentView(R.layout.maze_win);
            //creation_labyrinthe();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN){
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE){
            float Axe_abscisse = event.getX();
            float Axe_ordonner = event.getY();

            float _position_Axe_abscisse_joueur = marge_horizontal + (entree.colonne + 0.5f) * taille_cellules;
            float _position_Axe_ordonner_joueur = marge_vertical + (entree.ligne + 0.5f) * taille_cellules;

            float _distance_X = Axe_abscisse - _position_Axe_abscisse_joueur;
            float _distance_Y = Axe_ordonner - _position_Axe_ordonner_joueur;

            float _valeur_absolue_X = Math.abs(_distance_X);
            float _valeur_absolue_Y =  Math.abs(_distance_Y);

            if (_valeur_absolue_X > taille_cellules || _valeur_absolue_Y > taille_cellules){

                // Deplacement dans direction horizontale
                if (_valeur_absolue_X > _valeur_absolue_Y){

                    // Deplacement vers la droite
                    if (_distance_X > 0){
                        deplacement_joueur(Direction.DROIT);
                    }

                    // Deplacement vers la gauche
                    else{
                        deplacement_joueur(Direction.GAUCHE);
                    }
                }

                // Deplacement dans direction verticale
                else {

                    // Deplacement vers le bas
                    if (_distance_Y > 0){
                        deplacement_joueur(Direction.DERRIERE);
                    }

                    // Deplacement vers le haut
                    else {
                        deplacement_joueur(Direction.DEVANT);
                    }
                }
            }
            return true;
        }

        return super.onTouchEvent(event);
    }


    private class Cellules {
        boolean
                mur_gauche = true,
                mur_droite = true,
                mur_devant = true,
                mur_derriere = true,
                case_a_supprimer = false;

        int colonne, ligne;
        public Cellules(int colonne, int ligne) {
            this.colonne = colonne;
            this.ligne = ligne;
        }

    }

    private Cellules prochainCellule(Cellules cells){
        ArrayList<Cellules> cellulesArrayList = new ArrayList<>();

        // Suppression mur a Gauche
        if (cells.colonne > 0){
            if (!cellules[cells.colonne - 1][cells.ligne].case_a_supprimer){
                cellulesArrayList.add(cellules[cells.colonne - 1][cells.ligne]);
            }
        }

        // Suppression mur a Droite
        if (cells.colonne < Colonnes - 1){
            if (!cellules[cells.colonne + 1][cells.ligne].case_a_supprimer){
                cellulesArrayList.add(cellules[cells.colonne + 1][cells.ligne]);
            }
        }

        // Suppression mur a Devant
        if (cells.ligne > 0){
            if (!cellules[cells.colonne][cells.ligne - 1].case_a_supprimer){
                cellulesArrayList.add(cellules[cells.colonne][cells.ligne - 1]);
            }
        }

//        // Suppression mur a Derriere
        if (cells.ligne < Lignes - 1){
            if (!cellules[cells.colonne][cells.ligne + 1].case_a_supprimer){
                cellulesArrayList.add(cellules[cells.colonne][cells.ligne + 1]);
            }
        }

        if (cellulesArrayList.size() > 0){
            int position_mur = mur_aleatoire.nextInt(cellulesArrayList.size());
            return cellulesArrayList.get(position_mur);
        }
        return null;
    }

    private void suppression_mur(Cellules Cell_avant, Cellules Cell_apres){

        if (Cell_avant.colonne == Cell_apres.colonne && Cell_avant.ligne == Cell_apres.ligne+1){
            Cell_avant.mur_devant = false;
            Cell_apres.mur_derriere = false;
        }

        if (Cell_avant.colonne == Cell_apres.colonne && Cell_avant.ligne == Cell_apres.ligne-1){
            Cell_avant.mur_derriere = false;
            Cell_apres.mur_devant = false;
        }

        if (Cell_avant.colonne == Cell_apres.colonne+1 && Cell_avant.ligne == Cell_apres.ligne){
            Cell_avant.mur_gauche = false;
            Cell_apres.mur_droite = false;
        }

        if (Cell_avant.colonne == Cell_apres.colonne-1 && Cell_avant.ligne == Cell_apres.ligne){
            Cell_avant.mur_droite = false;
            Cell_apres.mur_gauche = false;
        }
    }


    public void creation_labyrinthe(){

        Stack<Cellules> stack = new Stack<>();
        Cellules Cell_avant, Cell_apres;

        cellules = new Cellules[Colonnes][Lignes];

        for (int i=0; i<Colonnes; i++){
            for (int j=0; j<Lignes; j++){
                cellules[i][j] = new Cellules(i, j);
            }
        }
//        joueur = cellules[0][0];
        entree = cellules[0][0];
        sortir = cellules[Colonnes-1][Lignes-1];

        Cell_avant = cellules[0][0];
        Cell_avant.case_a_supprimer = true;

        //
        do {
            Cell_apres = prochainCellule(Cell_avant);

            if (Cell_apres != null){
                suppression_mur(Cell_avant, Cell_apres);
                stack.push(Cell_avant);
                Cell_avant = Cell_apres;
                Cell_avant.case_a_supprimer = true;
            }
            else {
                Cell_avant = stack.pop();
            }
        }while (!stack.empty());

    }

    public void MazeWinAnimation(){
//        Intent intent = new Intent(getContext(), MazeWinAnimation());
    }

}
