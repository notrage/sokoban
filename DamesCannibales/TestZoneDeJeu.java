import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class TestZoneDeJeu {
	static void execute(String s, ZoneDeJeuAbstraite z) {
		String[] parts = s.split("\\s+");
		int[] args = new int[parts.length - 1];

		try {
			for (int i = 0; i < parts.length - 1; i++)
				args[i] = Integer.parseInt(parts[i + 1]);

			switch (parts[0]) {
				case "joue":
					z.joue(args[0], args[1], args[2]);
					break;
				case "valeur":
					z.valeurCase(args[0], args[1]);
					break;
				case "deplace":
					z.deplace(args[0], args[1], args[2], args[3]);
					break;
				case "annule":
					if (z.peutAnnuler())
						z.annule();
					else
						System.out.println("Impossible d'annuler");
					break;
				case "refais":
					if (z.peutRefaire())
						z.refais();
					else
						System.out.println("Impossible de refaire");
					break;
				case "print":
					System.out.println(z);
					break;
				default:
					throw new UnsupportedOperationException(parts[0]);
			}
		} catch (Exception e) {
			System.err.println("Commande invalide : " + s + " (" + e + ")");
		}
	}

	public static void main(String[] args) {
		ZoneDeJeuAbstraite z = null;
		String fichierSortie = null;
		int width = 5, height = 5;
		int indiceOption = 0;
		boolean finOptions = false;

		while (!finOptions && indiceOption < (args.length - 1)) {
			switch (args[indiceOption]) {
				case "-i":
					try {
						z = new ZoneDeJeuConcrete(args[indiceOption + 1]);
						indiceOption += 2;
					} catch (Exception e) {
						System.err.println("Impossible de lire la zone : " + e);
						System.exit(2);
					}
					break;
				case "-o":
					fichierSortie = args[indiceOption + 1];
					indiceOption += 2;
					break;
				case "-w":
					width = Integer.parseInt(args[indiceOption + 1]);
					indiceOption += 2;
					break;
				case "-h":
					height = Integer.parseInt(args[indiceOption + 1]);
					indiceOption += 2;
					break;
				default:
					finOptions = true;
			}
		}
		if (indiceOption >= args.length) {
			System.err.println("Utilisation :\n" +
					"TestZone [ -i inputfile ] [ -o outputfile ] [ -w width ] [ -h height ] interactive|random [ graine ] [ nb_iterations ]\n"
					+
					"Ce programme permet de tester l'utilisation d'une zone avec historique soit en prennant ses commandes depuis l'entrée standard (mode interactive),\n"
					+
					"soit en les tirant au hasard (mode random). Si l'option '-i' est donnée, l'état initial de la zone est lu depuis le fichier 'inputfile'. Si l'option\n"
					+
					"'-o' est donnée, l'état final de la zone est écrit dans le fichier 'outputfile'. Lorsque la zone n'est pas lue depuis un fichier, sa largeur et sa\n"
					+
					"hauteur sont données par width et height (5 et 5 par défaut). Les commandes sont les suivantes :\n"
					+
					"- joue valeur ligne colonne\n" +
					"- valeur ligne colonne\n" +
					"- deplace depuis_ligne depuis_colonne vers_ligne vers_colonne\n" +
					"- annule\n" +
					"- refais\n" +
					"- print");
			System.exit(1);
		}
		if (z == null)
			z = new ZoneDeJeuConcrete(height, width);
		switch (args[indiceOption]) {
			case "interactive":
				Scanner s = new Scanner(System.in);
				while (s.hasNextLine()) {
					execute(s.nextLine(), z);
				}
				break;
			case "random":
				Random r;
				int size;

				Point[] directions = new Point[8];
				// Les directions possibles
				size = 0;
				for (int l = -1; l < 2; l++)
					for (int c = -1; c < 2; c++) {
						if ((l != 0) || (c != 0))
							directions[size++] = new Point(c, l);
					}

				indiceOption++;
				try {
					if (indiceOption < args.length) {
						r = new Random(Integer.parseInt(args[indiceOption]));
						indiceOption++;
					} else
						r = new Random(42);
					if (indiceOption < args.length)
						size = Integer.parseInt(args[indiceOption]);
					else
						size = r.nextInt(100) + 100;
					for (int i = 0; i < size; i++) {
						switch (r.nextInt(6)) {
							case 0:
							case 1: {
								int v = r.nextInt(2) + 1;
								int l = r.nextInt(z.nbLignes());
								int c = r.nextInt(z.nbColonnes());
								System.out.println("joue " + v + " " + l + " " + c);
								z.joue(v, l, c);
								break;
							}
							case 2: {
								List<Point> pions = new ArrayList<>();
								// Les pions
								for (int l = 0; l < z.nbLignes(); l++)
									for (int c = 0; c < z.nbColonnes(); c++) {
										if (z.valeurCase(l, c) > 0)
											pions.add(new Point(c, l));
									}
								// S'il y a assez d'espace pour trouver facilement un déplacement
								if ((pions.size() > 0) && (pions.size() < 0.8 * z.nbLignes() * z.nbColonnes())) {
									boolean found = false;

									while (!found) {
										Point pion = pions.get(r.nextInt(pions.size()));
										Point direction = directions[r.nextInt(directions.length)];
										List<Point> libres = new ArrayList<>();

										int l, c;
										l = pion.y + direction.y;
										c = pion.x + direction.x;
										while (z.dansZone(l, c)) {
											if (z.valeurCase(l, c) == 0) {
												libres.add(new Point(c, l));
											}
											l += direction.y;
											c += direction.x;
										}
										if (libres.size() > 0) {
											Point coup = libres.get(r.nextInt(libres.size()));
											System.out.println(
													"deplace " + pion.y + " " + pion.x + " " + coup.y + " " + coup.x);
											z.deplace(pion.y, pion.x, coup.y, coup.x);
											found = true;
										}
									}
								}
								break;
							}
							case 3:
								System.out.println(z);
								break;
							case 4:
								if (z.peutAnnuler()) {
									System.out.println("annule");
									z.annule();
								}
								break;
							case 5:
								if (z.peutRefaire()) {
									System.out.println("refais");
									z.refais();
								}
								break;
						}
					}
				} catch (Exception e) {
					System.err.println("Bug interne dans le jeu aléatoire : " + e);
					System.exit(4);
				}
		}
		if (fichierSortie != null) {
			try {
				z.sauve(fichierSortie);
			} catch (Exception e) {
				System.err.println("Impossible de sauver la zone dans le fichier " + fichierSortie + " : " + e);
				System.exit(3);
			}
		}
	}
}
