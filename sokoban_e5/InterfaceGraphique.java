import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.*;

// L'interface runnable déclare une méthode run
public class InterfaceGraphique implements Runnable, KeyListener {
    Jeu jeu;
    Boolean maximized;
    JFrame frame;

    InterfaceGraphique(Jeu jeu) {
        this.jeu = jeu;
        maximized = false;
    }

    public void run() {
        // Creation d'une fenetre
        frame = new JFrame("Sokoban");

        // Ajout de notre composant de dessin dans la fenetre
        frame.add(new NiveauGraphique(jeu));

        // Un clic sur le bouton de fermeture clos l'application
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // On fixe la taille et on demarre
        frame.addKeyListener(this);
        frame.setSize(500, 300);
        frame.setVisible(true);
    }

    public void toggleFullscreen() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        if (maximized) {
            device.setFullScreenWindow(null);
            maximized = false;
        } else {
            device.setFullScreenWindow(frame);
            maximized = true;
        }
    }

    public void keyTyped(KeyEvent e) {

        if (e.getKeyChar() == 'n') {
            if (jeu.prochainNiveau()){
                frame.repaint();
            } else {
                frame.dispose();
            }
            
        }
        if (e.getKeyChar() == 'f') {
            toggleFullscreen();
        }
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <chemin_du_fichier>");
            System.exit(1);
        }

        String cheminFichier = args[0];
        try (InputStream inputStream = new FileInputStream(cheminFichier)) {
            Jeu jeu = new Jeu(inputStream);
            jeu.prochainNiveau();
            jeu.prochainNiveau();
            SwingUtilities.invokeLater(new InterfaceGraphique(jeu));
        } catch (FileNotFoundException e) {
            System.err.println("ERREUR : impossible de trouver le map");
            System.exit(2);
        } catch (IOException e) {
            System.err.println("ERREUR : impossible de charger le fichier map");
            System.exit(3);
        }
    }
}
