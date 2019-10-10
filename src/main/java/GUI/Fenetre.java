package GUI;

import ControleCommande.Moniteur;

import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame{

    Moniteur moniteur;

    public Fenetre(Moniteur moniteur){

        this.moniteur = moniteur;

        this.setTitle("Ascenseur");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.setBackground(Color.WHITE);

        int x_cabine = 105;//immeuble.etages[9].x + immeuble.width+5;
        int y_cabine = 675;//immeuble.etages[9].y;

        Cabine cabine = new Cabine(x_cabine,y_cabine);
        cabine.start();

        moniteur.setCabine(cabine);

        Immeuble immeuble = new Immeuble(50,0, moniteur);

        Panneau visuPan = new Panneau(immeuble, cabine);
        cabine.setPanneau(visuPan);

        CabinePad cabinePad = new CabinePad(moniteur);

        moniteur.setPanneau(visuPan);

        immeuble.setMoniteurInCapteur(moniteur);

        JPanel controlsPan1 = cabinePad.buildPad();
        JPanel controlsPan2 = CabinePrintFloor.buildFloor();

        this.setBackground(Color.WHITE);

        this.getContentPane().add(controlsPan1, null);
        this.getContentPane().add(controlsPan2, null);
        this.getContentPane().add(visuPan, null);

        this.setVisible(true);
    }
}