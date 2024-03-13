
public class ListeChainee {
    node tete;
    node queue;

    public static void main(String[] args) {
        int[] integers = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        int x;
        ListeChainee LC = new ListeChainee(integers);
        Iterateur it = LC.iterateur();
        while (it.aProchain()) {
            System.out.print((x = it.prochain()) + " ");
            if (x % 2 == 1 && it.aProchain()) {
                it.supprime();
            }
        }
        System.out.println();
        ListeDynamique LD = new ListeDynamique();
        for (int i = 1; i <= 10; i++) LD.insereQueue(i);
        Iterateur IT = LD.iterateur();
        while (IT.aProchain()) {
            System.out.print((x = IT.prochain()) + " ");
            if (x % 2 == 1 && IT.aProchain()) {
                IT.supprime();
            }
        }
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
        if (this.estVide()) {
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

    public Iterateur iterateur() {
        return new IterateurListeChainee(this);
    }
}
