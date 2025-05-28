package com.gofinance.integrador.main;

import java.awt.EventQueue;

import com.formdev.flatlaf.FlatLaf;    
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.gofinance.integrador.controller.AppControlador;
import com.gofinance.integrador.database.DatabaseInitializer;
import com.gofinance.integrador.view.LoginView;
import com.gofinance.integrador.view.SignupView;

public class Main {

    public static void main(String[] args) {
        // registra carpeta de propiedades en src/main/resources/themes
        FlatLaf.registerCustomDefaultsSource("themes");

        // tema visual
        try {
            // carga tu FlatMacDarkLaf.properties y aplica el LAF
            FlatMacDarkLaf.setup();
        } catch (Exception e) {
            System.out.println("No se pudo cargar FlatLaf: " + e.getMessage());
        }

        // Se inicializa la estructura de la base de datos
        DatabaseInitializer.inicializarTablas();

        // Se inicializa la interfaz gráfica
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
