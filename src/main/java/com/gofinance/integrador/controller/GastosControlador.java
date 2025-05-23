package com.gofinance.integrador.controller;

import com.gofinance.integrador.model.Gasto;
import com.gofinance.integrador.model.Usuario;
import com.gofinance.integrador.view.VentanaGastos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GastosControlador implements ActionListener {

    private VentanaGastos vista;

    public GastosControlador(VentanaGastos vista) {
        this.vista = vista;
        this.vista.getBtnAniadir().addActionListener(this);
    }

    public GastosControlador(VentanaGastos ventanaGastos, Usuario usuario) {
		// TODO Auto-generated constructor stub
	}

	@Override
    public void actionPerformed(ActionEvent e) {
        String fecha = vista.getFechaSeleccionada();
        String nombre = vista.getNombre();
        String categoria = vista.getCategoria();
        String valor = vista.getValor();

        if (!nombre.isEmpty() && !valor.isEmpty()) {
            Gasto nuevoGasto = new Gasto(fecha, nombre, categoria, valor);
            vista.aniadirFilaTabla(
                nuevoGasto.getFecha(),
                nuevoGasto.getNombre(),
                nuevoGasto.getCategoria(),
                nuevoGasto.getValor()
            );
            vista.limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(null, "Por favor complete todos los campos.");
        }
    }
} 
