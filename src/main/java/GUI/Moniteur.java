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

    int actualFloor = 0;
    int maxFloor = 10;
    boolean goingUp = true;

    public void insideRequest(int numFloor){

        if(numFloor > actualFloor){
            upQueue.add(numFloor);
            goToFloor(searchNextFloor(), goingUp);
        }
        else{
            downQueue.add(numFloor);
            goToFloor(searchNextFloor(), goingUp);
        }

    }

    public void outSideRequest(int numFloor, boolean up){

        if(up){
            upQueue.add(numFloor);
            goToFloor(searchNextFloor(), up);
        }
        else{
            downQueue.add(numFloor);
            goToFloor(searchNextFloor(), up);
        }

    }

    public int searchNextFloor(){

        System.out.println("upQueue: ");
        printList(upQueue);
        System.out.println("downQueue: ");
        printList(downQueue);

        if(actualFloor == maxFloor){
            goingUp = false;
            return nearestRequest(false);
        }
        if(actualFloor == 0){
            goingUp = true;
            return nearestRequest(true);
        }
        if(upQueue.size()+downQueue.size()==1){
            int down = nearestRequest(false);
            int up = nearestRequest(true);
            int min = Math.min(down, up);
            if(min == down)
                goingUp = false;
            else
                goingUp = true;

            return min;
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

        if (up) {
            for (int i = 0; i < upQueue.size(); i++) {
                if((actualFloor < upQueue.get(i)) && (upQueue.get(i) - actualFloor < nearest)) { nearest = upQueue.get(i) - actualFloor; nearestInd = upQueue.get(i); }
            }
        }
        else{
            for (int i = 0; i < downQueue.size(); i++) {
                if((actualFloor > downQueue.get(i)) && (actualFloor - downQueue.get(i) < nearest)) { nearest = actualFloor - downQueue.get(i); nearestInd = downQueue.get(i); }
            }
        }

        return nearestInd;
    }

    public void goToFloor(int floor, boolean up){

        System.out.println("moving to "+floor+"\n");
        //va au floor en faisant bouger l'ascenseur
        actualFloor = floor;
        Integer object = floor;
        if(up){
            upQueue.remove(object);
        }
        else{
            downQueue.remove(object);
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
}
