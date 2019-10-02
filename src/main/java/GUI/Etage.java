package GUI;

import java.awt.*;

public class Etage {

    int height;
    int width;

    BoutonExterieur[] boutonsExt;

    public Etage(int height, int width) {
        this.height = height;
        this.width = width;

        boutonsExt = new BoutonExterieur[2];

        boutonsExt[0] = new BoutonExterieur(10, height/2, true);
        boutonsExt[1] = new BoutonExterieur(10, height, false);
    }

    public void draw(Graphics g){

    }
}
