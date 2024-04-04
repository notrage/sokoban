package Controleur;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import Modele.Coup;
import Modele.Jeu;
import Modele.Niveau;
import Vue.InterfaceGraphique;

public class AdaptateurClavier extends KeyAdapter {
	InterfaceGraphique inter;
	Jeu jeu;
	Animation animationPousseur, animationCaisse;

	public AdaptateurClavier(InterfaceGraphique i, Jeu j, Animation pousseur, Animation caisse) {
		inter = i;
		jeu = j;
		animationPousseur = pousseur;
		animationCaisse = caisse;
	}

	void deplace(int l, int c) {
		Coup dernierCoup;
		if (!animationPousseur.animationEnCours() && !animationCaisse.animationEnCours()) {
			Niveau tmp = jeu.niveau();
			if (jeu.deplace(l, c) && (dernierCoup = jeu.dernierCoup()) != null) {
				if (tmp == jeu.niveau()) {
					animationPousseur.nouvelleAnimation(dernierCoup.departPousseur(), dernierCoup.arriveePousseur());
					if (dernierCoup.aCaisse()) {
						animationCaisse.nouvelleAnimation(dernierCoup.departCaisse(), dernierCoup.arriveeCaisse());
					}
					inter.repaint();
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (inter.ng().isIArunning()) {
			if (e.getKeyCode() == KeyEvent.VK_I || e.getKeyCode() == KeyEvent.VK_T || e.getKeyCode() == KeyEvent.VK_Y
					|| e.getKeyCode() == KeyEvent.VK_U) {
				inter.toggleIA(-1);
			}
			return;
		}
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
				break;
			case KeyEvent.VK_ENTER:
				jeu.redoCoup();
				break;
			case KeyEvent.VK_W:
				inter.toggleAnimation();
				break;
			case KeyEvent.VK_T:
				inter.toggleIA(0);
				break;
			case KeyEvent.VK_Y:
				inter.toggleIA(1);
				break;
			case KeyEvent.VK_U:
				inter.toggleIA(2);
				break;
			case KeyEvent.VK_M:
				jeu.marquer(jeu.pousseurL(), jeu.pousseurC());
				break;
			case KeyEvent.VK_R:
				jeu.resetMarques();
				break;
			case KeyEvent.VK_L:
				jeu.niveau().marqueAccessibles(jeu.pousseurL(), jeu.pousseurC());
				break;
			case KeyEvent.VK_C:
				System.out.println(jeu.niveau().positionCaisses());
				break;
			case KeyEvent.VK_N:
				jeu.prochainNiveau();
				break;
			case KeyEvent.VK_H:
				jeu.afficherHeuristique();
				break;
		}
		inter.repaint();
	}
}
