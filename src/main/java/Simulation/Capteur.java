package Simulation;

import ControleCommande.Moniteur;

public class Capteur extends Thread {

    private volatile Moniteur moniteur;

    int capteurs[] = new int[9];

    public Capteur(Moniteur moniteur, int initial, int floorHeight){
        this.moniteur = moniteur;
        for(int i = 0; i<9; i++){

            capteurs[i] = initial-((i+1)*floorHeight);
            System.out.println("new capteurs at "+capteurs[i]);

        }
    }

    private boolean detecteCabine(){

        int getY = moniteur.cabine.position_y_inf;
        for(int i = 0; i<9; i++){
            if(capteurs[i] == getY)
                return true;
        }

        return false;

    }

    /**
     * Lance le thread capteur
     */
    public void run(){
        while(true) {
            if (detecteCabine()) {
                System.out.println("DETECTE");
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
