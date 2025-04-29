package com.gofinance.integrador.view;

import javax.swing.*;
import java.awt.*;

public class IngresosView extends JPanel {

    public IngresosView() {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout(10, 10));

        // Panel de botones arriba
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

        JButton btnAgregar = new JButton("Registrar Ingreso");
        btnAgregar.setBackground(new Color(0, 191, 99));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 14));
        btnAgregar.setPreferredSize(new Dimension(160, 40));

        JButton btnEliminar = new JButton("Eliminar Ingreso");
        btnEliminar.setBackground(new Color(255, 49, 49));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEliminar.setPreferredSize(new Dimension(160, 40));

        topPanel.add(btnAgregar, BorderLayout.WEST);
        topPanel.add(btnEliminar, BorderLayout.EAST);

        // Panel de lista
        JPanel listaPanel = new JPanel();
        listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
        listaPanel.setBackground(new Color(1, 1, 1));
        listaPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Ejemplo de ingresos
        listaPanel.add(createIngresoItem("Trabajo fijo 1", "Periodico", "+700€"));
        listaPanel.add(createIngresoItem("Trabajo App", "Ocasional", "+400€"));

        // Scroll para la lista
        JScrollPane scrollPane = new JScrollPane(listaPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(30, 30, 30));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.LIGHT_GRAY;
                this.trackColor = new Color(30, 30, 30);
            }
        });

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createIngresoItem(String titulo, String tipo, String monto) {
        JPanel item = new JPanel(new GridLayout(1, 3));
        item.setBackground(new Color(45, 45, 45));
        item.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel lblTipo = new JLabel(tipo);
        lblTipo.setForeground(Color.LIGHT_GRAY);
        lblTipo.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel lblMonto = new JLabel(monto);
        lblMonto.setForeground(new Color(0, 255, 128));
        lblMonto.setFont(new Font("Arial", Font.BOLD, 16));
        lblMonto.setHorizontalAlignment(SwingConstants.RIGHT);

        item.add(lblTitulo);
        item.add(lblTipo);
        item.add(lblMonto);

        return item;
    }
}
