import java.security.InvalidParameterException;
import java.util.Arrays;

public abstract class ZoneDeJeuAbstraite {
	protected int[][] cases;

	public ZoneDeJeuAbstraite(int i, int j) {
		cases = new int[i][j];
	}

	// Constructeur vide à l'usage des classes filles qui peuvent construire
	// le plateau (y compris le tableau de cases) différemment
	protected ZoneDeJeuAbstraite() {
	}

	// Constructeur copie à plat pour avoir un plateau qui travaille
	// sur le même tableau de cases que le plateau passé en paramètre
	protected ZoneDeJeuAbstraite(ZoneDeJeuAbstraite p) {
		cases = p.cases;
	}

	boolean in(int valeur, int min, int max) {
		return (valeur >= min) && (valeur < max);
	}

	boolean dansZone(int i, int j) {
		return in(i, 0, cases.length) && in(j, 0, cases[0].length);
	}

	boolean coupJouable(int valeur, int i, int j) {
		return in(valeur, 1, 3) && dansZone(i, j);
	}

	public void joue(int valeur, int i, int j) {
		if (coupJouable(valeur, i, j))
			fixeValeurCase(valeur, i, j);
		else
			throw new InvalidParameterException("Joue " + valeur + " en (" + i + ", " + j + ")");
	}

	public void deplace(int depuisL, int depuisC, int versL, int versC) {
		int valeur = 0;
		if (dansZone(depuisL, depuisC))
			valeur = valeurCase(depuisL, depuisC);
		int di = versL-depuisL;
		int dj = versC-depuisC;
		boolean valide = (valeur > 0) && dansZone(versL, versC) && (valeurCase(versL, versC) == 0) &&
				((di == 0) || (dj == 0) || (Math.abs(di) == Math.abs(dj)));

		if (valide) {
			if (di != 0)
				di = di / Math.abs(di);
			if (dj != 0)
				dj = dj / Math.abs(dj);
			while ((depuisL != versL) || (depuisC != versC)) {
				fixeValeurCase(0, depuisL, depuisC);
				depuisL += di;
				depuisC += dj;
			}
			fixeValeurCase(valeur, versL, versC);
		}
	}

	public int valeurCase(int i, int j) {
		return cases[i][j];
	}

	public void fixeValeurCase(int v, int i, int j) {
		cases[i][j] = v;
	}

	public boolean peutAnnuler() {
		return false;
	}

	public boolean peutRefaire() {
		return false;
	}

	public void annule() {
	}

	public void refais() {
	}

	public void sauve(String fichier) throws Exception {
	}

	public int nbLignes() {
		return cases.length;
	}

	public int nbColonnes() {
		return cases[0].length;
	}

	@Override
	public String toString() {
		String result = "Plateau:\n[";
		String sep = "";
		for (int i=0; i<cases.length; i++) {
			result += sep + Arrays.toString(cases[i]);
			sep = "\n ";
		}
		result += 	"]\nEtat:" +
				"\n- peut annuler : " + peutAnnuler() +
				"\n- peut refaire : " + peutRefaire();
		return result;
	}
}
