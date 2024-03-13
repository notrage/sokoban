import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class NiveauGraphique extends JComponent {
    Jeu jeu;
    int counter;
    Image but;
    Image caisse_sur_but;
    Image caisse;
    Image mur;
    Image pousseur;
    Image sol;
    
    public NiveauGraphique(Jeu jeu) {
        try {
            this.jeu = jeu;
            but = ImageIO.read(new FileInputStream("img/But.png"));
            caisse_sur_but = ImageIO.read(new FileInputStream("img/Caisse_sur_but.png"));
            caisse = ImageIO.read(new FileInputStream("img/Caisse.png"));
            mur = ImageIO.read(new FileInputStream("img/Mur.png"));
            pousseur = ImageIO.read(new FileInputStream("img/Pousseur.png"));
            sol = ImageIO.read(new FileInputStream("img/Sol.png"));
        } catch (FileNotFoundException e) {
            System.err.println("ERREUR : impossible de trouver les fichiers d'images");
            System.exit(2);
        } catch (IOException e) {
            System.err.println("ERREUR : impossible de charger l'image");
            System.exit(3);
        }
        counter = 1;
    }
    
    public void paintComponent(Graphics g) {
        System.out.println("Entree dans paintComponent : " + counter++);
        Graphics2D drawable = (Graphics2D) g;
        int width = getSize().width;
        int height = getSize().height;
        System.out.println("width: " + width + " height:" + height);
        Niveau niveau = jeu.niveau();
        int n_lignes = niveau.lignes();
        int n_colonnes = niveau.colonnes();
		drawable.clearRect(0, 0, width, height);
        int tuile_size = Math.min(height / n_colonnes, width / n_lignes);
        drawable.clearRect(0, 0, width, height);
        for (int i = 0; i < n_lignes; i++) {
            for (int j = 0; j < n_colonnes; j++) {
                int x = tuile_size * i;
                int y = tuile_size * j;
                if (niveau.aCaisse(i, j) && niveau.aBut(i, j)) {
                    drawable.drawImage(caisse_sur_but, x, y, tuile_size, tuile_size, null);
                } else if (niveau.aPousseur(i, j) && niveau.aBut(i, j)) {
                    drawable.drawImage(but, x, y, tuile_size, tuile_size, null);
                    drawable.drawImage(pousseur, x, y, tuile_size, tuile_size, null);
                } else if (niveau.aCaisse(i, j)) {
                    drawable.drawImage(caisse, x, y, tuile_size, tuile_size, null);
                } else if (niveau.estVide(i, j)) {
                    drawable.drawImage(sol, x, y, tuile_size, tuile_size, null);
                } else if (niveau.aMur(i, j)) {
                    drawable.drawImage(mur, x, y, tuile_size, tuile_size, null);
                } else if (niveau.aPousseur(i, j)) {
                    drawable.drawImage(sol, x, y, tuile_size, tuile_size, null);
                    drawable.drawImage(pousseur, x, y, tuile_size, tuile_size, null);
                } else if (niveau.aBut(i, j)) {
                    drawable.drawImage(but, x, y, tuile_size, tuile_size, null);
                }
            }
        }
    }
}
