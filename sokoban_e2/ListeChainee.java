public class ListeChainee {
    node tete;
    node queue;

    public static void main(String[] args) {
        int[] integers = new int[] {1, 3, 5};
        ListeChainee LC = new ListeChainee(integers);
        System.out.println(LC.toString());
        System.out.println(LC.extraitTete());
        System.out.println(LC.toString());

    }

    ListeChainee() {
        this.tete = null;
        this.queue = null;
    }

    ListeChainee(node n) {
        this.tete = n;
        this.queue = n;
    }

    ListeChainee(int n) {
        insereTete(n);
        this.queue = this.tete;
    }

    ListeChainee(int[] v) {
        if (v.length > 0) {
            node n = new node(v[0]);
            this.tete = n;
            this.queue = n;
            for (int i = 1; i < v.length; i++) {
                this.insereQueue(v[i]);
            }
        } else {
            this.tete = null;
            this.queue = null;
        }

    }

    public void insereTete(int element) {
        node n = new node(element, this.tete);
        if (this.estVide()) {
            this.tete = n;
            this.queue = n;
        } else {
            this.tete = n;

        }
    }

    public void insereQueue(int element) {
        node n = new node(element, null);
        if (this.estVide()) {
            this.tete = n;
            this.queue = n;
            return;
        } else {
            this.queue.suivant = n;
            this.queue = n;
        }
    }

    public int extraitTete() {
        if (this.estVide()){
            throw new RuntimeException("Séquence vide");
        }
        node n = this.tete;
        this.tete = n.suivant;
        return n.valeur;
    }

    public boolean estVide() {
        return this.tete == null;
    }

    public String toString() {
        String s = "";
        node n = tete;
        while (n != null) {
            s += Integer.toString(n.valeur) + " => ";
            n = n.suivant;
        }
        s += "null";
        return s;
    }
}

class node {
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