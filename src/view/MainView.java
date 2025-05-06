package view;

import java.awt.*;
import javax.swing.*;
import model.Usuario;

public class MainView extends JFrame {

    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private final Usuario usuario;
    public static final String BTN_LOGIN = "Login";

    public MainView(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Dashboard");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String nombreCompleto = this.usuario.getNombre() + " " + this.usuario.getApellido();

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 20, 20)); // Márgenes externos

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 5, 0); // Espaciado vertical entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Usuario
        mainPanel.add(new JLabel("Hola " + nombreCompleto), gbc);
        gbc.gridy++;

        add(mainPanel);

    }
}
