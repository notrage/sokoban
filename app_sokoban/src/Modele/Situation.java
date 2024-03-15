package Modele;

import java.awt.Point;

public class Situation {
    Point[] positionCaisses;
    int[] positionFuturCaisses;

    Situation(Point[] pC, int[] pFC) {
        positionCaisses = pC;
        positionFuturCaisses = pFC;
    }

    public Situation[] futurSituations(Niveau n) {
        int[][] directions = { { 0, -1 }, { -1, 0 }, { 0, 1 }, { 1, 0 } };
        Situation[] fS = new Situation[positionFuturCaisses.length * 4];
        int idx = 0;
        for (int i = 0; i < positionCaisses.length; i++) {
            for (int j = 0; j < 4; j++) {
                if ((positionFuturCaisses[i] & (int) Math.pow(2, j)) > 0) {
                    Point arriveCaisse = new Point(positionCaisses[i].x + directions[j][0],
                            positionCaisses[i].y + directions[j][1]);
                    Point departJoueur = new Point(positionCaisses[i].x - directions[j][0],
                            positionCaisses[i].y - directions[j][1]);
                    Point arriveJoueur = positionCaisses[i];
                    Coup c = new Coup(departJoueur, arriveJoueur, arriveCaisse);
                    n.jouerCoup(c, false);
                    fS[idx] = n.toSituation();
                    idx++;
                    n.annulerDernierCoup();
                }
            }
        }
        return fS;
    }
}
