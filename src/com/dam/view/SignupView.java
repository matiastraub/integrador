package com.dam.view;

import javax.swing.*;

import com.dam.controller.UsuarioValidacion;

import java.awt.*;
import java.awt.event.ActionEvent;

public class SignupView extends JFrame {

    public SignupView() {
        setTitle("Signup");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // Márgenes externos

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 10, 0);  // Espaciado vertical entre componentes
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Campos del formulario
        String[] labels = {
            "Username:", "Email:", "Phone:", 
            "Date of Birth (YYYY-MM-DD):", "Password:", "Repeat Password:"
        };
        
        JTextField[] fields = {
            new JTextField(), new JTextField(), new JTextField(),
            new JTextField(), new JPasswordField(), new JPasswordField()
        };

        // Labels y campos
        for (int i = 0; i < labels.length; i++) {
            // Label
            JLabel label = new JLabel(labels[i]);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            mainPanel.add(label, gbc);
            
            // Campo
            gbc.gridy++;
            fields[i].setPreferredSize(new Dimension(200, 25));
            mainPanel.add(fields[i], gbc);
            
            gbc.gridy++;
        }

        // Botón de Registro
        JButton registerBtn = new JButton("Register");
        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 15, 0);  // Espacio superior
        gbc.fill = GridBagConstraints.NONE;
        registerBtn.setPreferredSize(new Dimension(120, 30));
        mainPanel.add(registerBtn, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Listeners
        registerBtn.addActionListener((ActionEvent e) -> {
            String user = fields[0].getText();
            String email = fields[1].getText();
            String phone = fields[2].getText();
            String dob = fields[3].getText();
            String pass = new String(((JPasswordField)fields[4]).getPassword());
            String repeatPass = new String(((JPasswordField)fields[5]).getPassword());

            // Validaciones (mantenidas igual)
            if (!UsuarioValidacion.esValidoNombre(user)) {
                JOptionPane.showMessageDialog(this, "Nombre inválido.");
                return;
            }
            if (!UsuarioValidacion.esValidoEmail(email)) {
                JOptionPane.showMessageDialog(this, "Email inválido.");
                return;
            }
            if (!UsuarioValidacion.esValidoTel(phone)) {
                JOptionPane.showMessageDialog(this, "Teléfono inválido.");
                return;
            }
            if (!UsuarioValidacion.esValidoFechaNac(dob)) {
                JOptionPane.showMessageDialog(this, "Fecha inválida.");
                return;
            }
            if (!UsuarioValidacion.esValidoPassword(pass)) {
                JOptionPane.showMessageDialog(this, "Contraseña inválida.");
                return;
            }
            if (!pass.equals(repeatPass)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Registro correcto (simulado).");
            new LoginView();
            dispose();
        });

        setVisible(true);
    }
    //Metodo main temporal para poder probar la ventana
    public static void main(String[] args) {
        new SignupView();
    }
}