package GUI;

import java.awt.*;

public class BoutonExterieur {

    int height = 10;
    int width = 10;

    boolean montant;

    int x;
    int y;

    public BoutonExterieur(int x, int y, boolean montant){
        this.x = x;
        this.y = y;
        this.montant = montant;
    }

    void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawOval(x,y,width,height);
    }
}
