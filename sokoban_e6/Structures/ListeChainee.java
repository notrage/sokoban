package Structures;
public class ListeChainee<E> {
    node<E> tete;
    node<E> queue;

    public static void main(String[] args) {
        ListeChainee<Integer> LC = new ListeChainee<>();
        LC.insereQueue(1);
        LC.insereQueue(2);
        LC.insereQueue(3);

        Iterateur it = LC.iterateur();
        while (it.aProchain()) {
            System.out.print(it.prochain().toString() + " ");
        }
        System.out.println();
        
        ListeDynamique<String> LD = new ListeDynamique<>();
        LD.insereQueue("Super");
        LD.insereQueue("Type");
        LD.insereQueue("Generique");
        LD.insereQueue("!");

        Iterateur IT = LD.iterateur();
        while (IT.aProchain()) {
            System.out.print(IT.prochain() + " ");
        }
    
    }

    ListeChainee() {
        this.tete = null;
        this.queue = null;
    }

    ListeChainee(E n) {
        insereTete(n);
        this.queue = this.tete;
    }

    ListeChainee(E[] v) {
        if (v.length > 0) {
            node<E> n = new node<>(v[0]);
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

    public void insereTete(E element) {
        node<E> n = new node<>(element, this.tete);
        if (this.estVide()) {
            this.tete = n;
            this.queue = n;
        } else {
            this.tete = n;

        }
    }

    public void insereQueue(E element) {
        node<E> n = new node<>(element, null);
        if (this.estVide()) {
            this.tete = n;
            this.queue = n;
            return;
        } else {
            this.queue.suivant = n;
            this.queue = n;
        }
    }

    public Object extraitTete() {
        if (this.estVide()) {
            throw new RuntimeException("Séquence vide");
        }
        node<E> n = this.tete;
        this.tete = n.suivant;
        return n.valeur;
    }

    public boolean estVide() {
        return this.tete == null;
    }

    public String toString() {
        String s = "";
        node<E> n = tete;
        while (n != null) {
            s += n.valeur.toString() + " => ";
            n = n.suivant;
        }
        s += "null";
        return s;
    }

    public Iterateur iterateur() {
        return new IterateurListeChainee<>(this);
    }
}
