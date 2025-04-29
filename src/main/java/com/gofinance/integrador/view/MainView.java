package com.gofinance.integrador.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.gofinance.integrador.model.Usuario;

import net.miginfocom.swing.MigLayout;

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
        setSize(1000, 600);
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
        sidePanel = new JPanel(new MigLayout("wrap 1", "[grow, fill]", "[]20[]5[]20[]5[]5[]5[]5[]5[]5[]push"));
        sidePanel.setPreferredSize(new Dimension(200, getHeight()));

        JLabel profilePic = new JLabel();
        profilePic.setPreferredSize(new Dimension(64, 64));
        profilePic.setHorizontalAlignment(SwingConstants.CENTER);
        profilePic.setIcon(UIManager.getIcon("OptionPane.informationIcon"));

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

        // Panel de contenido con CardLayout
        contentPanel = new JPanel(new CardLayout());

        // Instanciar vistas
        JPanel dashboardView = new DashboardView();
        JPanel ingresosView = new IngresosView();
        JPanel gastosView = new GastosView();
        JPanel rendimientoView = new RendView();
        JPanel utilidadesView = new UtilsView();
        JPanel ajustesView = new AjustesView();

        // Agregar vistas al CardLayout
        contentPanel.add(dashboardView, "Dashboard");
        contentPanel.add(ingresosView, "Ingresos");
        contentPanel.add(gastosView, "Gastos");
        contentPanel.add(rendimientoView, "Rendimiento");
        contentPanel.add(utilidadesView, "Utilidades");
        contentPanel.add(ajustesView, "Ajustes");

        // Listeners
        btnDashboard.addActionListener(e -> switchPanel("Dashboard"));
        btnIngresos.addActionListener(e -> switchPanel("Ingresos"));
        btnGastos.addActionListener(e -> switchPanel("Gastos"));
        btnRendimiento.addActionListener(e -> switchPanel("Rendimiento"));
        btnUtilidades.addActionListener(e -> switchPanel("Utilidades"));
        btnAjustes.addActionListener(e -> switchPanel("Ajustes"));

        add(sidePanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        // Mostrar vista por defecto
        switchPanel("Dashboard");
    }

    private void switchPanel(String panelName) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, panelName);
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}
