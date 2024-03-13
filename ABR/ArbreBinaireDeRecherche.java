// NE PAS MODIFIER : ce fichier est écrasé lors de l'évaluation et toute modification sera complètement ignorée
import java.util.Iterator;

public abstract class ArbreBinaireDeRecherche<E> {
	Noeud<E> racine;
	Noeud<E> nouveauNoeud(E etiquette) {
		return new Noeud<>(etiquette);
	}

	Noeud<E> racine() {
		return racine;
	}

	abstract public void add(E e);
	abstract public boolean find(E e);
	public Iterator<E> iterator() {
		throw new UnsupportedOperationException();
	}

	private String toStringRec(Noeud<E> n) {
		if (n == null)
			return "";
		else if (n.estFeuille())
			return n.toString();
		else
			return "("+toStringRec(n.filsGauche)+","+n.toString()+","+toStringRec(n.filsDroit)+")";
	}
	@Override
	public final String toString() {
		return toStringRec(racine());
	}

	private String recursiveDotPrint(Noeud<E> current) {
		String result = "";
		if (current != null) {
			result += current.hashCode() + " [label=\"" + current + "\"];\n";
			if (current.filsGauche != null) {
				result += current.hashCode() + ":s -> " + current.filsGauche.hashCode() + ":ne;\n";
				result += recursiveDotPrint(current.filsGauche);
			} else {
				//result += "left_" + current.hashCode() + " [color=white, label=\"\"];\n";
				//result += current.hashCode() + ":s -> left_" + current.hashCode() + ":ne;\n";
			}
			if (current.filsDroit != null) {
				result += current.hashCode() + ":s -> " + current.filsDroit.hashCode() + ":nw;\n";
				result += recursiveDotPrint(current.filsDroit);
			} else {
				//result += "right_" + current.hashCode() + " [color=white, label=\"\"];\n";
				//result += current.hashCode() + ":s -> right_" + current.hashCode() + ":nw;\n";
			}
		}
		return result;
	}

	public String toDot() {
		return "Digraph {" + recursiveDotPrint(racine) + "}";
	}
}
