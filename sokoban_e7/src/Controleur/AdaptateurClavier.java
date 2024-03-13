package Controleur;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import Modele.Jeu;
import Vue.InterfaceGraphique;

public class AdaptateurClavier extends KeyAdapter {
	InterfaceGraphique inter;
	Jeu jeu;

	public AdaptateurClavier(InterfaceGraphique i, Jeu j) {
		inter = i;
		jeu = j;
	}

	void deplace(int l, int c) {
		jeu.deplace(l, c);
		inter.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				deplace(-1, 0);
				break;
			case KeyEvent.VK_DOWN:
				deplace(1, 0);
				break;
			case KeyEvent.VK_LEFT:
				deplace(0, -1);
				break;
			case KeyEvent.VK_RIGHT:
				deplace(0, 1);
				break;
			case KeyEvent.VK_A:
			case KeyEvent.VK_Q:
				System.exit(0);
			case KeyEvent.VK_ESCAPE:
				inter.toggleFullscreen();
				break;
		}
	}
}
