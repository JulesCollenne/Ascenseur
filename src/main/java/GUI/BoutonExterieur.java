package GUI;

import javax.swing.*;
import java.awt.*;

class BoutonExterieur extends JButton {

    private int height = 10;
    private int width = 10;

    private boolean montant;

    private int x;
    private int y;

    BoutonExterieur(int x, int y, boolean montant){
        this.x = x;
        this.y = y;
        this.montant = montant;

        if(montant)
            this.setLabel("↑");
        else
            this.setLabel("↓");
        this.setLocation(x,y);
        this.setBounds(x,y,44,20);
    }

    void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawOval(x,y,width,height);
    }
}
