package Modele;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Niveau {
	/*
	 * Une case contient un entier pour coder MUR, POUSSEUR, BUT, POUSSEUR_SUR_BUT,
	 * ...
	 * On choisit de prendre un bit différent de notre entier pour coder la présence
	 * de chaque objet
	 */
	final int MUR = 1;
	final int POUSSEUR = 2;
	final int BUT = 4;
	final int CAISSE = 8;

	private int[][] contenu;
	private Marque[][] marques;
	private int l, c;
	private String nom;
	private int pousseurL, pousseurC;
	private int nbBut;
	private int nbCaisseSurBut;
	private ArrayList<Coup> coups;
	private int idx_coup;
	private Coup dernierCoup;
	int derniereDirection;

	Niveau() {
		contenu = new int[1][1];
		marques = new Marque[1][1];
		l = 0;
		c = 0;
		coups = new ArrayList<>();
		idx_coup = 0;
	}

	public int ajuste(int c, int i) {
		while (c <= i) {
			c *= 2;
		}
		return c;
	}

	public void redimensionne(int l, int c) {
		int oldL = contenu.length;
		int oldC = contenu[0].length;
		if ((oldL <= l) || (oldC <= c)) {
			int newL = ajuste(oldL, l);
			int newC = ajuste(oldC, c);
			System.err.println("Redimensionnement : " + newL + "*" + newC);
			int[][] newTab = new int[newL][newC];
			Marque[][] newMarques = new Marque[newL][newC];
			for (int i = 0; i < oldL; i++) {
				for (int j = 0; j < oldC; j++) {
					newMarques[i][j] = marques[i][j];
					newTab[i][j] = contenu[i][j];
				}
			}
			contenu = newTab;
			marques = newMarques;
		}
	}

	public void fixeNom(String s) {
		nom = s;
	}

	public void videCase(int i, int j) {
		contenu[i][j] = 0;
	}

	public void ajoute(int element, int i, int j) {
		redimensionne(i, j);
		contenu[i][j] |= element;
		if (l <= i)
			l = i + 1;
		if (c <= j)
			c = j + 1;
		if (element == CAISSE)
			if (aBut(i, j))
				nbCaisseSurBut++;
	}

	public void supprime(int element, int i, int j) {
		contenu[i][j] &= ~element;
		if (element == CAISSE)
			if (aBut(i, j))
				nbCaisseSurBut--;
	}

	public void jouerCoup(Coup c, boolean isRedo) {
		dernierCoup = c;
		System.out.println("Coup joué : " + c);
		if (c.aCaisse()) {
			supprime(CAISSE, c.departCaisse().x, c.departCaisse().y);
			ajoute(CAISSE, c.arriveeCaisse().x, c.arriveeCaisse.y);
		}
		supprime(POUSSEUR, pousseurL(), pousseurC());
		ajoute(POUSSEUR, c.arriveePousseur().x, c.arriveePousseur().y);
		pousseurL = c.arriveePousseur().x;
		pousseurC = c.arriveePousseur().y;
		if (!isRedo) {
			while (coups.size() > idx_coup) {
				coups.remove(coups.size() - 1);
			}
			coups.add(c);
			idx_coup++;
		}
	}

	public void pouCreuouJ(Coup c) {
		dernierCoup = c;
		System.out.println("Coup déjoué : " + c);
		supprime(POUSSEUR, c.arriveePousseur().x, c.arriveePousseur().y);
		ajoute(POUSSEUR, c.departPousseur().x, c.departPousseur().y);
		if (c.aCaisse()) {
			supprime(CAISSE, c.arriveeCaisse().x, c.arriveeCaisse().y);
			ajoute(CAISSE, c.departCaisse().x, c.departCaisse().y);
		}
		pousseurL = c.departPousseur().x;
		pousseurC = c.departPousseur().y;
	}

	public void annulerDernierCoup() {
		if (coups.size() > 0 && idx_coup > 0) {
			idx_coup--;
			pouCreuouJ(coups.get(idx_coup));
		}
	}

	public void redoCoup() {
		if (idx_coup < coups.size()) {
			jouerCoup(coups.get(idx_coup), true);
			idx_coup++;
		}
	}

	public boolean deplace(int dL, int dC) {
		Coup c;
		int destL = pousseurL + dL;
		int destC = pousseurC + dC;
		if (aCaisse(destL, destC)) {
			int caisseL = destL + dL;
			int caisseC = destC + dC;
			if (estLibre(caisseL, caisseC)) {
				c = new Coup(new Point(pousseurL, pousseurC), new Point(destL, destC), new Point(caisseL, caisseC));
				jouerCoup(c, false);
				return true;
			} else {
				return false;
			}
		} else if (estLibre(destL, destC)) {
			c = new Coup(new Point(pousseurL, pousseurC), new Point(destL, destC));
			jouerCoup(c, false);
			return true;
		} else {
			return false;
		}
	}

	public void ajouteMur(int i, int j) {
		// System.out.println("Ajout d'un mur en (" + i + ", " + j + ")");
		ajoute(MUR, i, j);
	}

	public void ajoutePousseur(int i, int j) {
		// System.out.println("Ajout d'un pousseur en (" + i + ", " + j + ")");
		ajoute(POUSSEUR, i, j);
		pousseurL = i;
		pousseurC = j;
	}

	public void ajouteCaisse(int i, int j) {
		// System.out.println("Ajout d'une caisse en (" + i + ", " + j + ")");
		ajoute(CAISSE, i, j);
		if (aBut(i, j))
			nbCaisseSurBut++;
	}

	public void ajouteBut(int i, int j) {
		// System.out.println("Ajout d'un but en (" + i + ", " + j + ")");
		ajoute(BUT, i, j);
		nbBut++;
		if (aCaisse(i, j))
			nbCaisseSurBut++;
	}

	public int lignes() {
		return l;
	}

	public int colonnes() {
		return c;
	}

	String nom() {
		return nom;
	}

	boolean estVide(int l, int c) {
		return contenu[l][c] == 0;
	}

	public boolean aMur(int l, int c) {
		return (contenu[l][c] & MUR) != 0;
	}

	public boolean aBut(int l, int c) {
		return (contenu[l][c] & BUT) != 0;
	}

	public boolean aPousseur(int l, int c) {
		return (contenu[l][c] & POUSSEUR) != 0;
	}

	public boolean aCaisse(int l, int c) {
		return (contenu[l][c] & CAISSE) != 0;
	}

	boolean estLibre(int l, int c) {
		return !aMur(l, c) && !aCaisse(l, c);
	}

	boolean estTermine() {
		return nbCaisseSurBut == nbBut;
	}

	int pousseurL() {
		return pousseurL;
	}

	int pousseurC() {
		return pousseurC;
	}

	public Coup dernierCoup() {
		return dernierCoup;
	}

	public int derniereDirection() {
		if (dernierCoup == null)
			return 2;
		return dernierCoup.direction();
	}

	public Niveau clone() {
		Niveau n = new Niveau();
		for (int i = 0; i < lignes(); i++) {
			for (int j = 0; j < colonnes(); j++) {
				n.ajoute(contenu[i][j], i, j);
			}
		}
		n.fixeNom(nom);
		n.pousseurL = pousseurL;
		n.pousseurC = pousseurC;
		n.nbBut = nbBut;
		n.nbCaisseSurBut = nbCaisseSurBut;
		return n;
	}

	public boolean marquer(int l, int c) {
		if (marques[l][c] == null) {
			marques[l][c] = new Marque(l, c);
			return true;
		}
		return false;
	}

	public boolean aMarque(int l, int c) {
		return marques[l][c] != null;
	}

	public void retirerMarque(int l, int c) {
		marques[l][c] = null;
	}

	public void resetMarques() {
		marques = new Marque[lignes()][colonnes()];
	}

	public Point[] positionCaisses() {
		Point[] caisses = new Point[nbBut];
		int idx = 0;
		for (int i = 0; i < lignes(); i++) {
			for (int j = 0; j < colonnes(); j++) {
				if (aCaisse(i, j)) {
					caisses[idx] = new Point(i, j);
					idx++;
				}
			}
		}
		return caisses;
	}

	// Methode pour marquer toutes les cases accessibles depuis la i,j
	public void marqueAccessibles(int l, int c) {
		int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
		Queue<Marque> f = new LinkedList<>();
		Marque m;
		f.add(new Marque(new Point(l, c), null));
		while (!f.isEmpty()) {
			m = f.remove();
			int i = m.caseCourante.x;
			int j = m.caseCourante.y;
			System.out.println(m.caseCourante);
			if (i >= 0 && i < lignes() &&
					j >= 0 && j < colonnes() &&
					!aMarque(i, j) && estLibre(i, j)) {
				System.out.println(m.caseCourante);
				marques[i][j] = m;
				for (int[] direction : directions) {
					int di = direction[0];
					int dj = direction[1];
					f.add(new Marque(new Point(i + di, j + dj), m.caseCourante));
				}
			}
		}
	}

	public int[] deplacementsCaisses(Point[] caisses) {
		int[][] directions = { { 0, -1 }, { -1, 0 }, { 0, 1 }, { 1, 0 } };
		int[] positionsFutursCaisses = new int[nbBut];
		for (int i = 0; i < nbBut; i++) {
			Point caisse = caisses[i];
			for (int j = 0; j < 4; j++) {
				if (marques[caisse.x - directions[j][0]][caisse.y - directions[j][i]] != null
						&& estLibre(caisse.x + directions[j][0], caisse.y + directions[j][i])) {
					positionsFutursCaisses[i] |= (int) Math.pow(2, j);
					System.out.println("la caisse en (" + caisse.x + ',' + caisse.y + ") peut aller en " + directions[j]);
				}
			}
		}
		return positionsFutursCaisses;
	}

	public Situation toSituation() {
		resetMarques();
		marqueAccessibles(pousseurL, pousseurC);
		Point[] caisses = positionCaisses();
		int[] directionsCaisses = deplacementsCaisses(caisses);
		return new Situation(caisses, directionsCaisses);
	}
}
