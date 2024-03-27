package Modele;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class GenerateurCoups {
    Niveau niveau;
    Point pousseur;

    public GenerateurCoups(Niveau n) {
        niveau = n.clone();
        pousseur = new Point(niveau.pousseurL(), niveau.pousseurC());
    }

    public ArrayList<Coup> solver_BFS() {
        Situation s = niveau.toSituation();
        HashSet<Situation> situationsSet = new HashSet<>();
        Queue<Situation> f = new LinkedList<>();
        f.add(s);
        int n_sit = 0;
        while (!f.isEmpty()) {
            s = f.remove();
            // System.out.println(s);
            if (!situationsSet.contains(s)) {
                // System.out.println(situationsMap);
                situationsSet.add(s);
                if (s.gagnante(niveau)) {
                    System.out.println("Victoire avec " + n_sit + " situations explorées");
                    return recuperationCoups(s);
                }
                n_sit++;
                Situation[] nextS = s.futurSituations(niveau);
                for (int i = 0; i < nextS.length; i++) {
                    if (nextS[i] != null) {
                        f.add(nextS[i]);
                    }
                }
            }
        }
        System.out.println("Défaite avec " + n_sit + " situations explorées");
        return null;
    }

    public ArrayList<Coup> recuperationCoups(Situation s) {
        ArrayList<Coup> coups = new ArrayList<>();
        while (s.pere != null) {
            ArrayList<Coup> tmp = pathFromTo(s.pere, s);
            coups.addAll(tmp);
            s = s.pere;

        }
        Collections.reverse(coups);
        return coups;

    }

    public ArrayList<Coup> pathFromTo(Situation sitDebut, Situation sitFin) {

        Point dP = null;
        Point aP = null;
        ArrayList<Coup> listeCoups = new ArrayList<Coup>();

        // on marque les cases acessibles à partir de la position du pousseur
        HashSet<Point> tmp = niveau.caisses;
        niveau.caisses = new HashSet<>(sitDebut.positionCaisses.keySet());
        niveau.marqueAccessibles(sitDebut.pousseur().x, sitDebut.pousseur().y);
        niveau.caisses = tmp; 
        // on récupère les positions des caisses à la fin et au début
        HashMap<Point, Integer> caissesDep = sitDebut.positionCaisses;
        HashMap<Point, Integer> caissesArr = sitFin.positionCaisses;

        // On cherche la caisse qui a bougé pour trouver la position du joueur à la fin
        for (Point p : caissesDep.keySet()) {
            if (caissesArr.get(p) == null)
                aP = p;
        }

        for (Point p : caissesArr.keySet()) {
            if (caissesDep.get(p) == null)
                dP = p;
        }

        // On cherche la position du joueur à la fin
        int diffX = aP.x - dP.x;
        int diffY = aP.y - dP.y;
        Marque courant = new Marque(aP, new Point(aP.x + diffX, aP.y + diffY));
        niveau.marques()[aP.x][aP.y] = courant;

        // on effectue un itération de la boucle while avant celle-ci
        // pour ajouter le dernier coup (qui effectue un déplacement de caisse)
        dP = courant.casePrecedente;
        listeCoups.add(new Coup(dP, aP, new Point(aP.x - diffX, aP.y - diffY)));

        courant = niveau.marques()[dP.x][dP.y];

        // on itère sur les cases précédentes pour ajouter les coups jusqu'à
        // l'emplacement du pousseur
        while (courant != null && courant.casePrecedente != null) {
            listeCoups.add(new Coup(courant.casePrecedente,  courant.caseCourante));
            courant = niveau.marques()[courant.casePrecedente.x][courant.casePrecedente.y];
        }

        //Collections.reverse(listeCoups);

        return listeCoups;
    }
}
