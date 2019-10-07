package GUI;

import javax.swing.*;
import java.awt.*;

class Cabine {

    int height = 600/10 + 15;
    int width = 50;

    int x;
    int y;

    int speed = 2;

    public Cabine(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void draw(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, width,height);
    }

    void moveUp(){
        y -= speed;
    }

    void moveDown(){
        y += speed;
    }
}
