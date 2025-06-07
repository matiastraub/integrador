package com.gofinance.integrador.controller;

import com.gofinance.integrador.database.TransaccionDAO;
import com.gofinance.integrador.view.UtilsView;

import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class UtilidadesControlador {

    private UtilsView vista;
    private int idUsuario;

    public UtilidadesControlador(UtilsView vista, int idUsuario) {
        this.vista = vista;
        this.idUsuario = idUsuario;
    }

    public String[] calcularAhorro(double objetivo, String fechaObjetivo) {
        String[] resultado = new String[3];

        if (objetivo <= 0) {
            resultado[0] = " ";
            resultado[1] = " ";
            resultado[2] = "El objetivo debe ser mayor que 0 €.";
            return resultado;
        }

        LocalDate hoy = LocalDate.now();
        LocalDate fechaFinal;

        try {
            fechaFinal = LocalDate.parse("01/" + fechaObjetivo, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            resultado[0] = " ";
            resultado[1] = " ";
            resultado[2] = "Fecha inválida. Usa el formato MM/yyyy.";
            return resultado;
        }

        if (fechaFinal.isBefore(hoy)) {
            resultado[0] = " ";
            resultado[1] = " ";
            resultado[2] = "La fecha objetivo debe ser futura.";
            return resultado;
        }

        long mesesRestantes = ChronoUnit.MONTHS.between(hoy.withDayOfMonth(1), fechaFinal.withDayOfMonth(1));
        if (mesesRestantes == 0) {
            resultado[0] = " ";
            resultado[1] = " ";
            resultado[2] = "Queda menos de un mes. El ahorro debe ser inmediato.";
            return resultado;
        }

        // Obtener saldo actual real
        double saldoActual = getSaldoActualDelMes();

        // Colorear la etiqueta desde el controlador (opcional según el enfoque)
        if (vista != null) {
            Color color = saldoActual >= 0 ? new Color(46, 204, 113) : new Color(231, 76, 60);
            vista.setColorSaldoActual(color);
        }

        // Calcular cuánto falta por ahorrar
        double faltante = objetivo - saldoActual;

        if (faltante <= 0) {
            resultado[0] = "Meses restantes: " + mesesRestantes;
            resultado[1] = "¡Ya has alcanzado tu objetivo!";
            resultado[2] = "No necesitas ahorrar más.";
            return resultado;
        }

        double ahorroMensual = faltante / mesesRestantes;

        resultado[0] = "Meses restantes: " + mesesRestantes;
        resultado[1] = "Ahorro mensual necesario: " + String.format("%.2f", ahorroMensual) + " €";
        resultado[2] = "Necesitas ahorrar " + String.format("%.2f", faltante) + " € en total.";

        return resultado;
    }

    public double getSaldoActualDelMes() {
        LocalDate hoy = LocalDate.now();
        int anio = hoy.getYear();
        int mes = hoy.getMonthValue();
        double ingresos = TransaccionDAO.getTotalIngresoPorMes(idUsuario, anio, mes);
        double gastos = TransaccionDAO.getTotalGastoPorMes(idUsuario, anio, mes);
        return ingresos - gastos;
    }
}
