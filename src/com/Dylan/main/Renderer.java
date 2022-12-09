package com.Dylan.main;

import com.Dylan.render.Player;
import com.Dylan.render.World;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel implements Runnable {
    int FPS = 60;
    public final int columns = 8;
    public final int rows = 4;
    public final int scale = 3;
    public final int blockSize = scale * 32;
    public int screenWidth = columns * blockSize;
    public int screenHeight = rows * blockSize;
    public final int speed = 3;
    public static final int worldSize = 64;

    KeyHandler keyH = new KeyHandler();
    Player player = new Player(this, keyH);
    World world = new World(this, keyH);
    Thread renderThread;

    public Renderer() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.setBackground(new Color(26, 16, 4));
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startThread() {
        renderThread = new Thread(this);
        renderThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000F / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while(renderThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        screenWidth = (int) Main.window.getSize().getWidth();
        screenHeight = (int) Main.window.getSize().getHeight();
        player.update();
        world.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        player.draw(g2);
        world.draw(g2);
        g2.dispose();
    }
}
