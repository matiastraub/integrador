package com.gofinance.integrador.view;

import com.gofinance.integrador.controller.CategoriaControlador;
import com.gofinance.integrador.controller.GastosControlador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class VentanaGastos extends JPanel {

    private JTable tablaGastos;
    private DefaultTableModel modeloTabla;
    private JButton btnRegistrar;
    private JButton btnAniadir;
    private JButton btnEliminarSeleccionada;
    private JTextField txtNombre;
    private JComboBox<String> comboBoxCategoria;
    private JTextField txtValor;
    private JSpinner datePicker;
    private JPanel panelFormulario;

    private GastosControlador controlador;

    public VentanaGastos() {

        setLayout(null);
        setPreferredSize(new Dimension(784, 600));

        // Tabla de gastos
        String[] columnas = {"Fecha", "Nombre", "Categoría", "Valor (€)"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaGastos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaGastos);
        scrollTabla.setBounds(50, 20, 700, 256);
        add(scrollTabla);

        // Botón Eliminar fila
        btnEliminarSeleccionada = new JButton("Eliminar fila seleccionada");
        btnEliminarSeleccionada.setFont(new Font("Arial", Font.BOLD, 16));
        btnEliminarSeleccionada.setBackground(Color.RED);
        btnEliminarSeleccionada.setForeground(Color.WHITE);
        btnEliminarSeleccionada.setBounds(439, 286, 311, 30);
        btnEliminarSeleccionada.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaGastos.getSelectedRow();
                if (controlador != null) {
                	controlador.eliminarGastoSeleccionado(filaSeleccionada);
                } else {
                    JOptionPane.showMessageDialog(VentanaGastos.this, "Selecciona una fila para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        add(btnEliminarSeleccionada);

        // Panel formulario
        panelFormulario = new JPanel(null);
        panelFormulario.setBounds(50, 326, 700, 200);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(0, 0, 100, 25);
        panelFormulario.add(lblFecha);
        SpinnerDateModel dateModel = new SpinnerDateModel();
        datePicker = new JSpinner(dateModel);
        datePicker.setEditor(new JSpinner.DateEditor(datePicker, "dd/MM/yyyy"));
        datePicker.setBounds(120, 0, 253, 25);
        panelFormulario.add(datePicker);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(0, 35, 100, 25);
        panelFormulario.add(lblNombre);
        txtNombre = new JTextField();
        txtNombre.setBounds(120, 35, 253, 25);
        panelFormulario.add(txtNombre);

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(0, 70, 100, 25);
        panelFormulario.add(lblCategoria);
        comboBoxCategoria = new JComboBox<>();
        String[] categorias = CategoriaControlador.obtenerCategoriasGastos();
        for (int i = 0; i < categorias.length; i++) {
            comboBoxCategoria.addItem(categorias[i]);
        }
        comboBoxCategoria.setBounds(120, 70, 253, 25);
        panelFormulario.add(comboBoxCategoria);

        JLabel lblValor = new JLabel("Valor (€):");
        lblValor.setBounds(0, 105, 100, 25);
        panelFormulario.add(lblValor);
        txtValor = new JTextField();
        txtValor.setBounds(120, 105, 253, 25);
        panelFormulario.add(txtValor);

        btnAniadir = new JButton("Añadir");
        btnAniadir.setBounds(120, 145, 253, 30);
        panelFormulario.add(btnAniadir);

        panelFormulario.setVisible(false);
        add(panelFormulario);

        // Botón Registrar
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(50, 286, 371, 30);
        btnRegistrar.setBackground(new Color(0, 224, 131));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 16));
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelFormulario.setVisible(true);
            }
        });
        add(btnRegistrar);

        // Botón Añadir → llama al controlador (sin tocar DAO)
        btnAniadir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fecha = ((JSpinner.DateEditor) datePicker.getEditor()).getFormat().format(datePicker.getValue());
                String nombre = txtNombre.getText();
                String categoria = (String) comboBoxCategoria.getSelectedItem();
                String valor = txtValor.getText();

                if (!nombre.isEmpty() && !valor.isEmpty()) {
                    controlador.registrarGasto(fecha, nombre, categoria, valor);
                    limpiarCampos();
                    datePicker.setValue(new Date());
                    panelFormulario.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(VentanaGastos.this, "Por favor completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void setControlador(GastosControlador controlador) {
        this.controlador = controlador;
    }

    public void aniadirFilaTabla(String fecha, String nombre, String categoria, String valor) {
        modeloTabla.addRow(new Object[]{fecha, nombre, categoria, valor});
    }

    public void limpiarCampos() {
        txtNombre.setText("");
        txtValor.setText("");
    }

    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public String getFechaSeleccionada() {
        return ((JSpinner.DateEditor) datePicker.getEditor()).getFormat().format(datePicker.getValue());
    }

    public String getNombre() {
        return txtNombre.getText();
    }

    public String getCategoria() {
        return (String) comboBoxCategoria.getSelectedItem();
    }

    public String getValor() {
        return txtValor.getText();
    }
}
