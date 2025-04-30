package com.gofinance.integrador.main;
import java.awt.EventQueue;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.gofinance.integrador.view.LoginView;

public class Main {

    public static void main(String[] args) {
        // Enable FlatLaf Look and Feel
    	FlatMacDarkLaf.setup();
        EventQueue.invokeLater(() -> {
            LoginView ve = new LoginView();
            ve.setVisible(true);
        });
    }  
}
