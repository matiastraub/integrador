package com.gofinance.integrador.controller;

import com.gofinance.integrador.database.TransaccionDAO;
import com.gofinance.integrador.model.Transaccion;
import com.gofinance.integrador.model.Usuario;
import com.gofinance.integrador.view.GastosView;

import raven.datetime.DatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GastosControlador implements ActionListener {

    private GastosView vista;
    private Usuario usuario;

    public GastosControlador(GastosView vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.vista.setControlador(this);
        cargarGastos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(vista.getBtnAgregar())) {
            registrarGasto();
        } else if (e.getSource().equals(vista.getBtnEliminar())) {
            eliminarUltimoGasto();
        }
    }

    private void registrarGasto() {
        String nombre = JOptionPane.showInputDialog(null, "Nombre del gasto:");
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

        // Mostrar el DatePicker desde la vista
        DatePicker dp = vista.getDatePicker();
        JOptionPane.showMessageDialog(null, dp, "Selecciona la fecha del gasto", JOptionPane.PLAIN_MESSAGE);
        LocalDate fechaSeleccionada = dp.getSelectedDate();

        if (fechaSeleccionada == null) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fecha válida.");
            return;
        }

        Transaccion t = new Transaccion(
                fechaSeleccionada.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                nombre,
                "Gasto registrado",
                monto,
                1, // fkCategoria por defecto (añadir más adelante)
                1, // fkMetodoPago por defecto (añadir más adelante)
                usuario.getId(),
                0 // esIngreso = 0 es gasto
        );

        int res = TransaccionDAO.crearTransaccion(t);
        if (res > 0) {
            cargarGastos();
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar gasto.");
        }
    }

    private void eliminarUltimoGasto() {
        DefaultTableModel modelo = vista.getTableModel();
        int filas = modelo.getRowCount();

        if (filas > 0) {
            int confirm = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar el último gasto?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int ultimoId = TransaccionDAO.getUltimoIdGasto(usuario.getId());
                TransaccionDAO.eliminarTransaccion(ultimoId);
                cargarGastos();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay gastos para eliminar.");
        }
    }

    private void cargarGastos() {
        vista.limpiarTabla();
        ArrayList<Transaccion> gastos = TransaccionDAO.getTransaccionesPorUsuario(usuario.getId());
        DefaultTableModel modelo = vista.getTableModel();

        for (Transaccion t : gastos) {
            if (t.getEsIngreso() == 0) {
                String montoStr = "<html><font color='red'>- " + t.getMonto() + " €</font></html>";
                modelo.addRow(new Object[]{t.getFecha(), t.getNombre(), "Categoría", montoStr});
            }
        }
    }
}
