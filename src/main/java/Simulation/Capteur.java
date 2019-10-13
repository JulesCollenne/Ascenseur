package Simulation;

import ControleCommande.Moniteur;
import GUI.CabinePrintFloor;

public class Capteur extends Thread {

    private volatile Moniteur moniteur;

    private int capteurs[] = new int[11];

    private int lastYdetected = 750;
    private boolean detection = false;

    public Capteur(Moniteur moniteur, int initial, int floorHeight){
        this.moniteur = moniteur;
        for(int i = 0; i<10; i++){
            capteurs[i] = initial-((i)*floorHeight);
        }
    }

    private void detecteCabine(){

        int getY = moniteur.cabine.position_y_inf;

        for(int i = 0; i<10; i++){
            if(capteurs[i] == getY){
                if(getY != lastYdetected){
                    lastYdetected = getY;
                    detection = true;
                }
            }
        }
    }


    /**
     * Lance le thread capteur
     */
    public void run(){
        while(true) {
            CabinePrintFloor.number.setText(moniteur.currentFloor+"");
            detecteCabine();
            if (detection) {
                detection = false;
                moniteur.detecteCapteur();
            }
        }
    }

}
