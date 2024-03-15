package Modele;

import java.awt.Point;
import java.util.HashSet;

public class Situation {
    HashSet<Point> positionCaisses;
    int[] positionFuturCaisses;

    Situation(HashSet<Point> pC, int[] pFC) {
        positionCaisses = new HashSet<>();
        for (Point p : pC){
            positionCaisses.add(p);
        }
        positionFuturCaisses = pFC;
    }

    public Situation[] futurSituations(Niveau n) {
        int[][] directions = { { 0, -1 }, { -1, 0 }, { 0, 1 }, { 1, 0 } };
        Situation[] fS = new Situation[positionFuturCaisses.length * 4];
        int idx = 0;
        int i = 0;
        for (Point caisse : positionCaisses) {
            for (int j = 0; j < 4; j++) {
                if ((positionFuturCaisses[i] & (int) Math.pow(2, j)) > 0) {
                    HashSet<Point> tmp = n.caisses;
                    n.caisses = positionCaisses;
                    Point arriveCaisse = new Point(caisse.x + directions[j][0],
                            caisse.y + directions[j][1]);
                    Point departJoueur = new Point(caisse.x - directions[j][0],
                            caisse.y - directions[j][1]);
                    Point arriveJoueur = caisse;
                    Coup c = new Coup(departJoueur, arriveJoueur, arriveCaisse);
                    n.jouerCoup(c, false);
                    fS[idx] = n.toSituation();
                    idx++;
                    n.annulerDernierCoup();
                    n.caisses = tmp;
                }
            }
            i++;
        }
        return fS;
    }

    public String toString() {
        String s = "";
        int i = 0;
        for (Point caisse : positionCaisses) {
            s += caisse + " => " + positionFuturCaisses[i] + "\n";
            i++;
        }
        return s;
    }
}
