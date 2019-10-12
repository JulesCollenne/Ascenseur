package GUI;

import ControleCommande.Moniteur;

import javax.swing.*;
import java.awt.*;

public class Cabine extends Thread {

    private int height = 600/10 + 15;
    private int width = 50;

    int afkTime = 0;

    Moniteur moniteur;

    private int position_x_sup;
    private int position_y_sup;

    private int position_x_inf;
    public volatile int position_y_inf;

    public boolean estDetecte;
    private Panneau panneau;

    void setPanneau(Panneau panneau) {
        this.panneau = panneau;
    }

    public enum mode {Monter, Descendre, ArretUrgence, ArretProchainNiv, Arret};
    public boolean goingUp;

    public volatile mode currentMode = mode.Arret;

    private int speed = 1;

    Cabine(int x, int y) {
        this.position_x_inf = x;
        this.position_x_sup = x;
        this.position_y_sup = y;
        this.position_y_inf = y + height;
    }

    public void setMoniteur(Moniteur moniteur){

        this.moniteur = moniteur;

    }

    /**
     * Affiche la cabine
     * @param g graphics
     */
    void draw(Graphics g) {
        g.setColor(Color.magenta);
        g.fillRect(position_x_sup, position_y_sup, width,height);
    }

    /**
     * S'arrete au prochain etage
     */
    private void stopNextFloor(){
        if(moniteur.goingUp){
            if(!estDetecte){
                position_y_sup -= speed;
                position_y_inf -= speed;
            }
            else{
                estDetecte = false;
                currentMode = mode.Arret;
            }
        }
        else{
            if(!estDetecte){
                position_y_sup += speed;
                position_y_inf += speed;
            }
            else{
                estDetecte = false;
                currentMode = mode.Arret;
            }
        }
    }


    /**
     * Lancement du thread Cabine
     */
    public void run(){
        while(true){
            switch(currentMode){
                case Monter:
                    afkTime = 0;
                    goingUp = true;
                    position_y_sup -= speed;
                    position_y_inf -= speed;
                    break;
                case Descendre:
                    afkTime = 0;
                    goingUp = false;
                    position_y_sup += speed;
                    position_y_inf += speed;
                    break;
                case ArretUrgence:
                    afkTime = 0;
                    break;
                case ArretProchainNiv:
                    afkTime = 0;
                    stopNextFloor();
                    break;
                case Arret:
                    try {
                        if((moniteur.currentFloor != 0) && (afkTime >= 3))
                            moniteur.detectAFK();
                        moniteur.isStop();
                        sleep(2000);
                        afkTime++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            panneau.repaint();
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
