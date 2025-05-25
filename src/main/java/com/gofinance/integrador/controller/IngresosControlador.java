package com.gofinance.integrador.controller;

import com.gofinance.integrador.database.TransaccionDAO;
import com.gofinance.integrador.model.Transaccion;
import com.gofinance.integrador.model.Usuario;
import com.gofinance.integrador.view.IngresosView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class IngresosControlador {

    private IngresosView vista;
    private Usuario usuario;

    public IngresosControlador(IngresosView vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.vista.setControlador(this);
        cargarIngresos();
    }

    public void registrarIngreso(String fecha, String nombre, String categoria, String valor) {
        float monto;
        try {
            monto = Float.parseFloat(valor);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Monto inválido.");
            return;
        }

        Transaccion t = new Transaccion(
                fecha,
                nombre,
                categoria,
                monto,
                1,
                1,
                usuario.getId(),
                1
        );

        int res = TransaccionDAO.crearTransaccion(t);
        if (res > 0) {
            cargarIngresos();
            JOptionPane.showMessageDialog(null, "Ingreso guardado correctamente.", "Añadido", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar ingreso.");
        }
    }

    public void eliminarUltimoIngreso() {
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

    public void eliminarIngresoSeleccionado(int filaSeleccionada) {
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona una fila para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas eliminar este ingreso?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmar != JOptionPane.YES_OPTION) {
            return;
        }

        ArrayList<Transaccion> lista = TransaccionDAO.getTransaccionesPorUsuario(usuario.getId());
        int contador = -1;
        int idReal = -1;

        for (int i = 0; i < lista.size(); i++) {
            Transaccion t = lista.get(i);
            if (t.getEsIngreso() == 1) {
                contador++;
                if (contador == filaSeleccionada) {
                    idReal = t.getId(); // ✅ usamos el nuevo campo `id`
                    break;
                }
            }
        }

        if (idReal != -1) {
            TransaccionDAO.eliminarTransaccion(idReal);
            cargarIngresos();
            JOptionPane.showMessageDialog(null, "Ingreso eliminado correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el ingreso.");
        }
    }

    public void cargarIngresos() {
        vista.limpiarTabla();
        ArrayList<Transaccion> ingresos = TransaccionDAO.getTransaccionesPorUsuario(usuario.getId());

        // Debug
        System.out.println("🧪 Usuario ID: " + usuario.getId());
        System.out.println("📊 Total transacciones encontradas: " + ingresos.size());

        DefaultTableModel modelo = vista.getTableModel();

        for (int i = 0; i < ingresos.size(); i++) {
            Transaccion t = ingresos.get(i);
            if (t.getEsIngreso() == 1) {
                String montoStr = String.format("+ %.2f €", t.getMonto());
                modelo.addRow(new Object[]{t.getFecha(), t.getNombre(), t.getDescripcion(), montoStr});
            }
        }
    }
}
