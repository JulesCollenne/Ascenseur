package GUI;

import javax.swing.*;
import java.awt.*;

class BoutonExterieur extends JButton {

    private int x;
    private int y;

    BoutonExterieur(int x, int y, boolean montant){
        this.x = x;
        this.y = y;

        if(montant)
            this.setLabel("↑");
        else
            this.setLabel("↓");
        this.setLocation(x,y);
        this.setBounds(x,y,44,20);

    }


    /**
     * Affiche les boutons
     * @param g graphics
     */
    void draw(Graphics g) {
        g.setColor(Color.BLUE);
        int height = 10;
        int width = 10;
        g.drawOval(x,y, width, height);
    }
}
