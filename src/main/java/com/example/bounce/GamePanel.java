package com.example.bounce;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static javafx.scene.CacheHint.SPEED;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 800;
    static final int UNIT_SIZE = 25;
    static int SPEEDX = 4;
    static int SPEEDY = 8;
    static int SPEED = 10;
    static final int DELAY = 10;
    static int scoreA = 0;
    static int scoreB = 0;
    static int rallyScore = 0;
    int x1 = 45;
    int y1 = 20;
    int x2 = 730;
    int y2 = 20;
    final int handleSize = 7;
    int ballX = 120;
    int ballY;
    boolean running = false;
    static boolean up = false;
    static boolean down = false;
    static boolean up1 = false;
    static boolean down1 = false;
    static int score = 0;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(255, 220, 200));
        this.setFocusable(true);
        this.addKeyListener(new KeyListener());
        startGame();
    }

    public void moveA () {
        if (y1 > 0 && y1 + UNIT_SIZE * handleSize < SCREEN_HEIGHT) {
            if (up){
                y1 -= SPEED;
            }
            if (down){
                y1 += SPEED;
            }
        }
        if (y1 <= 0 && down) {
            y1 += SPEED;
        }
        if (y1 + UNIT_SIZE * handleSize >= SCREEN_HEIGHT && up) {
            y1 -= SPEED;
        }
    }
    public void moveB () {
        if (y2 > 0 && y2 + UNIT_SIZE * handleSize < SCREEN_HEIGHT) {
            if (up1){
                y2 -= SPEED;
            }
            if (down1){
                y2 += SPEED;
            }
        }
        if (y2 <= 0 && down1) {
            y2 += SPEED;
        }
        if (y2 + UNIT_SIZE * handleSize >= SCREEN_HEIGHT && up1) {
            y2 -= SPEED;
        }
    }
    public void moveBall() {
        if (checkCollisionX()){
            rallyScore ++;
            SPEEDX = -SPEEDX;
        }
        if (checkCollisionY()){
            SPEEDY = -SPEEDY;
        }
        ballX += SPEEDX;
        ballY += SPEEDY;

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void startGame() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void scoreTally() {
        if (ballX < UNIT_SIZE && checkCollisionX()){
            rallyScore = 0;
            scoreA += 1;
            ballX = SCREEN_WIDTH/2 - (int)UNIT_SIZE/2;
            ballY = SCREEN_HEIGHT/2 - (int)UNIT_SIZE/2;
            SPEEDY = SPEEDY > 0 ? -4 : 4;
            SPEEDX = SPEEDX > 0 ? -8 : 8;
        }
        if (ballX >= 775 && checkCollisionX()){
            rallyScore = 0;
            scoreB += 1;
            ballX = SCREEN_WIDTH/2 - (int)UNIT_SIZE/2;
            ballY = SCREEN_HEIGHT/2 - (int)UNIT_SIZE/2;
            SPEEDX = SPEEDX > 0 ? -4 : 4;
            SPEEDY = SPEEDY > 0 ? -8 : 8;
        }
        if(checkCollisionX() && SPEEDX < 6 && SPEEDY < 12) {
            SPEEDX += SPEEDX > 0 ? 1 : -1;
            SPEEDY += SPEEDY > 0 ? 1 : -1;
        }

    }

//    public void updateRally(){
//        if(ballX <= x1 + UNIT_SIZE && checkCollisionX() && ballX >= x1){
//            rallyScore ++;
//        } else if(ballX >= x2 + UNIT_SIZE && checkCollisionX() && ballX <= x2 + UNIT_SIZE){
//            rallyScore ++;
//        }
//    }

    public void draw (Graphics g) {
        if(running){
            g.setColor(Color.BLACK);
            g.drawLine(SCREEN_WIDTH/2, 0, SCREEN_WIDTH/2, SCREEN_HEIGHT);
            g.drawOval(SCREEN_WIDTH/2 - UNIT_SIZE*3, SCREEN_HEIGHT/2 - UNIT_SIZE*3, UNIT_SIZE*6, UNIT_SIZE*6);
            g.fillOval(ballX, ballY, UNIT_SIZE, UNIT_SIZE);
            g.fillRect(x1, y1, UNIT_SIZE, UNIT_SIZE*handleSize);
            g.fillRect(x2, y2, UNIT_SIZE, UNIT_SIZE*handleSize);
            if (ballX < UNIT_SIZE){
                g.setColor(Color.GREEN);
            }      else {
                g.setColor(new Color(0, 75, 0));
            }
            g.fillRect(0, 0, UNIT_SIZE, SCREEN_HEIGHT);
            if (ballX >= 775){
                g.setColor(new Color(0, 200, 200));
            } else {
                g.setColor(new Color(0, 30, 225));
            }
            g.fillRect(775, 0, UNIT_SIZE, SCREEN_HEIGHT);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            g.drawString("Score B: " + scoreA, SCREEN_WIDTH/2 + metrics.stringWidth("Score B: " + scoreA), g.getFont().getSize() );
            g.drawString("Score A: " + scoreB, SCREEN_WIDTH/2 - 2*metrics.stringWidth("Score A: " + scoreB), g.getFont().getSize() );
                g.drawString("Consecutive hits: "+ rallyScore, SCREEN_WIDTH/2, 760);

        }
    }
    public void checkBallLoss(){

    }
    public boolean checkCollisionX() {
        return ballX < 0 || ballX + UNIT_SIZE > SCREEN_WIDTH ||
                (ballY >= y1 && ballY <= y1 + UNIT_SIZE*handleSize && ballX < x1 + UNIT_SIZE) ||
                (ballY >= y2 && ballY <= y2 + UNIT_SIZE*handleSize && ballX > x2 - UNIT_SIZE);
    }
    public boolean checkCollisionY() {
        return ballY < 0 || ballY + UNIT_SIZE> SCREEN_HEIGHT;
    }
    public class KeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_W){
                up = true;
            } else if(event.getKeyCode() == KeyEvent.VK_S){
                down = true;
            }
            if(event.getKeyCode() == KeyEvent.VK_UP){
                up1 = true;
            } else if(event.getKeyCode() == KeyEvent.VK_DOWN){
                down1 = true;
            }

        }
        @Override
        public void keyReleased(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_W){
                up = false;
            }
            else if(event.getKeyCode() == KeyEvent.VK_S){
                down = false;
            }
            if(event.getKeyCode() == KeyEvent.VK_UP){
                up1 = false;
            } else if(event.getKeyCode() == KeyEvent.VK_DOWN){
                down1 = false;
            }

        }
    }
    @Override
    public void actionPerformed(ActionEvent event){
        if(running){
            moveBall();
            moveA();
            moveB();
            scoreTally();
//            updateRally();
//            checkCollision();
        }
        repaint();
    }
}
