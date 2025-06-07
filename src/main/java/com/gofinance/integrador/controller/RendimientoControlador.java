package com.gofinance.integrador.controller;

import com.gofinance.integrador.view.RendView;
import com.gofinance.integrador.model.Rendimiento;
import com.gofinance.integrador.model.Transaccion;
import com.gofinance.integrador.database.TransaccionDAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Controlador para RendView, orquesta la selección de mes,
 * el cálculo de totales y la agrupación manual de gastos por categoría.
 */
public class RendimientoControlador {
    private RendView view;
    private int idUsuario;

    // Meses en español, usados únicamente aquí
    private static final String[] MESES_ES = {
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    public RendimientoControlador(RendView view, int idUsuario) {
        this.view = view;
        this.idUsuario = idUsuario;
        inicializarVista();
        inicializarListeners();
    }

    /** Poblamos el combo y seleccionamos el mes actual */
    private void inicializarVista() {
        for (String mes : MESES_ES) {
            view.getComboMes().addItem(mes);
        }
        int idxActual = LocalDate.now().getMonthValue() - 1;
        view.getComboMes().setSelectedIndex(idxActual);
        refrescarDatos(idxActual);
    }

    /** Instalamos el listener para reaccionar al cambio de mes */
    private void inicializarListeners() {
        view.getComboMes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idx = view.getComboMes().getSelectedIndex();
                refrescarDatos(idx);
            }
        });
    }


    //Carga totales y agrupa gastos manualmente por categoría
    private void refrescarDatos(int mesIdx) {
        int year = LocalDate.now().getYear();
        int mesValue = mesIdx + 1;
        Month mesEnum = Month.of(mesValue);

        // 1) Totales de ingreso y gasto (delegados al DAO)
        double ingreso = TransaccionDAO.getTotalIngresoPorMes(idUsuario, year, mesValue);
        double gasto   = TransaccionDAO.getTotalGastoPorMes(   idUsuario, year, mesValue);

        // 2) Agrupación manual de gastos por categoría
        List<Transaccion> todas = TransaccionDAO.getTransaccionesPorUsuario(idUsuario);
        Map<String, Double> gastosPorCategoria = new LinkedHashMap<String, Double>();
        for (Transaccion t : todas) {
            // Filtrar sólo gastos del mes/año escogido
            if (t.getEsIngreso() == 0) {
                String[] partes = t.getFecha().split("/");
                @SuppressWarnings("unused")
				int dia = Integer.parseInt(partes[0]);
                int mes = Integer.parseInt(partes[1]);
                int anio = Integer.parseInt(partes[2]);
                if (mes == mesValue && anio == year) {
                    String cat = t.getDescripcion(); // aquí almacenas el nombre de la categoría
                    Double acumulado = gastosPorCategoria.get(cat);
                    if (acumulado == null) {
                        gastosPorCategoria.put(cat, t.getMonto());
                    } else {
                        gastosPorCategoria.put(cat, acumulado + t.getMonto());
                    }
                }
            }
        }

        // 3) Construcción del modelo y refresco de la vista
        Rendimiento model = new Rendimiento(
            LocalDate.of(year, mesEnum, 1),
            ingreso,
            gasto
        );

        view.setMonthTitle(  "Rendimiento mensual de " + MESES_ES[mesIdx] );
        view.setBalanceText( "Balance de " + MESES_ES[mesIdx] + ":" );
        view.updateValues(   model );
        view.updateChart(    gastosPorCategoria );
    }
}
