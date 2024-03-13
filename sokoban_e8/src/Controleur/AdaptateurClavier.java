package Controleur;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import Modele.Jeu;
import Vue.InterfaceGraphique;

public class AdaptateurClavier extends KeyAdapter {
	InterfaceGraphique inter;
	Jeu jeu;
	Animation animations;

	public AdaptateurClavier(InterfaceGraphique i, Jeu j, Animation a) {
		inter = i;
		jeu = j;
		animations = a;
	}

	void deplace(int l, int c) {
		if (!animations.animationEnCours()) {
			Point depart = new Point(jeu.pousseurC(), jeu.pousseurL());
			jeu.deplace(l, c);
			Point arrivee = new Point(jeu.pousseurC(), jeu.pousseurL());
			if (!depart.equals(arrivee)) {
				animations.nouvelleAnimation(depart, arrivee);
			}
			inter.repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				deplace(-1, 0);
				break;
			case KeyEvent.VK_DOWN:
				deplace(1, 0);
				break;
			case KeyEvent.VK_LEFT:
				deplace(0, -1);
				break;
			case KeyEvent.VK_RIGHT:
				deplace(0, 1);
				break;
			case KeyEvent.VK_A:
			case KeyEvent.VK_Q:
				System.exit(0);
			case KeyEvent.VK_ESCAPE:
				inter.toggleFullscreen();
				break;
			case KeyEvent.VK_DELETE:
			case KeyEvent.VK_BACK_SPACE:
				jeu.annulerDernierCoup();
				inter.repaint();
				break;
			case KeyEvent.VK_ENTER:
				jeu.redoCoup();
				inter.repaint();
				break;
		}
	}
}
