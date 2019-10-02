package GUI;

import javax.swing.JFrame;

public class Fenetre extends JFrame{
    private Panneau pan = new Panneau();

    public Fenetre(){
        this.setTitle("Ascenseur");
        this.setSize(600, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setContentPane(pan);
        this.setVisible(true);
        run();
    }

    private void run(){
        while(true) {
            //pan.cabine.moveUp();
            pan.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}