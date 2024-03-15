package Vue;
import javax.swing.*;

import Controleur.*;
import Modele.Jeu;
import java.awt.*;

public class InterfaceGraphique implements Runnable {
	Jeu jeu;
	boolean maximized;
	JFrame frame;
	NiveauGraphique ng;
	Animation animationPousseur;
	Animation animationCaisse;
	AnimationPousseur changementImagePousseur;
	AnimationJeuAutomatique ia;

	InterfaceGraphique(Jeu j) {
		jeu = j;
	}

	public static void demarrer(Jeu j) {
		SwingUtilities.invokeLater(new InterfaceGraphique(j));
	}

	public void run() {
		frame = new JFrame("Sokoban");
		animationPousseur = new Animation(this);
		animationCaisse = new Animation(this);
		changementImagePousseur = new AnimationPousseur(this);
		ia = new AnimationJeuAutomatique(this, jeu, animationPousseur, animationCaisse);
		ng = new NiveauGraphique(jeu, animationPousseur, animationCaisse, changementImagePousseur, ia);
		frame.add(ng);
		frame.addMouseListener(new AdaptateurSouris(this, jeu, animationPousseur, animationCaisse));
		frame.addKeyListener(new AdaptateurClavier(this, jeu, animationPousseur, animationCaisse));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1920, 1080);
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
	public NiveauGraphique ng(){
		return ng;
	}

	public void repaint() {
		ng.repaint();
	}

	public void toggleAnimation(){
		ng.toggleAnimation();
	}

	public void toggleIA(){
		ng.toggleIA();
	}
}
