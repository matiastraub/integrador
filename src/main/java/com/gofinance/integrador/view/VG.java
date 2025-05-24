package com.gofinance.integrador.view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class VG extends JPanel {
	private JTable TablaGastos;
	private DefaultTableModel modelo;
	private JButton btnAgregar;
	private JTextField txtNombreGts;
	private JTextField textField;
	private JTextField textField_2;
	
	
	
	public VG() {
		setBackground(Color.BLACK);
		setLayout(null);
		
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 557, 379);
		add(panel);
		panel.setLayout(null);
		
		String[] columnas = {"Fecha", "Nombre", "Categoría", "Monto"};
        modelo = new DefaultTableModel(columnas, 0);
		TablaGastos = new JTable();
		TablaGastos.setBounds(10, 10, 521, 27);
		panel.add(TablaGastos);
		
		btnAgregar = new JButton("Registrar");
		btnAgregar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAgregar.setBounds(22, 237, 85, 21);
		panel.add(btnAgregar);
		
		JLabel lblNombreGasto = new JLabel("Nombre");
		lblNombreGasto.setBounds(124, 194, 45, 13);
		panel.add(lblNombreGasto);
		
		txtNombreGts = new JTextField();
		txtNombreGts.setBounds(122, 211, 96, 21);
		panel.add(txtNombreGts);
		txtNombreGts.setColumns(10);
		
		JLabel lblFechaGts = new JLabel("Fecha");
		lblFechaGts.setBounds(245, 194, 45, 13);
		panel.add(lblFechaGts);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(245, 212, 96, 21);
		panel.add(textField);
		
		JLabel lblNombreGasto_2 = new JLabel("Categoria");
		lblNombreGasto_2.setBounds(243, 249, 45, 13);
		panel.add(lblNombreGasto_2);
		
		JLabel lblValorGts = new JLabel("Valor");
		lblValorGts.setBounds(124, 246, 45, 13);
		panel.add(lblValorGts);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(124, 264, 96, 21);
		panel.add(textField_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(242, 264, 110, 21);
		panel.add(comboBox);
		

	}
}
