package com.example.labyrinthe.Views;

public class Cellules {
    public boolean mur_gauche = true, mur_droite = true, mur_devant = true, mur_derriere = true;
    public int colonne, ligne;
    public Cellules[][] cellules;
    public static final int Colonnes = 8, Lignes = 12;
//    public Paint murs_colorer;
    public float taille_cellules, marge_vertical, marge_horizontal;

    public Cellules(int colonne, int ligne) {
        this.colonne = colonne;
        this.ligne = ligne;
    }

    private void creation_des_murs(){
        cellules = new Cellules[Colonnes][Lignes];
        for (int i=0; i<Colonnes; i++){
            for (int j=0; j<Lignes; j++){
                cellules[i][j] = new Cellules(i, j);
            }
        }
    }
}
