package ControleCommande;

import GUI.Cabine;
import GUI.CabinePrintFloor;
import GUI.Panneau;

import java.util.ArrayList;

public class Moniteur {

    public volatile Cabine cabine;
    public CabinePrintFloor cabinePrintFloor;

    private static ArrayList<Integer> upQueue = new ArrayList<Integer>();
    private static ArrayList<Integer> downQueue = new ArrayList<Integer>();

    public int currentFloor = 0;
    int maxFloor = 10;
    boolean goingUp = true; //true si la cabine monte, false si elle descend
    int currentCabineRequest = -1;
    boolean floorRequest[] = new boolean[10];
    boolean arretUrgence = false;
    public int currentDestination = -1;

    public Moniteur(){

        for(int i=0; i<10; i++){

            floorRequest[i] = false;

        }

    }

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


        if((currentCabineRequest == -1) && (currentFloor != numFloor) && (!arretUrgence)){  // S'assure que 2 requetes depuis la cabine ne soientt pas acceptés, et que l'on ai aps déja a l'étage demandé
            if(numFloor > currentFloor){
                if(!upQueue.contains(numFloor)){       // s'assure que 2 requete pour le même etage ne soientt pas accepté
                    currentCabineRequest = numFloor;
                    upQueue.add(numFloor);
                    goToFloor(searchNextFloor(), goingUp);
                }
            }
            else{
                if(!downQueue.contains(numFloor)){       // s'assure que 2 requete pour le même etage ne soient pas accepté
                    currentCabineRequest = numFloor;
                    downQueue.add(numFloor);
                    goToFloor(searchNextFloor(), goingUp);
                }
            }
        }

    }


    /**
     * Gère l'arrivée de requêtes des boutons extérieurs
     * @param numFloor le numéro de l'étage
     * @param isAskingUp true si l'on demande à aller vers le haut
     */
    public void outSideRequest(int numFloor, boolean isAskingUp){

        if((!floorRequest[numFloor]) && (currentFloor != numFloor) && (!arretUrgence)) {// vérifie qu'une requete n'a pas deja été faite a cette étage
            floorRequest[numFloor] = true;
            if (isAskingUp) { // s'il demande a monter, on l'ajoute a upQueue
                upQueue.add(numFloor);
                goToFloor(searchNextFloor(), goingUp);  // on va vers la prochaine étape
            } else {   // sinon on l'ajoute a downQueue;
                downQueue.add(numFloor);
                goToFloor(searchNextFloor(), goingUp);   // on va vers la prochaine étape
            }
        }

    }


    /**
     *
     * Gère l'appuie sur l'arrêt d'urgence
     *
     */
    public void arretUrgence(){

        if(!arretUrgence) { // premier appui: clear toute les requetes, passe arret d'urgence a true: plus aucun bouton ne fonctionne
            arretUrgence = true;
            upQueue.clear();
            downQueue.clear();
        }
        else{   // second appui: reset a l'étage 0
            goToFloor(0, false);
            arretUrgence = false;
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
                    if((currentFloor+1 < upQueue.get(i)) && (upQueue.get(i) - currentFloor+1 < nearest)) { nearest = upQueue.get(i) - currentFloor; nearestInd = upQueue.get(i); }
                }
            }
            else{
                for (int i = 0; i < downQueue.size(); i++) {
                    if((currentFloor-1 > downQueue.get(i)) && (currentFloor-1 - downQueue.get(i) < nearest)) { nearest = currentFloor - downQueue.get(i); nearestInd = downQueue.get(i); }
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

        currentDestination = floor;

        if(up) {
            System.out.println("moving up to " + floor + "\n");
            cabine.currentMode = Cabine.mode.Monter;
        }
        else {
            System.out.println("moving down to " + floor + "\n");
            cabine.currentMode = Cabine.mode.Descendre;
            System.out.println("Mode : " + cabine.currentMode);
        }

        Integer object = floor;

        if(floor == currentCabineRequest){
            currentCabineRequest = -1;
        }
        floorRequest[currentFloor] = false;
        upQueue.remove(object);
        downQueue.remove(object);

        // try { sleep(1500); } catch (InterruptedException e) { e.printStackTrace(); } // on attend un peu

        if(upQueue.size() > 0 || downQueue.size() > 0){
            // si pas de requete on fait rien
            goToFloor(searchNextFloor(), goingUp);

        }
    }

    // La cabine envoie un signal ici pour dire que l'ascenseur s'est stoppé a l'étage "floor" (quand elle est en mode arret prochain étage)
    public void isStop(int floor){

        Integer object = floor;

        if(floor == currentCabineRequest){
            currentCabineRequest = -1;
        }
        floorRequest[currentFloor] = false;
        upQueue.remove(object);
        downQueue.remove(object);



        if(upQueue.size() > 0 || downQueue.size() > 0){
            // si pas de requete on fait rien
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
     * Envoie le signal à la machine de s'arreter au prochain etage
     */
    public void detecteCapteur() {

        cabine.estDetecte = true;

        if(goingUp) {
            currentFloor++;
            cabinePrintFloor.number.setText(currentFloor+"");
        }
        else {
            currentFloor--;
            cabinePrintFloor.number.setText(currentFloor+"");
        }

        System.out.println(""+currentFloor);
        int etagesRestant = Math.abs(currentDestination - currentFloor);
        System.out.println("destination: "+currentDestination);
        System.out.println("etage restant: "+etagesRestant);
        if(etagesRestant == 1){
            cabine.currentMode = Cabine.mode.ArretProchainNiv;
        }
        System.out.println();
    }

}
