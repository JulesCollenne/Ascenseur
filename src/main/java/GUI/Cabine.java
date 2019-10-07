package GUI;

import javax.swing.*;
import java.awt.*;

class Cabine {

    int height = 600/10 + 15;
    int width = 50;

    int position_x_sup;
    int position_y_sup;

    int position_x_inf;
    int position_y_inf;

    enum mode {Monter, Descendre, ArretUrgence, ArretProchainNiv};


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

    void moveUp(){
        position_y_sup -= speed;
    }

    void moveDown(){
        position_y_sup += speed;
    }

    void stopNextFloor(){

    }

    void emergencyStop(){

    }
}
