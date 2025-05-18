package com.gofinance.integrador.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

import raven.datetime.DatePicker;

@SuppressWarnings("serial")
public class IngresosView extends JPanel {

    private JButton btnAgregar;
    private JButton btnEliminar;
    private JTable tablaIngresos;
    private DefaultTableModel modelo;

    private DatePicker datePicker; // ✅ DatePicker declarado para uso desde el controlador

    public IngresosView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.BLACK);

        // Panel superior con botones
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        btnAgregar = new JButton("Registrar Ingreso");
        btnAgregar.setBackground(new Color(0, 191, 99));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);

        btnEliminar = new JButton("Eliminar Ingreso");
        btnEliminar.setBackground(new Color(255, 49, 49));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);

        topPanel.add(btnAgregar);
        topPanel.add(btnEliminar);

        // Tabla de ingresos
        String[] columnas = {"Fecha", "Nombre", "Categoría", "Monto"};
        modelo = new DefaultTableModel(columnas, 0);
        tablaIngresos = new JTable(modelo);
        tablaIngresos.setRowHeight(30);

        JScrollPane scroll = new JScrollPane(tablaIngresos);

        // Instanciar DatePicker para uso externo
        datePicker = new DatePicker();

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    public void setControlador(ActionListener c) {
        btnAgregar.addActionListener(c);
        btnEliminar.addActionListener(c);
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public DefaultTableModel getTableModel() {
        return modelo;
    }

    public void limpiarTabla() {
        modelo.setRowCount(0);
    }

    //Getter para que el controlador pueda usar el DatePicker
    public DatePicker getDatePicker() {
        return datePicker;
    }
}
