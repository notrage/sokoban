import java.awt.Point;

public class Niveau {
    private static final char VIDE = ' ';
    private static final char MUR = '#';
    private static final char BUT = '.';
    private static final char PUSHEUR = '@';
    private static final char CAISSE = '$';
    private static final char CAISSE_BUT = '*';
    private static final char PUSHEUR_BUT = '+';

    private char[][] mapMatrix;
    private String nom;
    public Point position_joueur;

    public Niveau(String nom, char[][] m) {
        position_joueur = new Point();
        this.mapMatrix = new char[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                switch (m[i][j]) {
                    case VIDE:
                        this.mapMatrix[i][j] = VIDE;
                        break;
                    case MUR:
                        this.mapMatrix[i][j] = MUR;
                        break;
                    case BUT:
                        this.mapMatrix[i][j] = BUT;
                        break;
                    case PUSHEUR:
                        this.mapMatrix[i][j] = VIDE;
                        position_joueur.x = i;
                        position_joueur.y = j;
                        break;
                    case PUSHEUR_BUT:
                        this.mapMatrix[i][j] = BUT;
                        position_joueur.x = i;
                        position_joueur.y = j;
                        break;
                    case CAISSE:
                        this.mapMatrix[i][j] = CAISSE;
                        break;
                    case CAISSE_BUT:
                        this.mapMatrix[i][j] = CAISSE_BUT;
                        break;
                    default:
                        throw new Error("Invalid map ! with char: " + m[i][j]);
                }
            }
        }
        this.mapMatrix = m;
        this.nom = nom;
    }

    public void fixeNom(String s) {
        this.nom = s;
    }

    public void videCase(int i, int j) {
        if (estCaseValide(i, j)) {
            this.mapMatrix[i][j] = VIDE;
        }
    }

    public void ajouteMur(int i, int j) {
        if (estCaseValide(i, j)) {
            this.mapMatrix[i][j] = MUR;
        }
    }

    public void ajoutePousseur(int i, int j) {
        if (estCaseValide(i, j)) {
            if (this.aBut(i, j))
                this.mapMatrix[i][j] = PUSHEUR_BUT;
            else
                this.mapMatrix[i][j] = PUSHEUR;
        }
    }

    public void ajouteCaisse(int i, int j) {
        if (estCaseValide(i, j)) {
            if (this.aBut(i, j))
                this.mapMatrix[i][j] = CAISSE_BUT;
            else
                this.mapMatrix[i][j] = CAISSE;
        }
    }

    public void ajouteBut(int i, int j) {
        if (estCaseValide(i, j)) {
            if (this.aPousseur(i, j))
                this.mapMatrix[i][j] = PUSHEUR_BUT;
            else if (this.aBut(i, j))
                this.mapMatrix[i][j] = CAISSE_BUT;
            else
                this.mapMatrix[i][j] = BUT;
        }
    }

    public int lignes() {
        return mapMatrix.length;
    }

    public int colonnes() {
        return mapMatrix[0].length;
    }

    public String nom() {
        return nom;
    }

    public boolean estVide(int l, int c) {
        return estCaseValide(l, c) && mapMatrix[l][c] == VIDE;
    }

    public boolean aMur(int l, int c) {
        return estCaseValide(l, c) && mapMatrix[l][c] == MUR;
    }

    public boolean aBut(int l, int c) {
        return estCaseValide(l, c)
                && (mapMatrix[l][c] == BUT || mapMatrix[l][c] == PUSHEUR_BUT || mapMatrix[l][c] == CAISSE_BUT);
    }

    public boolean aPousseur(int l, int c) {
        return estCaseValide(l, c) && position_joueur.x == l && position_joueur.y == c;
    }

    public boolean aCaisse(int l, int c) {
        return estCaseValide(l, c) && (mapMatrix[l][c] == CAISSE || mapMatrix[l][c] == CAISSE_BUT);
    }

    private boolean estCaseValide(int x, int y) {
        return x >= 0 && x < mapMatrix.length && y >= 0 && y < mapMatrix[0].length;
    }

    public boolean niveauFini() {
        for (int i = 0; i < this.lignes(); i++) {
            for (int j = 0; j < this.colonnes(); j++) {
                if (aCaisse(i, j) && !aBut(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean deplacement(int x, int y) {
        if (Math.sqrt(Math.pow(x - position_joueur.x, 2) + Math.pow(y - position_joueur.y, 2)) == 1) {
            if (estVide(x, y) || (aBut(x, y) && !aCaisse(x, y))) {
                position_joueur.x = x;
                position_joueur.y = y;
            } else if (aCaisse(x, y)) {
                if (position_joueur.x - 1 == x) {
                    if (estVide(x, y) || (aBut(x, y) && !aCaisse(x, y))) {
                        position_joueur.x = x;
                        position_joueur.y = y;
                        
                    }
                }
            }
        }
        return true;
    }
}
