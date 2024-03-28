public class Coup {
    int ancienne_valeur, nouvelle_valeur;
    int position_x, position_y;

    Coup(int ancienne_v, int nouvelle_v, int p_x, int p_y) {
        ancienne_valeur = ancienne_v;
        nouvelle_valeur = nouvelle_v;
        position_x = p_x;
        position_y = p_y;
    }

    public String toString() {
        return ancienne_valeur + " " + nouvelle_valeur + " " + position_x + " " + position_y;
    }

}
