package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



class CabinePad {

    static JPanel buildPad(){
        JPanel panel = new JPanel();
        panel.setBounds(500,400,200,250);

        panel.setLayout(new GridLayout(6,2));

        JButton boutons[] = new JButton[10];

        for(int i = 0; i < 10; i++){
            boutons[i] = new JButton(String.valueOf(i));
            panel.add(boutons[i]);
        }

        JButton boutonA = new JButton("A");

        panel.add(boutonA);

        for(int i=0; i<boutons.length; i++) {
            final int finalI = i;
            boutons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Moniteur.chosenFloor(finalI);
                }
            });
        }

        panel.setBackground(Color.WHITE);

        return panel;
    }
    /*salut*/
}