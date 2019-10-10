package ControleCommande;

import GUI.Cabine;
import GUI.Panneau;

import java.util.ArrayList;

public class Moniteur {

    public Cabine cabine;

    private static ArrayList<Integer> upQueue = new ArrayList<Integer>();
    private static ArrayList<Integer> downQueue = new ArrayList<Integer>();

    private int currentFloor = 0;
    private boolean goingUp = true;
    private int currentCabineRequest = -1;


    public void setCabine(Cabine cabine){
        this.cabine = cabine;
    }

    /**
     * S'assure que 2 requetes depuis la cabine ne soient pas acceptés, et que l'on ai pas déja a l'étage demandé
     * s'assure que 2 requete pour le même etage ne soient pas accepté
     * @param numFloor le numéro de l'étage demandé dans la requête
     * @return si la requête est valide selon les critère du dessus
     */
    private boolean requeteValide(int numFloor){
        return (!cabineAUneRequete() && currentFloor != numFloor) && !upQueue.contains(numFloor);
    }

    /**
     *
     * @return true si la cabine a reçu une requête à cet étage
     */
    private boolean cabineAUneRequete(){
        return currentCabineRequest > -1;
    }

    /**
     * ajoute la requete faite depuis la cabine dans la file d'attente
     * @param numFloor le numéro de l'étage demandé
     */
    public void insideRequest(int numFloor){

        if(requeteValide(numFloor)){
            if(numFloor > currentFloor){
                currentCabineRequest = numFloor;
                upQueue.add(numFloor);
                goToFloor(searchNextFloor(), goingUp);
            }
            else{
                if(!downQueue.contains(numFloor)){
                    currentCabineRequest = numFloor;
                    downQueue.add(numFloor);
                    goToFloor(searchNextFloor(), goingUp);
                }
            }
        }

    }

    //TODO : verifier que 2 requetes ne soit pas faites depuis le même étages
    /**
     * Gère l'arrivée de requêtes des boutons extérieurs
     * @param numFloor le numéro de l'étage
     * @param isAskingUp true si l'on demande à aller vers le haut
     */
    public void outSideRequest(int numFloor, boolean isAskingUp){
        if(isAskingUp){
            upQueue.add(numFloor);
            goToFloor(searchNextFloor(), goingUp);
        }
        else{
            downQueue.add(numFloor);
            goToFloor(searchNextFloor(), goingUp);
        }

    }

    /**
     * cherche le prochain arrêt de la cabine
     * @return le prochain arret de la cabine
     */
    private int searchNextFloor(){
        System.out.println("upQueue: ");
        printList(upQueue);
        System.out.println("downQueue: ");
        printList(downQueue);

        int maxFloor = 10;
        if(currentFloor == maxFloor){
            goingUp = false;
            return nearestRequest(false);
        }

        if(currentFloor == 0){
            goingUp = true;
            return nearestRequest(true);
        }

        if(upQueue.size()+downQueue.size()==1){
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

    private boolean uneSeuleRequete(){
        return upQueue.size() + downQueue.size() == 1;
    }

    /**
     * si c'est la derniere requete, on la récupere peu importe le sens de l'ascenseur
     * sinon on cherche la plus proche qui sur le chemin (ie dans le meme sens que toi et au dessus si tu monte, en dessous si tu descend)
     * @param up true si l'on va vers le haut
     * @return La requete la plus proche
     */
    private int nearestRequest(boolean up) {

        int nearest = 11;
        int nearestInd = 11;

        if(uneSeuleRequete()){
            if(upQueue.size()>0){
                return upQueue.get(0);
            }
            else
                return downQueue.get(0);
        }
        else{
            if (up) {
                for (Integer anUpQueue : upQueue) {
                    if ((currentFloor < anUpQueue) && (anUpQueue - currentFloor < nearest)) {
                        nearest = anUpQueue - currentFloor;
                        nearestInd = anUpQueue;
                    }
                }
            }
            else{
                for (Integer aDownQueue : downQueue) {
                    if ((currentFloor > aDownQueue) && (currentFloor - aDownQueue < nearest)) {
                        nearest = currentFloor - aDownQueue;
                        nearestInd = aDownQueue;
                    }
                }
            }
            return nearestInd;
        }
    }

    /**
     * fait bouger la cabine vers l'étage demandé
     * La requete depuis la cabine a été réalisé, on peut en faire une nouvelle
     * une fois arrivé et attendu, on repart vers la prochaine étape si il y en a dans la liste, sinon on attant la prochaine
     * @param floor
     * @param up
     */
    private void goToFloor(int floor, boolean up){

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

        if(floor == currentCabineRequest){
            currentCabineRequest = -1;
        }

        if(up){
            upQueue.remove(object);
        }
        else{
            downQueue.remove(object);
        }

        // try { sleep(1500); } catch (InterruptedException e) { e.printStackTrace(); } // on attend un peu

        if(upQueue.size() > 0 || downQueue.size() > 0){     //
            // requete donc on fait rien
            goToFloor(searchNextFloor(), goingUp);

        }
    }

    /**
     * Affiche les requetes
     * @param list la liste de requetes
     */
    private void printList(ArrayList<Integer> list){
        for(int i=0; i<list.size();i++){
            System.out.println(i+": "+list.get(i));
        }
    }

    /**
     * Envoie le signal d'urgence à la cabine
     */
    public void emergency() {
        cabine.currentMode = Cabine.mode.ArretUrgence;
    }

    /**
     * Envoie le signal à la machine de s'arreter au prochain etage
     */
    public void detecteCapteur() {
        cabine.estDetecte = true;
        System.out.println();
        int etagesRestant = currentCabineRequest - currentFloor;
        if(etagesRestant == 1 || etagesRestant == -1){
            cabine.currentMode = Cabine.mode.ArretProchainNiv;
        }
    }
}
