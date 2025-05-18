package com.gofinance.integrador.main;

import java.awt.EventQueue;
import javax.swing.UIManager;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.gofinance.integrador.controller.AppControlador;
import com.gofinance.integrador.database.DatabaseInit;
import com.gofinance.integrador.view.LoginView;
import com.gofinance.integrador.view.SignupView;

public class Main {

    public static void main(String[] args) {
        // Establecer tema visual
        try {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        } catch (Exception e) {
            System.out.println("No se pudo cargar FlatLaf: " + e.getMessage());
        }

        // Inicializar la estructura de la base de datos
        DatabaseInit.inicializarTablas();

        // Iniciar interfaz gráfica
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginView login = new LoginView();
                SignupView registro = new SignupView();
                AppControlador controlador = new AppControlador(login, registro);

                login.setControlador(controlador);
                registro.setControlador(controlador);

                login.setVisible(true);
            }
        });
    }
}
