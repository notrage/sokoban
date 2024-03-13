public class node {
    int valeur;
    node suivant;

    node(int valeur) {
        this.valeur = valeur;
        this.suivant = null;
    }

    node(int valeur, node suivant) {
        this.valeur = valeur;
        this.suivant = suivant;
    }
}