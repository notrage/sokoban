package Structures;
import java.util.ArrayList;

class Fap<E extends Comparable<E>> {
    ArrayList<E> nodes;
    int n_nodes;

    public static void main(String[] args) {
        Fap<Integer> F = new Fap<>();
        F.inserer(1);
        F.inserer(5);
        F.inserer(4);
        F.inserer(3);
        System.out.println(F);
        while (!F.estVide()){
            System.out.print(F.extraire() + " ");
        }
    }

    Fap(){
        nodes = new ArrayList<>();
        n_nodes = 0;
    }

    public Boolean estVide(){
        return n_nodes == 0;
    }

    public void inserer(E element){
        int i = 0;
        while (i < nodes.size() && nodes.get(i).compareTo(element) <= 0){
            i++;
        }
        n_nodes++;
        nodes.add(i,element);
    }

    public E prioritaire(){
        if (n_nodes == 0){
            throw new RuntimeException("Impossible de lire, file vide");
        }
        return nodes.get(0);
    }

    public E extraire(){
        if (n_nodes == 0){
            throw new RuntimeException("Impossible d'extraire, file vide");
        }
        n_nodes--;
        return nodes.remove(0);
    }

    public String toString(){
        String s = "";
        for (int i = 0; i < n_nodes; i++){
            s += nodes.get(i).toString() + " ";
        }
        return s;
    }
}


