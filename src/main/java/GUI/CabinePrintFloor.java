package GUI;

import ControleCommande.Moniteur;

import javax.swing.*;
import java.awt.*;

public class CabinePrintFloor {


    public static JTextArea number;

    /**
     * Construit le panel
     * @return le panel
     */
    static JPanel buildFloor(Moniteur moniteur){
        JPanel panel = new JPanel();
        panel.setBounds(550, 300, 100 , 100);
        JTextArea floor = new JTextArea("Floor : ");
        floor.setEditable(false);
        Font font = new Font("Verdana", Font.BOLD, 24);
        floor.setFont(font);

        number = new JTextArea("");
        number.setEditable(false);
        number.setText(moniteur.currentFloor+"");
        number.setFont(font);

        panel.add(floor);
        panel.add(number);
        panel.setBackground(Color.WHITE);

        return panel;
    }

}
