package Modele;
import java.util.Scanner;

public class InterfaceTextuelle {
	public static void demarrer(Jeu j) {
		RedacteurNiveau r;

		r = new RedacteurNiveau(System.out);
		Scanner s = new Scanner(System.in);
		r.ecrisNiveau(j.niveau());
		while (s.hasNextLine()) {
			String ligne = s.nextLine();
			switch (ligne) {
				case "up":
					j.deplace(-1, 0);
					break;
				case "down":
					j.deplace(1, 0);
					break;
				case "left":
					j.deplace(0, -1);
					break;
				case "right":
					j.deplace(0, 1);
					break;
			}
			r.ecrisNiveau(j.niveau());
		}
		s.close();
	}
}
