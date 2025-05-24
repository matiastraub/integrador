package com.gofinance.integrador.view;

import com.gofinance.integrador.controller.CategoriaControlador;

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

    public VentanaGastos() {
       
        setLayout(null);
        setPreferredSize(new Dimension(784, 600));

        // Tabla de gastos
        String[] columnas = {"Fecha", "Nombre", "Categoria", "Valor (€)"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaGastos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaGastos);
        scrollTabla.setBounds(50, 20, 700, 256);
        add(scrollTabla);

        // Botón Eliminar fila
        btnEliminarSeleccionada = new JButton("Eliminar fila seleccionada");
        btnEliminarSeleccionada.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnEliminarSeleccionada.setBackground(Color.RED);
        btnEliminarSeleccionada.setForeground(Color.WHITE);
        btnEliminarSeleccionada.setBounds(439, 286, 311, 30);
        btnEliminarSeleccionada.addActionListener(e -> {
            int filaSeleccionada = tablaGastos.getSelectedRow();
            if (filaSeleccionada != -1) {
                modeloTabla.removeRow(filaSeleccionada);
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });
        add(btnEliminarSeleccionada);

        // Panel formulario
        panelFormulario = new JPanel(null);
        panelFormulario.setBounds(50, 326, 700, 200);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblFecha.setBounds(0, 0, 100, 25);
        panelFormulario.add(lblFecha);
        SpinnerDateModel dateModel = new SpinnerDateModel();
        datePicker = new JSpinner(dateModel);
        datePicker.setFont(new Font("Tahoma", Font.PLAIN, 14));
        datePicker.setEditor(new JSpinner.DateEditor(datePicker, "dd/MM/yyyy"));
        datePicker.setBounds(120, 0, 253, 25);
        panelFormulario.add(datePicker);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNombre.setBounds(0, 35, 100, 25);
        panelFormulario.add(lblNombre);
        txtNombre = new JTextField();
        txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNombre.setBounds(120, 35, 253, 25);
        panelFormulario.add(txtNombre);

        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCategoria.setBounds(0, 70, 100, 25);
        panelFormulario.add(lblCategoria);
        comboBoxCategoria = new JComboBox<>();
        comboBoxCategoria.setFont(new Font("Tahoma", Font.PLAIN, 14));
        for (String categoria : CategoriaControlador.obtenerCategoriasGastos()) {
            comboBoxCategoria.addItem(categoria);
        }
        comboBoxCategoria.setBounds(120, 70, 253, 25);
        panelFormulario.add(comboBoxCategoria);

        JLabel lblValor = new JLabel("Valor (€):");
        lblValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblValor.setBounds(0, 105, 100, 25);
        panelFormulario.add(lblValor);
        txtValor = new JTextField();
        txtValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtValor.setBounds(120, 105, 253, 25);
        panelFormulario.add(txtValor);

        btnAniadir = new JButton("Añadir");
        btnAniadir.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnAniadir.setBackground(new Color(0, 148, 255));
        btnAniadir.setForeground(Color.WHITE);
        btnAniadir.setBounds(120, 145, 253, 30);
        panelFormulario.add(btnAniadir);

        panelFormulario.setVisible(false);
        add(panelFormulario);

        // Botón Registrar
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnRegistrar.setBackground(new Color(0, 224, 131));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setBounds(50, 286, 371, 30);
        btnRegistrar.addActionListener(e -> panelFormulario.setVisible(true));
        add(btnRegistrar);

        // Lógica botón Añadir
        btnAniadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fecha = ((JSpinner.DateEditor) datePicker.getEditor()).getFormat().format(datePicker.getValue());
                String nombre = txtNombre.getText();
                String categoria = (String) comboBoxCategoria.getSelectedItem();
                String valor = txtValor.getText();

                if (!nombre.isEmpty() && !valor.isEmpty()) {
                    aniadirFilaTabla(fecha, nombre, categoria, valor);

                    int resultado = com.gofinance.integrador.database.TransaccionDAO.insertarGastoBasico(fecha, nombre, categoria, valor);
                    if (resultado > 0) {
                        JOptionPane.showMessageDialog(null, "Gasto guardado correctamente.", "Añadido", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo guardar el gasto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    limpiarCampos();
                    datePicker.setValue(new Date());
                    panelFormulario.setVisible(false);

                } else {
                    JOptionPane.showMessageDialog(VentanaGastos.this, "Por favor completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        cargarGastosDesdeBD();
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnAniadir() {
        return btnAniadir;
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

    public void limpiarCampos() {
        txtNombre.setText("");
        txtValor.setText("");
    }

    public void aniadirFilaTabla(String fecha, String nombre, String categoria, String valor) {
        modeloTabla.addRow(new Object[]{fecha, nombre, categoria, valor});
    }

    public void cargarGastosDesdeBD() {
    java.util.List<com.gofinance.integrador.model.Transaccion> lista = com.gofinance.integrador.database.TransaccionDAO.getTransaccionesPorUsuario(1);
    for (com.gofinance.integrador.model.Transaccion t : lista) {
        if (t.getEsIngreso() == 0) {
            modeloTabla.addRow(new Object[]{t.getFecha(), t.getNombre(), t.getDescripcion(), String.format("%.2f €", t.getMonto())});
        }
    }
    
}

            
        
    
} 
