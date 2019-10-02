package GUI;

import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame{

    public Fenetre(){
        this.setTitle("Ascenseur");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        CabinePad cabinePad = new CabinePad();
        CabinePrintFloor cabinePF = new CabinePrintFloor();

        Panneau visuPan = new Panneau();
        JPanel controlsPan1 = cabinePad.buildPad();
        JPanel controlsPan2 = cabinePF.buildFloor();

        this.getContentPane().add(controlsPan1, null);
        this.getContentPane().add(controlsPan2, null);
        this.getContentPane().add(visuPan, null);

        //this.setContentPane(visuPan);

        this.setVisible(true);
    }
}