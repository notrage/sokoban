package Modele;

import java.awt.Point;

public class Coup {
    Point departPousseur;
    Point arriveePousseur;
    boolean aCaisse;
    boolean aMarque;
    Point arriveeCaisse;

    public Point departPousseur() {
        return departPousseur;
    }

    public Point arriveePousseur() {
        return arriveePousseur;
    }

    public Point departCaisse() {
        return arriveePousseur;
    }

    public Point arriveeCaisse() {
        return arriveeCaisse;
    }
    Coup(Point dP, Point aP, Point aC, boolean aCaisse, boolean marque){
        departPousseur = dP;
        arriveePousseur = aP;
        this.aCaisse = aCaisse;
        arriveeCaisse = aC;
        aMarque = marque;
    }

    Coup(Point dP, Point aP, Point aC) {
        this(dP, aP, aC, true, false);
    }

    Coup(Point dP, Point aP) {
        this(dP, aP, null, false, false);
    }

    public int direction() {
        switch (departPousseur.x - arriveePousseur.x) {
            case -1:
                return 2;
            case 1:
                return 0;
            default:
                if (departPousseur.y - arriveePousseur.y == 1) {
                    return 1;
                }
                return 3;
        }
    }

    public int directionX(){
        return arriveePousseur.x - departPousseur.x;
    }

    public int directionY(){
        return arriveePousseur.y - departPousseur.y;
    }

    public boolean aCaisse() {
        return aCaisse;
    }

    public String toString() {
        return departPousseur.x + "," + departPousseur.y + " => " + arriveePousseur.x + "," + arriveePousseur.y;
    }

    public boolean aMarque() {
        return aMarque;
    }
}
