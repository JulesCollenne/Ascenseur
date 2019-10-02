package GUI;

import java.awt.*;

class Immeuble {
    private int nbEtages = 12;

    private int x;
    private int y;

    int height = 500;
    int width = 50;
    int floorHeight = (int) (height / nbEtages) + 1;

    BoutonExterieur[] boutonsExt;

    Immeuble(int x, int y, int nbEtages) {
        this.x = x;
        this.y = y;
        this.nbEtages = nbEtages;
        boutonsExt = new BoutonExterieur[nbEtages * 2];
        for(int i = 0; i < boutonsExt.length; i ++) {
            boutonsExt[i] = new BoutonExterieur(10, floorHeight/2 * i, true);
            i++;
            boutonsExt[i] = new BoutonExterieur(10, floorHeight/2 * i, false);
        }
    }

    void draw(Graphics g) {

        g.setColor(Color.gray);
        g.fillRect(x, y, width, height);

        for(int i = 0; i < nbEtages; i++) {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, floorHeight * i);
        }

        for (BoutonExterieur boutonExterieur : boutonsExt) {
            boutonExterieur.draw(g);
        }

    }
}
