
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    private static final char VIDE = ' ';
    private static final char MUR = '#';
    private static final char BUT = '.';
    private static final char PUSHEUR = '@';
    private static final char CAISSE = '$';
    private static final char CAISSE_BUT = '*';
    private static final char PUSHEUR_BUT = '+';

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <chemin_du_fichier>");
            System.exit(1);
        }

        String cheminFichier = args[0];

        try (InputStream inputStream = new FileInputStream(cheminFichier)) {
            Jeu jeu = new Jeu(inputStream);
            while (jeu.prochainNiveau()) {
                Niveau niveau = jeu.niveau();
                if (niveau != null) {
                    System.out.println("Nom du niveau : " + niveau.nom());
                    System.out.println("Matrice du niveau : ");
                    afficherMatrice(niveau);
                } else {
                    System.out.println("Fin du fichier ou fichier vide.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void afficherMatrice(Niveau m) {
        for (int i = 0; i < m.lignes(); i++) {
            for (int j = 0; j < m.colonnes(); j++) {
                if (m.aCaisse(i, j) && m.aBut(i, j)) {
                    System.out.print(CAISSE_BUT);
                } else if (m.aPousseur(i, j) && m.aBut(i, j)) {
                    System.out.print(PUSHEUR_BUT);
                } else if (m.aCaisse(i, j)) {
                    System.out.print(CAISSE);
                } else if (m.estVide(i, j)) {
                    System.out.print(VIDE);
                } else if (m.aMur(i, j)) {
                    System.out.print(MUR);
                } else if (m.aPousseur(i, j)) {
                    System.out.print(PUSHEUR);
                } else if (m.aBut(i, j)) {
                    System.out.print(BUT);
                }
            }
            System.out.println();
        }
    }
}
