package GUI;

import javax.swing.*;
import java.awt.*;

class CabinePrintFloor {
    static JPanel buildFloor(){
        JPanel panel = new JPanel();
        panel.setBounds(550, 300, 100 , 100);
        JTextArea floor = new JTextArea("Floor : ");
        floor.setEditable(false);
        Font font = new Font("Verdana", Font.BOLD, 24);
        floor.setFont(font);

        JTextArea number = new JTextArea("1");
        number.setEditable(false);
        number.setText("3");
        number.setFont(font);

        panel.add(floor);
        panel.add(number);
        panel.setBackground(Color.WHITE);

        return panel;
    }
}
