package com.gofinance.integrador.view;

import com.gofinance.integrador.controller.UsuarioValidacion;
import com.gofinance.integrador.database.UsuarioDAO;
import com.gofinance.integrador.model.Usuario;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class LoginView extends JFrame {

    public static final String BTN_LOGIN = "Login";

    private final JButton loginBtn;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JLabel signupLink;
    private SignupView sv;
    private MainView mainView;

    public LoginView() {
        setTitle("Login");
        setSize(300, 270);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurar MigLayout
        JPanel mainPanel = new JPanel(new MigLayout("wrap 2", "[fill, grow]", "[]10[]10[]10[]"));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Usuario
        mainPanel.add(new JLabel("Email:"), "span 2");
        usernameField = new JTextField();
        mainPanel.add(usernameField, "span 2, growx");

        // Contraseña
        mainPanel.add(new JLabel("Password:"), "span 2");
        passwordField = new JPasswordField();
        mainPanel.add(passwordField, "span 2, growx");

        // Botón de Login
        loginBtn = new JButton(BTN_LOGIN);
        mainPanel.add(loginBtn, "span 2, growx, gaptop 10");

        loginBtn.addActionListener((ActionEvent e) -> login());

        // Enlace de registro
        signupLink = new JLabel("¿Nuevo usuario? Regístrate aquí");
        signupLink.setForeground(Color.BLUE);
        signupLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(signupLink, "span 2, align center, gaptop 10");

        signupLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                sv = new SignupView();
                sv.setVisible(true);
                dispose();
            }
        });

        add(mainPanel, BorderLayout.CENTER);
    }

    public void login() {
        String user = usernameField.getText();
        String pass = new String(passwordField.getPassword());
        if (!UsuarioValidacion.esValidoEmail(user)) {
            JOptionPane.showMessageDialog(this, "Email inválido.");
            return;
        }
        if (!UsuarioValidacion.esValidoPassword(pass)) {
            JOptionPane.showMessageDialog(this, "Contraseña inválida.");
            return;
        }
        boolean esValido = UsuarioDAO.checkLogin(user, pass);
        if (esValido) {
            dispose();
            Usuario usuario = UsuarioDAO.getUsuarioEmail(user);
            mainView = new MainView(usuario);
            mainView.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
        }
    }
}
