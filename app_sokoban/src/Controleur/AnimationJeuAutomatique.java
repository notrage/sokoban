package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import Modele.Coup;
import Modele.GenerateurCoups;
import Modele.Jeu;
import Modele.Niveau;
import Vue.InterfaceGraphique;

public class AnimationJeuAutomatique implements ActionListener {
    InterfaceGraphique inter;
    Jeu jeu;
    Timer timer;
    Random r;
    Animation animationPousseur, animationCaisse;
    ArrayList<Coup> coups;
    GenerateurCoups generateur;
    boolean sauteCoups;

    public AnimationJeuAutomatique(InterfaceGraphique inter, Jeu j, Animation pousseur, Animation caisse) {
        animationPousseur = pousseur;
        animationCaisse = caisse;
        this.inter = inter;
        jeu = j;
        timer = new Timer(500, this);
        sauteCoups = false;
    }

    public void toggleIA(int typeSolver, boolean sauteCoups) {
        if (timer.isRunning()) {
            timer.stop();
        } else {
            this.sauteCoups = sauteCoups;
            generateur = new GenerateurCoups(jeu.niveau());
            if (typeSolver == 0) {
                System.out.println("Lancement solver minimisant les deplacements des caisses (BFS naïf)");
                coups = generateur.solver_BFS();
            } else if (typeSolver == 1) {
                System.out.println("Lancement solver minimisant les deplacements des caisses (A*)");
                coups = generateur.solver_minDeplacementCaisse();
            } else {
                System.out.println("Lancement solver minimisant les deplacements du joueur (A*)");
                coups = generateur.solver_minDeplacementJoueur();
            }
            timer.start();
        }
    }

    public void toggleJump(){
        sauteCoups = !sauteCoups;
    }

    public void actionPerformed(ActionEvent evt) {
        if (coups == null || coups.size() <= 0) {
            return;
        }
        while (animationPousseur.animationEnCours() || animationCaisse.animationEnCours())
            ;
        jeu.niveau().marqueAccessibles(jeu.niveau().pousseurL(), jeu.niveau().pousseurC());
        Niveau tmp = jeu.niveau();
        if (!sauteCoups) {
            Coup c = coups.remove(0);
            if (jeu.deplace(c.directionX(), c.directionY())) {
                if (tmp == jeu.niveau()) {
                    Coup dernierCoup = jeu.dernierCoup();
                    if (dernierCoup.aCaisse()) {
                        jeu.niveau().marqueAccessibles(jeu.niveau().pousseurL(), jeu.niveau().pousseurC());
                    } else {
                        jeu.niveau().resetMarques();
                    }
                    animationPousseur.nouvelleAnimation(dernierCoup.departPousseur(), dernierCoup.arriveePousseur());
                    if (dernierCoup.aCaisse()) {
                        animationCaisse.nouvelleAnimation(dernierCoup.departCaisse(), dernierCoup.arriveeCaisse());
                    }
                    inter.repaint();
                }
            }
        } else {
            Coup c = coups.remove(0);
            while (!c.aCaisse()){
                c = coups.remove(0);
            }
            jeu.niveau().jouerCoup(c, false);
            if (tmp == jeu.niveau()) {
                Coup dernierCoup = jeu.dernierCoup();
                if (dernierCoup.aCaisse()) {
                    jeu.niveau().marqueAccessibles(jeu.niveau().pousseurL(), jeu.niveau().pousseurC());
                } else {
                    jeu.niveau().resetMarques();
                }
                inter.repaint();
            }
        }
    }
}
