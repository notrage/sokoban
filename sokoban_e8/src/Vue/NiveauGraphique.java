package Vue;
/*
 * Sokoban - Encore une nouvelle version (à but pédagogique) du célèbre jeu
 * Copyright (C) 2018 Guillaume Huard
 *
 * Ce programme est libre, vous pouvez le redistribuer et/ou le
 * modifier selon les termes de la Licence Publique Générale GNU publiée par la
 * Free Software Foundation (version 2 ou bien toute autre version ultérieure
 * choisie par vous).
 *
 * Ce programme est distribué car potentiellement utile, mais SANS
 * AUCUNE GARANTIE, ni explicite ni implicite, y compris les garanties de
 * commercialisation ou d'adaptation dans un but spécifique. Reportez-vous à la
 * Licence Publique Générale GNU pour plus de détails.
 *
 * Vous devez avoir reçu une copie de la Licence Publique Générale
 * GNU en même temps que ce programme ; si ce n'est pas le cas, écrivez à la Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * États-Unis.
 *
 * Contact:
 *          Guillaume.Huard@imag.fr
 *          Laboratoire LIG
 *          700 avenue centrale
 *          Domaine universitaire
 *          38401 Saint Martin d'Hères
 */

import Global.Configuration;
import Modele.Jeu;
import Modele.Niveau;

import javax.imageio.ImageIO;
import javax.swing.*;

import Controleur.Animation;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.InputStream;

public class NiveauGraphique extends JComponent {
	Jeu jeu;
	Image but, caisse, caisseSurBut, mur, pousseur, sol;
	int largeurCase, hauteurCase;
	Animation animations;

	private Image chargeImage(String nom) {
		Image img = null;
		InputStream in = Configuration.charge("Images" + File.separator + nom + ".png");
		try {
			// Chargement d'une image utilisable dans Swing
			img = ImageIO.read(in);
		} catch (Exception e) {
			Configuration.instance().logger().severe("Impossible de charger l'image");
			System.exit(1);
		}
		return img;
	}

	public NiveauGraphique(Jeu j, Animation a) {
		but = chargeImage("But");
		caisse = chargeImage("Caisse");
		caisseSurBut = chargeImage("Caisse_sur_but");
		mur = chargeImage("Mur");
		pousseur = chargeImage("Pousseur");
		sol = chargeImage("Sol");
		jeu = j;
		animations = a;
	}

	@Override
	public void paintComponent(Graphics g) {
		// Graphics 2D est le vrai type de l'objet passé en paramètre
		// Le cast permet d'avoir acces a un peu plus de primitives de dessin
		Graphics2D drawable = (Graphics2D) g;

		// On reccupere quelques infos provenant de la partie JComponent
		int width = getSize().width;
		int height = getSize().height;

		// On efface tout
		drawable.clearRect(0, 0, width, height);

		Niveau niv = jeu.niveau();
		largeurCase = width / niv.colonnes();
		hauteurCase = height / niv.lignes();
		largeurCase = Math.min(largeurCase, hauteurCase);
		hauteurCase = largeurCase;

		for (int i = 0; i < niv.colonnes(); i++) {
			for (int j = 0; j < niv.lignes(); j++) {
				int x = i * largeurCase;
				int y = j * hauteurCase;
				if (niv.aBut(j, i))
					drawable.drawImage(but, x, y, largeurCase, hauteurCase, null);
				else
					drawable.drawImage(sol, x, y, largeurCase, hauteurCase, null);
				if (niv.aMur(j, i))
					drawable.drawImage(mur, x, y, largeurCase, hauteurCase, null);
				else if (niv.aCaisse(j, i)) {
					if (niv.aBut(j, i))
						drawable.drawImage(caisseSurBut, x, y, largeurCase, hauteurCase, null);
					else
						drawable.drawImage(caisse, x, y, largeurCase, hauteurCase, null);

				}
			}
		}

		for (int i = 0; i < niv.colonnes(); i++) {
			for (int j = 0; j < niv.lignes(); j++) {
				int x = i * largeurCase;
				int y = j * hauteurCase;
				if (niv.aPousseur(j, i)) {
					if (animations.animationEnCours()) {
						//System.out.println("Redraw with anim");
						Point2D coord = deplacement(animations.depart(), animations.arrivee(), animations.etape(),
								animations.maxEtape());
						drawable.drawImage(pousseur, (int) coord.getX(), (int) coord.getY(), largeurCase, hauteurCase,
								null);
					} else {
						drawable.drawImage(pousseur, x, y, largeurCase, hauteurCase, null);
					}
				}
			}
		}
	}

	public int largeurCase() {
		return largeurCase;
	}

	public int hauteurCase() {
		return hauteurCase;
	}

	public Point2D deplacement(Point2D depart, Point2D arrivee, int etape_courante, int n_etape) {
		// Calcul des deltas
		double x1 = depart.getX() * largeurCase();
		double y1 = depart.getY() * hauteurCase();
		double x2 = arrivee.getX() * largeurCase();
		double y2 = arrivee.getY() * hauteurCase();
		double newX = x1 + ((float) etape_courante / (float) n_etape) * (x2 - x1);
		double newY = y1 + ((float) etape_courante / (float) n_etape) * (y2 - y1);
		//System.out.println(new Point2D.Double(x1, y1) + " => " + new Point2D.Double(newX, newY) + " => "
		//			+ new Point2D.Double(x2, y2));

		// Retourne les nouvelles coordonnées sous forme de Point2D
		return new Point2D.Double(newX, newY);
	}
}