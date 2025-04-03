package com.dam.view;

import com.dam.controller.UsuarioValidacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginView extends JFrame {

    public LoginView() {
        setTitle("Login");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Márgenes externos

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0); // Espaciado vertical entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Usuario
        mainPanel.add(new JLabel("Username:"), gbc);
        gbc.gridy++;
        JTextField usernameField = new JTextField();
        mainPanel.add(usernameField, gbc);
        gbc.gridy++;

        // Contraseña
        mainPanel.add(new JLabel("Password:"), gbc);
        gbc.gridy++;
        JPasswordField passwordField = new JPasswordField();
        mainPanel.add(passwordField, gbc);
        gbc.gridy++;

        // Botón de Login
        JButton loginBtn = new JButton("Login");
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.0;
        mainPanel.add(loginBtn, gbc);
        gbc.gridy++;

        // Enlace de registro
        JLabel signupLink = new JLabel("New User? Sign in");
        mainPanel.add(signupLink, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Listeners
        loginBtn.addActionListener((ActionEvent e) -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            if (!UsuarioValidacion.esValidoNombre(user)) {
                JOptionPane.showMessageDialog(this, "Nombre inválido.");
                return;
            }
            if (!UsuarioValidacion.esValidoPassword(pass)) {
                JOptionPane.showMessageDialog(this, "Contraseña inválida.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Login correcto (simulado).");
        });

        signupLink.setForeground(Color.BLUE);
        signupLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new SignupView();
                dispose();
            }
        });

        setVisible(true);
    }
  //Metodo main temporal para poder probar la ventana
    public static void main(String[] args) {
        new LoginView();
    }
}