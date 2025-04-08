package view;

import controller.UsuarioValidacion;
import database.UsuarioDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import model.Usuario;

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
        mainPanel.add(new JLabel("Email:"), gbc);
        gbc.gridy++;
        usernameField = new JTextField();
        mainPanel.add(usernameField, gbc);
        gbc.gridy++;

        // Contraseña
        mainPanel.add(new JLabel("Password:"), gbc);
        gbc.gridy++;
        passwordField = new JPasswordField();
        mainPanel.add(passwordField, gbc);
        gbc.gridy++;

        // Botón de Login
        loginBtn = new JButton(BTN_LOGIN);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.0;
        mainPanel.add(loginBtn, gbc);
        gbc.gridy++;

        loginBtn.addActionListener((ActionEvent e) -> {
            login();
        });

        // Enlace de registro
        signupLink = new JLabel("Nuevo Usuario? Registrarse aquí");
        mainPanel.add(signupLink, gbc);

        add(mainPanel, BorderLayout.CENTER);

        signupLink.setForeground(Color.BLUE);
        signupLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                sv = new SignupView();
                sv.setVisible(true);
                dispose();
            }
        });

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
            // Aquí puedes implementar la lógica de inicio de sesión
            // Por ejemplo, abrir la ventana principal de la aplicación
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
        }
    }

}