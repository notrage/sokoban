package Controleur;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import Vue.InterfaceGraphique;

public class AnimationPousseur implements ActionListener {
    InterfaceGraphique inter;
    Point depart, arrivee;
    int n_etape;
    int max_etape;
    Timer timer;

    public AnimationPousseur(InterfaceGraphique inter) {
        this.inter = inter;
        n_etape = 0;
        max_etape = 4;
        timer = new Timer(100, this);
        timer.start();
    }

    public int etape() {
        return n_etape;
    }
 
    public void actionPerformed(ActionEvent evt) {
        n_etape = (n_etape + 1) % max_etape;
        inter.repaint();
    }

    public void toggle(){
        if (timer.isRunning()){
            timer.stop();
        } else {
            timer.restart();
        }
    }
}
