package com.gofinance.integrador.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.gofinance.integrador.database.UsuarioDAO;
import com.gofinance.integrador.model.Usuario;
import com.gofinance.integrador.view.AjustesView;

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
        // Asignamos un listener para abrir el selector de fecha
        this.vista.getBtnSeleccionarFecha().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(vista, vista.getDatePicker(),
                        "Selecciona tu fecha de nacimiento", JOptionPane.PLAIN_MESSAGE);
            }
        });
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

    public boolean isPasswordChangeValid(String storedPassword) {
        if (!vista.getIsOpenPasswordField()) {
            return true;
        }

        String actualPassword = vista.getCampoPasswordActual();
        String newPassword = vista.getCampoPassword();
        String repeatPassword = vista.getCampoConfirmPassword();

        if (actualPassword.isEmpty()) {
            vista.mostrarError("Debe ingresar la contraseña actual.");
            return false;
        }

        if (!actualPassword.equals(storedPassword)) {
            vista.mostrarError("La contraseña actual no es correcta.");
            return false;
        }

        if (newPassword.isEmpty() || repeatPassword.isEmpty()) {
            vista.mostrarError("Debe ingresar la nueva contraseña y repetirla.");
            return false;
        }

        if (!newPassword.equals(repeatPassword)) {
            vista.mostrarError("Las nuevas contraseñas no coinciden.");
            return false;
        }

        if (newPassword.length() < 6) {
            vista.mostrarError("La nueva contraseña debe tener al menos 6 caracteres.");
            return false;
        }

        // Add more security rules as needed here (e.g., special chars, digits)

        return true;
    }

    private String applyValidations() {
        Usuario usuario = vista.getUsuario();
        UsuarioValidacion validacion = new UsuarioValidacion();
        if (usuario.getNombre().isEmpty() || usuario.getApellido().isEmpty() || usuario.getEmail().isEmpty() ||
                usuario.getTelefono().isEmpty() || usuario.getFechaNac().isEmpty()) {
            return "Todos los campos son obligatorios";
        }
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

        return "success";
    }

    private void updateUsuario() {
        // Aquí se pueden agregar validaciones adicionales si es necesario
        String validacionResultado = applyValidations();
        if (validacionResultado.equals("success")) {
            Usuario usuarioActualizado = vista.getUsuario();
            String passwordOriginal = usuarioOriginal.getPassword();

            if (!vista.getIsOpenPasswordField()) {
                // Si la contraseña está vacía, no se requiere validación adicional
                System.out.println("User password: " + usuarioOriginal.getPassword());
                usuarioActualizado.setPassword(passwordOriginal);
            } else if (isPasswordChangeValid(passwordOriginal)) {
                // Si la contraseña es válida, la actualizamos
                String nuevaPassword = vista.getCampoPassword();
                System.out.println("Nueva contraseña: " + nuevaPassword);
                usuarioActualizado.setPassword(nuevaPassword);
            } else {
                // Si la contraseña no es válida, mostramos un error

                return;
            }

            // Intentamos actualizar el usuario en la base de datos
            try {
                Usuario usuarioDB = usuarioDAO.buscarUsuarioPorEmail(usuarioOriginal.getEmail());
                int res = usuarioDAO.actualizarUsuario(usuarioDB.getId(), usuarioActualizado);
                System.out.println("Resultado de la actualización: " + res);
                if (res == 1) {
                    System.out.println("Usuario actualizado correctamente.");
                    vista.cancelarCambios(usuarioActualizado);
                    // SwingUtilities.updateComponentTreeUI(new MainView(usuarioActualizado));
                    vista.guardarCambios(validacionResultado);

                } else {
                    System.out.println("No se pudo actualizar el usuario. Verifique los datos.");
                    vista.mostrarError("No se pudo actualizar el usuario. Verifique los datos.");
                }
            } catch (Exception e) {
                System.out.println("Error al actualizar el usuario: " + e.getMessage());
            }

        } else {
            vista.mostrarError(validacionResultado);
        }

    }
}
