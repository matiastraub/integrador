package com.gofinance.integrador.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.gofinance.integrador.controller.AjustesControlador;
import com.gofinance.integrador.model.Usuario;

import raven.datetime.DatePicker;

public class AjustesView extends JPanel {
    public Usuario usuario;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblEmail;
    private JLabel lblTelefono;
    private JLabel lblFechaNac;
    private JLabel lblPasswordActual;
    private JLabel lblPassword;
    private JLabel lblRepeatPassword;

    private JTextField campoNombre;
    private JTextField campoApellido;
    private JTextField campoEmail;
    private JTextField campoTelefono;
    private JTextField campoFechaNac;
    private JPasswordField campoPasswordActual;
    private JPasswordField campoPassword;
    private JPasswordField campoConfirmPassword;
    private boolean isOpenPasswordField = false;
    private DatePicker datePicker;

    private JLabel[] etiquetas = {
            lblNombre, lblApellido, lblEmail, lblTelefono, lblFechaNac
    };
    private JTextField[] camposTexto = {
            campoNombre, campoApellido, campoEmail, campoTelefono, campoFechaNac
    };

    public static final String BTN_EDITAR = "Editar";
    public static final String BTN_ACEPTAR = "Guardar";
    public static final String BTN_CANCELAR = "Cancelar";
    public static final String BTN_PASSWORD = "CambiarPassword";

    private JButton btnAceptar;
    private JButton btnCancelar;
    private JButton btnEditar;
    private JButton btnSeleccionarFecha;
    private JButton btnCambiarPassword;
    private final JPanel panel;

    final int START_LABEL_X = 50;
    final int START_FIELD_X = START_LABEL_X + 180;
    final int MIN_HEIGHT = 25;
    final int MIN_LENGTH_LABEL = 180;
    final int MIN_LENGTH_FIELDS = 200;
    final int START_HEIGHT_Y = 40;
    Color COLOR_GREY = new Color(161, 146, 146);
    Color COLOR_GREEN = new Color(0, 224, 131);

