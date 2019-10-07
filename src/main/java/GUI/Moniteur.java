package GUI;

public class Moniteur {

    Cabine cabine;

    public Moniteur(Cabine cabine){
        this.cabine = cabine;
    }

    public void chosenFloor(int numFloor){
        System.out.println(numFloor);
        cabine.moveUp();
    }

}
