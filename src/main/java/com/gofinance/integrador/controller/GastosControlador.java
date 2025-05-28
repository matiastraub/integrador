package com.gofinance.integrador.controller;

import com.gofinance.integrador.database.TransaccionDAO;
import com.gofinance.integrador.model.Transaccion;
import com.gofinance.integrador.model.Usuario;
import com.gofinance.integrador.view.VentanaGastos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class GastosControlador {

    private VentanaGastos vista;
    private Usuario usuario;

    public GastosControlador(VentanaGastos vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.vista.setControlador(this);
        cargarGastos(); // Cargar los gastos al iniciar
    }

    public void registrarGasto(String fecha, String nombre, String categoria, String valor) {
        float monto;
        int idCat = CategoriaControlador.getIdCategoriaGasto(categoria);
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
                idCat, // fkcategoria
                1, // fkmetodo_pago
                usuario.getId(),
                0 // esIngreso = 0 (gasto)
        );

        int resultado = TransaccionDAO.crearTransaccion(t);
        if (resultado > 0) {
            cargarGastos();
            JOptionPane.showMessageDialog(null, "Gasto guardado correctamente.", "Añadido", JOptionPane.INFORMATION_MESSAGE);

            // Debug
            int total = TransaccionDAO.getTransaccionesPorUsuario(usuario.getId()).size();
            System.out.println("🧾 Usuario ID: " + usuario.getId());
            System.out.println("📦 Total transacciones actuales: " + total);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo guardar el gasto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargarGastos() {
        vista.limpiarTabla();
        List<Transaccion> lista = TransaccionDAO.getTransaccionesPorUsuario(usuario.getId());
        DefaultTableModel modelo = vista.getModeloTabla();

        for (int i = 0; i < lista.size(); i++) {
            Transaccion t = lista.get(i);
            if (t.getEsIngreso() == 0) {
                String montoStr = String.format("- %.2f €", t.getMonto());
                modelo.addRow(new Object[]{t.getFecha(), t.getNombre(), t.getDescripcion(), montoStr});
            }
        }

        System.out.println("📂 Carga de gastos para usuario ID " + usuario.getId() + ": " + modelo.getRowCount() + " filas.");
    }

    public void eliminarGastoSeleccionado(int filaSeleccionada) {
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona una fila para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "¿Eliminar este gasto?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Obtener ID real de la transacción desde la posición en tabla
        List<Transaccion> lista = TransaccionDAO.getTransaccionesPorUsuario(usuario.getId());
        int contador = -1;
        int idReal = -1;
        
        for (Transaccion t : lista) {
            if (t.getEsIngreso() == 0) {
                contador++;
                if (contador == filaSeleccionada) {
                    idReal = t.getId(); // Asegúrate de que el modelo Transaccion tiene getId()
                    break;
                }
            }
        }

        if (idReal != -1) {
            TransaccionDAO.eliminarTransaccion(idReal);
            cargarGastos();
            JOptionPane.showMessageDialog(null, "Gasto eliminado correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el gasto.");
        }
    }
}
