package com.gofinance.integrador.view;

import java.awt.Dimension;

import javax.swing.JPanel;

import com.gofinance.integrador.model.Rendimiento;

import javax.swing.JLabel;
import java.awt.Font;
import java.time.LocalDate;
import javax.swing.JTextField;

public class RendView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Rendimiento rendimiento = new Rendimiento();
	private JTextField txtAhorro;

	
	public RendView() {
		
        initComponents();   
        
        
	}

	
	private void initComponents() {
		setPreferredSize(new Dimension(784, 600));
        setLayout(null);
        
        JLabel lblTitulo = new JLabel("Rendimiento mensual");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setBounds(43, 45, 238, 39);
        add(lblTitulo);
        
        JLabel lblBalance = new JLabel("Balance de " + LocalDate.now().getMonth() +":");
        lblBalance.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblBalance.setBounds(43, 114, 116, 22);
        add(lblBalance);
        
        JLabel lblIngreso = new JLabel("Ingreso");
        lblIngreso.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblIngreso.setBounds(43, 163, 53, 22);
        add(lblIngreso);
        
        JLabel lblGasto = new JLabel("Gasto:");
        lblGasto.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblGasto.setBounds(201, 169, 45, 13);
        add(lblGasto);
        
        JLabel lblAhorro = new JLabel("Ahorro: ");
        lblAhorro.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblAhorro.setBounds(43, 214, 45, 13);
        add(lblAhorro);
        
        JLabel lblTitulo2 = new JLabel("Gastos por categorias");
        lblTitulo2.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo2.setBounds(43, 264, 238, 22);
        add(lblTitulo2);
        
        JLabel lblRestBalance = new JLabel(conversionDatos(rendimiento.getBalance()) + "€");
        lblRestBalance.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblRestBalance.setBounds(169, 120, 63, 13);
        add(lblRestBalance);
        
        JLabel lblTotalIngreso = new JLabel(conversionDatos(rendimiento.getTotalIngreso()) + "€");
        lblTotalIngreso.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblTotalIngreso.setBounds(114, 169, 45, 13);
        add(lblTotalIngreso);
        
        JLabel lblTotalGasto = new JLabel(conversionDatos(rendimiento.getTotalGasto()) + "€");
        lblTotalGasto.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblTotalGasto.setBounds(256, 169, 45, 13);
        add(lblTotalGasto);
        
        txtAhorro = new JTextField();
        txtAhorro.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtAhorro.setBounds(98, 212, 96, 19);
        add(txtAhorro);
        txtAhorro.setColumns(10);
	}
	
	private String conversionDatos(double valor){
		String valorString = String.format("%.2f", valor);
		
		return valorString;
	}
}