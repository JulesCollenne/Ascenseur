package GUI;

import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame{
    private Panneau visuPan = new Panneau();

    public Fenetre(){
        this.setTitle("Ascenseur");
        this.setSize(600, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        CabinePad cabinePad = new CabinePad();
        CabinePrintFloor cabinePF = new CabinePrintFloor();

        JPanel controlsPan = new JPanel();

        cabinePad.buildPad(controlsPan);
        cabinePF.buildFloor(controlsPan);

        this.add(visuPan, BorderLayout.WEST);
        this.add(controlsPan, BorderLayout.EAST);

        this.setContentPane(visuPan);

        this.setVisible(true);
        run();
    }

    private void run(){
        /*
        while(true) {
            //pan.cabine.moveUp();
            visuPan.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        */
    }
}