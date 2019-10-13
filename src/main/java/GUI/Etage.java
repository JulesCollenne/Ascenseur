package GUI;

import javax.swing.*;
import java.awt.*;

class Etage {

    private int x;
    private int y;
    private int height;
    private int width;

    BoutonExterieur[] boutonsExt = new BoutonExterieur[2];

    Etage(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;

        boutonsExt[0] = new BoutonExterieur(10, y, true);
        boutonsExt[1] = new BoutonExterieur(10, y + height / 2, false);
    }


    /**
     * Dessine l'etage
     * @param g graphics
     */
    void draw(Graphics g){

        g.setColor(Color.gray);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        boutonsExt[0].draw(g);
        boutonsExt[1].draw(g);
    }

    /**
     * Pose les boutons exterieurs
     * @param panel le panneau auquel on ajoute les boutons exterieurs
     */
    void poseBoutons(JPanel panel) {
        panel.add(boutonsExt[0]);
        panel.add(boutonsExt[1]);
    }
}
