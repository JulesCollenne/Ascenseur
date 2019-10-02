package GUI;

import javax.swing.*;
import java.awt.*;

class Immeuble {
    private int nbEtages = 12;

    private int x;
    private int y;

    int height = 800;
    int width = 50;
    int floorHeight = (int) (height / nbEtages) - 5;

    Etage[] etages;


    Immeuble(int x, int y, int nbEtages) {
        this.x = x;
        this.y = y;
        this.nbEtages = nbEtages;

        etages = new Etage[nbEtages];

        for(int i = 0; i < nbEtages; i++)
            etages[i] = new Etage(x, floorHeight* i, floorHeight,width);
    }

    void draw(Graphics g) {

        for(int i = 0; i < nbEtages; i++) {

        }

        for(Etage etage : etages){
            etage.draw(g);
        }
    }

    public void poseBoutons(JPanel panel) {
        for(Etage etage : etages){
            etage.poseBoutons(panel);
        }
    }
}
