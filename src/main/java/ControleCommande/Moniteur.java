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
    public int currentDestination = 100;
    private boolean entreDeuxEtages = false;
    public boolean isMoving = false;

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
            System.out.println(currentFloor+ ", "+currentDestination);
            if(numFloor > currentFloor){
                if(!upQueue.contains(numFloor)){
                    currentCabineRequest = numFloor;
                    upQueue.add(numFloor);
                    if(isMoving && Math.abs(currentDestination - currentFloor) > 1) {
                        System.out.println("good");
                        currentDestination = searchNextFloor();
                        goToFloor(currentDestination, goingUp);
                    }
                }
            }
            else{
                if(!downQueue.contains(numFloor)){
                    currentCabineRequest = numFloor;
                    downQueue.add(numFloor);
                    if(isMoving && Math.abs(currentDestination - currentFloor) > 1) {
                        System.out.println("good");
                        currentDestination = searchNextFloor();
                        goToFloor(currentDestination, goingUp);
                    }
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
            System.out.println(currentFloor+ ", "+currentDestination);
            floorRequest[numFloor] = true;
            if (isAskingUp) {
                upQueue.add(numFloor);
                if(isMoving && Math.abs(currentDestination - currentFloor) > 1) {
                    System.out.println("good");
                    currentDestination = searchNextFloor();
                    goToFloor(currentDestination, goingUp);
                }
            } else {
                downQueue.add(numFloor);
                if(isMoving && Math.abs(currentDestination - currentFloor) > 1) {
                    System.out.println("good");
                    currentDestination = searchNextFloor();
                    goToFloor(currentDestination, goingUp);
                }
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
    public int searchNextFloor(){
        /*System.out.println("upQueue: ");
        printList(upQueue);
        System.out.println("downQueue: ");
        printList(downQueue);*/

        int maxFloor = 9;
        if (currentFloor == maxFloor) {
            goingUp = false;
            return nearestRequest(false);
        }
        if (currentFloor == 0) {
            goingUp = true;
            return nearestRequest(true);
        }
        if (uneSeuleRequete()) {
            int tempNextFloor = nearestRequest(true);
            if (tempNextFloor > currentFloor) {
                goingUp = true;
                return tempNextFloor;
            } else {
                goingUp = false;
                return tempNextFloor;
            }
        }
        if (goingUp) {
            return nearestRequest(true);
        } else {
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
        int temp = 0;

        if(uneSeuleRequete()){
            if(upQueue.size()>0){
                return upQueue.get(0);
            }
            else
                return downQueue.get(0);
        }
        else{
            if (up) {
                System.out.println("je cherche montée");

                if(isThereRequest(true, true)) {
                    System.out.println("close");
                    if(isMoving)
                        temp = currentFloor + 1;
                    else
                        temp = currentFloor;
                    for (Integer anUpQueue : upQueue) {
                        if ((temp < anUpQueue) && (anUpQueue - temp < nearest)) {
                            nearest = anUpQueue - temp;
                            nearestInd = anUpQueue;
                        }
                    }
                    return nearestInd;
                }
                else{
                    if(isThereRequest(true, false)){
                        return getFarRequest(true, false);
                    }
                    else{
                        goingUp = false;
                        return nearestRequest(false);
                    }
                }
            }
            else{
                if(isThereRequest(false, false)) {
                    System.out.println("je cherche descente");

                    if(isMoving)
                        temp = currentFloor - 1;
                    else
                        temp = currentFloor;
                    for (Integer aDownQueue : downQueue) {
                        if ((temp > aDownQueue) && (temp - aDownQueue < nearest)) {
                            nearest = temp - aDownQueue;
                            nearestInd = aDownQueue;
                        }
                    }

                    return nearestInd;
                }else {
                    if(isThereRequest(false, true)){
                        return getFarRequest(false, true);
                    }
                    else{
                        goingUp = true;
                        return nearestRequest(true);
                    }
                }
            }
        }
    }

    public int getFarRequest(boolean sens, boolean type){

        int max = 0;
        int current = currentFloor;

        if(sens){
            if(type){

                for (Integer aupQueue : upQueue) {
                    if ((aupQueue - currentFloor) > max) {
                        max = aupQueue - currentFloor;
                        current = aupQueue;
                    }
                }

            }
            else {

                for (Integer adownQueue : downQueue) {
                    if ((adownQueue - currentFloor) > max) {
                        max = adownQueue - currentFloor;
                        current = adownQueue;
                    }
                }

            }
        }
        else{
            if(type){

                for (Integer aupQueue : upQueue) {
                    if ((currentFloor - aupQueue) > max) {
                        max = aupQueue - currentFloor;
                        current = aupQueue;
                    }
                }

            }
            else {

                for (Integer adownQueue : downQueue) {
                    if ((currentFloor - adownQueue) > max) {
                        max = adownQueue - currentFloor;
                        current = adownQueue;
                    }
                }

            }
        }
        return current;
    }

    private int searchFarFloorDown(){
        int max = 0;
        int floor = 10;

        for (Integer aDownQueue : downQueue) {
            if (Math.abs(currentFloor - aDownQueue) > max) {
                max = Math.abs(currentFloor - aDownQueue);
                floor = aDownQueue;
            }
        }

        goingUp = currentFloor - floor <= 0;

        return floor;
    }
    private int searchFarFloorUp(){
        int max = 0;
        int floor = 10;

        for (Integer aUpQueue : upQueue) {
            if (Math.abs(currentFloor - aUpQueue) > max) {
                max = Math.abs(currentFloor - aUpQueue);
                floor = aUpQueue;
            }
        }

        goingUp = currentFloor - floor <= 0;
        System.out.println(floor);

        return floor;
    }

    /**
     * fait bouger la cabine vers l'étage demandé
     * La requete depuis la cabine a été réalisé, on peut en faire une nouvelle
     * une fois arrivé et attendu, on repart vers la prochaine étape si il y en a dans la liste, sinon on attant la prochaine
     * @param floor l'etage choisi
     * @param up si l'on va en haut
     */
    public void goToFloor(int floor, boolean up){
        currentDestination = floor;
        goingUp = up;
        isMoving = true;

        System.out.println("je vais a " +floor);

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
    public boolean resteRequete(){
        return upQueue.size() > 0 || downQueue.size() > 0;
    }

    /**
     * La cabine envoie un signal ici pour dire que l'ascenseur s'est stoppé a l'étage "floor" (quand elle est en mode arret prochain étage)
      */
    public void isStop(){
        Integer object = currentFloor;
        currentCabineRequest = -1;
        isMoving = false;
        if(object == currentCabineRequest){
            currentCabineRequest = -1;
        }
        floorRequest[currentFloor] = false;
        upQueue.remove(object);
        downQueue.remove(object);
        arretUrgence = false;

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

    public boolean isThereRequest(boolean sens, boolean type) {
        if (sens) {
            if (type) {
                for (Integer floor : upQueue) {
                    if (floor - currentFloor > 0) {
                        return true;
                    }
                }
            } else {
                for (Integer floor : downQueue) {
                    if (floor - currentFloor > 0) {
                        return true;
                    }
                }
            }
        } else {
            if (type) {
                for (Integer floor : upQueue) {
                    if (currentFloor - floor > 0) {
                        return true;
                    }
                }
            } else {
                for (Integer floor : downQueue) {
                    if (currentFloor - floor > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * S'il n'y a aucune requete, aller au RDC
     */
    public void detectAFK(){
        goToFloor(0, false);
    }
}
