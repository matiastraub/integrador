package com.gofinance.integrador.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Map;
import com.gofinance.integrador.model.Rendimiento;

/**
 * Vista para mostrar el rendimiento mensual:
 * el controlador es quien puebla TODO (título, combo, valores y tabla).
 */
@SuppressWarnings("serial")
public class RendView extends JPanel {

    private JComboBox<String> comboMes;
    private JLabel lblTitulo;
    private JLabel lblBalanceText;
    private JLabel lblBalanceValue;
    private JLabel lblIngresoValue;
    private JLabel lblGastoValue;
    private JTable tblCategorias;
    private DefaultTableModel tblModel;

    public RendView() {
        initComponents();
    }

	private void initComponents() {
        setPreferredSize(new Dimension(800, 600));
        setLayout(null);

        // 1) Título arrancando vacío
        lblTitulo = new JLabel("");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        // Aumentamos ancho para no truncar meses largos
        lblTitulo.setBounds(43, 10, 600, 30);
        add(lblTitulo);

        // 2) Combo vacío (lo llena el controlador)
        comboMes = new JComboBox<>();
        comboMes.setBounds(480, 10, 200, 30);
        add(comboMes);

        // 3) Balance
        lblBalanceText = new JLabel("");
        lblBalanceText.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblBalanceText.setBounds(43, 60, 300, 22);
        add(lblBalanceText);

        lblBalanceValue = new JLabel("");
        lblBalanceValue.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblBalanceValue.setBounds(200, 60, 100, 22);
        add(lblBalanceValue);

        // 4) Ingreso / Gasto
        JLabel lblIngresoText = new JLabel("Ingreso:");
        lblIngresoText.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblIngresoText.setBounds(43, 90, 100, 22);
        add(lblIngresoText);

        lblIngresoValue = new JLabel("");
        lblIngresoValue.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblIngresoValue.setBounds(150, 90, 100, 22);
        add(lblIngresoValue);

        JLabel lblGastoText = new JLabel("Gasto:");
        lblGastoText.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblGastoText.setBounds(43, 120, 100, 22);
        add(lblGastoText);

        lblGastoValue = new JLabel("");
        lblGastoValue.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblGastoValue.setBounds(150, 120, 100, 22);
        add(lblGastoValue);

        // 5) Encabezado de la tabla
        JLabel lblTabla = new JLabel("Gastos por categoría");
        lblTabla.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTabla.setBounds(43, 160, 300, 22);
        add(lblTabla);

        // 6) Tabla vacía; la rellenará updateChart()
        tblModel = new DefaultTableModel(new String[]{"Categoría", "Monto (€)"}, 0) {
            @Override public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblCategorias = new JTable(tblModel);
        tblCategorias.getColumnModel().getColumn(0).setPreferredWidth(200);
        tblCategorias.getColumnModel().getColumn(1).setPreferredWidth(100);

        JScrollPane scroll = new JScrollPane(tblCategorias);
        scroll.setBounds(43, 190, 700, 370);
        add(scroll);
    }

    /** Permite al controlador poblar el combo e instalar el listener */
    public JComboBox<String> getComboMes() {
        return comboMes;
    }

    /** El controlador manda el texto completo (p.ej. "Rendimiento mensual de Abril") */
    public void setMonthTitle(String tituloCompleto) {
        lblTitulo.setText(tituloCompleto);
    }

    /** El controlador fija aquí el texto de balance, p.ej. "Balance de Abril:" */
    public void setBalanceText(String texto) {
        lblBalanceText.setText(texto);
    }

    /**
     * Actualiza los valores numéricos del modelo.
     * @param r modelo con balance, ingreso y gasto ya calculados
     */
    public void updateValues(Rendimiento r) {
        lblBalanceValue.setText(format(r.getBalance()));
        lblIngresoValue .setText(format(r.getTotalIngreso()));
        lblGastoValue   .setText(format(r.getTotalGasto()));
    }

    /**
     * Rellena la tabla con cada categoría y su importe.
     * @param gastosPorCategoria mapa nombreCategoría→monto
     */
    public void updateChart(Map<String, Double> gastosPorCategoria) {
        tblModel.setRowCount(0);
        for (Map.Entry<String, Double> e : gastosPorCategoria.entrySet()) {
            String cat = (e.getKey() != null && !e.getKey().isEmpty())
                         ? e.getKey()
                         : "(Sin categoría)";
            tblModel.addRow(new Object[]{cat, format(e.getValue())});
        }
    }

    /** Formatea un doble a moneda con dos decimales */
    private String format(double v) {
        return String.format("%.2f €", v);
    }
}
