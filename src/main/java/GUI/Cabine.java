package GUI;

import java.awt.*;

class Cabine {

    private int height = 600/10 + 15;
    private int width = 50;

    private int x;
    private int y;

    private int speed = 2;

    Cabine(int x, int y) {
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
