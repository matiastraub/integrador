package com.gofinance.integrador.view;
import javax.swing.*;
import java.awt.*;

public class TestView extends JPanel {

    public TestView() {
        setLayout(new FlowLayout());

        JButton testButton = new JButton("¡Botón Slate Green!");
        testButton.setBackground(new Color(15, 130, 255)); // Slate Green
        testButton.setForeground(Color.WHITE);
        testButton.setFocusPainted(false);

        add(testButton);
    }
}
