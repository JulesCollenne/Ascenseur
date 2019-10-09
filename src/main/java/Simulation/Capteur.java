package Simulation;

import ControleCommande.Moniteur;

public class Capteur extends Thread {

    Moniteur moniteur;

    int positionY;
    int num_etage;

    public Capteur(int positionY, int num_etage, Moniteur moniteur){
        this.positionY = positionY;
        this.num_etage = num_etage;
        this.moniteur = moniteur;
    }

    public boolean detecteCabine(){
        return positionY == moniteur.cabine.position_y_inf;
    }

    public void run(){
        while(true){
            if(detecteCabine()){
                moniteur.detecteCapteur();
            }
        }
    }
}
