package Modele;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class GenerateurCoups {
    Niveau niveau;
    
    public GenerateurCoups(Niveau n) {
        //niveau = n.clone();
        niveau = n;
    }

    public void solver() {
        Situation s = niveau.toSituation();
        HashMap<Situation, Boolean> situationsMap = new HashMap<>();
        Queue<Situation> f = new LinkedList<>();
        f.add(s);
        while (!f.isEmpty()){
            s = f.remove();
            System.out.println(s);
            if (situationsMap.get(s) == null || situationsMap.get(s) == false){
                situationsMap.put(s, true);
                Situation[] nextS = s.futurSituations(niveau);
                for (int i = 0; i < nextS.length; i++){
                    if (nextS[i] != null){
                        f.add(nextS[i]);
                    }
                }
            }
        }
    }

    public ArrayList<Coup> pathFromTo(Point dP) {
        ArrayList<Coup> listeCoups = new ArrayList<Coup>();

        return listeCoups;
    }
}
