package com.gofinance.integrador.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoriaControlador implements ActionListener {
	
	public static String[] obtenerCategoriasGastos() {
		return new String[] { 
				"Alimentación", "Transporte", "Ocio", "Educación", "Salud", "Otros"
		};
		
	}
	
	public static String[] obtenerCategoriasIngresos() {
	    return new String[] {
		        "Salario", "Venta", "Premio", "Interés", "Otro"
	    };
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Evento en controlador de categorías: " + e.getActionCommand());
	}

}
