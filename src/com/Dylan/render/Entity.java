package com.Dylan.render;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public static int centerX, centerY, speed, frameNum, step;
    public BufferedImage left1, left2, left3, right1, right2, right3, up1, up2, up3, up4, up5;
    public static boolean showHitBoxes;
    public static String direction;
    public long lastTime;
    public static Shape entity;
}
