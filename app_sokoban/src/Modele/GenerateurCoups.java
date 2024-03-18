package Modele;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class GenerateurCoups {
    Niveau niveau;

    public GenerateurCoups(Niveau n) {
        // niveau = n.clone();
        niveau = n.clone();
    }

    public boolean solver() {
        Situation s = niveau.toSituation();
        HashMap<Situation, Boolean> situationsMap = new HashMap<>();
        Queue<Situation> f = new LinkedList<>();
        f.add(s);
        int n_sit = 0;
        while (!f.isEmpty()) {
            s = f.remove();
            //System.out.println(s);
            if (situationsMap.get(s) == null || situationsMap.get(s) == false) {
                //System.out.println(situationsMap);
                situationsMap.put(s, true);
                if (s.gagnante(niveau)) {
                    System.out.println("GG: " + s);
                    return true;
                }
                System.out.println("Nouvelle situation : " + n_sit++);
                Situation[] nextS = s.futurSituations(niveau);
                for (int i = 0; i < nextS.length; i++) {
                    if (nextS[i] != null) {
                        f.add(nextS[i]);
                    }
                }
            }
        }
        System.out.println("pas GG");
        return false;
    }

    public ArrayList<Coup> pathFromTo(Point dP) {
        ArrayList<Coup> listeCoups = new ArrayList<Coup>();
        return listeCoups;
    }
}
