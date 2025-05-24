package com.gofinance.integrador.view;

import com.gofinance.integrador.model.Usuario;
import net.miginfocom.swing.MigLayout;
import raven.datetime.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("serial")
public class SignupView extends JFrame {

    private JTextField campoNombre;
    private JTextField campoApellido;
    private JTextField campoEmail;
    private JTextField campoTelefono;
    private JTextField campoFechaNac;

    private JPasswordField campoPassword;
    private JPasswordField campoRepeatPassword;

    private JButton btnAceptar;
    private JButton btnCancelar;
    private JButton btnSeleccionarFecha;

    private DatePicker datePicker;

    public SignupView() {
        setTitle("Registro");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new MigLayout("wrap 2", "[fill, grow]", "[]10[]10[]10[]10[]10[]10[]10[]20[]10[]"));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // =================== CAMPOS DE TEXTO ===================
        panel.add(new JLabel("Nombre:"), "span 2");
        campoNombre = new JTextField();
        panel.add(campoNombre, "span 2, growx");

        panel.add(new JLabel("Apellido:"), "span 2");
        campoApellido = new JTextField();
        panel.add(campoApellido, "span 2, growx");

        panel.add(new JLabel("Email:"), "span 2");
        campoEmail = new JTextField();
        panel.add(campoEmail, "span 2, growx");

        panel.add(new JLabel("Teléfono:"), "span 2");
        campoTelefono = new JTextField();
        panel.add(campoTelefono, "span 2, growx");

        panel.add(new JLabel("Fecha de Nacimiento (YYYY-MM-DD):"), "span 2");
        campoFechaNac = new JTextField();
        campoFechaNac.setEditable(false);
        panel.add(campoFechaNac, "span 2, growx");

        btnSeleccionarFecha = new JButton("Seleccionar Fecha");
        panel.add(btnSeleccionarFecha, "span 2, growx");

        // =================== CONTRASEÑAS ===================
        panel.add(new JLabel("Contraseña:"), "span 2");
        campoPassword = new JPasswordField();
        panel.add(campoPassword, "span 2, growx");

        panel.add(new JLabel("Repetir Contraseña:"), "span 2");
        campoRepeatPassword = new JPasswordField();
        panel.add(campoRepeatPassword, "span 2, growx");

        // =================== BOTONES ===================
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");
        panel.add(btnAceptar, "span 2, growx, gaptop 20");
        panel.add(btnCancelar, "span 2, growx");

        add(panel, BorderLayout.CENTER);

        // =================== CONFIGURACIÓN DATE PICKER ===================
        datePicker = new DatePicker();
        datePicker.setDateSelectionAble(date -> !date.isAfter(LocalDate.now()));
        datePicker.addDateSelectionListener(evt -> {
            LocalDate fecha = datePicker.getSelectedDate();
            if (fecha != null) {
                campoFechaNac.setText(fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        });
    }

    // =================== GETTERS PARA EL CONTROLADOR ===================
    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JButton getBtnSeleccionarFecha() {
        return btnSeleccionarFecha;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    // =================== INYECCIÓN DE CONTROLADOR ===================
    public void setControlador(ActionListener c) {
        btnAceptar.addActionListener(c);
        btnCancelar.addActionListener(c);
        // El botón de seleccionar fecha ahora se maneja desde AppControlador
    }

    // =================== OBTENER DATOS DEL USUARIO ===================
    public Usuario getDatosUsuario() {
        String nombre = campoNombre.getText();
        String apellido = campoApellido.getText();
        String email = campoEmail.getText();
        String telefono = campoTelefono.getText();
        String fechaNac = campoFechaNac.getText();
        String pass = new String(campoPassword.getPassword());
        String pass2 = new String(campoRepeatPassword.getPassword());

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || telefono.isEmpty()
                || fechaNac.isEmpty() || pass.isEmpty() || pass2.isEmpty()) {
            mostrarError("Todos los campos son obligatorios.");
            return null;
        }

        if (!pass.equals(pass2)) {
            mostrarError("Las contraseñas no coinciden.");
            return null;
        }

        return new Usuario(nombre, apellido, telefono, fechaNac, pass, email);
    }

    // =================== MÉTODOS AUXILIARES ===================
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void limpiarCampos() {
        campoNombre.setText("");
        campoApellido.setText("");
        campoEmail.setText("");
        campoTelefono.setText("");
        campoFechaNac.setText("");
        campoPassword.setText("");
        campoRepeatPassword.setText("");
    }
} 
