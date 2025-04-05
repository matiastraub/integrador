package view;

import controller.UsuarioValidacion;
import database.UsuarioDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import model.Usuario;

public class SignupView extends JFrame {

    private final JButton registerBtn;
    private String nombre;
    private String apellido;
    private String email;
    private String tel;
    private String fechaNac;
    private String pass;
    private String repeatPass;
    private final JTextField[] fields = {
            new JTextField(), new JTextField(), new JTextField(), new JTextField(),
            new JTextField(), new JPasswordField(), new JPasswordField()
    };
    private final JLabel signupLink;
    private LoginView lv;

    public static final String BTN_REGISTRAR = "Register";

    public SignupView() {
        setTitle("Registro");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // Márgenes externos

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 10, 0); // Espaciado vertical entre componentes
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Campos del formulario
        String[] labels = {
                "Nombre:", "Apellido", "Email:", "Teléfono:",
                "Fecha de Nac. (YYYY-MM-DD):", "Contraseña:", "Repetir contraseña:"
        };

        // Labels y campos
        for (int i = 0; i < labels.length; i++) {
            // Label
            JLabel label = new JLabel(labels[i]);

            mainPanel.add(label, gbc);

            // Campo
            gbc.gridy++;
            fields[i].setPreferredSize(new Dimension(200, 25));
            mainPanel.add(fields[i], gbc);

            gbc.gridy++;
        }

        // Botón de Registro

        registerBtn = new JButton(BTN_REGISTRAR);
        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 15, 0); // Espacio superior
        gbc.fill = GridBagConstraints.NONE;
        registerBtn.setPreferredSize(new Dimension(120, 30));
        mainPanel.add(registerBtn, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Listeners
        registerBtn.addActionListener((ActionEvent e) -> {
            registrar();
        });

        gbc.gridy++;

        signupLink = new JLabel("Volver a Login");
        mainPanel.add(signupLink, gbc);

        add(mainPanel, BorderLayout.CENTER);

        signupLink.setForeground(Color.BLUE);
        signupLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupLink.setHorizontalAlignment(SwingConstants.CENTER);
        // signupLink.setFont(new Font("Arial", Font.PLAIN, 12));
        signupLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                lv = new LoginView();
                lv.setVisible(true);
                dispose();
            }
        });
        setResizable(false);

    }

    public void mostrar() {
        setVisible(true);
    }

    private boolean validarCampos() {
        for (JTextField field : fields) {
            if (field.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
                return false;
            }
        }
        return true;
    }

    private boolean validarEmail() {
        if (!UsuarioValidacion.esValidoEmail(email)) {
            JOptionPane.showMessageDialog(this, "Email inválido.");
            return false;
        }
        return true;
    }

    private boolean validarTelefono() {
        if (!UsuarioValidacion.esValidoTel(tel)) {
            JOptionPane.showMessageDialog(this, "Teléfono inválido.");
            return false;
        }
        return true;
    }

    private boolean validarFechaNac() {
        if (!UsuarioValidacion.esValidoFechaNac(fechaNac)) {
            JOptionPane.showMessageDialog(this, "Fecha de nacimiento inválida.");
            return false;
        }
        return true;
    }

    private boolean validarPassword() {
        if (!UsuarioValidacion.esValidoPassword(pass)) {
            JOptionPane.showMessageDialog(this,
                    "Contraseña inválida. Debe contener al menos 8 caracteres, una letra mayúscula, una letra minúscula y un número.");
            return false;
        }
        return true;
    }

    private boolean validarRepetirPassword() {
        if (!pass.equals(repeatPass)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.");
            return false;
        }
        return true;
    }

    private boolean validarNombre() {
        if (!UsuarioValidacion.esValidoNombre(nombre)) {
            JOptionPane.showMessageDialog(this, "Nombre inválido.");
            return false;
        }
        return true;
    }

    private boolean validarApellido() {
        if (!UsuarioValidacion.esValidoNombre(apellido)) {
            JOptionPane.showMessageDialog(this, "Apellido inválido.");
            return false;
        }
        return true;
    }

    private boolean checkUsuarioNoExiste() {
        if (UsuarioDAO.checkUsuarioValido(email)) {
            JOptionPane.showMessageDialog(this, "El email ya está registrado.");
            return false;
        }
        return true;
    }

    private boolean validarTodo() {
        return validarCampos() && validarEmail() && validarTelefono() && validarFechaNac()
                && validarPassword() && validarRepetirPassword() && validarNombre() && validarApellido()
                && checkUsuarioNoExiste();
    }

    public void registrar() {
        nombre = fields[0].getText();
        apellido = fields[1].getText();
        email = fields[2].getText();
        tel = fields[3].getText();
        fechaNac = fields[4].getText();
        pass = new String(((JPasswordField) fields[5]).getPassword());
        repeatPass = new String(((JPasswordField) fields[6]).getPassword());

        if (validarTodo()) {
            Usuario usuario = new Usuario(nombre, apellido, tel, fechaNac, pass, email);
            int result = UsuarioDAO.crearUsuario(usuario);
            if (result == 1) {
                JOptionPane.showMessageDialog(this, "Usuario creado con éxito.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al crear usuario.");

            }
        }
        // dispose();

    }

}