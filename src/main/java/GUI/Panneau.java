package GUI;

import javax.swing.*;
import java.awt.*;

public class Panneau extends JPanel {

    Cabine cabine = new Cabine(120,420);
    Immeuble immeuble = new Immeuble(50,0,12);

    public void paintComponent(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        this.setLayout(null);

        immeuble.poseBoutons(this);

        immeuble.draw(g);
        cabine.draw(g);
    }
}
