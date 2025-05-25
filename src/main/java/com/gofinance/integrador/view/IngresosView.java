package com.gofinance.integrador.view;

import com.gofinance.integrador.controller.CategoriaControlador;
import com.gofinance.integrador.controller.IngresosControlador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class IngresosView extends JPanel {

    private JTable tablaIngresos;
    private DefaultTableModel modeloTabla;
    private JButton btnRegistrar;
    private JButton btnAniadir;
    private JButton btnEliminar;
    private JTextField txtNombre;
    private JComboBox<String> comboBoxCategoria;
    private JTextField txtValor;
    private JSpinner datePicker;
    private JPanel panelFormulario;

    private IngresosControlador controlador;

    public IngresosView() {
        setLayout(null);
        setPreferredSize(new Dimension(784, 600));

        // Tabla
        String[] columnas = {"Fecha", "Nombre", "Categoría", "Valor (€)"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaIngresos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaIngresos);
        scrollTabla.setBounds(50, 20, 700, 256);
        add(scrollTabla);

        // Botón Registrar
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 16));
        btnRegistrar.setBounds(50, 286, 340, 30);
        btnRegistrar.setBackground(new Color(0, 224, 131));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelFormulario.setVisible(true);
            }
        });
        add(btnRegistrar);

        // Botón Eliminar
        btnEliminar = new JButton("Eliminar fila seleccionada");
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 16));
        btnEliminar.setBounds(410, 286, 340, 30);
        btnEliminar.setBackground(Color.RED);
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int filaSeleccionada = tablaIngresos.getSelectedRow();
                if (controlador != null) {
                	controlador.eliminarIngresoSeleccionado(filaSeleccionada);
                }
            }
        });
        add(btnEliminar);

        // Formulario
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
        String[] categorias = CategoriaControlador.obtenerCategoriasIngresos();
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

        // Evento clásico del botón Añadir
        btnAniadir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fecha = ((JSpinner.DateEditor) datePicker.getEditor()).getFormat().format(datePicker.getValue());
                String nombre = txtNombre.getText();
                String categoria = (String) comboBoxCategoria.getSelectedItem();
                String valor = txtValor.getText();

                if (!nombre.isEmpty() && !valor.isEmpty()) {
                    controlador.registrarIngreso(fecha, nombre, categoria, valor);
                    limpiarCampos();
                    datePicker.setValue(new Date());
                    panelFormulario.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(IngresosView.this, "Por favor completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panelFormulario.setVisible(false);
        add(panelFormulario);
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

    public DefaultTableModel getTableModel() {
        return modeloTabla;
    }

    public void setControlador(IngresosControlador controlador) {
        this.controlador = controlador;
    }

    public Object getBtnAgregar() {
        return btnAniadir;
    }

    public Object getBtnEliminar() {
        return btnEliminar;
    }
}
