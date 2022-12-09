package com.Dylan.render;

import com.Dylan.main.Renderer;
import com.Dylan.main.KeyHandler;
import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    Renderer rd;
    KeyHandler keyH;

    public Player(Renderer rd, KeyHandler keyH) {
        this.rd = rd;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        centerX = rd.screenWidth / 2 - rd.blockSize / 2;
        centerY = rd.screenHeight / 2 - rd.blockSize / 2;
        entity = new Rectangle(centerX, centerY, centerX + 32, centerY + 32);
        speed = rd.scale * rd.speed;
        direction = "down";
        frameNum = 1;
        step = 1;
        showHitBoxes = true;
        lastTime = System.nanoTime() + 1000000000;
    }

    public void getPlayerImage() {
        try {
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/com/Dylan/sprites/lemon/lemonL1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/com/Dylan/sprites/lemon/lemonL2.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/com/Dylan/sprites/lemon/lemonL3.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/com/Dylan/sprites/lemon/lemonR1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/com/Dylan/sprites/lemon/lemonR2.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/com/Dylan/sprites/lemon/lemonR3.png")));
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/com/Dylan/sprites/lemon/lemonU1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/com/Dylan/sprites/lemon/lemonU2.png")));
            up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/com/Dylan/sprites/lemon/lemonU3.png")));
            up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/com/Dylan/sprites/lemon/lemonU4.png")));
            up5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/com/Dylan/sprites/lemon/lemonU5.png")));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        centerX = rd.screenWidth / 2 - rd.blockSize / 2;
        centerY = rd.screenHeight / 2 - rd.blockSize / 2;
        entity = new Rectangle(centerX + 8 * rd.scale, centerY + 8 * rd.scale, 16 * rd.scale, 24 * rd.scale);

        if(keyH.upPressed) {
            direction = "up";
        }

        if(keyH.leftPressed) {
            direction = "left";
        }

        if(keyH.rightPressed) {
            direction = "right";
        }

        if(keyH.leftPressed || keyH.rightPressed) {
            if(frameNum == 1 || frameNum > 3) frameNum = 2;
            step ++;
            if(step > speed - 3) {
                if(frameNum == 2) frameNum = 3;
                else if(frameNum == 3) frameNum = 2;
                step = 0;
            }
        }else if(keyH.upPressed) {
            if(frameNum == 1 ) frameNum = 2;
            step ++;
            if(step > rd.speed) {
                if(frameNum == 2) frameNum = 3;
                else if(frameNum ==3) frameNum = 4;
                else if(frameNum ==4) frameNum = 5;
                else if(frameNum ==5) frameNum = 2;
                step = 0;
            }
        }else {
            frameNum = 1;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "left" -> {
                if(frameNum == 1) image = left1;
                if(frameNum == 2) image = left2;
                if(frameNum == 3) image = left3;
            }
            case "right" -> {
                if(frameNum == 1) image = right1;
                if(frameNum == 2) image = right2;
                if(frameNum == 3) image = right3;
            }
            case "up" -> {
                if(frameNum == 1) image = up1;
                if(frameNum == 2) image = up2;
                if(frameNum == 3) image = up3;
                if(frameNum == 4) image = up4;
                if(frameNum == 5) image = up5;
            }
        }
        g2.drawImage(image, centerX, centerY, rd.blockSize, rd.blockSize, null);
    }
}
