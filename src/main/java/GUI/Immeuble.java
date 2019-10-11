package GUI;

import ControleCommande.Moniteur;
import Simulation.Capteur;
import Simulation.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Immeuble {
    private int nbEtages = 10;

    private int x;
    private int y;

    private int height = 600;
    private int width = 50;
    private int floorHeight = (int) (height / nbEtages)+15;

    private Etage[] etages;

    volatile Moniteur moniteur;

    Immeuble(int x, int y, final Moniteur moniteur) {
        this.x = x;
        this.y = y;

        this.moniteur = moniteur;

        etages = new Etage[nbEtages];

        for(int i = 0; i < nbEtages; i++) {
            etages[i] = new Etage(x, floorHeight * i, floorHeight, width, i);
        }

        Capteur capteurs = new Capteur(moniteur, 750, floorHeight);
        capteurs.start();

        for(int i = 0; i<nbEtages; i++) {
            final int finalI = nbEtages-i-1;
            etages[i].boutonsExt[0].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moniteur.outSideRequest(finalI, true);
                }
            });
            etages[i].boutonsExt[1].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moniteur.outSideRequest(finalI, false);
                }
            });
        }
    }

    /**
     * Set le moniteur
     * @param moniteur le moniteur
     */
   /* void setMoniteurInCapteur(Moniteur moniteur){
        for(int i = 0; i < nbEtages; i++) {
            capteurs[i].setMoniteur(moniteur);
        }
    }*/

    /**
     * Dessine l'immeuble
     * @param g graphics
     */
    void draw(Graphics g) {
        for(Etage etage : etages){
            etage.draw(g);
        }
    }

    /**
     * Pose les boutons exterieurs
     * @param panel le panneau auquel on va poser les boutons
     */
    void poseBoutons(JPanel panel) {
        for(Etage etage : etages){
            etage.poseBoutons(panel);
        }
    }
}
