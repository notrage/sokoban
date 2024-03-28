package Modele;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
	private int[][] chemins;
	private int[][] heuristique;
	private int l, c;
	private String nom;
	private int pousseurL, pousseurC;
	private int nbBut;
	private int nbCaisseSurBut;
	private ArrayList<Coup> coups;
	private int idx_coup;
	private Coup dernierCoup;
	int derniereDirection;
	HashSet<Point> caisses;

	Niveau() {
		contenu = new int[1][1];
		marques = new Marque[1][1];
		chemins = new int[1][1];
		l = 0;
		c = 0;
		coups = new ArrayList<>();
		caisses = new HashSet<>();
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
			int[][] newTab = new int[newL][newC];
			Marque[][] newMarques = new Marque[newL][newC];
			int[][] newChemins = new int[newL][newC];
			for (int i = 0; i < oldL; i++) {
				for (int j = 0; j < oldC; j++) {
					newMarques[i][j] = marques[i][j];
					newTab[i][j] = contenu[i][j];
					newChemins[i][j] = chemins[i][j];
				}
			}
			contenu = newTab;
			marques = newMarques;
			chemins = newChemins;
		}
	}

	public void fixeNom(String s) {
		nom = s;
	}

	public void videCase(int i, int j) {
		contenu[i][j] = 0;
		caisses.remove(new Point(i, j));
	}

	public void ajoute(int element, int i, int j) {
		redimensionne(i, j);
		contenu[i][j] |= element;
		if (l <= i)
			l = i + 1;
		if (c <= j)
			c = j + 1;
		if ((element & CAISSE) > 0) {
			caisses.add(new Point(i, j));
			if (aBut(i, j))
				nbCaisseSurBut++;
		}
		if ((element & BUT) > 0) {
			nbBut++;
			if (aCaisse(i, j)) {
				nbCaisseSurBut++;
			}
		}
		if ((element & POUSSEUR) > 0) {
			pousseurL = i;
			pousseurC = j;
		}

	}

	public void supprime(int element, int i, int j) {
		contenu[i][j] &= ~element;
		if (element == CAISSE) {
			caisses.remove(new Point(i, j));
			if (aBut(i, j)) {
				nbCaisseSurBut--;
			}
		}

	}

	public void jouerCoup(Coup c, boolean isRedo) {
		dernierCoup = c;
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
	}

	public void ajouteCaisse(int i, int j) {
		// System.out.println("Ajout d'une caisse en (" + i + ", " + j + ")");
		ajoute(CAISSE, i, j);

	}

	public void ajouteBut(int i, int j) {
		// System.out.println("Ajout d'un but en (" + i + ", " + j + ")");
		ajoute(BUT, i, j);
		;
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
		return caisses.contains(new Point(l, c));
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
			marques[l][c] = new Marque(l, c, 0);
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

	public HashSet<Point> positionCaisses() {
		return caisses;
	}

	// Methode pour marquer toutes les cases accessibles depuis la i,j (i,j sera
	// accessible en tout cas)
	public void marqueAccessibles(int l, int c) {
		resetMarques();
		int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
		Queue<Marque> f = new LinkedList<>();
		Marque m;
		f.add(new Marque(new Point(l, c), null, 0));
		while (!f.isEmpty()) {
			m = f.remove();
			int i = m.caseCourante.x;
			int j = m.caseCourante.y;
			if (i >= 0 && i < lignes() &&
					j >= 0 && j < colonnes() &&
					!aMarque(i, j) && estLibre(i, j)) {
				marques[i][j] = m;
				for (int[] direction : directions) {
					int di = direction[0];
					int dj = direction[1];
					f.add(new Marque(new Point(i + di, j + dj), m.caseCourante, m.distance));
				}
			}
		}
	}

	public HashMap<Point, Integer> deplacementsCaisses() {
		HashMap<Point, Integer> pC = new HashMap<>();
		// gauche, haut, droite, bas
		int[][] directions = { { 0, -1 }, { -1, 0 }, { 0, 1 }, { 1, 0 } };
		for (Point caisse : caisses) {
			if (!pC.containsKey(caisse)) {
				pC.put(caisse, 0);
			}
			for (int j = 0; j < 4; j++) {
				// System.out.println(caisse);
				if (marques[caisse.x - directions[j][0]][caisse.y - directions[j][1]] != null
						&& estLibre(caisse.x + directions[j][0], caisse.y + directions[j][1])) {
					pC.put(caisse, pC.get(caisse) | 1 << j);
					// System.out.println("la caisse en (" + caisse.x + ',' + caisse.y + ") peut
					// aller en "
					// + directions[j][0] + ", " + directions[j][1]);
				}
			}
		}
		return pC;
	}

	public void remplirChemins() {
		for (int i = 0; i < lignes(); i++) {
			for (int j = 0; j < colonnes(); j++) {
				if (aBut(i, j)) {
					chemins[i][j] = 0;
				} else {
					chemins[i][j] = 1;
				}
			}
		}
		boolean ajouter = true;
		while (ajouter) {
			ajouter = false;

		}
	}

	public Situation toSituation() {
		resetMarques();
		marqueAccessibles(pousseurL, pousseurC);
		genereHeuristique();
		int score_heuristique = 0;
		for (Point p : caisses) {
			score_heuristique += heuristique[p.x][p.y];
		}
		return new Situation(deplacementsCaisses(), new Point(pousseurL(), pousseurC()), score_heuristique);
	}

	Marque[][] marques() {
		return marques;
	}

	public void afficherHeuristique() {
		genereHeuristique();
		for (int i = 0; i < lignes(); i++) {
			for (int j = 0; j < colonnes(); j++) {
				if (aMur(i, j))
					System.out.print("#  ");
				else if (heuristique[i][j] == Integer.MAX_VALUE)
					System.out.print("X  ");
				else if (heuristique[i][j] >= 10)
					System.out.print(heuristique[i][j] + " ");
				else
					System.out.print(heuristique[i][j] + "  ");

			}
			System.out.println();
		}
	}

	public void genereHeuristique() {
		if (heuristique != null)
			return;
		int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
		heuristique = new int[lignes()][colonnes()];
		int i, j, k, acc_i, acc_j, der_i, der_j;
		boolean ajoute;

		for (i = 0; i < lignes(); i++) {
			for (j = 0; j < colonnes(); j++) {
				if (aBut(i, j)) {
					heuristique[i][j] = 0;
				} else {
					heuristique[i][j] = Integer.MAX_VALUE;
				}
			}
		}

		ajoute = true;

		while (ajoute) {
			ajoute = false;
			for (i = 0; i < lignes(); i++) {
				for (j = 0; j < colonnes(); j++) {
					for (k = 0; k < 4; k++) {
						acc_i = i + directions[k][0];
						acc_j = j + directions[k][1];
						der_i = i - directions[k][0];
						der_j = j - directions[k][1];
						// si la case adjacente de dépasse pas de la map
						// et que la case adjacente est accessible
						// et qu'il est possible de pousser une caisse dessus
						if (acc_i >= 0 && acc_i < lignes() && acc_j >= 0 && acc_j < colonnes() &&
								der_i >= 0 && der_i < lignes() && der_j >= 0 && der_j < colonnes() &&
								heuristique[i][j] == Integer.MAX_VALUE &&
								heuristique[acc_i][acc_j] != Integer.MAX_VALUE &&
								!aMur(der_i, der_j) && !aMur(i, j)) {
							heuristique[i][j] = heuristique[acc_i][acc_j] + 1;
							ajoute = true;
						}
					}
				}
			}
		}
	}
}
