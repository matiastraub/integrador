package com.gofinance.integrador.view;

import com.gofinance.integrador.controller.UsuarioValidacion;
import com.gofinance.integrador.database.UsuarioDAO;
import com.gofinance.integrador.model.Usuario;
import raven.datetime.DatePicker;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("serial")
public class SignupView extends JFrame {

    private final JButton registerBtn;
    private final JTextField nombreField;
    private final JTextField apellidoField;
    private final JTextField emailField;
    private final JTextField telField;
    private JTextField fechaNacField; // No es final y lo inicializamos correctamente
    private final JPasswordField passField;
    private final JPasswordField repeatPassField;
    private final JLabel loginLink;
    private MainView mainView;
    private LoginView lv;
    private DatePicker datePicker; // DatePicker para la fecha de nacimiento
    private JButton selectDateBtn; // Botón para abrir el DatePicker

    public static final String BTN_REGISTRAR = "Registrar";

    public SignupView() {
        setTitle("Registro");
        setSize(400, 660);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurar MigLayout
        JPanel mainPanel = new JPanel(new MigLayout("wrap 2", "[fill, grow]", "[]10[]10[]10[]10[]10[]10[]10[]20[]15[]"));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Campos del formulario
        mainPanel.add(new JLabel("Nombre:"), "span 2");
        nombreField = new JTextField();
        mainPanel.add(nombreField, "span 2, growx");

        mainPanel.add(new JLabel("Apellido:"), "span 2");
        apellidoField = new JTextField();
        mainPanel.add(apellidoField, "span 2, growx");

        mainPanel.add(new JLabel("Email:"), "span 2");
        emailField = new JTextField();
        mainPanel.add(emailField, "span 2, growx");

        mainPanel.add(new JLabel("Teléfono:"), "span 2");
        telField = new JTextField();
        mainPanel.add(telField, "span 2, growx");

        // Fecha de Nac. (Usando DatePicker en lugar de JTextField)
        mainPanel.add(new JLabel("Fecha de Nac. (YYYY-MM-DD):"), "span 2");

        // Inicializamos el campo fechaNacField
        fechaNacField = new JTextField();
        fechaNacField.setEditable(false); // Solo lectura

        // Crear botón para abrir el DatePicker
        selectDateBtn = new JButton("Seleccionar Fecha");
        selectDateBtn.addActionListener(e -> abrirDatePicker());

        // Configurar el DatePicker
        datePicker = new DatePicker();
        datePicker.setDateSelectionAble(date -> !date.isAfter(LocalDate.now())); // Solo permitir fechas hasta hoy
        datePicker.addDateSelectionListener(dateEvent -> {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate selectedDate = datePicker.getSelectedDate();
            if (selectedDate != null) {
                fechaNacField.setText(df.format(selectedDate)); // Establecer la fecha seleccionada en el campo de texto
            }
        });
        datePicker.now();

        // Crear editor de texto para mostrar la fecha seleccionada
        JFormattedTextField editor = new JFormattedTextField();
        datePicker.setEditor(editor);

        // Agregar los componentes al panel
        mainPanel.add(fechaNacField, "span 2, growx");
        mainPanel.add(selectDateBtn, "span 2, growx");

        mainPanel.add(new JLabel("Contraseña:"), "span 2");
        passField = new JPasswordField();
        mainPanel.add(passField, "span 2, growx");

        mainPanel.add(new JLabel("Repetir Contraseña:"), "span 2");
        repeatPassField = new JPasswordField();
        mainPanel.add(repeatPassField, "span 2, growx");

        // Botón de Registro
        registerBtn = new JButton(BTN_REGISTRAR);
        mainPanel.add(registerBtn, "span 2, growx, gaptop 20");

        registerBtn.addActionListener((ActionEvent e) -> registrar());

        // Enlace para volver al Login
        loginLink = new JLabel("¿Ya tienes una cuenta? Inicia sesión aquí");
        loginLink.setForeground(Color.BLUE);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(loginLink, "span 2, align center, gaptop 15");

        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                lv = new LoginView();
                lv.setVisible(true);
                dispose();
            }
        });

        add(mainPanel, BorderLayout.CENTER);
        setResizable(false);
    }

    private void abrirDatePicker() {
        // Mostrar el DatePicker en un JDialog al hacer clic en el botón
        JOptionPane.showMessageDialog(this, datePicker, "Selecciona tu fecha de nacimiento", JOptionPane.PLAIN_MESSAGE);
    }

    public void registrar() {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String email = emailField.getText();
        String tel = telField.getText();
        String fechaNac = fechaNacField.getText(); // Obtener la fecha seleccionada
        String pass = new String(passField.getPassword());
        String repeatPass = new String(repeatPassField.getPassword());

        if (validarTodo(nombre, apellido, email, tel, fechaNac, pass, repeatPass)) {
            Usuario usuario = new Usuario(nombre, apellido, tel, fechaNac, pass, email);
            int result = UsuarioDAO.crearUsuario(usuario);
            if (result == 1) {
                dispose();
                mainView = new MainView(usuario);
                mainView.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error al crear usuario.");
            }
        }
    }

    private boolean validarTodo(String nombre, String apellido, String email, String tel, String fechaNac, String pass, String repeatPass) {
        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || tel.isEmpty() || fechaNac.isEmpty() || pass.isEmpty() || repeatPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
            return false;
        }
        if (!UsuarioValidacion.esValidoEmail(email)) {
            JOptionPane.showMessageDialog(this, "Email inválido.");
            return false;
        }
        if (!UsuarioValidacion.esValidoTel(tel)) {
            JOptionPane.showMessageDialog(this, "Teléfono inválido.");
            return false;
        }
        if (!UsuarioValidacion.esValidoFechaNac(fechaNac)) {
            JOptionPane.showMessageDialog(this, "Fecha de nacimiento inválida.");
            return false;
        }
        if (!UsuarioValidacion.esValidoPassword(pass)) {
            JOptionPane.showMessageDialog(this, "Contraseña inválida. Debe contener al menos 8 caracteres, una letra mayúscula, una letra minúscula y un número.");
            return false;
        }
        if (!pass.equals(repeatPass)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.");
            return false;
        }
        if (!UsuarioValidacion.esValidoNombre(nombre)) {
            JOptionPane.showMessageDialog(this, "Nombre inválido.");
            return false;
        }
        if (!UsuarioValidacion.esValidoNombre(apellido)) {
            JOptionPane.showMessageDialog(this, "Apellido inválido.");
            return false;
        }
        if (UsuarioDAO.checkUsuarioValido(email)) {
            JOptionPane.showMessageDialog(this, "El email ya está registrado.");
            return false;
        }
        return true;
    }
}

