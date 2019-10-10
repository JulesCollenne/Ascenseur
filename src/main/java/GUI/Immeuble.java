package GUI;

import ControleCommande.Moniteur;
import Simulation.Capteur;
import Simulation.Simulation;

import javax.swing.*;
import java.awt.*;

class Immeuble {
    private int nbEtages = 10;

    private int x;
    private int y;

    private int height = 600;
    private int width = 50;
    private int floorHeight = (int) (height / nbEtages)+15;

    private Etage[] etages;

    private Capteur[] capteurs = new Capteur[10];

    Immeuble(int x, int y, Moniteur moniteur) {
        this.x = x;
        this.y = y;

        etages = new Etage[nbEtages];

        for(int i = 0; i < nbEtages; i++) {
            etages[i] = new Etage(x, floorHeight * i, floorHeight, width, i);
            capteurs[i] = new Capteur(floorHeight * i, i, moniteur);
            capteurs[i].start();
        }
    }

    /**
     * Set le moniteur
     * @param moniteur le moniteur
     */
    void setMoniteurInCapteur(Moniteur moniteur){
        for(int i = 0; i < nbEtages; i++) {
            capteurs[i].setMoniteur(moniteur);
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
