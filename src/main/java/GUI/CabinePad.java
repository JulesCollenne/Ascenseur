package GUI;

import javax.swing.*;
import java.awt.*;


class CabinePad {

    static JPanel buildPad(){
        JPanel panel = new JPanel();
        panel.setBounds(500,400,200,250);

        panel.setLayout(new GridLayout(6,2));

        JButton bouton0 = new JButton("0");
        JButton bouton1 = new JButton("1");
        JButton bouton2 = new JButton("2");
        JButton bouton3 = new JButton("3");
        JButton bouton4 = new JButton("4");
        JButton bouton5 = new JButton("5");
        JButton bouton6 = new JButton("6");
        JButton bouton7 = new JButton("7");
        JButton bouton8 = new JButton("8");
        JButton bouton9 = new JButton("9");
        JButton bouton10 = new JButton("A");


        panel.add(bouton0);
        panel.add(bouton1);
        panel.add(bouton2);
        panel.add(bouton3);
        panel.add(bouton4);
        panel.add(bouton5);
        panel.add(bouton6);
        panel.add(bouton7);
        panel.add(bouton8);
        panel.add(bouton9);
        panel.add(bouton10);

        panel.setBackground(Color.WHITE);

        return panel;
    }
}