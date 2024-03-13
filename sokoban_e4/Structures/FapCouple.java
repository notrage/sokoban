package Structures;
import java.util.ArrayList;

class FapCouple<E> {
    ArrayList<Couple<E>> nodes;
    int n_nodes;

    public static void main(String[] args) {
        FapCouple<Integer> F = new FapCouple<>();
        F.inserer(1, 7);
        F.inserer(2, 8);
        F.inserer(4, 9);
        F.inserer(3, 10);
        System.out.println(F);
        while (!F.estVide()){
            System.out.print(F.extraire() + " ");
        }
    }

    FapCouple(){
        nodes = new ArrayList<>();
        n_nodes = 0;
    }

    public Boolean estVide(){
        return n_nodes == 0;
    }

    public void inserer(E element, int priority){
        int i = 0;
        while (i < nodes.size() && nodes.get(i).getPriority() > priority){
            i++;
        }
        n_nodes++;
        nodes.add(i,new Couple<E>(element, priority));
    }

    public E prioritaire(){
        if (n_nodes == 0){
            throw new RuntimeException("Impossible de lire, file vide");
        }
        return nodes.get(0).getData();
    }

    public E extraire(){
        if (n_nodes == 0){
            throw new RuntimeException("Impossible d'extraire, file vide");
        }
        n_nodes--;
        return nodes.remove(0).getData();
    }

    public String toString(){
        String s = "";
        for (int i = 0; i < n_nodes; i++){
            s += nodes.get(i).toString() + " ";
        }
        return s;
    }
}


class Couple<F> {
    F data;
    int priority;

    Couple(F e, int priority){
        this.data = e;
        this.priority = priority;
    }

    public int getPriority(){
        return this.priority;
    }

    public F getData(){
        return this.data;
    }

    public String toString(){
        return this.data + ":" + this.priority;
    }
}