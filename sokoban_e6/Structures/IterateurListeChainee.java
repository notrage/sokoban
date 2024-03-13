package Structures;
public class IterateurListeChainee<E> implements Iterateur {
    ListeChainee<E> liste;
    node<E> position;

    IterateurListeChainee(ListeChainee<E> liste) {
        this.liste = liste;
        this.position = this.liste.tete;
    }

    public Boolean aProchain() {
        return (this.position != null);
    }

    public E prochain() {
        if (!aProchain()) {
            throw new RuntimeException("Pas d'élément suivant");
        }
        E valeur = position.valeur;
        position = position.suivant;
        return valeur;
    }

    public void supprime() {
        if (position == null) {
            throw new RuntimeException("Impossible de supprimer, séquence vide");
        }

        if (position == liste.tete) {
            liste.tete = liste.tete.suivant;
        } else {
            node<E> prec = null;
            node<E> current = liste.tete;

            while (current != null && current != position) {
                prec = current;
                current = current.suivant;
            }

            if (current == null) {
                throw new RuntimeException("Erreur : position de l'itérateur non trouvée");
            }

            prec.suivant = position.suivant;
        }

        position = position.suivant;
    }
}