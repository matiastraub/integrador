package com.gofinance.integrador.view;
import net.miginfocom.swing.MigLayout;
import com.gofinance.integrador.model.Usuario;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class MainView extends JFrame {

    private final Usuario usuario;
    private JPanel sidePanel;
    private JPanel contentPanel;

    private JLabel userName;
    private JLabel userEmail;

    private JButton btnDashboard;
    private JButton btnIngresos;
    private JButton btnGastos;
    private JButton btnRendimiento;
    private JButton btnUtilidades;
    private JButton btnAjustes;

    public MainView(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        String nombreCompleto = usuario.getNombre() + " " + usuario.getApellido();
        String correo = usuario.getEmail();

        // Panel lateral
        sidePanel = new JPanel(new MigLayout("wrap 1", "[grow, fill]", "[]20[]5[]20[]5[]5[]5[]5[]5[]push"));
        sidePanel.setPreferredSize(new Dimension(200, getHeight()));

        JLabel profilePic = new JLabel(); // Placeholder para imagen
        profilePic.setPreferredSize(new Dimension(64, 64));
        profilePic.setHorizontalAlignment(SwingConstants.CENTER);
        profilePic.setIcon(UIManager.getIcon("OptionPane.informationIcon")); // Temporal

        userName = new JLabel(nombreCompleto, SwingConstants.CENTER);
        userEmail = new JLabel(correo, SwingConstants.CENTER);

        btnDashboard = new JButton("Dashboard");
        btnIngresos = new JButton("Ingresos");
        btnGastos = new JButton("Gastos");
        btnRendimiento = new JButton("Rendimiento");
        btnUtilidades = new JButton("Utilidades");
        btnAjustes = new JButton("Ajustes");

        sidePanel.add(profilePic, "align center");
        sidePanel.add(userName, "align center");
        sidePanel.add(userEmail, "align center");

        sidePanel.add(btnDashboard);
        sidePanel.add(btnIngresos);
        sidePanel.add(btnGastos);
        sidePanel.add(btnRendimiento);
        sidePanel.add(btnUtilidades);
        sidePanel.add(btnAjustes);

        // Panel principal (contenido)
        contentPanel = new JPanel(new MigLayout("center, center"));
        JLabel welcomeLabel = new JLabel("Página Principal");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        contentPanel.add(welcomeLabel);

        add(sidePanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}
