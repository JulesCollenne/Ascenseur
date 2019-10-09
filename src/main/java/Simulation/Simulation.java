package Simulation;

import ControleCommande.Moniteur;

public class Simulation extends Thread {

    Moniteur moniteur;

    public Simulation(Moniteur moniteur){
        this.moniteur = moniteur;
    }

}
