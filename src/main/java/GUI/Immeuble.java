package GUI;

import ControleCommande.Moniteur;
import Simulation.Capteur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Immeuble {

    private Etage[] etages;

    Immeuble(int x, final Moniteur moniteur) {

        int nbEtages = 10;
        etages = new Etage[nbEtages];

        int height = 600;
        int floorHeight = (height / nbEtages) + 15;
        for(int i = 0; i < nbEtages; i++) {
            int width = 50;
            etages[i] = new Etage(x, floorHeight * i, floorHeight, width);
        }

        Capteur capteurs = new Capteur(moniteur, 750, floorHeight);
        capteurs.start();

        for(int i = 0; i< nbEtages; i++) {
            final int finalI = nbEtages -i-1;
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
