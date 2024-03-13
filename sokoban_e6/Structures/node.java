package Structures;
public class node<E> {
    E valeur;
    node<E> suivant;

    node(E valeur) {
        this.valeur = valeur;
        this.suivant = null;
    }

    node(E valeur, node<E> suivant) {
        this.valeur = valeur;
        this.suivant = suivant;
    }
}