package com.example.labyrinthe.Views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Maze_level_medium extends View {

    private Paint murs_colorer, dessiner_joueur, dessiner_entree, dessiner_sortir;
    private Cellules[][] cellules;
    private Random mur_aleatoire;
    private static final int Colonnes = 16, Lignes = 16;
    private Cellules joueur, entree, sortir;
    private enum Direction{DEVANT, DERRIERE, DROIT, GAUCHE}
    private float taille_cellules, marge_vertical, marge_horizontal;
    private TextView tv_gagnee;


    public Maze_level_medium(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        murs_colorer = new Paint();
        murs_colorer.setColor(Color.parseColor("#eed9c6"));
        murs_colorer.setStrokeWidth(11);

//        dessiner_joueur.getShadowLayerColor(Color.parseColor("@0d3200"));


//        dessiner_joueur = new Paint();
//        dessiner_joueur.setColor(Color.BLUE);


        dessiner_entree = new Paint();
        dessiner_entree.setColor(Color.RED);

        dessiner_sortir = new Paint();
        dessiner_sortir.setColor(Color.GREEN);


        mur_aleatoire = new Random();

        creation_labyrinthe();
    }

    private void sortir(){
        if (joueur == sortir){

//            System.out.println("Vous avez gagne");

//            System.out.println("Presser \"ENTER\" pour commencer rejouer");

            creation_labyrinthe();
        }
    }

    private void deplacement_joueur(Direction direction){
        switch (direction){

            case DEVANT:
                if (!joueur.mur_devant){
                    joueur = cellules[joueur.colonne][joueur.ligne-1];
                }
                break;

            case DERRIERE:
                if (!joueur.mur_derriere){
                    joueur = cellules[joueur.colonne][joueur.ligne+1];
                }
                break;

            case GAUCHE:
                if (!joueur.mur_gauche){
                    joueur = cellules[joueur.colonne-1][joueur.ligne];
                }
                break;

            case DROIT:
                if (!joueur.mur_droite){
                    joueur = cellules[joueur.colonne+1][joueur.ligne];
                }
                break;
        }
        sortir();
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN){
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE){
            float Axe_abscisse = event.getX();
            float Axe_ordonner = event.getY();

            float _position_Axe_abscisse_joueur = marge_horizontal + (joueur.colonne + .5f) * taille_cellules;
            float _position_Axe_ordonner_joueur = marge_vertical + (joueur.ligne + .5f) * taille_cellules;
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

    protected void onDraw(Canvas canvas){
//        canvas.drawPicture(Picture picture);
        canvas.drawColor(Color.parseColor("#869b00"));
        int largeur_cellule = getWidth();
        int hauteur_cellule = getHeight();
        float marge = taille_cellules / 4;

        if ((largeur_cellule / hauteur_cellule) < (Colonnes / Lignes)){
            taille_cellules = largeur_cellule / (Colonnes + 1);
        }else{
            taille_cellules = hauteur_cellule / (Lignes + 1);
        }

        marge_horizontal = (largeur_cellule - Colonnes * taille_cellules) / 2;
        marge_vertical = (hauteur_cellule - Lignes * taille_cellules)/ 2;
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

            // Dessiner Joueur
//            canvas.drawCircle(
//                    joueur.colonne * taille_cellules,
//                    joueur.ligne * taille_cellules,
//                    25,
//                    dessiner_joueur
//            );


            // Dessiner Joueur
//            canvas.drawRect(
//                    joueur.colonne * taille_cellules + marge,
//                    joueur.ligne * taille_cellules + marge,
//                    (joueur.colonne+1) * taille_cellules - marge,
//                    (joueur.ligne+1) * taille_cellules - marge,
//                    dessiner_joueur
//            );

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
            if (!cellules[cells.colonne-1][cells.ligne].case_a_supprimer){
                cellulesArrayList.add(cellules[cells.colonne-1][cells.ligne]);
            }
        }

        // Suppression mur a Droite
        if (cells.colonne < Colonnes-1){
            if (!cellules[cells.colonne+1][cells.ligne].case_a_supprimer){
                cellulesArrayList.add(cellules[cells.colonne+1][cells.ligne]);
            }
        }

        // Suppression mur a Devant
        if (cells.ligne > 0){
            if (!cellules[cells.colonne][cells.ligne-1].case_a_supprimer){
                cellulesArrayList.add(cellules[cells.colonne][cells.ligne-1]);
            }
        }

//        // Suppression mur a Derriere
        if (cells.ligne < Lignes-1){
            if (!cellules[cells.colonne][cells.ligne+1].case_a_supprimer){
                cellulesArrayList.add(cellules[cells.colonne][cells.ligne+1]);
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

    private void creation_labyrinthe(){

        Stack<Cellules> stack = new Stack<>();
        Cellules Cell_avant, Cell_apres;

        cellules = new Cellules[Colonnes][Lignes];

        for (int i=0; i<Colonnes; i++){
            for (int j=0; j<Lignes; j++){
                cellules[i][j] = new Cellules(i, j);
            }
        }
        joueur = cellules[1][0];
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
}
