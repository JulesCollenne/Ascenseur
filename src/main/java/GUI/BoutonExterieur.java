package GUI;

import ControleCommande.Moniteur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoutonExterieur extends JButton {

    int height = 10;
    int width = 10;
    int num;

    boolean montant;

    Moniteur moniteur;

    int x;
    int y;

    public BoutonExterieur(int x, int y, boolean montant){
        this.x = x;
        this.y = y;

        this.montant = montant;

        if(montant)
            this.setLabel("↑");
        else
            this.setLabel("↓");
        this.setLocation(x,y);
        this.setBounds(x,y,44,20);

        if(montant) {
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moniteur.outSideRequest(num, true);
                }
            });
        }
        else{
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moniteur.outSideRequest(num, false);
                }
            });
        }
    }

    /**
     * Affiche les boutons
     * @param g graphics
     */
    void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawOval(x,y,width,height);
    }
}
