import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.awt.Point;

import Modele.Situation;

public class TestHASH {
    public static void main(String[] args) {
        // Création de deux ensembles de points pour les caisses
        HashSet<Point> positionCaisses1 = new HashSet<>();
        positionCaisses1.add(new Point(1, 2));
        positionCaisses1.add(new Point(3, 4));

        HashSet<Point> positionCaisses2 = new HashSet<>();
        positionCaisses2.add(new Point(3, 4));
        positionCaisses2.add(new Point(1, 2));

        // Création de deux tableaux d'entiers pour les positions futures des caisses
        int[] positionFuturCaisses1 = { 1, 2 };
        int[] positionFuturCaisses2 = { 1, 2 };

        // Création de deux objets Situation
        Situation situation1 = new Situation(positionCaisses1, positionFuturCaisses1);
        Situation situation2 = new Situation(positionCaisses2, positionFuturCaisses2);

        // Création d'une HashMap qui utilise Situation comme clé
        Map<Situation, String> map = new HashMap<>();

        // Ajout des situations à la HashMap
        map.put(situation1, "Situation 1");
        map.put(situation2, "Situation 2");

        // Affichage de la taille de la HashMap pour démontrer que les deux situations
        // sont considérées comme égales
        System.out.println(map);
        System.out.println("Taille de la HashMap: " + map.size()); // Doit afficher 1
    }

}
