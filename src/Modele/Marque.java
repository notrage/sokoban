package Modele;

import java.awt.Point;

public class Marque {
    Point casePrecedente;
    Point caseCourante;
    int distance;

    Marque(int i, int j, int d) {
        this(new Point(i, j), null, d);
    }

    Marque(int i, int j, int old_i, int old_j, int d) {
        this(new Point(old_i, old_j), new Point(i, j), d);
    }

    Marque(Point p, Point p_prec, int d) {
        casePrecedente = p_prec;
        caseCourante = p;
        distance = d;
    }
}
