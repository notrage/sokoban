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
    int totalDeplacementsCaisses;
    int totalDeplacementsJoueur;
    HashMap<Point, int[]> nbDeplacementJoueur;

    public Situation(HashMap<Point, Integer> pC, Point pousseur, int scoreHeuristique,
            int totalDeplacementsCaisses, HashMap<Point, int[]> nDJ, int totalDeplacementsJoueur) {
        positionCaisses = pC;
        positionPousseur = pousseur;
        pere = null;
        this.scoreHeuristique = scoreHeuristique;
        this.totalDeplacementsCaisses = totalDeplacementsCaisses;
        nbDeplacementJoueur = nDJ;
        this.totalDeplacementsJoueur = totalDeplacementsJoueur;
    }

    public Situation(Situation s){
        this(s.positionCaisses, s.positionPousseur, s.scoreHeuristique, s.totalDeplacementsCaisses, s.nbDeplacementJoueur, s.totalDeplacementsJoueur);
    }

    public Situation getPere() {
        return pere;
    }

    public Point pousseur() {
        return positionPousseur;
    }

    public int scoreHeuristique() {
        return scoreHeuristique;
    }

    public int totalDeplacementsJoueur() {
        return totalDeplacementsJoueur;
    }

    public Situation[] futurSituations(Niveau n, boolean isCaisseSituation ) {
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
                    fS[idx] = n.toSituation(totalDeplacementsCaisses + 1,
                            totalDeplacementsJoueur + nbDeplacementJoueur.get(caisse)[j] + 1, isCaisseSituation);
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
            s += entry.getKey() + " => " + entry.getValue();
        }
        s += " en " + totalDeplacementsJoueur + " deplacement(s) du joueur et " + totalDeplacementsCaisses +" deplacement(s) de caisses\n";
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Situation that = (Situation) o;
        return Objects.equals(positionCaisses, that.positionCaisses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionCaisses);
    }

    public SituationDepCaisse toSituationMinDeplacement(){
        return new SituationDepCaisse(this);
    }
}
