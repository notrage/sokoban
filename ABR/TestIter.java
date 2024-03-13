import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestIter {
    public static void main(String[] args) {
        // Création d'une liste d'entiers
        List<Integer> liste = new ArrayList<>();
        liste.add(1);
        liste.add(2);
        liste.add(3);
        liste.add(4);
        liste.add(5);

        // Création de l'itérateur
        Iterator<Integer> iterateur = liste.iterator();

        // Affichage des éléments avant la suppression
        System.out.println("Éléments avant la suppression :");
        while (iterateur.hasNext()) {
            System.out.print(iterateur.next() + " ");
        }
        System.out.println();

        // Réinitialisation de l'itérateur
        iterateur = liste.iterator();

        // Suppression de l'élément 3
        while (iterateur.hasNext()) {
            int element = iterateur.next();
            if (element == 3) {
                iterateur.remove(); // Suppression de l'élément courant
                iterateur.remove();
            }
        }

        // Affichage des éléments après la suppression
        System.out.println("\nÉléments après la suppression :");
        for (Integer integer : liste) {
            System.out.print(integer + " ");
        }
        System.out.println();
    }
}
