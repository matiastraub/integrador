package com.gofinance.integrador.view;

import com.gofinance.integrador.model.Usuario;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class LoginView extends JFrame {

    private JButton loginBtn;
    private JButton registroBtn;

    private JTextField campoEmail;
    private JPasswordField campoPassword;

    public LoginView() {
        setTitle("Login");
        setSize(300, 250);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new MigLayout("wrap 2", "[fill, grow]", "[]10[]10[]10[]10[]"));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Campo Email
        panel.add(new JLabel("Email:"), "span 2");
        campoEmail = new JTextField();
        panel.add(campoEmail, "span 2, growx");

        // Campo Password
        panel.add(new JLabel("Contraseña:"), "span 2");
        campoPassword = new JPasswordField();
        panel.add(campoPassword, "span 2, growx");

        // Botones
        loginBtn = new JButton("Login");
        registroBtn = new JButton("Registrarse");

        panel.add(loginBtn, "span 2, growx, gaptop 10");
        panel.add(registroBtn, "span 2, growx");

        add(panel, BorderLayout.CENTER);
    }

    // ✅ Método para inyectar el ActionListener externo
    public void setControlador(ActionListener c) {
        loginBtn.addActionListener(c);
        registroBtn.addActionListener(c);
    }

    // ✅ Método que devuelve un objeto Usuario con los datos ingresados
    public Usuario getDatosUsuario() {
        String email = campoEmail.getText();
        String pass = new String(campoPassword.getPassword());

        if (email.isEmpty() || pass.isEmpty()) {
            mostrarError("Todos los campos son obligatorios.");
            return null;
        }

        return new Usuario(0, null, null, null, null, pass, email);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void limpiarCampos() {
        campoEmail.setText("");
        campoPassword.setText("");
    }

    public void cargarUsuario(String email) {
        campoEmail.setText(email);
    }

    public JButton getBtnLogin() {
        return loginBtn;
    }

    public JButton getBtnRegistro() {
        return registroBtn;
    }
}
