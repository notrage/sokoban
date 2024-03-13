package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import javax.swing.Timer;

import Vue.InterfaceGraphique;

public class Animation implements ActionListener {
    InterfaceGraphique inter;
    Point2D depart, arrivee;
    int n_etape;
    int max_etape;
    Timer timer;

    public Animation(InterfaceGraphique inter) {
        this.inter = inter;
        n_etape = -1;
        max_etape = 20;
    }

    public Point2D depart() {
        return depart;
    }

    public Point2D arrivee() {
        return arrivee;
    }

    public boolean animationEnCours() {
        //System.out.println("n_etape:" + n_etape);
        return n_etape >= 0;
    }

    public void nouvelleAnimation(Point2D d, Point2D a) {
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
}
