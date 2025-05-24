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
    private JTextField txtNombre;
    private JComboBox<String> comboBoxCategoria;
    private JTextField txtValor;
    private JSpinner datePicker;
    private JPanel panelFormulario;
	private String fecha;
	private String nombre;
	private String categoria;
	private double valor;

    public IngresosView() {
        setLayout(null);
        setPreferredSize(new Dimension(784, 600));

        // Tabla de ingresos
        String[] columnas = {"Fecha", "Nombre", "Categoría", "Valor (€)"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaIngresos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaIngresos);
        scrollTabla.setBounds(50, 20, 700, 256);
        add(scrollTabla);

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
        for (String categoria : CategoriaControlador.obtenerCategoriasIngresos()) {
            comboBoxCategoria.addItem(categoria);
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
        btnRegistrar.setBounds(50, 286, 700, 30);
        btnRegistrar.setBackground(new Color(0, 224, 131));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.addActionListener(e -> panelFormulario.setVisible(true));
        add(btnRegistrar);

        // Lógica del botón Añadir
        btnAniadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fecha = ((JSpinner.DateEditor) datePicker.getEditor()).getFormat().format(datePicker.getValue());
                String nombre = txtNombre.getText();
                String categoria = (String) comboBoxCategoria.getSelectedItem();
                String valor = txtValor.getText();

                if (!nombre.isEmpty() && !valor.isEmpty()) {
                    aniadirFilaTabla(fecha, nombre, categoria, valor);

                    int resultado = com.gofinance.integrador.database.TransaccionDAO.insertarIngreso(fecha, nombre, categoria, valor);
                    if (resultado > 0) {
                        JOptionPane.showMessageDialog(null, "Ingreso guardado correctamente.", "Añadido", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo guardar el ingreso.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    limpiarCampos();
                    datePicker.setValue(new Date());
                    panelFormulario.setVisible(false);

                } else {
                    JOptionPane.showMessageDialog(IngresosView.this, "Por favor completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
     
        cargarIngresosDesdeBD();


    }

    public void aniadirFilaTabla(String fecha, String nombre, String categoria, String valor) {
        modeloTabla.addRow(new Object[]{fecha, nombre, categoria, valor});
    }

    

    public void limpiarCampos() {
        txtNombre.setText("");
        txtValor.setText("");
    }

    public void cargarIngresosDesdeBD() {
        java.util.List<com.gofinance.integrador.model.Transaccion> lista = com.gofinance.integrador.database.TransaccionDAO.getTransaccionesPorUsuario(1);
        for (com.gofinance.integrador.model.Transaccion t : lista) {
            if (t.getEsIngreso() == 1) {
            	modeloTabla.addRow(new Object[]{t.getFecha(), t.getNombre(), t.getDescripcion(), t.getMonto()});
            }
        }
        txtNombre.setText("");
        txtValor.setText("");
    }
    
   

    public DefaultTableModel getTableModel() {
        return modeloTabla;
    }

    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

	public void setControlador(IngresosControlador ingresosControlador) {
		// TODO Auto-generated method stub
		
	}

	public Object getBtnAgregar() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getBtnEliminar() {
		// TODO Auto-generated method stub
		return null;
	}

 }


