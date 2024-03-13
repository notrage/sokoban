package Vue;
import javax.swing.*;

import Controleur.AdaptateurClavier;
import Controleur.AdaptateurSouris;
import Modele.Jeu;
import java.awt.*;

public class InterfaceGraphique implements Runnable {
	Jeu jeu;
	boolean maximized;
	JFrame frame;
	NiveauGraphique ng;

	InterfaceGraphique(Jeu j) {
		jeu = j;
	}

	public static void demarrer(Jeu j) {
		SwingUtilities.invokeLater(new InterfaceGraphique(j));
	}

	public void run() {
		frame = new JFrame("Sokoban");
		ng = new NiveauGraphique(jeu);
		frame.add(ng);
		ng.addMouseListener(new AdaptateurSouris(ng, jeu));
		frame.addKeyListener(new AdaptateurClavier(this, jeu));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	public void repaint() {
		ng.repaint();
	}
}
