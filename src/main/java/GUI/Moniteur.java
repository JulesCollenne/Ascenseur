package GUI;

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
}
