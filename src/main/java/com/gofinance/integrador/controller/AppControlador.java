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

    // Controladores específicos
    private IngresosControlador ingresosControlador;
    private GastosControlador gastosControlador;
    // private DashboardControlador dashboardControlador;
    // ...

    private int intentos;

    public AppControlador(LoginView lv, SignupView sv) {
        this.loginView = lv;
        this.signupView = sv;
        this.usuarioDAO = new UsuarioDAO();
        this.validador = new UsuarioValidacion();
        this.intentos = 0;

        // =================== SELECCIÓN DE FECHA EN SIGNUP ===================
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

            // =================== LOGIN ===================
            if (src.equals(loginView.getBtnLogin())) {
                Usuario u = loginView.getDatosUsuario();

                if (u != null) {
                    Usuario dbUser = usuarioDAO.buscarUsuarioPorEmail(u.getEmail());

                    if (dbUser != null) {
                        if (dbUser.getPassword().equals(u.getPassword())) {
                            loginView.dispose();
                            mainView = new MainView(dbUser);
                            mainView.setControlador(this); //Conectar navegación
                            mainView.setVisible(true);
                        } else {
                            intentos++;
                            if (intentos < MAX_INTENTOS) {
                                loginView.mostrarError("Contraseña incorrecta. Te quedan " + (MAX_INTENTOS - intentos) + " intentos.");
                            } else {
                                loginView.mostrarError("Has agotado todos los intentos.");
                                System.exit(0);
                            }
                        }
                    } else {
                        loginView.mostrarError("El usuario no existe.");
                    }
                }

            // =================== CAMBIAR A REGISTRO ===================
            } else if (src.equals(loginView.getBtnRegistro())) {
                loginView.dispose();
                signupView.setVisible(true);

            // =================== CANCELAR REGISTRO ===================
            } else if (src.equals(signupView.getBtnCancelar())) {
                signupView.dispose();
                loginView.setVisible(true);
                loginView.limpiarCampos();

            // =================== ACEPTAR REGISTRO ===================
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

            // =================== BOTONES DE LA VISTA PRINCIPAL ===================
            if (mainView != null) {

                if (src.equals(mainView.getBtnDashboard())) {
                    mainView.mostrarVista("Dashboard");

                    // En el futuro: inicializar dashboardControlador aquí

                } else if (src.equals(mainView.getBtnIngresos())) {
                    mainView.mostrarVista("Ingresos");

                    if (ingresosControlador == null) {
                        ingresosControlador = new IngresosControlador(mainView.getIngresosView(), mainView.getUsuario());
                    }

                } else if (src.equals(mainView.getBtnGastos())) {
                    mainView.mostrarVista("Gastos");

                    if (gastosControlador == null) {
                        gastosControlador = new GastosControlador(mainView.getGastosView(), mainView.getUsuario());
                    }

                } else if (src.equals(mainView.getBtnRendimiento())) {
                    mainView.mostrarVista("Rendimiento");

                    // rendimientoControlador = ...

                } else if (src.equals(mainView.getBtnUtilidades())) {
                    mainView.mostrarVista("Utilidades");

                    // utilidadesControlador = ...

                } else if (src.equals(mainView.getBtnAjustes())) {
                    mainView.mostrarVista("Ajustes");

                    // ajustesControlador = ...
                }
            }
        }
    }
}
