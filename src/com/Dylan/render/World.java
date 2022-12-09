package com.Dylan.render;

import com.Dylan.main.KeyHandler;
import com.Dylan.main.Renderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class World {
    Renderer rd;
    KeyHandler keyH;
    BufferedImage dirt, grass;
    public int speed, offsetX, offsetY, lastWidth, lastHeight, lastX, lastY, velocity, gravity;
    public boolean canJump;
    int[][] worldPoints = new int[2][12];
    int[][] rawWorld = new int[64][64];
    public Polygon world = new Polygon();
    public Shape worldBox = world;

    public World(Renderer rd, KeyHandler keyH) {
        this.rd = rd;
        this.keyH = keyH;

        setDefaultValues();
        getWorldImages();
    }

    public void setDefaultValues() {
        canJump = false;
        velocity = 0;
        gravity = 1;
        speed = rd.scale * rd.speed;
        offsetX = 0;
        offsetY = 60;
        makeWorldHitBox();
        lastX = offsetX;
        lastY = offsetY;

        File file = new File("src/com/Dylan/data/world/lvl1.dat");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            for (int i = 0; i < Renderer.worldSize; i++) {
                for (int j = 0; j < Renderer.worldSize; j++) {
                    rawWorld[j][i] = br.read();
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File("src/com/Dylan/data/world/lvl1Hit.dat");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 12; j++) {
                    worldPoints[i][j] = br.read();
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getWorldImages() {
        try {
            dirt = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/com/Dylan/sprites/terrain/dirt.png")));
            grass = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/com/Dylan/sprites/terrain/grass.png")));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(keyH.leftPressed) {
            offsetX += speed;
            Player.direction = "left";
        }

        if(keyH.rightPressed) {
            offsetX -= speed;
            Player.direction = "right";
        }

        if(keyH.upPressed && canJump) {
            velocity = 20;
            gravity = 1;
            Player.direction = "up";
            canJump = false;
        }

        if(lastHeight != rd.screenHeight || lastWidth != rd.screenWidth) {
            lastY += (int) (0.5 * (rd.screenHeight - lastHeight));
            lastX += (int) (0.5 * (rd.screenWidth - lastWidth));
            offsetY += (int) (0.5 * (rd.screenHeight - lastHeight));
            offsetX += (int) (0.5 * (rd.screenWidth - lastWidth));
            makeWorldHitBox();
        }

        if(worldBox.intersects((Rectangle2D) Player.entity) && !canJump) {
            canJump = true;
            velocity = 0;
            gravity = 0;
        }

        while(worldBox.intersects((Rectangle2D) Player.entity)) {
            if(offsetX - lastX > 0) offsetX --;
            if(offsetX - lastX < 0) offsetX ++;
            if(offsetY - lastY > 0) offsetY --;
            if(offsetY - lastY < 0) offsetY ++;
            makeWorldHitBox();
        }

        offsetY += velocity;
        velocity -= gravity;

        lastX = offsetX;
        lastY = offsetY;
        lastWidth = rd.screenWidth;
        lastHeight = rd.screenHeight;

        makeWorldHitBox();
    }

    public void makeWorldHitBox() {
        world.reset();
        for(int i = 0; i < 12; i ++) {
            world.addPoint(worldPoints[0][i] + offsetX, worldPoints[1][i] + offsetY);
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = grass;
        for(int i = 0; i < 64; i++) {
            for(int j = 0; j < 64; j++) {
                if(rawWorld[j][i] == 1) image = grass;
                else if(rawWorld[j][i] == 2) image = dirt;
                if(rawWorld[j][i] != 0) g2.drawImage(image, j * rd.blockSize + offsetX, i * rd.blockSize + offsetY, rd.blockSize, rd.blockSize, null);
            }
        }
        g2.draw(worldBox);
    }
}