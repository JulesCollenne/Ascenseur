package GUI;

import java.util.ArrayList;

public class Moniteur {

    Cabine cabine;
    Panneau panneau;

    public void setCabine(Cabine cabine){
        this.cabine = cabine;
    }

    public void chosenFloor(int numFloor){
        System.out.println(numFloor);
        cabine.moveUp();
        panneau.repaint();
    }

    public void setPanneau(Panneau panneau) {
        this.panneau = panneau;
    }

    static ArrayList<Integer> upQueue = new ArrayList<Integer>();
    static ArrayList<Integer> downQueue = new ArrayList<Integer>();

    static int actualFloor = 0;
    static int maxFloor = 10;
    static boolean goingUp = true;

    public static void insideRequest(int numFloor){

        if(numFloor > actualFloor){
            upQueue.add(numFloor);
            searchNextFloor();
        }
        else{
            downQueue.add(numFloor);
            searchNextFloor();
        }

    }

    public static void outSideRequest(int numFloor, boolean up){

        if(up){
            upQueue.add(numFloor);
            searchNextFloor();
        }
        else{
            downQueue.add(numFloor);
            searchNextFloor();
        }

    }

    public static int searchNextFloor(){

        if(actualFloor == maxFloor){
            return nearestRequest(false);
        }
        if(actualFloor == 0){
            return nearestRequest(true);
        }
        if(upQueue.size()+downQueue.size()==1){
            return Math.min(nearestRequest(true), nearestRequest(false));
        }

        if(goingUp){
            return nearestRequest(true);
        }
        else{
            return nearestRequest(false);
        }

    }

    public static int nearestRequest(boolean up) {

        int nearest = 11;
        int nearestInd = 11;

        if (up) {
            for (int i = 0; i < upQueue.size(); i++) {
                if(Math.abs(actualFloor - upQueue.get(i)) < nearest) { nearest = upQueue.get(i); nearestInd = i; }
            }
        }

        return nearestInd;
    }

    public void goToNextFloor(){



    }
}
