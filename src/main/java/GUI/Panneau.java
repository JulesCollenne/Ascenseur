package GUI;

import javax.swing.*;
import java.awt.*;

public class Panneau extends JPanel {

    Cabine cabine;
    Immeuble immeuble;
    boolean justMade = true;

    public Panneau(Immeuble immeuble, Cabine cabine){
        this.immeuble= immeuble;
        this.cabine = cabine;
    }

    public void paintComponent(Graphics g){
        if(justMade){
            g.setColor(Color.white);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            justMade = false;
        }
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth()/2, this.getHeight());

        this.setLayout(null);

        immeuble.poseBoutons(this);

        immeuble.draw(g);
        cabine.draw(g);
    }
}
