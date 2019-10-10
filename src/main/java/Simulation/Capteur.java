package Simulation;

import ControleCommande.Moniteur;

public class Capteur extends Thread {

    private Moniteur moniteur;

    private int positionY;
    private int num_etage;

    public Capteur(int positionY, int num_etage, Moniteur moniteur){
        this.positionY = positionY;
        this.num_etage = num_etage;
        this.moniteur = moniteur;
    }

    private boolean detecteCabine(){
        return positionY == moniteur.cabine.position_y_inf;
    }

    /**
     * Lance le thread capteur
     */
    public void run(){
        while(true) {
            if (detecteCabine()) {
                moniteur.detecteCapteur();
            }
        }
    }

    /**
     * Set le moniteur
     * @param moniteur le moniteur
     */
    public void setMoniteur(Moniteur moniteur) {
        this.moniteur = moniteur;
    }
}
