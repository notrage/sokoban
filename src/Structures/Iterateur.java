package Structures;

public abstract class Iterateur<Tata> {
	boolean peutSupprimer;

	abstract boolean aProchain();

	Tata prochain() {
		peutSupprimer = true;
		return null;
	}
	void supprime() {
		if (!peutSupprimer) {
			throw new IllegalStateException("Deux suppressions d'affilée");
		}
		peutSupprimer = false;
	}
}
