package com.gofinance.integrador.view;

import com.gofinance.integrador.model.Usuario;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class MainView extends JFrame {

    private Usuario usuario;
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

    // Vistas individuales como atributos privados
    private DashboardView dashboardView;
    private IngresosView ingresosView;
    private GastosView gastosView;
    private RendView rendimientoView;
    private UtilsView utilidadesView;
    private AjustesView ajustesView;

    public MainView(Usuario usuario) {
        this.usuario = usuario;

        setTitle("GoFinance!");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initComponents();
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

        // Panel central con CardLayout
        contentPanel = new JPanel(new CardLayout());

        // Instanciar todas las vistas una sola vez
        dashboardView = new DashboardView();
        ingresosView = new IngresosView();
        gastosView = new GastosView();
        rendimientoView = new RendView();
        utilidadesView = new UtilsView();
        ajustesView = new AjustesView();

        // Agregar vistas al contentPanel
        contentPanel.add(dashboardView, "Dashboard");
        contentPanel.add(ingresosView, "Ingresos");
        contentPanel.add(gastosView, "Gastos");
        contentPanel.add(rendimientoView, "Rendimiento");
        contentPanel.add(utilidadesView, "Utilidades");
        contentPanel.add(ajustesView, "Ajustes");

        // Añadir al frame
        add(sidePanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    //Mostrar una vista según nombre
    public void mostrarVista(String nombreVista) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, nombreVista);
    }

    //Inyectar controlador principal (AppControlador)
    public void setControlador(ActionListener c) {
        btnDashboard.addActionListener(c);
        btnIngresos.addActionListener(c);
        btnGastos.addActionListener(c);
        btnRendimiento.addActionListener(c);
        btnUtilidades.addActionListener(c);
        btnAjustes.addActionListener(c);
    }

    //Getters para que AppControlador compare eventos
    public JButton getBtnDashboard() { return btnDashboard; }
    public JButton getBtnIngresos() { return btnIngresos; }
    public JButton getBtnGastos() { return btnGastos; }
    public JButton getBtnRendimiento() { return btnRendimiento; }
    public JButton getBtnUtilidades() { return btnUtilidades; }
    public JButton getBtnAjustes() { return btnAjustes; }

    //Getters públicos para los controladores específicos
    public IngresosView getIngresosView() { return ingresosView; }
    public DashboardView getDashboardView() { return dashboardView; }
    public GastosView getGastosView() { return gastosView; }
    public RendView getRendimientoView() { return rendimientoView; }
    public UtilsView getUtilidadesView() { return utilidadesView; }
    public AjustesView getAjustesView() { return ajustesView; }

	public Usuario getUsuario() {
		
		return usuario;
	}
}
