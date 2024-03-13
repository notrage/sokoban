
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class LecteurNiveaux {
    private static final char VIDE = ' ';
    private Scanner scanner;

    public LecteurNiveaux(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
    }

    public Niveau lisProchainNiveau() {
        ArrayList<String> lignesMap = new ArrayList<>();
        String lastComment = "";
        int col_size = 0;
        int line_size = 0;

        while (scanner.hasNextLine()) {
            String ligne = scanner.nextLine();

            if (ligne.isEmpty()) {
                break;
            }
            line_size += 1;
            int i;
            for (i = 0; i < ligne.length(); i++) {
                if (ligne.charAt(i) == ';') {
                    lastComment = ligne.substring(i + 1).trim();
                    break;
                }
                col_size = col_size < i + 1 ? i + 1 : col_size;
            }

            lignesMap.add(ligne.substring(0, i));
        }

        if (line_size > 0 && col_size > 0) {
            char[][] tableauNiveau = convertirListEnTableau(lignesMap, line_size, col_size);
            return new Niveau(lastComment, tableauNiveau);
        }

        return null;
    }

    private char[][] convertirListEnTableau(ArrayList<String> liste, int lignes, int colonnes) {
        char[][] tableau = new char[lignes][colonnes];

        for (int i = 0; i < liste.size(); i++) {
            String ligne = liste.get(i);

            // Assurez-vous que la longueur de la ligne ne dépasse pas les colonnes
            int longueurLigne = Math.min(colonnes, ligne.length());

            // Remplir les colonnes avec les caractères de la ligne
            for (int j = 0; j < longueurLigne; j++) {
                tableau[i][j] = ligne.charAt(j);
            }

            // Remplir les colonnes manquantes avec des caractères VIDE
            for (int j = longueurLigne; j < colonnes; j++) {
                tableau[i][j] = VIDE;
            }
        }

        return tableau;
    }
}
