package ControleCommande;

import GUI.Cabine;

import java.util.ArrayList;

/**
 * Le moniteur est le controle commande qui gère la file de requète et change le mode de la cabine
 */
public class Moniteur {

    public volatile Cabine cabine;

    private static ArrayList<Integer> upQueue = new ArrayList<Integer>();
    private static ArrayList<Integer> downQueue = new ArrayList<Integer>();

    public int currentFloor = 0;
    public boolean goingUp = true;
    private int currentCabineRequest = -1;
    private boolean floorRequest[] = new boolean[10];
    private boolean arretUrgence = false;
    private int currentDestination = -1;
    private boolean entreDeuxEtages = false;

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
    private boolean requeteInterneValide(int numFloor){
        return (!cabineAUneRequete() && currentFloor != numFloor) && !arretUrgence;
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
        if(requeteInterneValide(numFloor)){
            if(numFloor > currentFloor){
                if(!upQueue.contains(numFloor)){
                    currentCabineRequest = numFloor;
                    upQueue.add(numFloor);
                    goToFloor(searchNextFloor(), goingUp);
                }
            }
            else{
                if(!downQueue.contains(numFloor)){
                    currentCabineRequest = numFloor;
                    downQueue.add(numFloor);
                    goToFloor(searchNextFloor(), goingUp);
                }
            }
            detecteCapteurActuel();
        }
    }

    /**
     *  vérifie qu'une requete n'a pas deja été faite a cette étage
     * @param numFloor le num de l'etage
     * @return true si la requete est valide
     */
    private boolean requeteExterneValide(int numFloor){
        return (!floorRequest[numFloor]) && (currentFloor != numFloor) && (!arretUrgence);
    }

    /**
     * Gère l'arrivée de requêtes des boutons extérieurs
     * @param numFloor le numéro de l'étage
     * @param isAskingUp true si l'on demande à aller vers le haut
     */
    public void outSideRequest(int numFloor, boolean isAskingUp){
        if(requeteExterneValide(numFloor)) {
            floorRequest[numFloor] = true;
            if (isAskingUp) {
                upQueue.add(numFloor);
                goToFloor(searchNextFloor(), goingUp);
            } else {
                downQueue.add(numFloor);
                goToFloor(searchNextFloor(), goingUp);
            }
        }
    }

    /**
     *
     * Gère l'appui sur l'arrêt d'urgence
     * premier appui: clear toute les requetes, passe arret d'urgence a true: plus aucun bouton ne fonctionne
     * second appui: reset a l'étage 0
     *
     */
    public void arretUrgence(){
        if(!arretUrgence) {
            arretUrgence = true;
            upQueue.clear();
            downQueue.clear();
            currentCabineRequest = -1;

            if(cabine.currentMode == Cabine.mode.Monter || cabine.currentMode == Cabine.mode.Descendre || cabine.currentMode == Cabine.mode.ArretProchainNiv)
                entreDeuxEtages = true;

            for(int i =0; i<10; i++)
                floorRequest[i] = false;
            cabine.currentMode = Cabine.mode.ArretUrgence;
        }
        else {
            resetCabine();
        }
    }

    /**
     * Après le second appui de l'arret d'urgence, fait redescndre la cabine au RDC
     */
    private void resetCabine(){
        goingUp = false;
        currentDestination = 0;
        if(currentFloor < 1 && entreDeuxEtages) {
            cabine.currentMode = Cabine.mode.ArretProchainNiv;
        }
        if(currentFloor < 1 && !entreDeuxEtages){
            cabine.currentMode = Cabine.mode.Arret;
        }
        if(currentFloor >= 1) {
            cabine.currentMode = Cabine.mode.Descendre;
        }
    }