    public AjustesView() {
        System.out.println("AjustesView creada");
        setLayout(null);
        setPreferredSize(new Dimension(784, 600));
        panel = new JPanel(null);
        panel.setBounds(50, 20, 700, 550);
        panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Ajustes de Usuario"));
        panel.setOpaque(true); // Asegura que el panel sea opaco

        String[] labelNames = { "Nombre:", "Apellido:", "Email:", "Teléfono:", "Fecha de Nacimiento:" };

        for (int i = 0; i < etiquetas.length; i++) {
            etiquetas[i] = new JLabel(labelNames[i]);
            etiquetas[i].setBounds(START_LABEL_X, START_HEIGHT_Y + (i * 40), MIN_LENGTH_LABEL, MIN_HEIGHT);
            panel.add(etiquetas[i], "span 2");
        }

        // Crear campos de texto

        campoNombre = new JTextField();
        campoNombre.setBounds(START_FIELD_X, START_HEIGHT_Y + (0 * 40), MIN_LENGTH_FIELDS, MIN_HEIGHT);
        campoNombre.setEditable(false);
        campoNombre.setForeground(COLOR_GREY);
        panel.add(campoNombre, "span 2, growx");

        campoApellido = new JTextField();
        campoApellido.setBounds(START_FIELD_X, START_HEIGHT_Y + (1 * 40), MIN_LENGTH_FIELDS, MIN_HEIGHT);
        campoApellido.setEditable(false);
        campoApellido.setForeground(COLOR_GREY);
        panel.add(campoApellido, "span 2, growx");

        campoEmail = new JTextField();
        campoEmail.setBounds(START_FIELD_X, START_HEIGHT_Y + (2 * 40), MIN_LENGTH_FIELDS, MIN_HEIGHT);
        campoEmail.setEditable(false);
        campoEmail.setForeground(COLOR_GREY);
        panel.add(campoEmail, "span 2, growx");

        campoTelefono = new JTextField();
        campoTelefono.setBounds(START_FIELD_X, START_HEIGHT_Y + (3 * 40), MIN_LENGTH_FIELDS, MIN_HEIGHT);
        campoTelefono.setEditable(false);
        campoTelefono.setForeground(COLOR_GREY);
        panel.add(campoTelefono, "span 2, growx");

        campoFechaNac = new JTextField();
        campoFechaNac.setBounds(START_FIELD_X, START_HEIGHT_Y + (4 * 40), MIN_LENGTH_FIELDS, MIN_HEIGHT);
        campoFechaNac.setEditable(false);
        campoFechaNac.setForeground(COLOR_GREY);
        datePicker = new DatePicker();
        datePicker.setDateSelectionAble(date -> !date.isAfter(LocalDate.now()));
        datePicker.addDateSelectionListener(evt -> {
            LocalDate fecha = datePicker.getSelectedDate();
            if (fecha != null) {
                campoFechaNac.setText(fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        });

        panel.add(campoFechaNac, "span 2, growx");

        createBtnFechaNac();

        createPasswordSection();

        // =================== BOTONES ===================

        crearBotones();
        panel.setVisible(true);
        add(panel);
        // Añadir el panel al JFrame o JPanel principal

    }

    private void createBtnFechaNac() {
        btnSeleccionarFecha = new JButton("Cambiar Fecha de Nacimiento");
        btnSeleccionarFecha.setBounds(START_LABEL_X, START_HEIGHT_Y + 40 * (camposTexto.length), 260, MIN_HEIGHT);
        btnSeleccionarFecha.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnSeleccionarFecha.setEnabled(false); // Deshabilitado por defecto
        panel.add(btnSeleccionarFecha, "span 2, growx");
    }

    private void hidePasswordFields() {
        lblPasswordActual.setVisible(false);
        campoPasswordActual.setVisible(false);
        lblPassword.setVisible(false);
        campoPassword.setVisible(false);
        lblRepeatPassword.setVisible(false);
        campoConfirmPassword.setVisible(false);
    }

    private void showPasswordFields() {
        lblPasswordActual.setVisible(true);
        campoPasswordActual.setVisible(true);
        lblPassword.setVisible(true);
        campoPassword.setVisible(true);
        lblRepeatPassword.setVisible(true);
        campoConfirmPassword.setVisible(true);
    }

    private void createPasswordSection() {

        btnCambiarPassword = new JButton("Cambiar contraseña");
        btnCambiarPassword.setBounds(START_LABEL_X, START_HEIGHT_Y + 40 * (camposTexto.length + 1), 210, MIN_HEIGHT);
        btnCambiarPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(btnCambiarPassword, "span 2, growx");

        lblPasswordActual = new JLabel("Contraseña:");
        lblPasswordActual.setBounds(START_LABEL_X, START_HEIGHT_Y + 40 * (camposTexto.length + 2), MIN_LENGTH_LABEL,
                MIN_HEIGHT);

        panel.add(lblPasswordActual, "span 2");

        campoPasswordActual = new JPasswordField();
        campoPasswordActual.setBounds(START_FIELD_X, START_HEIGHT_Y + 40 * (camposTexto.length + 2), MIN_LENGTH_FIELDS,
                MIN_HEIGHT);
        campoPasswordActual.setEditable(true);
        campoPasswordActual.setForeground(COLOR_GREY);

        panel.add(campoPasswordActual, "span 2, growx");

        lblPassword = new JLabel("Nueva Contraseña:");
        lblPassword.setBounds(START_LABEL_X, START_HEIGHT_Y + 40 * (camposTexto.length + 3), MIN_LENGTH_LABEL,
                MIN_HEIGHT);

        panel.add(lblPassword, "span 2");

        campoPassword = new JPasswordField();
        campoPassword.setBounds(START_FIELD_X, START_HEIGHT_Y + 40 * (camposTexto.length + 3), MIN_LENGTH_FIELDS,
                MIN_HEIGHT);
        campoPassword.setEditable(true);
        campoPassword.setForeground(COLOR_GREY);

        panel.add(campoPassword, "span 2, growx");

        campoConfirmPassword = new JPasswordField();
        campoConfirmPassword.setBounds(START_FIELD_X, START_HEIGHT_Y + 40 * (camposTexto.length + 4),
                MIN_LENGTH_FIELDS, MIN_HEIGHT);
        campoConfirmPassword.setEditable(true);
        campoConfirmPassword.setForeground(COLOR_GREY);

        lblRepeatPassword = new JLabel("Repetir Contraseña:");
        lblRepeatPassword.setBounds(START_LABEL_X, START_HEIGHT_Y + 40 * (camposTexto.length + 4),
                MIN_LENGTH_LABEL, MIN_HEIGHT);
        panel.add(lblRepeatPassword, "span 2");
        panel.add(campoConfirmPassword, "span 2, growx");
        hidePasswordFields();
    }

    private void crearBotones() {
        btnEditar = new JButton(BTN_EDITAR);

        btnEditar.setBounds(START_LABEL_X, START_HEIGHT_Y + 40 * (camposTexto.length
                + 6), 100, MIN_HEIGHT);
        btnEditar.setEnabled(true); // Deshabilitado por defecto
        btnEditar.setBackground(COLOR_GREEN);
        btnEditar.setFont(new Font("Tahoma", Font.BOLD, 14));

        btnEditar.setForeground(Color.WHITE);
        // btnEditar.addActionListener(e -> clickBtnEditar(e));
        panel.add(btnEditar, "span 2, growx");

        btnCancelar = new JButton(BTN_CANCELAR);
        btnCancelar.setBounds(START_LABEL_X, START_HEIGHT_Y + 40 * (camposTexto.length + 6), 100, MIN_HEIGHT);

        btnCancelar.setEnabled(false); // Deshabilitado por defecto
        btnCancelar.setBackground(Color.GRAY);
        btnCancelar.setForeground(Color.WHITE);
        btnEditar.setFont(new Font("Tahoma", Font.BOLD, 14));
        // btnCancelar.addActionListener(e -> clickBtnCancelar(e));
        btnCancelar.setVisible(false); // Oculto por defecto

        panel.add(btnCancelar, "span 2, growx");

        btnAceptar = new JButton(BTN_ACEPTAR);
        btnAceptar.setBounds(START_LABEL_X + 110, START_HEIGHT_Y + 40 * (camposTexto.length + 6), 100, MIN_HEIGHT);
        btnAceptar.setEnabled(true); // Deshabilitado por defecto

        btnAceptar.setBackground(Color.RED);
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 14));
        // btnAceptar.addActionListener(e -> clickBtnAceptar(e));
        // Asignar el comando de acción a los botones
        // para que el controlador pueda identificarlos
        btnAceptar.setActionCommand(BTN_ACEPTAR);
        btnEditar.setActionCommand(BTN_EDITAR);
        btnCancelar.setActionCommand(BTN_CANCELAR);
        btnCambiarPassword.setActionCommand(BTN_PASSWORD);

        panel.add(btnAceptar, "span 2, growx");

    }

