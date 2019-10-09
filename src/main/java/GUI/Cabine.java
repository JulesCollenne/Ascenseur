package GUI;

import javax.swing.*;
import java.awt.*;

public class Cabine extends Thread {

    int height = 600/10 + 15;
    int width = 50;

    int position_x_sup;
    int position_y_sup;

    int position_x_inf;
    public int position_y_inf;

    public boolean estDetecte;
    private Panneau panneau;

    public void setPanneau(Panneau panneau) {
        this.panneau = panneau;
    }

    public enum mode {Monter, Descendre, ArretUrgence, ArretProchainNiv, Arret};
    boolean goingUp;

    public volatile mode currentMode = mode.Arret;

    int speed = 1;

    public Cabine(int x, int y) {
        this.position_x_sup = x;
        this.position_y_sup = y;
        this.position_x_inf = x + width;
        this.position_y_inf = y + height;
    }

    void draw(Graphics g) {
        g.setColor(Color.magenta);
        g.fillRect(position_x_sup, position_y_sup, width,height);
    }

    void moveDown(){
        position_y_sup += speed;
    }

    void stopNextFloor(){
        if(goingUp){
            if(!estDetecte){
                position_x_inf--;
            }
            else{
                currentMode = mode.Arret;
            }
        }
        else{
            if(!estDetecte){
                position_x_inf++;
            }
            else{
                estDetecte = false;
                currentMode = mode.Arret;
            }
        }
    }

    public void run(){
        while(true){
            switch(currentMode){
                case Monter:
                    goingUp = true;
                    position_y_sup -= speed;
                    break;
                case Descendre:
                    position_y_sup += speed;
                    goingUp = false;
                    break;
                case ArretUrgence:

                    break;
                case ArretProchainNiv:
                    stopNextFloor();
                    break;
                case Arret:
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            panneau.repaint();
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
