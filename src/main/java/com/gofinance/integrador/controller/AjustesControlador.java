package com.gofinance.integrador.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import com.gofinance.integrador.database.UsuarioDAO;
import com.gofinance.integrador.model.Usuario;
import com.gofinance.integrador.view.AjustesView;
import com.gofinance.integrador.view.MainView;

public class AjustesControlador implements ActionListener {

    private AjustesView vista;
    private final Usuario usuarioOriginal;

    private final UsuarioDAO usuarioDAO;

    public AjustesControlador(AjustesView vista, Usuario usuario) {
        this.vista = vista;
        this.vista.setUsuario(usuario);
        this.vista.setControlador(this);
        this.usuarioOriginal = usuario;
        this.usuarioDAO = new UsuarioDAO();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() instanceof JButton) {

            switch (e.getActionCommand()) {
                case AjustesView.BTN_ACEPTAR:
                    updateUsuario();
                    break;
                case AjustesView.BTN_CANCELAR:
                    vista.cancelarCambios(usuarioOriginal);
                    break;
                case AjustesView.BTN_EDITAR:
                    vista.editarCambios();
                    break;
                case AjustesView.BTN_PASSWORD:
                    vista.togglePasswordFields();
                    break;
                default:
                    System.out.println("Comando no reconocido: " + e.getActionCommand());
            }
        }

    }

    private String applyValidations() {
        Usuario usuario = vista.getUsuario();
        UsuarioValidacion validacion = new UsuarioValidacion();
        if (!validacion.esValidoNombre(usuario.getNombre())) {
            return "Nombre inválido. Debe tener entre 2 y 30 caracteres y solo letras.";
        }
        if (!validacion.esValidoNombre(usuario.getApellido())) {

            return "Apellido inválido. Debe tener entre 2 y 30 caracteres y solo letras.";
        }
        if (!validacion.esValidoEmail(usuario.getEmail())) {
            return "Email inválido. Debe tener al menos 5 caracteres y un formato válido.";
        }
        if (!validacion.esValidoTel(usuario.getTelefono())) {
            return "Teléfono inválido. Debe tener 9 dígitos.";
        }
        if (!usuario.getPassword().isEmpty() && !validacion.esValidoPassword(usuario.getPassword())) {
            return "Contraseña inválida. Debe tener entre 8 y 20 caracteres, al menos una mayúscula, una minúscula y un número.";
        }
        return "success";
    }

    private void updateUsuario() {
        // Aquí se pueden agregar validaciones adicionales si es necesario
        String validacionResultado = applyValidations();
        if (validacionResultado.equals("success")) {
            Usuario usuarioActualizado = vista.getUsuario();
            if (usuarioActualizado != null) {
                try {
                    Usuario usuarioDB = usuarioDAO.buscarUsuarioPorEmail(usuarioOriginal.getEmail());
                    int res = usuarioDAO.actualizarUsuario(usuarioDB.getId(), usuarioActualizado);
                    System.out.println("Resultado de la actualización: " + res);
                    if (res == 1) {
                        System.out.println("Usuario actualizado correctamente.");
                        vista.mostrarMensaje("Usuario actualizado correctamente.");
                        vista.cancelarCambios(usuarioActualizado);
                        SwingUtilities.updateComponentTreeUI(new MainView(usuarioActualizado));

                    } else {
                        System.out.println("No se pudo actualizar el usuario. Verifique los datos.");
                        vista.mostrarError("No se pudo actualizar el usuario. Verifique los datos.");
                    }
                } catch (Exception e) {
                    System.out.println("Error al actualizar el usuario: " + e.getMessage());
                }
            } else {
                System.out.println("No se pudo actualizar el usuario. Datos inválidos.");
            }
        } else {
            System.out.println(validacionResultado);
        }
        vista.guardarCambios(validacionResultado);
    }
}
