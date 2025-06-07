package com.gofinance.integrador.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo para contener datos de rendimiento mensual: ingresos, gastos, balance y ahorro.
 */
public class Rendimiento {
    private LocalDate mes;
    private List<Transaccion> transacciones;
    private double totalIngreso;
    private double totalGasto;
    private double balance;
    private double ahorro;

    public Rendimiento() {
        this.mes = LocalDate.now().withDayOfMonth(1);
        this.transacciones = new ArrayList<>();
    }

    /**
     * Constructor para datos ya calculados.
     * @param mes mes de los datos (día=1)
     * @param totalIngreso suma de ingresos del mes
     * @param totalGasto suma de gastos del mes
     */
    public Rendimiento(LocalDate mes, double totalIngreso, double totalGasto) {
        this.mes = mes.withDayOfMonth(1);
        this.transacciones = new ArrayList<>();
        this.totalIngreso = totalIngreso;
        this.totalGasto = totalGasto;
        this.balance = totalIngreso - totalGasto;
        this.ahorro = this.balance;
    }

    public LocalDate getMes() {
        return mes;
    }

    public double getTotalIngreso() {
        return totalIngreso;
    }

    public double getTotalGasto() {
        return totalGasto;
    }

    public double getBalance() {
        return balance;
    }

    public double getAhorro() {
        return ahorro;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }

    public void setMes(LocalDate mes) {
        this.mes = mes.withDayOfMonth(1);
    }
}
