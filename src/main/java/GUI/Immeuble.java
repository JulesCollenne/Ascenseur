package GUI;

import java.awt.*;

class Immeuble {
    private int nbEtages = 12;

    private int x;
    private int y;

    int height = 500;
    int width = 50;
    int floorHeight = (int) (height / nbEtages) + 1;


    Immeuble(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void draw(Graphics g) {

        g.setColor(Color.gray);
        g.fillRect(x, y, width, height);

        for(int i = 0; i < nbEtages; i++) {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, floorHeight * i);
        }
    }
}
