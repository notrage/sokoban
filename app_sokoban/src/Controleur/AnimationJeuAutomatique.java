package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import Modele.Coup;
import Modele.GenerateurCoups;
import Modele.Jeu;
import Vue.InterfaceGraphique;

public class AnimationJeuAutomatique implements ActionListener {
    InterfaceGraphique inter;
    Jeu jeu;
    Timer timer;
    Random r;
    Animation animationPousseur, animationCaisse;
    ArrayList<Coup> coups;
    GenerateurCoups generateur;

    public AnimationJeuAutomatique(InterfaceGraphique inter, Jeu j, Animation pousseur, Animation caisse) {
        animationPousseur = pousseur;
        animationCaisse = caisse;
        this.inter = inter;
        jeu = j;
        timer = new Timer(1000, this);
    }

    public void toggleIA() {
        if (timer.isRunning()) {
            timer.stop();
        } else {
            generateur = new GenerateurCoups(jeu.niveau());
            generateur.solver();
        }
    }

    public void actionPerformed(ActionEvent evt) {
        if (coups.size() <= 0){
            return;
        }
        while (animationPousseur.animationEnCours() || animationCaisse.animationEnCours())
            ;
        Coup c = coups.remove(0);
        if (jeu.deplace(c.directionX(), c.directionY())) {
            Coup dernierCoup = jeu.dernierCoup();
            jeu.marquer(dernierCoup.departPousseur().x, dernierCoup.departPousseur().y);
            animationPousseur.nouvelleAnimation(dernierCoup.departPousseur(), dernierCoup.arriveePousseur());
            if (dernierCoup.aCaisse()) {
                animationCaisse.nouvelleAnimation(dernierCoup.departCaisse(), dernierCoup.arriveeCaisse());
            }
            inter.repaint();
        }

    }
}
