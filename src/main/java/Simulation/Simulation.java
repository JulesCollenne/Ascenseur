package Simulation;

import ControleCommande.Moniteur;

import java.util.ArrayList;

public class Simulation extends Thread {

    public volatile Moniteur moniteur;

    volatile boolean detecte = false;

    public Simulation(Moniteur moniteur){
        this.moniteur = moniteur;
    }

    public void run(){
        while(true) {
            if(detecte)
                moniteur.detecteCapteur();
        }
    }
}
