import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;

public class ZoneDeJeuConcrete extends ZoneDeJeuAbstraite {
	ArrayList<ArrayList<Coup>> historique;
	int idx_historique;
	Coup dernierCoup;

	public ZoneDeJeuConcrete(int height, int width) {
		super(height, width);
		historique = new ArrayList<>();
	}

	public ZoneDeJeuConcrete(String arg) throws Exception {
		historique = new ArrayList<>();
		ArrayList<Coup> coup;
		FileInputStream fis = null;
		Scanner scanner = null, scanner1 = null, scanner2 = null;
		try {
			fis = new FileInputStream(arg);
			scanner = new Scanner(fis);
			int width = scanner.nextInt();
			int height = scanner.nextInt();
			cases = new int[width][height];
			int x = 0, y = 0;
			int n = 0;
			int current = 0;
			String l1 = scanner.nextLine();
			String l2 = scanner.nextLine();
			scanner1 = new Scanner(l1);
			scanner2 = new Scanner(l2);

			while (scanner1.hasNextInt()) {
				current = scanner1.nextInt();
				n = scanner1.nextInt();
				for (int i = 0; i < n; i++) {
					cases[x][y] = current;
					// Passage à la prochaine case
					y++;
					if (y >= width) {
						y = 0;
						x++;
					}
				}
			}
			idx_historique = scanner2.nextInt();
			while (scanner2.hasNextInt()){
				coup = new ArrayList<>();
				n = scanner2.nextInt();
				for (int i = 0; i < n; i++){
					coup.add(new Coup(scanner2.nextInt(), scanner2.nextInt(), scanner2.nextInt(), scanner2.nextInt()));
				}
				historique.add(coup);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// Fermeture des flux
			if (fis != null) {
				fis.close();
			}
			if (scanner != null) {
				scanner.close();
			}
			if (scanner1 != null) {
				scanner1.close();
			}
			if (scanner2 != null) {
				scanner2.close();
			}
		}
	}

	public boolean peutAnnuler() {
		return historique.size() > 0 && idx_historique > 0;
	}

	public boolean peutRefaire() {
		return idx_historique < historique.size();
	}

	public void joue(int valeur, int i, int j) {
		if (coupJouable(valeur, i, j)) {
			ArrayList<Coup> c = new ArrayList<>();
			c.add(new Coup(valeurCase(i, j), valeur, i, j));
			jouerCoup(c, false);
		} else
			throw new InvalidParameterException("Joue " + valeur + " en (" + i + ", " + j + ")");
	}

	public void deplace(int depuisL, int depuisC, int versL, int versC) {
		int valeur = 0;
		int v;
		if (dansZone(depuisL, depuisC))
			valeur = valeurCase(depuisL, depuisC);
		int di = versL - depuisL;
		int dj = versC - depuisC;
		boolean valide = (valeur > 0) && dansZone(versL, versC) && (valeurCase(versL, versC) == 0) &&
				((di == 0) || (dj == 0) || (Math.abs(di) == Math.abs(dj)));
		if (valide) {
			if (di != 0)
				di = di / Math.abs(di);
			if (dj != 0)
				dj = dj / Math.abs(dj);
			ArrayList<Coup> c = new ArrayList<>();
			while ((depuisL != versL) || (depuisC != versC)) {
				if ((v = valeurCase(depuisL, depuisC)) != 0) {
					c.add(new Coup(v, 0, depuisL, depuisC));
				}
				depuisL += di;
				depuisC += dj;
			}
			c.add(new Coup(valeurCase(versL, versC), valeur, versL, versC));
			jouerCoup(c, false);
		}
	}

	public void pouCreuouJ(ArrayList<Coup> coups) {
		for (Coup c : coups) {
			fixeValeurCase(c.ancienne_valeur, c.position_x, c.position_y);
		}

	}

	public void jouerCoup(ArrayList<Coup> coups, Boolean isRedo) {
		for (Coup c : coups) {
			fixeValeurCase(c.nouvelle_valeur, c.position_x, c.position_y);
		}
		if (!isRedo) {
			while (historique.size() > idx_historique) {
				historique.remove(historique.size() - 1);
			}
			historique.add(coups);
			idx_historique++;
		}
	}

	public void annule() {
		if (historique.size() > 0 && idx_historique > 0) {
			idx_historique--;
			pouCreuouJ(historique.get(idx_historique));
		}
	}

	public void refais() {
		if (idx_historique < historique.size()) {
			jouerCoup(historique.get(idx_historique), true);
			idx_historique++;
		}
	}

	public void sauve(String fichier) throws Exception {
		FileOutputStream fos = null;
		PrintStream ps = null;
		try {
			fos = new FileOutputStream(fichier);
			ps = new PrintStream(fos);
			int n = 0;
			int current = cases[0][0];
			ps.print(cases.length + " " + cases[0].length + " ");
			for (int i = 0; i < cases.length; i++) {
				for (int j = 0; j < cases[0].length; j++) {
					if (current == cases[i][j]) {
						n++;
					} else {
						ps.print(current + " " + n + " ");
						current = cases[i][j];
						n = 1;
					}
				}
			}
			if (n > 0) {
				ps.print(current + " " + n + " ");
			}

			ps.print("\n");
			ps.print(idx_historique + " ");
			for (int i = 0; i < historique.size(); i++){
				n = historique.get(i).size();
				ps.print(n + " ");
				while (n > 0){
					ps.print(historique.get(i).get(n - 1) + " ");
					n--;
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// Fermeture des flux
			if (ps != null) {
				ps.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}
}
