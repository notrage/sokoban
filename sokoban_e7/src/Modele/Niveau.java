package Modele;
public class Niveau {
	/* Une case contient un entier pour coder MUR, POUSSEUR, BUT, POUSSEUR_SUR_BUT, ...
       On choisit de prendre un bit différent de notre entier pour coder la présence
       de chaque objet
	*/
    final int MUR = 1;
	final int POUSSEUR = 2;
	final int BUT = 4;
	final int CAISSE = 8;

	int [][] contenu;
	int l, c;
	String nom;
	int pousseurL, pousseurC;
	int nbBut;
	int nbCaisseSurBut;

	Niveau() {
		contenu = new int[1][1];
		l = 0;
		c = 0;
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
			int [][] newTab = new int[newL][newC];
			for (int i=0; i<oldL; i++)
				for (int j=0; j<oldC; j++)
					newTab[i][j] = contenu[i][j];
			contenu = newTab;
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
			l = i+1;
		if (c <= j)
			c = j+1;
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

	public boolean deplace(int dL, int dC) {
		int destL = pousseurL+dL;
		int destC = pousseurC+dC;
		if (aCaisse(destL, destC)) {
			int caisseL = destL+dL;
			int caisseC = destC+dC;
			if (estLibre(caisseL, caisseC)) {
				supprime(CAISSE, destL, destC);
				ajoute(CAISSE, caisseL, caisseC);
			}
		}
		if (estLibre(destL, destC)) {
			supprime(POUSSEUR, pousseurL, pousseurC);
			pousseurL = destL;
			pousseurC = destC;
			ajoute(POUSSEUR, pousseurL, pousseurC);
			return true;
		} else
			return false;
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
		return !aMur(l,c) && !aCaisse(l,c);
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
}
