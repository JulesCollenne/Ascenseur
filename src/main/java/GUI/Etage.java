package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Etage {

    int x;
    int y;
    int height;
    int width;
    int number;
    Label numberFloor;

    BoutonExterieur[] boutonsExt = new BoutonExterieur[2];;

    Etage(int x, int y, int height, int width,int number) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.number = number;

        boutonsExt[0] = new BoutonExterieur(10, y, true);
        boutonsExt[1] = new BoutonExterieur(10, y + height / 2, false);

        boutonsExt[0].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    void draw(Graphics g){

        g.setColor(Color.gray);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        boutonsExt[0].draw(g);
        boutonsExt[1].draw(g);
    }

    void poseBoutons(JPanel panel) {
        panel.add(boutonsExt[0]);
        panel.add(boutonsExt[1]);
    }
}