    /**
     * cherche le prochain arrêt de la cabine
     * @return le prochain arret de la cabine
     */
    private int searchNextFloor(){
        /*System.out.println("upQueue: ");
        printList(upQueue);
        System.out.println("downQueue: ");
        printList(downQueue);*/

        int maxFloor = 9;
        if(currentFloor == maxFloor){
            goingUp = false;
            return nearestRequest(false);
        }
        if(currentFloor == 0){
            goingUp = true;
            return nearestRequest(true);
        }
        if(uneSeuleRequete()){
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

    /**
     *
     * @return true si une seule requete
     */
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
        int nearest = 10;
        int nearestInd = 10;

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
                    if ((currentFloor + 1 < anUpQueue) && (anUpQueue - currentFloor + 1 < nearest) || ((cabine.currentMode == Cabine.mode.Arret) && (currentFloor < anUpQueue) && (anUpQueue - currentFloor < nearest))) {
                        nearest = anUpQueue - currentFloor + 1;
                        nearestInd = anUpQueue;
                    }
                }
                if(nearest == 10 && downQueue.size() != 0){
                    goingUp = false;
                    return searchClosestFloor();
                }
            }
            else{
                for (Integer aDownQueue : downQueue) {
                    if ((currentFloor - 1 > aDownQueue) && (currentFloor - 1 - aDownQueue < nearest)) {
                        nearest = currentFloor - 1 - aDownQueue;
                        nearestInd = aDownQueue;
                    }
                }
                if(nearest == 10){
                    goingUp = true;
                    return searchClosestFloor();
                }
            }
            return nearestInd;
        }
    }

    private int searchClosestFloor(){
        int min = 10;
        int floor = 10;

        for (Integer anUpQueue : upQueue) {
            if (Math.abs(currentFloor - anUpQueue) < min) {
                min = Math.abs(currentFloor - anUpQueue);
                floor = anUpQueue;
            }
        }
        for (Integer aDownQueue : downQueue) {
            if (Math.abs(currentFloor - aDownQueue) < min) {
                min = Math.abs(currentFloor - aDownQueue);
                floor = aDownQueue;
            }
        }

        goingUp = currentFloor - floor <= 0;

        return floor;
    }

    /**
     * fait bouger la cabine vers l'étage demandé
     * La requete depuis la cabine a été réalisé, on peut en faire une nouvelle
     * une fois arrivé et attendu, on repart vers la prochaine étape si il y en a dans la liste, sinon on attant la prochaine
     * @param floor l'etage choisi
     * @param up si l'on va en haut
     */
    private void goToFloor(int floor, boolean up){
        currentDestination = floor;
        goingUp = up;

        if(Math.abs(currentDestination - currentFloor) > 1) {
            if (up) {
                cabine.currentMode = Cabine.mode.Monter;
            } else {
                cabine.currentMode = Cabine.mode.Descendre;
            }
        }
        else{
            cabine.currentMode = Cabine.mode.ArretProchainNiv;
        }
    }

    /**
     *
     * @return true s'il n'y a aucune requete dans les files
     */
    private boolean aucuneRequete(){
        return upQueue.size() > 0 || downQueue.size() > 0;
    }

    /**
     * La cabine envoie un signal ici pour dire que l'ascenseur s'est stoppé a l'étage "floor" (quand elle est en mode arret prochain étage)
      */
    public void isStop(){
        Integer object = currentFloor;

        if(object == currentCabineRequest){
            currentCabineRequest = -1;
        }
        floorRequest[currentFloor] = false;
        upQueue.remove(object);
        downQueue.remove(object);
        arretUrgence = false;

        if(aucuneRequete()){
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
        int etagesRestant;
        cabine.estDetecte = true;

        if(arretUrgence){
            if(!entreDeuxEtages) {
                if (goingUp) {
                    currentFloor++;
                } else {
                    currentFloor--;
                }
            }
            etagesRestant = Math.abs(currentDestination - currentFloor);
            System.out.println(entreDeuxEtages);
            if(etagesRestant == 1){
                cabine.estDetecte = false;
                cabine.currentMode = Cabine.mode.ArretProchainNiv;
            }
            if(etagesRestant == 0){
                cabine.estDetecte = false;
                cabine.currentMode = Cabine.mode.Arret;
                arretUrgence = false;
                entreDeuxEtages = false;
                return;
            }
        }
        else {
            if (goingUp) {
                currentFloor++;
            } else {
                currentFloor--;
            }
            etagesRestant = Math.abs(currentDestination - currentFloor);

            if (etagesRestant == 1) {
                cabine.estDetecte = false;
                cabine.currentMode = Cabine.mode.ArretProchainNiv;
            }
            if (etagesRestant == 0) {
                cabine.estDetecte = false;
                cabine.currentMode = Cabine.mode.Arret;
            }
        }
        entreDeuxEtages = false;
    }

    /**
     * Ecoute le capteur actuel
     */
    private void detecteCapteurActuel(){
        cabine.estDetecte = false;

        int etagesRestant = Math.abs(currentDestination - currentFloor);

        cabine.goingUp = currentDestination - currentFloor > 0;

        if(etagesRestant == 1){
            cabine.estDetecte = false;
            cabine.currentMode = Cabine.mode.ArretProchainNiv;
        }
    }

    /**
     * S'il n'y a aucune requete, aller au RDC
     */
    public void detectAFK(){
        goToFloor(0, false);
    }
}
