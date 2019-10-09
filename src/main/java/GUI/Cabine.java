package GUI;

import javax.swing.*;
import java.awt.*;

public class Cabine {

    int height = 600/10 + 15;
    int width = 50;

    int position_x_sup;
    int position_y_sup;

    int position_x_inf;
    int position_y_inf;

    public enum mode {Monter, Descendre, ArretUrgence, ArretProchainNiv, Arret};

    public mode currentMode = mode.Arret;

    int speed = 2;

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

    public void moveUp(){
        currentMode = mode.Monter;
        while(currentMode == mode.Monter) {
            position_y_sup -= speed;
        }
    }

    void moveDown(){
        position_y_sup += speed;
    }

    void stopNextFloor(){

    }

    void emergencyStop(){
        currentMode = mode.ArretUrgence;
    }
}
