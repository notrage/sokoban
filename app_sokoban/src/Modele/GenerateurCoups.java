package Modele;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class GenerateurCoups {
    Niveau niveau;
    Point pousseur;

    public GenerateurCoups(Niveau n) {
        niveau = n.clone();
        pousseur = new Point(niveau.pousseurL(), niveau.pousseurC());
    }

    public ArrayList<Coup> solver_minDeplacementCaisse() {
        Situation s = niveau.toSituation(0, 0, true);
        HashSet<Situation> situationsSet = new HashSet<>();
        
        // création de la file à priorité pour notre parcours de graphe de situation
        PriorityQueue<Situation> fap = new PriorityQueue<Situation>(
                (Situation s1, Situation s2) -> Integer.compare(
                        s1.scoreHeuristique(),
                        s2.scoreHeuristique()));
        fap.add(s);
        int n_sit = 0;
        while (!fap.isEmpty()) {
            s = fap.remove();
            if (!situationsSet.contains(s)) {
                situationsSet.add(s);
                System.out.print("\r" + n_sit);
                if (s.gagnante(niveau)) {
                    System.out.println("\rVictoire avec " + n_sit + " situations explorées en "
                            + s.totalDeplacementsJoueur + " deplacements de joueur et " + s.totalDeplacementsCaisses
                            + " deplacements de caisses");
                    return recuperationCoups(s);
                }
                n_sit++;
                Situation[] nextS = s.futurSituations(niveau, true);
                for (int i = 0; i < nextS.length; i++) {
                    if (nextS[i] != null) {
                        fap.add(nextS[i]);
                    }
                }
            }
        }
        System.out.println("\rDéfaite avec " + n_sit + " situations explorées");
        return null;
    }
    
    

    public ArrayList<Coup> solver_minDeplacementJoueur() {
        Situation s =  niveau.toSituation(0, 0, false);
        HashSet<Situation> situationsSet = new HashSet<>();
        HashMap<Situation, Integer> distance = new HashMap<>();
        // création de la file à priorité pour notre parcours de graphe de situation
        PriorityQueue<Situation> fap = new PriorityQueue<Situation>(
                (Situation s1, Situation s2) -> Integer.compare(
                        distance.get(s1) + s1.scoreHeuristique(),
                        distance.get(s2) + s2.scoreHeuristique()));
        distance.put(s, 0);
        fap.add(s);
        int n_sit = 0;
        while (!fap.isEmpty()) {
            s = fap.remove();
            if (!situationsSet.contains(s)) {
                situationsSet.add(s);
                System.out.print("\r" + n_sit);
                if (s.gagnante(niveau)) {
                    System.out.println("\rVictoire avec " + n_sit + " situations explorées en "
                            + s.totalDeplacementsJoueur + " deplacements de joueur et " + s.totalDeplacementsCaisses
                            + " deplacements de caisses");
                    return recuperationCoups(s);
                }
                n_sit++;
                Situation[] nextS =  s.futurSituations(niveau, false);
                for (int i = 0; i < nextS.length; i++) {
                    if (nextS[i] != null) { 
                        if (!distance.containsKey(nextS[i])){
                            distance.put(nextS[i], nextS[i].totalDeplacementsJoueur);
                            fap.add(nextS[i]);
                        } else if (distance.get(nextS[i]) > nextS[i].totalDeplacementsJoueur){
                            distance.put(nextS[i], nextS[i].totalDeplacementsJoueur);
                            fap.remove(nextS[i]);
                            fap.add(nextS[i]);
                        }
                    }
                }
            }
        }
        System.out.println("\rDéfaite avec " + n_sit + " situations explorées");
        return null;
    } 

    public ArrayList<Coup> solver_BFS() {
        Situation s = niveau.toSituation(0, 0, true);
        HashSet<Situation> situationsSet = new HashSet<>();
        Queue<Situation> f = new LinkedList<>();
        f.add(s);
        int n_sit = 0;
        while (!f.isEmpty()) {
            s = f.remove();
            if (!situationsSet.contains(s)) {
                situationsSet.add(s);
                System.out.print("\r" + n_sit);
                if (s.gagnante(niveau)) {
                    System.out.println("\rVictoire avec " + n_sit + " situations explorées en "
                            + s.totalDeplacementsJoueur + " deplacements de joueur et " + s.totalDeplacementsCaisses
                            + " deplacements de caisses");
                }
                n_sit++;
                Situation[] nextS = s.futurSituations(niveau, true);
                for (int i = 0; i < nextS.length; i++) {
                    if (nextS[i] != null) {
                        f.add(nextS[i]);
                    }
                }
            }
        }
        System.out.println("\rDéfaite avec " + n_sit + " situations explorées");
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
        Marque courant = new Marque(aP, new Point(aP.x + diffX, aP.y + diffY), 0);
        niveau.marques()[aP.x][aP.y] = courant;

        // on effectue un itération de la boucle while avant celle-ci
        // pour ajouter le dernier coup (qui effectue un déplacement de caisse)
        dP = courant.casePrecedente;
        listeCoups.add(new Coup(dP, aP, new Point(aP.x - diffX, aP.y - diffY)));

        courant = niveau.marques()[dP.x][dP.y];

        // on itère sur les cases précédentes pour ajouter les coups jusqu'à
        // l'emplacement du pousseur
        while (courant != null && courant.casePrecedente != null) {
            listeCoups.add(new Coup(courant.casePrecedente, courant.caseCourante));
            courant = niveau.marques()[courant.casePrecedente.x][courant.casePrecedente.y];
        }

        // Collections.reverse(listeCoups);

        return listeCoups;
    }
}
