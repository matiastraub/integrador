


package com.gofinance.integrador.model;


import java.time.LocalDate;
import java.util.ArrayList;

import com.gofinance.integrador.database.RendimientoPersistencia;

public class Rendimiento {

	private static int id;

	private ArrayList<Transaccion> transacciones;
	private double totalIngreso; 
	private double totalGasto;  
	private double balance;
	private Usuario usuario;     

	public Rendimiento() {

	}

	public Rendimiento(double totalIngreso, double totalGasto, double balance , Usuario usuario) {
		super();
		this.transacciones = new ArrayList<Transaccion>();
		this.totalIngreso = totalIngreso;
		this.totalGasto = totalGasto;
		this.balance = balance;
 		this.usuario = usuario;	}

	// Método para calcular totales para un mes y año dados

	public void calcularTotalIngreso() {

		RendimientoPersistencia rendimientoPersistencia = new RendimientoPersistencia();
		totalIngreso = rendimientoPersistencia.obtenerTotalIngresos(usuario.getId()); 
	}

	
	public void calcularTotalGasto() {
		RendimientoPersistencia rendimientoPersistencia = new RendimientoPersistencia();

		totalGasto = rendimientoPersistencia.ObtenerTotalGastos(usuario.getId());
	}

	public void calcularBalance() {
		calcularTotalIngreso();
		calcularTotalGasto();
		balance = totalIngreso - totalGasto;
	}
	
	// Métodos auxiliares para obtener mes y año actuales
	private int obtenerMesActual() {
		return LocalDate.now().getMonthValue();
	}

	private int obtenerAnioActual() {
		return LocalDate.now().getYear();
	}



	public double getTotalIngreso() {
		//totalIngreso = calcularTotalIngreso(); 
		return totalIngreso;
	}


	public double getTotalGasto() {
		//totalGasto = calcularTotalGasto();
		return totalGasto;
	}



	public double getBalance() {
		return balance;
	}






}

