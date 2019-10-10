package GUI;

import ControleCommande.Moniteur;
import Simulation.Capteur;
import Simulation.Simulation;

import javax.swing.*;
import java.awt.*;

public class Immeuble {
    private int nbEtages = 10;

    private int x;
    private int y;

    private int height = 600;
    int width = 50;
    private int floorHeight = (int) (height / nbEtages)+15;

    public Etage[] etages;

    public Capteur[] capteurs = new Capteur[10];

    public Immeuble(int x, int y, Moniteur moniteur) {
        this.x = x;
        this.y = y;

        etages = new Etage[nbEtages];

        for(int i = 0; i < nbEtages; i++) {
            etages[i] = new Etage(x, floorHeight * i, floorHeight, width, i);
            capteurs[i] = new Capteur(floorHeight * i, i, moniteur);
            capteurs[i].start();
        }
    }

    public void setMoniteurInCapteur(Moniteur moniteur){
        for(int i = 0; i < nbEtages; i++) {
            capteurs[i].setMoniteur(moniteur);
        }
    }

    void draw(Graphics g) {

        for(Etage etage : etages){
            etage.draw(g);
        }
    }

    void poseBoutons(JPanel panel) {
        for(Etage etage : etages){
            etage.poseBoutons(panel);
        }
    }
}
