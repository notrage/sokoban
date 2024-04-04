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
import Controleur.AnimationJeuAutomatique;
import Controleur.AnimationPousseur;

import java.awt.*;
import java.io.File;
import java.io.InputStream;

public class NiveauGraphique extends JComponent {
	Jeu jeu;
	Image but, caisse, caisseSurBut, mur, sol;
	Image[][] pousseur;
	int direction; // 0 = haut, 1 = gauche, 2 = droite, 3 = bas
	int largeurCase, hauteurCase;
	Animation animationPousseur;
	Animation animationCaisse;
	AnimationPousseur changementImagePousseur;
	AnimationJeuAutomatique ia;
	boolean avecAnimation;
	boolean modeia;

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

	public NiveauGraphique(Jeu jeu, Animation p, Animation c, AnimationPousseur po, AnimationJeuAutomatique ia) {
		but = chargeImage("But");
		caisse = chargeImage("Caisse");
		caisseSurBut = chargeImage("Caisse_sur_but");
		mur = chargeImage("Mur");
		sol = chargeImage("Sol");
		pousseur = new Image[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				pousseur[i][j] = chargeImage("Pousseur_" + i + "_" + j);
			}
		}
		this.jeu = jeu;
		changementImagePousseur = po;
		animationPousseur = p;
		animationCaisse = c;
		avecAnimation = true;
		this.ia = ia;
		modeia = false;
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
				if (niv.aBut(j, i)) {
					drawable.drawImage(but, x, y, largeurCase, hauteurCase, null);
				} else {
					drawable.drawImage(sol, x, y, largeurCase, hauteurCase, null);
				}
				if (niv.aMur(j, i)) {
					drawable.drawImage(mur, x, y, largeurCase, hauteurCase, null);
				}
				if (niv.aMarque(j, i)) {
					drawable.setColor(new Color(0x00FF00));
					drawable.setStroke(new BasicStroke(5));
					drawable.drawLine(x, y, x+largeurCase, y+hauteurCase);
				}
			}
		}

		for (int i = 0; i < niv.colonnes(); i++) {
			for (int j = 0; j < niv.lignes(); j++) {
				drawAnimation(drawable, j, i);
			}
		}
	}

	private void drawAnimation(Graphics2D drawable, int j, int i) {
		if (!avecAnimation) {
			animationCaisse.stop();
			animationPousseur.stop();
		}
		int x = i * largeurCase;
		int y = j * hauteurCase;
		Niveau niv = jeu.niveau();
		if (niv.aPousseur(j, i)) {
			if (animationPousseur.animationEnCours()) {
				Point coord = deplacement(animationPousseur.depart(), animationPousseur.arrivee(),
						animationPousseur.etape(),
						animationPousseur.maxEtape());
				drawable.drawImage(pousseur[jeu.derniereDirection()][changementImagePousseur.etape()], (int) coord.x,
						(int) coord.y, largeurCase, hauteurCase,
						null);
			} else {
				drawable.drawImage(pousseur[jeu.derniereDirection()][changementImagePousseur.etape()], x, y,
						largeurCase, hauteurCase, null);
			}
		} else if (niv.aCaisse(j, i)) {
			if (animationCaisse.animationEnCours() && animationCaisse.arrivee().x == j
					&& animationCaisse.arrivee().y == i) {
				Point coord = deplacement(animationCaisse.depart(), animationCaisse.arrivee(),
						animationCaisse.etape(),
						animationCaisse.maxEtape());
				drawable.drawImage(caisse, (int) coord.x, (int) coord.y, largeurCase, hauteurCase,
						null);
			} else {
				if (niv.aBut(j, i)) {
					drawable.drawImage(caisseSurBut, x, y, largeurCase, hauteurCase, null);
				} else {
					drawable.drawImage(caisse, x, y, largeurCase, hauteurCase, null);
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

	public Point deplacement(Point depart, Point arrivee, int etape_courante, int n_etape) {
		// Calcul des deltas
		float x1 = depart.x * largeurCase();
		float y1 = depart.y * hauteurCase();
		float x2 = arrivee.x * largeurCase();
		float y2 = arrivee.y * hauteurCase();
		int newX = (int) (x1 + ((float) etape_courante / (float) n_etape) * (x2 - x1));
		int newY = (int) (y1 + ((float) etape_courante / (float) n_etape) * (y2 - y1));
		// Inversion pour correspondre à l'affichage
		return new Point(newY, newX);
	}

	public void toggleAnimation() {
		avecAnimation = !avecAnimation;
		changementImagePousseur.toggle();
	}

	public void toggleIA(int typeSolver, boolean sauteCoups) {
		modeia = !modeia;
		ia.toggleIA(typeSolver, sauteCoups);
	}

	public boolean isIArunning() {
		return modeia;
	}
}