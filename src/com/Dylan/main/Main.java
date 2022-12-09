package com.Dylan.main;

import javax.swing.*;

public class Main {
    public static JFrame window = new JFrame();
    public static void main(String[] args) {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Lemon Runner");
        window.setResizable(true);

        Renderer renderer = new Renderer();
        window.add(renderer);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        renderer.startThread();
    }
}
