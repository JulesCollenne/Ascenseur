package GUI;

import javax.swing.*;

public class Fenetre extends JFrame{

    public Fenetre(){
        this.setTitle("Ascenseur");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        Panneau visuPan = new Panneau();
        JPanel controlsPan1 = CabinePad.buildPad();
        JPanel controlsPan2 = CabinePrintFloor.buildFloor();

        this.getContentPane().add(controlsPan1, null);
        this.getContentPane().add(controlsPan2, null);
        this.getContentPane().add(visuPan, null);

        this.setVisible(true);
    }
}