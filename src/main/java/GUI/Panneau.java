package GUI;

import javax.swing.*;
import java.awt.*;

public class Panneau extends JPanel {

    private Immeuble immeuble = new Immeuble(50,0);

    private int x = immeuble.etages[9].x + immeuble.width+5;
    private int y = immeuble.etages[9].y;

    private Cabine cabine = new Cabine(x,y);

    private Moniteur moniteur = new Moniteur(cabine);

    public void paintComponent(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        this.setLayout(null);

        immeuble.poseBoutons(this);

        immeuble.draw(g);
        cabine.draw(g);
    }
}
