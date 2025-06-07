package com.gofinance.integrador.view;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class UtilsView extends JPanel {
    public UtilsView() {
        setLayout(new FlowLayout());

        JButton testButton = new JButton("Próximamente");
        testButton.setBackground(new Color(255, 152, 0)); 
        testButton.setForeground(Color.WHITE);
        testButton.setFocusPainted(false);

        add(testButton);
    }
}

