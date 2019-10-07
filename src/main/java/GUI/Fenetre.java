package GUI;

import javax.swing.*;

public class Fenetre extends JFrame{

    public Fenetre(){
        this.setTitle("Ascenseur");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        Immeuble immeuble = new Immeuble(50,0);

        int x_cabine = immeuble.etages[9].x + immeuble.width+5;
        int y_cabine = immeuble.etages[9].y;

        Cabine cabine = new Cabine(x_cabine,y_cabine);

        Panneau visuPan = new Panneau(immeuble, cabine);

        CabinePad cabinePad = new CabinePad(cabine, visuPan);

        JPanel controlsPan1 = cabinePad.buildPad();
        JPanel controlsPan2 = CabinePrintFloor.buildFloor();

        this.getContentPane().add(controlsPan1, null);
        this.getContentPane().add(controlsPan2, null);
        this.getContentPane().add(visuPan, null);

        this.setVisible(true);
    }
}