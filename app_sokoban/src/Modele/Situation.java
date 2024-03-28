package Modele;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class Situation {
    HashMap<Point, Integer> positionCaisses;
    public Situation pere; 
    public Point positionPousseur;
    int scoreHeuristique;
    int nombreDeplacementsCaisses;
    int nombreDeplacementsJoueuers;

    public Situation(HashMap<Point, Integer> pC, Point pousseur, int scoreHeuristique) {
        positionCaisses = pC;
        positionPousseur = pousseur;
        pere = null;
        this.scoreHeuristique = scoreHeuristique;
    }
    public Situation getPere(){
        return pere;
    }

    public Point pousseur(){
        return positionPousseur;
    }

    public int scoreHeuristique() {
        return scoreHeuristique;
    }
    
    public Situation[] futurSituations(Niveau n) {
        int[][] directions = { { 0, -1 }, { -1, 0 }, { 0, 1 }, { 1, 0 } };
        Situation[] fS = new Situation[positionCaisses.size() * 4];
        int idx = 0;
        for (Map.Entry<Point, Integer> entry : positionCaisses.entrySet()) {
            Point caisse = entry.getKey();
            int value = entry.getValue();
            for (int j = 0; j < 4; j++) {
                if ((value & (1 << j)) > 0) {
                    HashSet<Point> tmp = n.caisses;
                    n.caisses = new HashSet<>(positionCaisses.keySet());
                    Point arriveCaisse = new Point(caisse.x + directions[j][0],
                            caisse.y + directions[j][1]);
                    Point departJoueur = new Point(caisse.x - directions[j][0],
                            caisse.y - directions[j][1]);
                    Point arriveJoueur = caisse;
                    Coup c = new Coup(departJoueur, arriveJoueur, arriveCaisse);
                    n.jouerCoup(c, false);
                    fS[idx] = n.toSituation();
                    fS[idx].pere = this;
                    idx++;
                    n.annulerDernierCoup();
                    n.caisses = tmp;
                }
            }
        }
        return fS;
    }

    public boolean gagnante(Niveau n) {
        for (Point p : positionCaisses.keySet()) {
            if (!n.aBut(p.x, p.y)) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        String s = "";
        for (Map.Entry<Point, Integer> entry : positionCaisses.entrySet()) {
            s += entry.getKey() + " => " + entry.getValue() + "\n";
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Situation that = (Situation) o;
        return Objects.equals(positionCaisses, that.positionCaisses);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(positionCaisses);
    }
}
