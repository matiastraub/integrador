// DashboardView.java
package com.gofinance.integrador.view;

import net.miginfocom.swing.MigLayout;
import raven.chart.line.LineChart;
import raven.chart.pie.PieChart;
import raven.chart.data.category.DefaultCategoryDataset;
import raven.chart.data.pie.DefaultPieDataset;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class DashboardView extends JPanel {

    private LineChart lineChart;
    private PieChart pieIngresos;
    private PieChart pieGastos;
    private JButton btnActualizar;
    private JLabel lblEstado;

    public DashboardView() {
        setLayout(new MigLayout("wrap, fillx, insets 10", "[fill]", "[]5[]10[]10[]10[]"));
        setBackground(Color.BLACK);
        crearComponentes();
        configurarEstilos();
    }

    private void crearComponentes() {
        crearPanelControl();
        crearLineChart();
        crearPieCharts();
    }

    private void crearPanelControl() {
        JPanel ctrl = new JPanel(new MigLayout("insets 0", "[]push[]", "[]"));
        ctrl.setBackground(Color.BLACK);
        lblEstado    = new JLabel("Dashboard cargado");
        btnActualizar = new JButton("Actualizar");
        lblEstado.setForeground(Color.GRAY);
        ctrl.add(lblEstado);
        ctrl.add(btnActualizar);
        add(ctrl, "growx");
    }

    private void crearLineChart() {
        lineChart = new LineChart();
        JLabel title = new JLabel("Evolución de Ingresos y Gastos");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        lineChart.setHeader(title);
        lineChart.setValuesFormat(new DecimalFormat("#,##0.00"));
        lineChart.getChartColor().addColor(
            Color.decode("#10b981"),  // verde ingreso
            Color.decode("#ef4444")   // rojo gasto
        );
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Gráfico de Líneas"));
        p.add(lineChart);
        add(p, "height 350");
    }

    private void crearPieCharts() {
        JPanel cont = new JPanel(new MigLayout("insets 0, gap 10", "[50%][50%]", "[fill]"));
        cont.setBackground(Color.BLACK);

        // Pie de Ingresos
        pieIngresos = new PieChart();
        JLabel lblI = new JLabel("Total Ingresos");
        lblI.setFont(lblI.getFont().deriveFont(Font.BOLD, 14f));
        pieIngresos.setHeader(lblI);
        pieIngresos.getChartColor().addColor(
            Color.decode("#34d399"), // verde claro
            Color.decode("#10b981")  // verde oscuro
        );
        JPanel pI = new JPanel(new BorderLayout());
        pI.setBorder(BorderFactory.createTitledBorder("Pie Ingresos"));
        pI.add(pieIngresos);
        cont.add(pI, "grow");

        // Pie de Gastos
        pieGastos = new PieChart();
        JLabel lblG = new JLabel("Total Gastos");
        lblG.setFont(lblG.getFont().deriveFont(Font.BOLD, 14f));
        pieGastos.setHeader(lblG);
        pieGastos.getChartColor().addColor(
            Color.decode("#f87171"), // rojo claro
            Color.decode("#ef4444")  // rojo oscuro
        );
        JPanel pG = new JPanel(new BorderLayout());
        pG.setBorder(BorderFactory.createTitledBorder("Pie Gastos"));
        pG.add(pieGastos);
        cont.add(pG, "grow");

        add(cont, "growx, height 300");
    }

    private void configurarEstilos() {
        lineChart.setOpaque(false);
        pieIngresos.setOpaque(false);
        pieGastos.setOpaque(false);
    }

    /**
     * Muestra los datos en el line chart y en los dos pie charts.
     */
    public void mostrarDatos(DefaultCategoryDataset<String, String> datasetLinea,
                             DefaultPieDataset<String> datasetPieIngresos,
                             DefaultPieDataset<String> datasetPieGastos) {

        if (datasetLinea != null)    lineChart.setCategoryDataset(datasetLinea);
        if (datasetPieIngresos != null) pieIngresos.setDataset(datasetPieIngresos);
        if (datasetPieGastos   != null) pieGastos  .setDataset(datasetPieGastos);

        lineChart.startAnimation();
        pieIngresos.startAnimation();
        pieGastos  .startAnimation();

        lblEstado.setText("Datos actualizados correctamente");
    }

    // Getters MVC
    public LineChart getLineChart()       { return lineChart;   }
    public PieChart  getPieIngresosChart(){ return pieIngresos; }
    public PieChart  getPieGastosChart()  { return pieGastos;   }
    public JButton   getBtnActualizar()   { return btnActualizar; }
}
