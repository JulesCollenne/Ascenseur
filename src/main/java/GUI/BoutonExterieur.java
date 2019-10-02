package GUI;

import javax.swing.*;
import java.awt.*;

public class BoutonExterieur extends JButton {

    int height = 10;
    int width = 10;

    boolean montant;

    int x;
    int y;

    public BoutonExterieur(int x, int y, boolean montant){
        this.x = x;
        this.y = y;
        this.montant = montant;

        if(montant)
            this.setLabel("^");
        else
            this.setLabel("v");
        this.setLocation(x,y);
        this.setBounds(x,y,44,20);
    }

    void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawOval(x,y,width,height);
    }
}
