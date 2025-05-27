package com.gofinance.integrador.model;


import java.time.LocalDate;
import java.util.ArrayList;

public class Rendimiento {

	private ArrayList<Transaccion> transacciones;
	private double totalIngreso; 
	private double totalGasto;  
	private double balance;     

	public Rendimiento() {

	}

	public Rendimiento(double totalIngreso, double totalGasto, double balance) {
		super();
		this.transacciones = new ArrayList<Transaccion>();
		this.totalIngreso = totalIngreso;
		this.totalGasto = totalGasto;
		this.balance = balance;
	}

	// Método para calcular totales para un mes y año dados

	public void calcularTotalIngreso() {
		int mes = obtenerMesActual();
		int anio = obtenerAnioActual();

		for (Transaccion transaccion : transacciones) {
			String fechaTransaccionStr = transaccion.getFecha(); 
			String[] partesFechaTransaccion = fechaTransaccionStr.split("/");

			// Verificar si el formato de fecha es correcto (día/mes/año)
			if (partesFechaTransaccion.length == 3) {
				int mesTransaccion = Integer.parseInt(partesFechaTransaccion[1]);
				int anioTransaccion = Integer.parseInt(partesFechaTransaccion[2]);


				// *** FILTRADO POR MES Y AÑO ***
				if (mesTransaccion == mes && anioTransaccion == anio) {
					if (transaccion.getEsIngreso() == 1) {
						totalIngreso += transaccion.getMonto();
					}
				}
			}
		}

		//return totalIngreso;
	}

	
	public void calcularTotalGasto() {
		int mes = obtenerMesActual();
		int anio = obtenerAnioActual();

		for (Transaccion transaccion : transacciones) {
			String fechaTransaccionStr = transaccion.getFecha(); 
			String[] partesFechaTransaccion = fechaTransaccionStr.split("/");

			// Verificar si el formato de fecha es correcto (día/mes/año)
			if (partesFechaTransaccion.length == 3) {
				int mesTransaccion = Integer.parseInt(partesFechaTransaccion[1]);
				int anioTransaccion = Integer.parseInt(partesFechaTransaccion[2]);


				// *** FILTRADO POR MES Y AÑO ***
				if (mesTransaccion == mes && anioTransaccion == anio) {
					if (transaccion.getEsIngreso() == 0) {
						totalGasto += transaccion.getMonto();
					}
				}
			}
		}

		//return totalGasto;
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

