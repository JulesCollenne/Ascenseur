package GUI;

import javax.swing.*;
import java.awt.*;

public class Panneau extends JPanel {

    Cabine cabine;
    Immeuble immeuble;

    public Panneau(Immeuble immeuble, Cabine cabine){
        this.immeuble= immeuble;
        this.cabine = cabine;
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth()/2, this.getHeight());

        this.setLayout(null);

        immeuble.poseBoutons(this);

        immeuble.draw(g);
        cabine.draw(g);
    }
}
