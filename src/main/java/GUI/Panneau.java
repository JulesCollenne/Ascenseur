package GUI;

import javax.swing.*;
import java.awt.*;

public class Panneau extends JPanel {

    private volatile Cabine cabine;
    private Immeuble immeuble;

    Panneau(Immeuble immeuble, Cabine cabine){
        this.immeuble= immeuble;
        this.cabine = cabine;
    }

    /**
     * Dessine le panneau contenant les dessins de la simulation
     * @param g graphics
     */
    public void paintComponent(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth()/2, this.getHeight());
        this.setLayout(null);

        immeuble.poseBoutons(this);

        immeuble.draw(g);
        cabine.draw(g);
    }
}
