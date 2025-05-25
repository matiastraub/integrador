package com.gofinance.integrador.controller;

import com.gofinance.integrador.database.TransaccionDAO;
import com.gofinance.integrador.model.Transaccion;
import com.gofinance.integrador.model.Usuario;
import com.gofinance.integrador.view.DashboardView;
import raven.chart.data.category.DefaultCategoryDataset;
import raven.chart.data.pie.DefaultPieDataset;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DashboardControlador {

    private DashboardView vista;
    private Usuario usuario;

    public DashboardControlador(DashboardView vista, Usuario usuario) {
        this.vista   = vista;
        this.usuario = usuario;
        // Listener sin lambdas
        this.vista.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarDatos();
            }
        });
        cargarDatos();
    }

    public void cargarDatos() {
        ArrayList<Transaccion> lista = TransaccionDAO
            .getTransaccionesPorUsuario(usuario.getId());

        // 1) Preparar datasets
        DefaultCategoryDataset<String, String> datasetLinea    = 
            new DefaultCategoryDataset<>();
        DefaultPieDataset<String>              datasetPieIngresos = 
            new DefaultPieDataset<>();
        DefaultPieDataset<String>              datasetPieGastos   = 
            new DefaultPieDataset<>();

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        // 2) Acumular en mapas por fecha
        Map<String, Double> ingresosPorFecha = new HashMap<>();
        Map<String, Double> gastosPorFecha   = new HashMap<>();
        double totalIngresos = 0.0, totalGastos = 0.0;

        for (Transaccion t : lista) {
            String fecha = t.getFecha();
            if (t.getEsIngreso() == 1) {
                Double acum = ingresosPorFecha.get(fecha);
                if (acum == null) acum = 0.0;
                acum += t.getMonto();
                ingresosPorFecha.put(fecha, acum);
                totalIngresos += t.getMonto();
            } else {
                Double acum = gastosPorFecha.get(fecha);
                if (acum == null) acum = 0.0;
                acum += t.getMonto();
                gastosPorFecha.put(fecha, acum);
                totalGastos += t.getMonto();
            }
        }

        // 3) Crear conjunto con todas las fechas (evita duplicados)
        Set<String> todasFechas = new HashSet<>();
        todasFechas.addAll(ingresosPorFecha.keySet());
        todasFechas.addAll(gastosPorFecha.keySet());

        // 4) Parsear a Date y ordenar cronológicamente
        List<Date> listaFechas = new ArrayList<>();
        for (String s : todasFechas) {
            try {
                listaFechas.add(formato.parse(s));
            } catch (ParseException ex) {
                // si falla el parse, lo ignoramos
            }
        }
        Collections.sort(listaFechas);

        // 5) Volcar al dataset de línea en orden,
        //    siempre añadiendo ambos valores (ingresos/gastos)
        for (Date d : listaFechas) {
            String clave = formato.format(d);
            double ing = 0.0;
            double gas = 0.0;
            if (ingresosPorFecha.containsKey(clave)) {
                ing = ingresosPorFecha.get(clave);
            }
            if (gastosPorFecha.containsKey(clave)) {
                gas = gastosPorFecha.get(clave);
            }
            datasetLinea.addValue(ing, "Ingresos", clave);
            datasetLinea.addValue(gas, "Gastos",   clave);
        }

        // 6) Si no hubo transacciones, injecta un punto 0 en hoy
        if (lista.isEmpty()) {
            String hoy = formato.format(new Date());
            datasetLinea.addValue(0.0, "Ingresos", hoy);
            datasetLinea.addValue(0.0, "Gastos",   hoy);
            datasetPieIngresos.setValue("Sin datos", 1.0);
            datasetPieGastos  .setValue("Sin datos", 1.0);
        } else {
            // rellenar pies con totales reales
            datasetPieIngresos.setValue("Ingresos", totalIngresos);
            datasetPieGastos  .setValue("Gastos",    totalGastos);
        }

        // 7) Enviar a la vista
        vista.mostrarDatos(datasetLinea, datasetPieIngresos, datasetPieGastos);
    }

    public void actualizarDatos() {
        cargarDatos();
    }

    // Getters MVC
    public Usuario      getUsuario() { return usuario; }
    public DashboardView getVista()  { return vista;   }
}
