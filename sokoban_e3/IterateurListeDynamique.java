public class IterateurListeDynamique implements Iterateur {
    ListeDynamique liste;
    int position;

    IterateurListeDynamique(ListeDynamique l) {
        liste = l;
        position = l.tete;
    }

    public Boolean aProchain() {
        return position < liste.n_elem;
    }

    @Override
    public int prochain() {
        if (!aProchain()) {
            throw new RuntimeException("Pas d'élément suivant");
        }
        int valeur = liste.integers[position];
        position = (position + 1) % liste.integers.length;
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