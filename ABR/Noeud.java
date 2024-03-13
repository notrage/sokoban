// NE PAS MODIFIER : ce fichier est écrasé lors de l'évaluation et toute modification sera complètement ignorée
public class Noeud<E> {
	E etiquette;
	Noeud<E> pere, filsGauche, filsDroit;

	Noeud(Noeud<E> g, E e, Noeud<E> d) {
		etiquette = e;
		filsGauche = g;
		if (g != null)
			g.pere = this;
		filsDroit = d;
		if (d != null)
			d.pere = this;
	}

	Noeud(E e) {
		this(null, e, null);
	}

	boolean estFeuille() {
		return filsGauche == null && filsDroit == null;
	}

	@Override
	public String toString() {
		return etiquette.toString();
	}
}
