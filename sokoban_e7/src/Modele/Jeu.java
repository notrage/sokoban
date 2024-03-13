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

	public void deplace(int l, int c) {
		if (!courant.deplace(l, c)) {
			Configuration.instance().logger().info("Déplacement impossible");
		}
		if (courant.estTermine())
			if (!prochainNiveau())
				System.exit(0);
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
}
