package com.gofinance.integrador.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.gofinance.integrador.database.UsuarioDAO;
import com.gofinance.integrador.model.Usuario;
import com.gofinance.integrador.view.*;

public class AppControlador implements ActionListener {

    private static final int MAX_INTENTOS = 3;

    private LoginView loginView;
    private SignupView signupView;
    private MainView mainView;

    private UsuarioDAO usuarioDAO;
    private UsuarioValidacion validador;

    // Controladores Específicos - aquí los guardamos como atributos
    private DashboardControlador dashboardControlador;
    @SuppressWarnings("unused")
	private IngresosControlador ingresosControlador;
    @SuppressWarnings("unused")
	private GastosControlador gastosControlador;
    @SuppressWarnings("unused")
	private AjustesControlador ajustesControlador;
    @SuppressWarnings("unused")
	private RendimientoControlador rendimientoControlador;
    @SuppressWarnings("unused")
	private UtilidadesControlador utilidadesControlador;
    private int intentos;

    public AppControlador(LoginView lv, SignupView sv) {
        this.loginView = lv;
        this.signupView = sv;
        this.usuarioDAO = new UsuarioDAO();
        this.validador = new UsuarioValidacion();
        this.intentos = 0;

        // Asignamos un listener para abrir el selector de fecha
        this.signupView.getBtnSeleccionarFecha().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(signupView, signupView.getDatePicker(),
                        "Selecciona tu fecha de nacimiento", JOptionPane.PLAIN_MESSAGE);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src instanceof JButton) {

            // Procesamos el intento de login
            if (src.equals(loginView.getBtnLogin())) {
                Usuario u = loginView.getDatosUsuario();

                if (u != null) {
                    Usuario dbUser = usuarioDAO.buscarUsuarioPorEmail(u.getEmail());

                    if (dbUser != null) {
                        if (dbUser.getPassword().equals(u.getPassword())) {
                            loginView.dispose();
                            mainView = new MainView(dbUser);
                            mainView.setControlador(this);
                            
                            // INSTANCIAR TODOS LOS CONTROLADORES AL CREAR MAINVIEW
                            inicializarControladores();
                            
                            mainView.setVisible(true);
                        } else {
                            intentos++;
                            if (intentos < MAX_INTENTOS) {
                                loginView.mostrarError(
                                        "Contraseña incorrecta. Te quedan " + (MAX_INTENTOS - intentos) + " intentos.");
                            } else {
                                loginView.mostrarError("Has agotado todos los intentos.");
                                System.exit(0);
                            }
                        }
                    } else {
                        loginView.mostrarError("El usuario no existe.");
                    }
                }

                // Abrimos la vista de registro
            } else if (src.equals(loginView.getBtnRegistro())) {
                loginView.dispose();
                signupView.setVisible(true);

                // Cancelamos el registro y volvemos al login
            } else if (src.equals(signupView.getBtnCancelar())) {
                signupView.dispose();
                loginView.setVisible(true);
                loginView.limpiarCampos();

                // Intentamos registrar al nuevo usuario
            } else if (src.equals(signupView.getBtnAceptar())) {
                Usuario nuevo = signupView.getDatosUsuario();

                if (nuevo != null &&
                        validador.esValidoNombre(nuevo.getNombre()) &&
                        validador.esValidoEmail(nuevo.getEmail()) &&
                        validador.esValidoTel(nuevo.getTelefono()) &&
                        validador.esValidoPassword(nuevo.getPassword()) &&
                        validador.esValidoFechaNac(nuevo.getFechaNac())) {

                    int res = usuarioDAO.crearUsuario(nuevo);
                    if (res == 1) {
                        signupView.mostrarMensaje("Usuario registrado con éxito.");
                        signupView.dispose();
                        loginView.setVisible(true);
                        loginView.cargarUsuario(nuevo.getEmail());
                    } else {
                        signupView.mostrarError("No se pudo registrar. Puede que el email ya exista.");
                    }

                } else {
                    signupView.mostrarError("Datos inválidos. Revisa todos los campos.");
                }
            }

            // Controlamos la navegación dentro de la vista principal
            if (mainView != null) {

                if (src.equals(mainView.getBtnDashboard())) {
                    mainView.mostrarVista("Dashboard");
                    // El dashboard ya está inicializado, solo actualizamos datos
                    if (dashboardControlador != null) {
                        dashboardControlador.actualizarDatos();
                    }

                } else if (src.equals(mainView.getBtnIngresos())) {
                    mainView.mostrarVista("Ingresos");
                    // Ya no necesitamos verificar null, está inicializado

                } else if (src.equals(mainView.getBtnGastos())) {
                    mainView.mostrarVista("Gastos");
                    // Ya no necesitamos verificar null, está inicializado

                } else if (src.equals(mainView.getBtnRendimiento())) {
                    mainView.mostrarVista("Rendimiento");
                    // rendimientoControlador = ...

                } else if (src.equals(mainView.getBtnUtilidades())) {
                    mainView.mostrarVista("Utilidades");
                    if (utilidadesControlador != null) {
                        mainView.getUtilidadesView().refrescarVista();
                    }

                } else if (src.equals(mainView.getBtnAjustes())) {
                    mainView.mostrarVista("Perfil");
                    // Ya no necesitamos verificar null, está inicializado
                }
            }
        }
    }

    /**
     * Inicializa todos los controladores de una vez cuando se crea MainView
     */
    private void inicializarControladores() {
        if (mainView != null) {
            Usuario usuario = mainView.getUsuario();
            
            // Inicializar todos los controladores
            dashboardControlador = new DashboardControlador(mainView.getDashboardView(), usuario);
            ingresosControlador = new IngresosControlador(mainView.getIngresosView(), usuario);
            gastosControlador = new GastosControlador(mainView.getGastosView(), usuario);
            ajustesControlador = new AjustesControlador(mainView.getAjustesView(), usuario);
            UtilsView utilsView = new UtilsView(usuario.getId());
            mainView.setUtilsView(utilsView);
            utilidadesControlador = new UtilidadesControlador(utilsView, usuario.getId());

         // Al pulsar el botón de Rendimiento en AppControlador:
            rendimientoControlador = new RendimientoControlador(mainView.getRendimientoView(), usuario.getId());

        }
    }

    /**
     * Método público para actualizar el dashboard desde otros controladores
     */
    public void actualizarDashboard() {
        if (dashboardControlador != null) {
            dashboardControlador.actualizarDatos();
        }
    }

    // Getters para que otros controladores puedan comunicarse
    public DashboardControlador getDashboardControlador() {
        return dashboardControlador;
    }
}