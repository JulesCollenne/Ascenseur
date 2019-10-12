package Simulation;

import ControleCommande.Moniteur;

public class Capteur extends Thread {

    private volatile Moniteur moniteur;

    int capteurs[] = new int[11];

    int lastYdetected = 750;
    boolean detection = false;

    public Capteur(Moniteur moniteur, int initial, int floorHeight){
        this.moniteur = moniteur;
        for(int i = 0; i<10; i++){
            capteurs[i] = initial-((i)*floorHeight);
            System.out.println("pos: "+(initial-((i)*floorHeight)));
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
            detecteCabine();
            if (detection) {
                detection = false;
                moniteur.detecteCapteur();
            }
        }
    }

}
