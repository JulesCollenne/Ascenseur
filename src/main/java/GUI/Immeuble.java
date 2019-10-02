package GUI;

import javax.swing.*;
import java.awt.*;

class Immeuble {
    private int nbEtages = 10;

    private int x;
    private int y;

    int height = 600;
    int width = 50;
    int floorHeight = (int) (height / nbEtages)+15;

    Etage[] etages;


    Immeuble(int x, int y) {
        this.x = x;
        this.y = y;

        etages = new Etage[nbEtages];

        for(int i = 0; i < nbEtages; i++)
            etages[i] = new Etage(x, floorHeight* i, floorHeight,width, i);
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
