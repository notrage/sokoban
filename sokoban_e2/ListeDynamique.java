public class ListeDynamique {
    int[] integers;
    int tete;
    int queue;
    int n_elem;

    ListeDynamique() {
        this.integers = new int[1];
        this.tete = 0;
        this.queue = 0;
        this.n_elem = 0;
    }

    public static void main(String[] args) {
        ListeDynamique L = new ListeDynamique();
        L.insereQueue(0);
        L.insereQueue(1);
        L.insereTete(-1);
        L.insereQueue(2);
        L.insereQueue(3);
        L.insereQueue(4);
        L.insereQueue(5);
        L.insereTete(-2);
        L.insereTete(-3);
        L.insereTete(-4);
        System.out.println(L.toString());
    }

    public void insereTete(int element) {
        
        if (this.n_elem  == this.integers.length) {
            this.resize();
        }
        this.tete = (tete - 1) % this.integers.length;
        if (tete < 0) {
            tete += this.integers.length;
        }
        this.integers[this.tete] = element;
        this.n_elem++;
    }

    public void insereQueue(int element) {
        if (this.n_elem  == this.integers.length) {
            this.resize();
        }
        this.integers[this.queue] = element;
        this.queue = (queue + 1) % this.integers.length;
        this.n_elem++;
    }

    public int extraitTete() {
        if (this.estVide()) {
            throw new RuntimeException("Séquence vide");
        }
        int n = this.integers[this.tete];
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
            s += " " + Integer.toString(this.integers[i]);
            i = (i + 1) % this.integers.length;
            j++;
        }
        return s;
    }

    private void resize() {
        int[] new_n = new int[this.integers.length * 2];
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
}
