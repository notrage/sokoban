package Controleur;
import Global.Configuration;
import Modele.Jeu;
import Vue.NiveauGraphique;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdaptateurSouris extends MouseAdapter {
	NiveauGraphique niveau;
	Jeu jeu;

	public AdaptateurSouris(NiveauGraphique n, Jeu j) {
		niveau = n;
		jeu = j;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX() / niveau.largeurCase();
		int y = e.getY() / niveau.hauteurCase();
		Configuration.instance().logger().info("Clic dans la case (" + x + ", " + y +")");
		int dX = x - jeu.pousseurC();
		int dY = y - jeu.pousseurL();
		int sum = dX+dY;
		sum = sum*sum;
		if ((dX*dY == 0) && (sum == 1)) {
			jeu.deplace(dY, dX);
			niveau.repaint();
		}
	}
}
