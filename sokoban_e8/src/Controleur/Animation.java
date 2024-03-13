package Controleur;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import Vue.InterfaceGraphique;

public class Animation implements ActionListener {
    InterfaceGraphique inter;
    Point depart, arrivee;
    int n_etape;
    int max_etape;
    Timer timer;

    public Animation(InterfaceGraphique inter) {
        this.inter = inter;
        n_etape = -1;
        max_etape = 20;
    }

    public Point depart() {
        return depart;
    }

    public Point arrivee() {
        return arrivee;
    }

    public boolean animationEnCours() {
        return n_etape >= 0;
    }

    public void nouvelleAnimation(Point d, Point a) {
        if (!animationEnCours()) {
            n_etape = 0;
            timer = new Timer(15, this);
            timer.start();
            depart = d;
            arrivee = a;
            System.out.println("Animation demarrée !");
        }
    }

    public int etape() {
        return n_etape;
    }

    public int maxEtape() {
        return max_etape;
    }

    public void actionPerformed(ActionEvent evt) {
        n_etape++;
        if (n_etape >= max_etape) {
            System.out.println("Animation finie !");
            n_etape = -1;
            timer.stop();
        }
        inter.repaint();
    }

    public void stop() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
            n_etape = -1;
        }

    }
}
