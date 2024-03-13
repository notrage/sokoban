import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArbreBinaireDeRechercheConcret<E extends Comparable<E>> extends ArbreBinaireDeRecherche<E> {

	@Override
	public void add(E e) {
		Noeud<E> n = new Noeud<>(e);
		if (racine == null) {
			racine = n;
			n.pere = null;
			return;
		}
		Noeud<E> r = racine;
		while (true) {
			if (e.compareTo(r.etiquette) >= 0) {
				if (r.filsDroit == null) {
					r.filsDroit = n;
					n.pere = r;
					return;
				} else {
					r = r.filsDroit;
				}
			} else {
				if (r.filsGauche == null) {
					r.filsGauche = n;
					n.pere = r;
					return;
				} else {
					r = r.filsGauche;
				}
			}
		}

	}

	@Override
	public boolean find(E e) {
		Noeud<E> r = racine;
		while (r != null) {
			if (e.compareTo(r.etiquette) == 0) {
				return true;
			} else if (e.compareTo(r.etiquette) > 0) {
				r = r.filsDroit;
			} else {
				r = r.filsGauche;
			}
		}
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		return new ArbreIterator();
	}

	private class ArbreIterator implements Iterator<E> {
		private ArrayList<E> liste;
		private int idx;

		public ArbreIterator() {
			liste = new ArrayList<>();
			DFS(racine);
		}

		private void DFS(Noeud<E> n) {
			if (n == null) {
				return;
			}
			DFS(n.filsGauche);
			liste.add(n.etiquette);
			DFS(n.filsDroit);
		}

		@Override
		public boolean hasNext() {
			return idx < liste.size();
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			E res = liste.get(idx);
			idx++;
			return res;
		}

		private Noeud<E> goTo(E e) {
			Noeud<E> r = racine;
			while (r != null) {
				if (e.compareTo(r.etiquette) == 0) {
					return r;
				} else if (e.compareTo(r.etiquette) > 0) {
					r = r.filsDroit;
				} else {
					r = r.filsGauche;
				}
			}
			return null;
		}

		public void remove() {
			Noeud<E> n;
			if (idx - 1 < 0 || idx - 1 >= liste.size() || (n = goTo(liste.get(idx - 1))) == null) {
				throw new IllegalStateException();
			}
			System.err.println("Parent : " + n.pere);
			Noeud<E> n_new;
			if (n.filsDroit == null) {
				n_new = n.filsGauche;
				System.err.println("Suppression de " + n.etiquette + " remplacé par " + n_new + " case 1");
			} else if (n.filsGauche == null || n.filsDroit.filsGauche == null) {
				n_new = n.filsDroit;
				if (n.filsGauche != null) {
					n_new.filsGauche = n.filsGauche;
					n.filsGauche.pere = n_new;
				}
				System.err.println("Suppression de " + n.etiquette + " remplacé par " + n_new + " case 2");
			} else {
				n_new = n.filsDroit;
				while (n_new.filsGauche != null) {
					n_new = n_new.filsGauche;
				}
				n_new.pere.filsGauche = n_new.filsDroit;
				if (n_new.filsDroit != null)
					n_new.filsDroit.pere = n_new.pere;
				n_new.filsDroit = n.filsDroit;
				n_new.filsGauche = n.filsGauche;
				n.filsDroit.pere = n_new;
				n.filsGauche.pere = n_new;
				System.err.println("Suppression de " + n.etiquette + " remplacé par " + n_new + " case 3");
			}
			if (n_new != null) {
				n_new.pere = n.pere;
			}
			if (n.pere == null) {
				racine = n_new;
			} else if (n.pere.filsDroit == n) {
				n.pere.filsDroit = n_new;
			} else {
				n.pere.filsGauche = n_new;
			}
			return;
		}
	}
}
