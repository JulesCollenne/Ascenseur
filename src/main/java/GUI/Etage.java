package GUI;

import ControleCommande.Moniteur;
import Simulation.Capteur;

import javax.swing.*;
import java.awt.*;

public class Etage {

    int x;
    int y;
    int height;
    int width;
    int number;

    BoutonExterieur[] boutonsExt = new BoutonExterieur[2];

    public Etage(int x, int y, int height, int width,int number) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.number = number;

        boutonsExt[0] = new BoutonExterieur(10, y, true);
        boutonsExt[1] = new BoutonExterieur(10, y + height / 2, false);
    }

    public void draw(Graphics g){

        g.setColor(Color.gray);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        boutonsExt[0].draw(g);
        boutonsExt[1].draw(g);
    }

    public void poseBoutons(JPanel panel) {
        panel.add(boutonsExt[0]);
        panel.add(boutonsExt[1]);
    }
}
