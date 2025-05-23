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
        setSize(316, 281);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new MigLayout("wrap 2", "[fill, grow]", "[]10[]10[]10[]10[]"));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Mostramos el campo para introducir el email
        panel.add(new JLabel("Email:"), "span 2");
        campoEmail = new JTextField();
        panel.add(campoEmail, "span 2, growx");

        // Mostramos el campo para introducir la contrasena
        panel.add(new JLabel("Contrasena:"), "span 2");
        campoPassword = new JPasswordField();
        panel.add(campoPassword, "span 2, growx");

        // Mostramos los botones de acceso y registro
        loginBtn = new JButton("Login");
        registroBtn = new JButton("Registrarse");
        panel.add(loginBtn, "span 2, growx, gaptop 10");
        panel.add(registroBtn, "span 2, growx");

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    // Asignamos el ActionListener al controlador externo
    public void setControlador(ActionListener c) {
        loginBtn.addActionListener(c);
        registroBtn.addActionListener(c);
    }

    // Obtenemos un objeto Usuario con el email y la contrasena proporcionados
    public Usuario getDatosUsuario() {
        String email = campoEmail.getText();
        String pass = new String(campoPassword.getPassword());

        if (email.isEmpty() || pass.isEmpty()) {
            mostrarError("Todos los campos son obligatorios.");
            return null;
        }

        return new Usuario(0, null, null, null, null, pass, email);
    }

    // Mostramos un mensaje de error al usuario
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Vaciamos los campos de entrada de datos
    public void limpiarCampos() {
        campoEmail.setText("");
        campoPassword.setText("");
    }

    // Cargamos el email registrado automaticamente en el campo correspondiente
    public void cargarUsuario(String email) {
        campoEmail.setText(email);
    }

    // Accedemos al boton de Login
    public JButton getBtnLogin() {
        return loginBtn;
    }

    // Accedemos al boton de Registro
    public JButton getBtnRegistro() {
        return registroBtn;
    }
}
