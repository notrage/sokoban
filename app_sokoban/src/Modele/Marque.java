package Modele;

import java.awt.Point;

public class Marque {
    Point casePrecedente;
    Point caseCourante;

    Marque(int i, int j){
        this(new Point(i, j), null);
    }

    Marque(int i, int j, int old_i, int old_j){
        casePrecedente = new Point(old_i,old_j);
        caseCourante = new Point(i,j);

    }

    Marque(Point p, Point p_prec){
        casePrecedente = p_prec;
        caseCourante = p;
    }
}
