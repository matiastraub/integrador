package com.gofinance.integrador.view;

import com.gofinance.integrador.model.Usuario;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

@SuppressWarnings("serial")
public class MainView extends JFrame {

    private Usuario usuario;
    private JPanel sidePanel;
    private JPanel contentPanel;

    private JLabel userName;
    @SuppressWarnings("unused")
	private JLabel userEmail;  // Se mantiene declarada, pero no se añade al panel

    private JButton btnDashboard;
    private JButton btnIngresos;
    private JButton btnGastos;
    private JButton btnRendimiento;
    private JButton btnUtilidades;
    private JButton btnAjustes;

    // Vistas individuales como atributos privados
    private DashboardView dashboardView;
    private IngresosView ingresosView;
    private VentanaGastos ventanaGastos;
    private RendView rendimientoView;
    private UtilsView utilidadesView;
    private AjustesView ajustesView;

    public MainView(Usuario usuario) {
        this.usuario = usuario;

        setTitle("GoFinance!");
        setSize(1000, 600);
        setResizable(false);               // La ventana ya no es redimensionable
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents() {
        String nombreCompleto = usuario.getNombre() + " " + usuario.getApellido();
        String correo = usuario.getEmail();

        // Panel lateral (8 filas antes del push: foto, nombre y 6 botones)
        sidePanel = new JPanel(new MigLayout(
            "wrap 1",
            "[grow, fill]",
            "[]5[]5[]5[]5[]5[]5[]5[]5[]push"
            //    pic  ▸gap5◂  nombre  ▸gap5◂  btn1  ▸gap5◂ btn2  ▸gap5◂ btn3
            // ▸gap5◂ btn4  ▸gap5◂ btn5  ▸gap5◂ btn6  ▸push◂
        ));
        sidePanel.setPreferredSize(new Dimension(200, getHeight()));

        JLabel profilePic = new JLabel();
        profilePic.setPreferredSize(new Dimension(64, 64));
        profilePic.setHorizontalAlignment(SwingConstants.CENTER);
        // Carga de imagen con try-catch y placeholder en caso de fallo
        try {
            URL url = getClass().getResource("/images/default_profile.png");
            if (url == null) throw new Exception("Recurso no encontrado");
            ImageIcon original = new ImageIcon(url);
            Image scaled = original.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            profilePic.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            profilePic.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
        }

        userName  = new JLabel(nombreCompleto, SwingConstants.CENTER);
        userEmail = new JLabel(correo, SwingConstants.CENTER);  // No se añade al panel

        btnDashboard   = new JButton("Dashboard");
        btnIngresos    = new JButton("Ingresos");
        btnGastos      = new JButton("Gastos");
        btnRendimiento = new JButton("Rendimiento");
        btnUtilidades  = new JButton("Utilidades");
        btnAjustes     = new JButton("Perfil");

        // Añadir componentes, con ancho completo y altura fija
        sidePanel.add(profilePic,  "align center");
        sidePanel.add(userName,    "align center");
        // sidePanel.add(userEmail, "align center");  // Desactivado para no mostrar correo
        sidePanel.add(btnDashboard,   "align center, growx, h 36!");
        sidePanel.add(btnIngresos,    "align center, growx, h 36!");
        sidePanel.add(btnGastos,      "align center, growx, h 36!");
        sidePanel.add(btnRendimiento, "align center, growx, h 36!");
        sidePanel.add(btnUtilidades,  "align center, growx, h 36!");
        sidePanel.add(btnAjustes,     "align center, growx, h 36!");

        // Panel central con CardLayout
        contentPanel = new JPanel(new CardLayout());

        // SOLO instanciar las vistas, NO los controladores aquí, para respetar el MVC
        dashboardView   = new DashboardView();
        ingresosView    = new IngresosView();
        ventanaGastos   = new VentanaGastos();
        rendimientoView = new RendView();
        ajustesView     = new AjustesView();

        // Agregar vistas al contentPanel
        contentPanel.add(dashboardView,   "Dashboard");
        contentPanel.add(ingresosView,    "Ingresos");
        contentPanel.add(ventanaGastos,   "Gastos");
        contentPanel.add(rendimientoView, "Rendimiento");
        contentPanel.add(ajustesView,     "Perfil");

        // Añadir al frame
        add(sidePanel,    BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    // Mostrar una vista según nombre
    public void mostrarVista(String nombreVista) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, nombreVista);
    }

    // Inyectar controlador principal (AppControlador)
    public void setControlador(ActionListener c) {
        btnDashboard.addActionListener(c);
        btnIngresos.addActionListener(c);
        btnGastos.addActionListener(c);
        btnRendimiento.addActionListener(c);
        btnUtilidades.addActionListener(c);
        btnAjustes.addActionListener(c);
    }

    // Getters para que AppControlador compare eventos
    public JButton getBtnDashboard()   { return btnDashboard; }
    public JButton getBtnIngresos()    { return btnIngresos; }
    public JButton getBtnGastos()      { return btnGastos; }
    public JButton getBtnRendimiento() { return btnRendimiento; }
    public JButton getBtnUtilidades()  { return btnUtilidades; }
    public JButton getBtnAjustes()     { return btnAjustes; }

    // Getters públicos para los controladores específicos
    public IngresosView getIngresosView()    { return ingresosView; }
    public DashboardView getDashboardView()  { return dashboardView; }
    public VentanaGastos getGastosView()     { return ventanaGastos; }
    public RendView getRendimientoView()     { return rendimientoView; }
    public UtilsView getUtilidadesView()     { return utilidadesView; }
    public AjustesView getAjustesView()      { return ajustesView; }

    public Usuario getUsuario() {
        return usuario;
    }
    // Setter para la visita de utilidades
    public void setUtilsView(UtilsView vista) {
        this.utilidadesView = vista;
        contentPanel.add(utilidadesView, "Utilidades");
    }

}
