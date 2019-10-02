package GUI;

import javax.swing.*;
import java.awt.*;

public class Panneau extends JPanel {

    Immeuble immeuble = new Immeuble(50,0);

    int x = immeuble.etages[9].x + immeuble.width+5;
    int y = immeuble.etages[9].y;

    Cabine cabine = new Cabine(x,y);

    public void paintComponent(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        this.setLayout(null);

        immeuble.poseBoutons(this);

        immeuble.draw(g);
        cabine.draw(g);
    }
}
