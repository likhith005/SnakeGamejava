//import java.awt.Graphics;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;



import javax.swing.JPanel;

//extends in Java means is-a. So your class is a JPanel; it's inheritance; a fundamental part of OOP.
// Since your class is a JPanel, if you want to set it's background color somewhere inside the class
// you'd just do setBackground(yourColor) from outside would be ui.
//Panel:
//JPanel is a class that represents a lightweight container within a JFrame or another container.
// It is often used to group and organize components together. JPanel doesn't have its own window decorations
// and is usually used as a building block for more complex GUI layouts.
// It's useful for creating sub-sections within a larger interface or for adding components that need to be
// updated or modified dynamically.
public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT) /UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int [GAME_UNITS];
    final int y[] = new int [GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random() ;
        this.setPreferredSize(new Dimension (SCREEN_WIDTH,SCREEN_HEIGHT)) ;
        this.setBackground (Color .black);
        this.setFocusable (true);
        this.addKeyListener (new MyKeyAdapter ()) ;
        startGame ();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font .BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten,(SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
    }
    public void move() {
//        for (int i = bodyParts;i>0;i--) {
//            x[i] = x[i-1];
//            y[i] = y[i-1];
//        }
//        switch (direction) {
//            case 'U':
//                y[0] = y[0] - UNIT_SIZE;
//                break;
//            case 'D':
//                y[0] = y[0] + UNIT_SIZE;
//                break;
//            case 'L':
//                x[0] = x[0] - UNIT_SIZE;
//                break;
//            case 'R':
//                x[0] = x[0] + UNIT_SIZE;
//                break;
//        }

            for (int i = bodyParts; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }
            switch (direction) {
                case 'U':
                    y[0] = (y[0] - UNIT_SIZE + SCREEN_HEIGHT) % SCREEN_HEIGHT;
                    break;
                case 'D':
                    y[0] = (y[0] + UNIT_SIZE) % SCREEN_HEIGHT;
                    break;
                case 'L':
                    x[0] = (x[0] - UNIT_SIZE + SCREEN_WIDTH) % SCREEN_WIDTH;
                    break;
                case 'R':
                    x[0] = (x[0] + UNIT_SIZE) % SCREEN_WIDTH;
                    break;
            }
    }



    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }
    public void checkCollisions(){
        //checks if head collides with body
        for (int i = bodyParts;i>0;i--) {
            if((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        //check if head touches left border
        if (x[0] < 0) {
            running = false;
        }
        //check if head touches right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        //check if head touches top border
        if (y[0] < 0) {
            running = false;
        }
        //check if head touches bottom border
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        //Score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font .BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten,(SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        //Game Over text
        g.setColor (Color.red);
        g.setFont(new Font("Ink Free", Font .BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over",(SCREEN_WIDTH - metrics2.stringWidth("Game over"))/2, SCREEN_HEIGHT/2) ;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
