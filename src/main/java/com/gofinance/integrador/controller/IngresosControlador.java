package com.gofinance.integrador.controller;

import com.gofinance.integrador.database.TransaccionDAO;
import com.gofinance.integrador.model.Transaccion;
import com.gofinance.integrador.model.Usuario;
import com.gofinance.integrador.view.IngresosView;
import raven.datetime.DatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class IngresosControlador implements ActionListener {

    private IngresosView vista;
    private Usuario usuario;
    private DatePicker datePicker;

    public IngresosControlador(IngresosView vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.vista.setControlador(this);
        this.datePicker = new DatePicker();
        this.datePicker.setDateSelectionAble(date -> !date.isAfter(LocalDate.now()));
        cargarIngresos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(vista.getBtnAgregar())) {
            registrarIngreso();
        } else if (e.getSource().equals(vista.getBtnEliminar())) {
            eliminarUltimoIngreso();
        }
    }

    private void registrarIngreso() {
        String nombre = JOptionPane.showInputDialog(null, "Nombre del ingreso:");
        if (nombre == null || nombre.trim().isEmpty()) return;

        String montoStr = JOptionPane.showInputDialog(null, "Monto:");
        if (montoStr == null || montoStr.trim().isEmpty()) return;

        float monto;
        try {
            monto = Float.parseFloat(montoStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Monto inválido.");
            return;
        }

        JOptionPane.showMessageDialog(null, datePicker, "Selecciona la fecha del ingreso", JOptionPane.PLAIN_MESSAGE);
        LocalDate fechaSeleccionada = datePicker.getSelectedDate();
        if (fechaSeleccionada == null) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fecha válida.");
            return;
        }

        Transaccion t = new Transaccion(
                fechaSeleccionada.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                nombre,
                "Ingreso registrado",
                monto,
                1, // fkCategoria por defecto
                1, // fkMetodoPago por defecto
                usuario.getId(), // ← Asociado al usuario logueado
                1 // esIngreso
        );

        int res = TransaccionDAO.crearTransaccion(t);
        if (res > 0) {
            cargarIngresos();
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar ingreso.");
        }
    }

    private void eliminarUltimoIngreso() {
        DefaultTableModel modelo = vista.getTableModel();
        int filas = modelo.getRowCount();

        if (filas > 0) {
            int confirm = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar el último ingreso?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int ultimoId = TransaccionDAO.getUltimoIdIngreso(usuario.getId());
                TransaccionDAO.eliminarTransaccion(ultimoId);
                cargarIngresos();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay ingresos para eliminar.");
        }
    }

    private void cargarIngresos() {
        vista.limpiarTabla();
        ArrayList<Transaccion> ingresos = TransaccionDAO.getTransaccionesPorUsuario(usuario.getId());
        DefaultTableModel modelo = vista.getTableModel();

        for (Transaccion t : ingresos) {
            if (t.getEsIngreso() == 1) {
                String montoStr = "<html><font color='green'>+ " + t.getMonto() + " €</font></html>";
                modelo.addRow(new Object[]{t.getFecha(), t.getNombre(), "Categoría", montoStr});
            }
        }
    }
}