    public void editarCambios() {
        btnCancelar.setEnabled(true);
        btnCancelar.setVisible(true);
        btnEditar.setEnabled(false);
        btnEditar.setVisible(false);
        hacerTextoEditable();
        btnSeleccionarFecha.setEnabled(true);
        btnSeleccionarFecha.setBackground(Color.BLUE); // Habilitar el botón de selección de fecha
        btnCambiarPassword.setEnabled(true);
        btnCambiarPassword.setBackground(Color.BLUE); // Habilitar el botón de cambiar contraseña
    }

    public boolean getIsOpenPasswordField() {
        return isOpenPasswordField;
    }

    public void togglePasswordFields() {
        if (isOpenPasswordField) {
            hidePasswordFields();
            isOpenPasswordField = false;
            btnCambiarPassword.setText("Cambiar contraseña");
        } else {
            showPasswordFields();
            isOpenPasswordField = true;
            btnCambiarPassword.setText("Ocultar contraseña");
        }
    }

    private void hacerTextoEditableNoEditable(boolean bool, Color col) {
        campoNombre.setEditable(bool);
        campoNombre.setForeground(col);
        campoApellido.setEditable(bool);
        campoApellido.setForeground(col);
        campoEmail.setEditable(bool);
        campoEmail.setForeground(col);
        campoTelefono.setEditable(bool);
        campoTelefono.setForeground(col);
        campoFechaNac.setEditable(bool);
        campoFechaNac.setForeground(col);
    }

