package ControleCommande;

import GUI.Cabine;
import GUI.Panneau;
import Simulation.Simulation;

import java.util.ArrayList;

public class Moniteur {

    public Cabine cabine;
    public Panneau panneau;

    static ArrayList<Integer> upQueue = new ArrayList<Integer>();
    static ArrayList<Integer> downQueue = new ArrayList<Integer>();

    int currentFloor = 0;
    int maxFloor = 10;
    boolean goingUp = true; //true si la cabine monte, false si elle descend
    int actualCabineRequest = -1;


    public void setCabine(Cabine cabine){
        this.cabine = cabine;
    }

    public void setPanneau(Panneau panneau) {
        this.panneau = panneau;
    }

    public void receiveFloorSignal(){
        if(goingUp){
            currentFloor++;
        }
        else{
            currentFloor--;
        }
    }

    public void insideRequest(int numFloor){   // ajoute la requete faite depuis la cabine dans la file d'attente

        if(actualCabineRequest == -1 && currentFloor != numFloor){  // S'assure que 2 requetes depuis la cabine ne soientt pas acceptés, et que l'on ai aps déja a l'étage demandé
            if(numFloor > currentFloor){
                if(!upQueue.contains(numFloor)){       // s'assure que 2 requete pour le même etage ne soientt pas accepté
                    actualCabineRequest = numFloor;
                    upQueue.add(numFloor);
                    goToFloor(searchNextFloor(), goingUp);
                }
            }
            else{
                if(!downQueue.contains(numFloor)){       // s'assure que 2 requete pour le même etage ne soient pas accepté
                    actualCabineRequest = numFloor;
                    downQueue.add(numFloor);
                    goToFloor(searchNextFloor(), goingUp);
                }
            }
        }

    }

    public void outSideRequest(int numFloor, boolean isAskingUp){   // a faire: verifier que 2 requetes ne soit pas faites depuis le même étages

        if(isAskingUp){ // s'il demande a monter, on l'ajoute a upQueue
            upQueue.add(numFloor);
            goToFloor(searchNextFloor(), goingUp);  // on va vers la prochaine étape
        }
        else{   // sinon on l'ajoute a downQueue;
            downQueue.add(numFloor);
            goToFloor(searchNextFloor(), goingUp);   // on va vers la prochaine étape
        }

    }

    public int searchNextFloor(){   // cherche le prochain arrêt de la cabine

        System.out.println("upQueue: ");
        printList(upQueue);
        System.out.println("downQueue: ");
        printList(downQueue);

        if(currentFloor == maxFloor){        // on est en haut donc on cherche la prochaine requete en descendant
            goingUp = false;
            return nearestRequest(false);
        }

        if(currentFloor == 0){   // on est en bas donc on cherche la propchaine requete en montant
            goingUp = true;
            return nearestRequest(true);
        }

        if(upQueue.size()+downQueue.size()==1){     // il ne reste plus qu'une requete, donc on y va peut importe si on monte ou on descend
            int tempNextFloor = nearestRequest(true);
            if(tempNextFloor> currentFloor){
                goingUp = true;
                return tempNextFloor;
            }
            else{
                goingUp = false;
                return tempNextFloor;
            }
        }

        if(goingUp){
            return nearestRequest(true);
        }
        else{
            return nearestRequest(false);
        }

    }

    public int nearestRequest(boolean up) {

        int nearest = 11;
        int nearestInd = 11;

        if(upQueue.size() + downQueue.size() == 1){ // si c'est la derniere requete, on la récupere peu importe le sens de l'ascenseur
            if(upQueue.size()>0){
                return upQueue.get(0);
            }
            else
                return downQueue.get(0);
        }
        else{   // sinon on cherche la plus proche qui sur le chemin (ie dans le meme sens que toi et au dessus si tu monte, en dessous si tu descend)
            if (up) {
                for (int i = 0; i < upQueue.size(); i++) {
                    if((currentFloor < upQueue.get(i)) && (upQueue.get(i) - currentFloor < nearest)) { nearest = upQueue.get(i) - currentFloor; nearestInd = upQueue.get(i); }
                }
            }
            else{
                for (int i = 0; i < downQueue.size(); i++) {
                    if((currentFloor > downQueue.get(i)) && (currentFloor - downQueue.get(i) < nearest)) { nearest = currentFloor - downQueue.get(i); nearestInd = downQueue.get(i); }
                }
            }
            return nearestInd;
        }
    }

    public void goToFloor(int floor, boolean up){       // fait bouger la cabine vers l'étage demandé

        if(up) {
            System.out.println("moving up to " + floor + "\n");
            cabine.currentMode = Cabine.mode.Monter;
        }
        else {
            System.out.println("moving down to " + floor + "\n");
            cabine.currentMode = Cabine.mode.Descendre;
            System.out.println("Mode : " + cabine.currentMode);
        }

        currentFloor = floor;
        Integer object = floor;

        if(floor == actualCabineRequest){ // La requete depuis la cabine a été réalisé, on peut en faire une nouvelle
            actualCabineRequest = -1;
        }

        if(up){
            upQueue.remove(object);
        }
        else{
            downQueue.remove(object);
        }

        // try { sleep(1500); } catch (InterruptedException e) { e.printStackTrace(); } // on attend un peu

        if(upQueue.size() > 0 || downQueue.size() > 0){     // une fois arrivé et attendu, on repart vers la prochaine étape si il y en a dans la liste, sinon on attant la prochaine
            // requete donc on fait rien
            goToFloor(searchNextFloor(), goingUp);

        }
    }

    public void printList(ArrayList<Integer> list){
        for(int i=0; i<list.size();i++){
            System.out.println(i+": "+list.get(i));
        }
    }


    public void emergency() {
        cabine.currentMode = Cabine.mode.ArretUrgence;
    }

    public void boutonExt(int num) {

    }

    public void detecteCapteur() {
        cabine.estDetecte = true;
        System.out.println();
        int etagesRestant = actualCabineRequest - currentFloor;
        if(etagesRestant == 1 || etagesRestant == -1){
            cabine.currentMode = Cabine.mode.ArretProchainNiv;
        }
    }
}
