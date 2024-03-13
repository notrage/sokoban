package Structures;
public class ListeDynamique<E> {
    Object[] integers;
    int tete;
    int queue;
    int n_elem;

    ListeDynamique() {
        this.integers = new Object[4];
        this.tete = 0;
        this.queue = 0;
        this.n_elem = 0;
    }

    public void insereTete(E element) {

        if (this.n_elem == this.integers.length) {
            this.resize();
        }
        this.tete = (tete - 1) % this.integers.length;
        if (tete < 0) {
            tete += this.integers.length;
        }
        this.integers[this.tete] = element;
        this.n_elem++;
    }

    public void insereQueue(E element) {
        if (this.n_elem == this.integers.length) {
            this.resize();
        }
        this.integers[this.queue] = element;
        this.queue = (queue + 1) % this.integers.length;
        this.n_elem++;
    }

    public E extraitTete() {
        if (this.estVide()) {
            throw new RuntimeException("Séquence vide");
        }
        @SuppressWarnings("unchecked")
        E n = (E) this.integers[this.tete];
        this.tete = (tete + 1) % this.integers.length;
        this.n_elem--;
        return n;
    }

    public Boolean estVide() {
        return this.n_elem == 0;
    }

    public String toString() {
        String s = "";
        int i = this.tete;
        int j = 0;
        while (j != this.n_elem) {
            s += " " + this.integers[i].toString();
            i = (i + 1) % this.integers.length;
            j++;
        }
        return s;
    }

    private void resize() {
        Object[] new_n = new Object[this.integers.length * 2];
        int tmp = this.n_elem;
        int i = 0;
        while (!this.estVide()) {
            new_n[i] = this.extraitTete();
            i++;
        }
        this.tete = 0;
        this.integers = new_n;
        this.queue = i;
        this.n_elem = tmp;
    }

    public Iterateur iterateur() {
        return new IterateurListeDynamique<>(this);
    }
}
