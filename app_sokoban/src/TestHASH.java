import java.util.HashMap;
import java.util.Map;
import java.awt.Point;

import Modele.Situation;

public class TestHASH {
    public static void main(String[] args) {
        // Création de deux ensembles de points pour les caisses
        HashMap<Point, Integer> positionCaisses1 = new HashMap<>();
        positionCaisses1.put(new Point(1, 2), 1);
        positionCaisses1.put(new Point(3, 4), 2);
                                
        HashMap<Point, Integer> positionCaisses2 = new HashMap<>();
        positionCaisses2.put(new Point(3, 4), 2);
        positionCaisses2.put(new Point(1, 2), 1);

        // Création de deux tableaux d'entiers pour les positions futures des caisses
        // Création de deux objets Situation
        Situation situation1 = new Situation(positionCaisses1, new Point(0, 0));
        Situation situation2 = new Situation(positionCaisses2,  new Point(0, 0));
        situation1.pere = situation2;
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
