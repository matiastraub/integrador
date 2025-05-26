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
import java.util.*;

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
        List<Transaccion> lista = TransaccionDAO.getTransaccionesPorUsuario(usuario.getId());

        // 1) Preparar datasets
        DefaultCategoryDataset<String, String> datasetLinea      = new DefaultCategoryDataset<>();
        DefaultPieDataset<String>              datasetPieIngresos = new DefaultPieDataset<>();
        DefaultPieDataset<String>              datasetPieGastos   = new DefaultPieDataset<>();

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        // 2) Acumular en mapas por fecha
        Map<String, Double> ingresosPorFecha = new HashMap<>();
        Map<String, Double> gastosPorFecha   = new HashMap<>();

        for (Transaccion t : lista) {
            String fecha = t.getFecha();
            if (t.getEsIngreso() == 1) {
                ingresosPorFecha.put(fecha,
                    ingresosPorFecha.getOrDefault(fecha, 0.0) + t.getMonto());
            } else {
                gastosPorFecha.put(fecha,
                    gastosPorFecha.getOrDefault(fecha, 0.0) + t.getMonto());
            }
        }

        // 3) Unir y ordenar fechas
        Set<String> todasFechas = new HashSet<>();
        todasFechas.addAll(ingresosPorFecha.keySet());
        todasFechas.addAll(gastosPorFecha.keySet());

        List<Date> listaFechas = new ArrayList<>();
        for (String s : todasFechas) {
            try {
                listaFechas.add(formato.parse(s));
            } catch (ParseException ignored) {}
        }
        Collections.sort(listaFechas);

        // 4) Volcar al dataset de línea
        for (Date d : listaFechas) {
            String clave = formato.format(d);
            double ing = ingresosPorFecha.getOrDefault(clave, 0.0);
            double gas = gastosPorFecha.getOrDefault(clave, 0.0);
            datasetLinea.addValue(ing, "Ingresos", clave);
            datasetLinea.addValue(gas,   "Gastos",   clave);
        }

        // 5) Acumular totales por categoría usando los mapas estáticos
        Map<Integer, Double> ingresosPorCategoria = new HashMap<>();
        Map<Integer, Double> gastosPorCategoria   = new HashMap<>();

        for (Transaccion t : lista) {
            int catId = t.getFkCategoria();
            if (t.getEsIngreso() == 1) {
                ingresosPorCategoria.put(catId,
                    ingresosPorCategoria.getOrDefault(catId, 0.0) + t.getMonto());
            } else {
                gastosPorCategoria.put(catId,
                    gastosPorCategoria.getOrDefault(catId, 0.0) + t.getMonto());
            }
        }

        // 6) Poblar pie de ingresos con nombres e importes
        for (String nombre : CategoriaControlador.obtenerCategoriasIngresos()) {
            int id = CategoriaControlador.getIdCategoriaIngreso(nombre);
            double total = ingresosPorCategoria.getOrDefault(id, 0.0);
            if (total > 0.0) {
            	datasetPieIngresos.setValue(nombre, total);
            }
        }

        // 7) Poblar pie de gastos con nombres e importes
        for (String nombre : CategoriaControlador.obtenerCategoriasGastos()) {
            int id = CategoriaControlador.getIdCategoriaGasto(nombre);
            double total = gastosPorCategoria.getOrDefault(id, 0.0);
            if (total > 0.0) {
            	datasetPieGastos.setValue(nombre, total);
            }
        }

        // 8) Caso sin datos
        if (lista.isEmpty()) {
            String hoy = formato.format(new Date());
            datasetLinea.addValue(0.0, "Ingresos", hoy);
            datasetLinea.addValue(0.0, "Gastos",   hoy);
        }
        if (datasetPieIngresos.getItemCount() == 0) {
            datasetPieIngresos.setValue("Sin datos", 1.0);
        }
        if (datasetPieGastos.getItemCount() == 0) {
            datasetPieGastos.setValue("Sin datos", 1.0);
        }

        // 9) Enviar a la vista
        vista.mostrarDatos(datasetLinea, datasetPieIngresos, datasetPieGastos);
    }

    public void actualizarDatos() {
        cargarDatos();
    }

    // Getters MVC
    public Usuario       getUsuario() { return usuario; }
    public DashboardView getVista()   { return vista;   }
}
