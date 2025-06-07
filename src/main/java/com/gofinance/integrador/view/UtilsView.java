package com.gofinance.integrador.view;

import com.gofinance.integrador.controller.UtilidadesControlador;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class UtilsView extends JPanel {
    private JSpinner objetivoSpinner;
    private JComboBox<String> mesCombo;
    private JSpinner anioSpinner;

    private JLabel saldoLabelValor;
    private JLabel resultadoMeses;
    private JLabel resultadoAhorro;
    private JLabel resultadoViabilidad;

    private UtilidadesControlador controlador;
    private final Color COLOR_GRIS = new Color(8, 8, 8);
    @SuppressWarnings("unused")
	private int idUsuario;

    public UtilsView(int idUsuario) {
        this.idUsuario = idUsuario;
        setLayout(new MigLayout("wrap 2", "[][grow]", "[][][][][][][]"));
        setBackground(COLOR_GRIS);

        JLabel titulo = new JLabel("Calculadora de Ahorro Objetivo");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        add(titulo, "span, center");

        // Objetivo
        JLabel objetivoLabel = new JLabel("Objetivo (€):");
        objetivoLabel.setForeground(Color.WHITE);
        objetivoSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1000000.0, 10.0));
        add(objetivoLabel);
        add(objetivoSpinner, "growx");

        // Saldo actual
        JLabel saldoLabel = new JLabel("Saldo actual (€):");
        saldoLabel.setForeground(Color.WHITE);
        saldoLabelValor = new JLabel("0.00 €");
        saldoLabelValor.setForeground(Color.WHITE);
        add(saldoLabel);
        add(saldoLabelValor);

        // Fecha objetivo
        JLabel fechaLabel = new JLabel("Fecha objetivo (mm/yyyy): ");
        fechaLabel.setForeground(Color.WHITE);
        JPanel fechaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        fechaPanel.setBackground(COLOR_GRIS);
        mesCombo = new JComboBox<>(new String[]{
                "01", "02", "03", "04", "05", "06",
                "07", "08", "09", "10", "11", "12"
        });
        anioSpinner = new JSpinner(new SpinnerNumberModel(2000, 1960, 2080, 1));
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(anioSpinner, "#");
        anioSpinner.setEditor(editor);
        fechaPanel.add(mesCombo);
        fechaPanel.add(anioSpinner);
        add(fechaLabel);
        add(fechaPanel);

        // Botón de cálculo
        JButton calcularBtn = new JButton("Calcular");
        add(new JLabel());
        add(calcularBtn, "right");

        // Resultados
        resultadoMeses = crearEtiquetaResultado();
        resultadoAhorro = crearEtiquetaResultado();
        resultadoViabilidad = crearEtiquetaResultado();

        add(resultadoMeses, "span, growx");
        add(resultadoAhorro, "span, growx");
        add(resultadoViabilidad, "span, growx");

        // Crear controlador con esta vista e idUsuario
        controlador = new UtilidadesControlador(this, idUsuario);

        calcularBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double objetivo = ((Double) objetivoSpinner.getValue()).doubleValue();
                String mes = (String) mesCombo.getSelectedItem();
                int anio = (Integer) anioSpinner.getValue();
                String fechaStr = mes + "/" + anio;

                String[] resultados = controlador.calcularAhorro(objetivo, fechaStr);

                resultadoMeses.setText(resultados[0]);
                resultadoAhorro.setText(resultados[1]);
                resultadoViabilidad.setText(resultados[2]);
                actualizarSaldoActual();
            }
        });

        actualizarSaldoActual(); // Inicialización
    }

    private JLabel crearEtiquetaResultado() {
        JLabel label = new JLabel(" ");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    public void actualizarSaldoActual() {
        double saldo = controlador.getSaldoActualDelMes();
        saldoLabelValor.setText(String.format("%.2f €", saldo));
        setColorSaldoActual(saldo >= 0 ? new Color(46, 204, 113) : new Color(231, 76, 60));
    }

    public void refrescarVista() {
        actualizarSaldoActual();
    }

    public void setColorSaldoActual(Color color) {
        saldoLabelValor.setForeground(color);
    }
} 
