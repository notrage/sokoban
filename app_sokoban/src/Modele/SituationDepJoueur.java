package Modele;

import java.awt.Point;
import java.util.HashMap;
import java.util.Objects;

public class SituationDepJoueur extends Situation {
    public SituationDepJoueur(HashMap<Point, Integer> pC, Point pousseur, int scoreHeuristique,
            int totalDeplacementsCaisses, HashMap<Point, int[]> nDJ, int totalDeplacementsJoueur) {
        super(pC, pousseur, scoreHeuristique, totalDeplacementsCaisses, nDJ, totalDeplacementsJoueur);
    }
    
    public SituationDepJoueur(Situation s){
        super(s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Situation that = (Situation) o;
        return Objects.equals(positionCaisses, that.positionCaisses) && Objects.equals(positionPousseur, that.positionPousseur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionCaisses, positionPousseur);
    }
    
}
