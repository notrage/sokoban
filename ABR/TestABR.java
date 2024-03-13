// NE PAS MODIFIER : ce fichier est écrasé lors de l'évaluation et toute modification sera complètement ignorée
import java.io.PrintStream;
import java.util.*;

public abstract class TestABR {
	static Iterator<Integer> it = null;
	static ArbreBinaireDeRecherche<Integer> abr;

	static void handleCommand(String line) {
		String[] words = line.split("\\s+");

		if (words.length > 0) {
			try {
				switch (words[0]) {
					case "default":
						abr.racine = new Noeud<>(
								new Noeud<>(17),
								42,
								new Noeud<>(
										new Noeud<>(64),
										96,
										new Noeud<>(128)
								)
						);
						break;
					case "add":
						for (int i = 1; i < words.length; i++) {
							abr.add(Integer.parseInt(words[i]));
						}
						break;
					case "find":
						for (int i = 1; i < words.length; i++) {
							System.out.print(abr.find(Integer.parseInt(words[i])) + " ");
						}
						System.out.println();
						break;
					case "print":
						System.out.println(abr);
						break;
					case "dot":
						PrintStream out = new PrintStream(words[1]);
						out.println(abr.toDot());
						out.close();
						break;
					case "iter":
						Iterator<Integer> i = abr.iterator();
						while (i.hasNext()) {
							System.out.print(i.next() + " ");
						}
						System.out.println();
						break;
					case "start":
						it = abr.iterator();
						break;
					case "next":
						if (it == null)
							System.out.println("Pas d'iterateur defini");
						else
							System.out.println(it.next());
						break;
					case "remove":
						if (it == null)
							System.out.println("Pas d'iterateur defini");
						else
							it.remove();
						break;
					default:
						System.err.println("Commande inconnue");
				}
			} catch (Exception e) {
				System.out.println(e+"");
			}
		}
	}

	public static void main(String[] args) {
		abr = new ArbreBinaireDeRechercheConcret<>();

		System.err.println("Ce programme fonctionne selon plusieurs modes distincts :\n" +
				"- avec deux arguments, il effectue un test consistant à répéter aléatoirement des ajouts dans l'arbre et des suppressions.\n" +
				"  Les arguments correspondent dans l'ordre à :\n" +
				"  - la graine utilisée pour les tirages aléatoires\n" +
				"  - le nombre d'opérations réalisées (ajouts ou goupes de suppressions)\n" +
				"- sans arguments il se met en mode interactif, avec un arbre initialement vide, et accepte les commandes suivantes :\n" +
				"  - defaut : fabrique un petit arbre utilisé par défaut\n" +
				"  - add number [ number ...] : ajoute à l'arbre les nombres donnés, dans l'ordre\n" +
				"  - find number [ number ...] : recherche dans l'arbre les nombres donnés, dans l'ordre\n" +
				"  - print : affiche l'arbre sous forme parenthésée\n" +
				"  - dot fichier : sauve un affichage de l'arbre au format dot dans le fichier de nom donné\n" +
				"  - iter : utilise un itérateur pour parcourir et afficher les éléments contenus dans l'arbre\n" +
				"  - start : crée un itérateur persistant pour l'arbre\n" +
				"  - next : fait avancer l'itérateur persistant et affiche l'élément récupéré\n" +
				"  - remove : supprime de l'arbre le dernier élément récupéré via l'itérateur persistant");

		if (args.length > 0) {
			long seed = Long.parseLong(args[0]);
			Random r = new Random(seed);
			int count = Integer.parseInt(args[1]);
			List<Integer> l = new ArrayList<>();
			for (int i=0; i<count; i++) {
				boolean suppress = false;
				ListIterator<Integer> itListe;
				Iterator<Integer> itArbre;
				if (r.nextInt(5) < 4) {
					int number = r.nextInt(1000);
					System.out.println("Ajout de " + number);
					abr.add(number);
					itListe = l.listIterator();
					if (itListe.hasNext()) {
						int element = itListe.next();
						while (itListe.hasNext() && (element < number)) {
							element = itListe.next();
						}
						if (element > number)
							itListe.previous();
					}
					itListe.add(number);
				} else {
					suppress = true;
				}
				int position = 0;
				itArbre = abr.iterator();
				itListe = l.listIterator();
				while (itArbre.hasNext()) {
					position++;
					int number = itListe.next();
					if (!itArbre.next().equals(number)) {
						System.err.println("Echec de la vérification de l'ABR, incohérence en position " + position);
					}
					if (suppress && r.nextBoolean()) {
						System.out.println("Suppression de " + number);
						itArbre.remove();
						itListe.remove();
					}
				}
				System.out.println("Arbre : " + abr);
				System.out.println("liste : " + l);
			}
		} else {
			Scanner s = new Scanner(System.in);
			while (s.hasNextLine()) {
				String line = s.nextLine();
				handleCommand(line);
			}
		}
		System.out.println("Exécution terminée, arguments : " + java.util.Arrays.toString(args));
	}
}
