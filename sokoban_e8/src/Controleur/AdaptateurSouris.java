package Controleur;
import Global.Configuration;
import Modele.Jeu;
import Vue.NiveauGraphique;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdaptateurSouris extends MouseAdapter {
	NiveauGraphique inter;
	Jeu jeu;
	Animation animations;

	public AdaptateurSouris(NiveauGraphique n, Jeu j, Animation a) {
		inter = n;
		jeu = j;
		animations = a;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX() / inter.largeurCase();
		int y = e.getY() / inter.hauteurCase();
		Configuration.instance().logger().info("Clic dans la case (" + x + ", " + y +")");
		int dX = x - jeu.pousseurC();
		int dY = y - jeu.pousseurL();
		int sum = dX+dY;
		sum = sum*sum;
		if ((dX*dY == 0) && (sum == 1)) {
			Point depart = new Point(jeu.pousseurC(), jeu.pousseurL());
			jeu.deplace(dY, dX);
			Point arrivee = new Point(jeu.pousseurC(), jeu.pousseurL());
			if (!depart.equals(arrivee)) {
				animations.nouvelleAnimation(depart, arrivee);
				inter.repaint();
			}
		}
	}
}
