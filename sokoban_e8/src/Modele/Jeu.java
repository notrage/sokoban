package Modele;
import Global.Configuration;

public class Jeu {
	Niveau courant;
	LecteurNiveau lecteur;

	public Jeu(LecteurNiveau l) {
		lecteur = l;
		prochainNiveau();
	}

	public Niveau niveau() {
		return courant;
	}

	public boolean deplace(int l, int c) {
		if (!courant.deplace(l, c)) {
			Configuration.instance().logger().info("Déplacement impossible");
			return false;
		}
		if (courant.estTermine())
			if (!prochainNiveau())
				System.exit(0);
		return true;
	}

	public boolean prochainNiveau() {
		courant = lecteur.lisProchainNiveau();
		return courant != null;
	}

	public int pousseurL() {
		return courant.pousseurL();
	}

	public int pousseurC() {
		return courant.pousseurC();
	}

	public void annulerDernierCoup(){
		courant.annulerDernierCoup();
	}

	public void redoCoup(){
		courant.redoCoup();
	}

	public Coup dernierCoup(){
		return courant.dernierCoup();
	}

	public int derniereDirection(){
		return courant.derniereDirection();
	}

	public boolean marquer(int i, int j){
		return courant.marquer(i, j);
	}

	public boolean aMarque(int i, int j){
		return courant.aMarque(i, j);
	}

	public void retirerMarque(int l, int c){
		courant.retirerMarque(l, c);
	}

	public void resetMarques() {
		courant.resetMarques();
	}


}
