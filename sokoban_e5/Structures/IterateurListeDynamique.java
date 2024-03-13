package Structures;
public class IterateurListeDynamique<E> implements Iterateur {
    ListeDynamique<E> liste;
    int position;
    int n_traverser;
    IterateurListeDynamique(ListeDynamique<E> l) {
        liste = l;
        position = l.tete;
        n_traverser = 0;
    }

    public Boolean aProchain() {
        return n_traverser < liste.n_elem;
    }

    @Override
    public E prochain() {
        if (!aProchain()) {
            throw new RuntimeException("Pas d'élément suivant");
        }
        @SuppressWarnings("unchecked")
        E valeur = (E) liste.integers[position];
        position = (position + 1) % liste.integers.length;
        n_traverser++;
        return valeur;
    }

    @Override
    public void supprime() {
        int i = position;
        while (i != liste.queue){
            liste.integers[i] = liste.integers[(i+1)% liste.integers.length];
            i = (i+1) % liste.integers.length;
        }
        liste.n_elem--;
        liste.queue = (liste.queue - 1) %  liste.integers.length;
    }
}