    private void hacerTextoEditable() {
        hacerTextoEditableNoEditable(true, Color.WHITE);
    }

    private void hacerTextoNoEditable() {
        hacerTextoEditableNoEditable(false, COLOR_GREY);
    }

    public JButton getBtnSeleccionarFecha() {
        return btnSeleccionarFecha;
    }

    public void setControlador(AjustesControlador controlador) {
        btnAceptar.addActionListener(controlador);
        btnEditar.addActionListener(controlador);
        btnCancelar.addActionListener(controlador);
        btnSeleccionarFecha.addActionListener(controlador);
        btnCambiarPassword.addActionListener(controlador);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {

        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void limpiarCampos() {
        // Aquí se puede implementar un método para limpiar los campos de la vista
        System.out.println("Campos limpiados");
    }

    public void guardarCambios(String validacionResultado) {
        if (!validacionResultado.equals("success")) {
            mostrarError(validacionResultado);
        } else {
            mostrarMensaje("Usuario actualizado correctamente");
            btnCancelar.setEnabled(false);
        }
    }

    private void restaurarUsuarioOriginal(Usuario usuarioOriginal) {
        if (usuarioOriginal != null) {
            campoNombre.setText(usuarioOriginal.getNombre());
            campoApellido.setText(usuarioOriginal.getApellido());
            campoEmail.setText(usuarioOriginal.getEmail());
            campoTelefono.setText(usuarioOriginal.getTelefono());
            campoFechaNac.setText(usuarioOriginal.getFechaNac());
        } else {
            mostrarError("Usuario no válido");
        }
        System.out.println("Datos del usuario original restaurados");
    }

    public String getConfirmedPassword() {
        return new String(campoConfirmPassword.getPassword());
    }

    public Usuario getUsuario() {
        // Aquí se puede implementar un método para obtener los datos del usuario
        // desde los campos de texto
        String nombre = campoNombre.getText();

        String apellido = campoApellido.getText();
        String email = campoEmail.getText();
        String telefono = campoTelefono.getText();
        String fechaNac = campoFechaNac.getText();

        String password = new String(campoPassword.getPassword());

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setTelefono(telefono);
        usuario.setFechaNac(fechaNac);
        usuario.setPassword(password);

        System.out.println("Nombre: " + nombre);
        return usuario;
    }

    public void cancelarCambios(Usuario usuarioOriginal) {
        btnCancelar.setEnabled(false);
        btnCancelar.setVisible(false);
        btnEditar.setEnabled(true);
        btnEditar.setVisible(true);
        hacerTextoNoEditable();
        btnSeleccionarFecha.setEnabled(false);
        btnSeleccionarFecha.setBackground(Color.GRAY);

        btnCambiarPassword.setEnabled(false);
        btnCambiarPassword.setBackground(Color.GRAY);
        restaurarUsuarioOriginal(usuarioOriginal);
        hidePasswordFields();
        isOpenPasswordField = false;
        btnCambiarPassword.setText("Cambiar contraseña");
        limpiarCampos();
        System.out.println("Cambios cancelados");
    }

    public String getCampoPasswordActual() {
        return new String(campoPasswordActual.getPassword());
    }

    public String getCampoPassword() {
        return new String(campoPassword.getPassword());
    }

    public String getCampoConfirmPassword() {
        return new String(campoConfirmPassword.getPassword());
    }

    public void setUsuario(Usuario usuarioCtl) {
        if (usuarioCtl != null) {
            usuario = new Usuario(usuarioCtl.getId(), usuarioCtl.getNombre(),
                    usuarioCtl.getApellido(), usuarioCtl.getTelefono(), usuarioCtl.getFechaNac(),
                    usuarioCtl.getPassword(), usuarioCtl.getEmail());
            campoNombre.setText(usuario.getNombre());
            campoApellido.setText(usuario.getApellido());
            campoEmail.setText(usuario.getEmail());
            campoTelefono.setText(usuario.getTelefono());
            campoFechaNac.setText(usuario.getFechaNac());
        } else {
            mostrarError("Usuario no válido");
        }
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

}
