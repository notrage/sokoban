package Controleur;

import Global.Configuration;
import Modele.Coup;
import Modele.Jeu;
import Vue.InterfaceGraphique;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdaptateurSouris extends MouseAdapter {
	InterfaceGraphique inter;
	Jeu jeu;
	Animation animationPousseur, animationCaisse;

	public AdaptateurSouris(InterfaceGraphique i, Jeu j, Animation pousseur, Animation caisse) {
		inter = i;
		jeu = j;
		animationPousseur = pousseur;
		animationCaisse = caisse;
	}

	void deplace(int l, int c) {
		if (!animationPousseur.animationEnCours() && !animationCaisse.animationEnCours()) {
			if (jeu.deplace(l, c)) {
				Coup dernierCoup = jeu.dernierCoup();
				animationPousseur.nouvelleAnimation(dernierCoup.departPousseur(), dernierCoup.arriveePousseur());
				if (dernierCoup.aCaisse()) {
					animationCaisse.nouvelleAnimation(dernierCoup.departCaisse(), dernierCoup.arriveeCaisse());
				}
				inter.repaint();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (inter.ng().isIArunning()) return;
		int x = e.getX() / inter.ng().largeurCase();
		int y = e.getY() / inter.ng().hauteurCase();
		int dX = x - jeu.pousseurC();
		int dY = y - jeu.pousseurL();
		int sum = dX + dY;
		Configuration.instance().logger().info("Clic dans la case (" + x + ", " + y + ") soit une direction : " + dX + ", " + dY);
		if ((dX * dY == 0) && (sum * sum == 1)) {
			deplace(dY, dX);
		}
	}
}
