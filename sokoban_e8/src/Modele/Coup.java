package Modele;

import java.awt.Point;

public class Coup {
    Point departPousseur;
    Point arriveePousseur;
    boolean aCaisse;
    Point arriveeCaisse;

    Point departPousseur(){
        return departPousseur;
    }

    Point arriveePousseur(){
        return arriveePousseur;
    }

    Point departCaisse(){
        return arriveePousseur;
    }

    Point arriveeCaisse(){
        return arriveeCaisse;
    }

    Coup(Point dP, Point aP, Point aC){
        departPousseur = dP;
        arriveePousseur = aP;
        aCaisse = true;
        arriveeCaisse = aC;
    }

    Coup(Point dP, Point aP){
        departPousseur = dP;
        arriveePousseur = aP;
        aCaisse = false;
    }

    public boolean aCaisse(){
        return aCaisse;
    }

    public String toString(){
        return departPousseur.x + "," + departPousseur.y + " => " + arriveePousseur.x + "," + arriveePousseur.y ;
    }
}
