package com.gofinance.integrador.view;

import raven.datetime.DatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class GastosView extends JPanel {

    private JButton btnAgregar;
    private JButton btnEliminar;
    private JTable tablaGastos;
    private DefaultTableModel modelo;
    private DatePicker datePicker;

    public GastosView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.BLACK);

        // Panel superior con botones
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        btnAgregar = new JButton("Registrar Gasto");
        btnAgregar.setBackground(new Color(255, 85, 85));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);

        btnEliminar = new JButton("Eliminar Gasto");
        btnEliminar.setBackground(new Color(0, 191, 99));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);

        topPanel.add(btnAgregar);
        topPanel.add(btnEliminar);

        // Tabla de gastos
        String[] columnas = {"Fecha", "Nombre", "Categoría", "Monto"};
        modelo = new DefaultTableModel(columnas, 0);
        tablaGastos = new JTable(modelo);
        tablaGastos.setRowHeight(30);

        JScrollPane scroll = new JScrollPane(tablaGastos);

        // Inicializar DatePicker
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

    public DatePicker getDatePicker() {
        return datePicker;
    }
} 
