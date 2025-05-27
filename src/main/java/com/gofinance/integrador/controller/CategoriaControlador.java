package com.gofinance.integrador.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Controlador para manejar las categorías de ingreso y gasto
 * junto a sus IDs estáticos, siguiendo el estilo clásico MVC.
 */
public class CategoriaControlador implements ActionListener {

    // Mapas estáticos que asocian nombre de categoría con su ID
    private static final Map<String, Integer> GASTOS_MAP = new LinkedHashMap<String, Integer>();
    private static final Map<String, Integer> INGRESOS_MAP = new LinkedHashMap<String, Integer>();

    static {
        // Inicialización de categorías de gasto
        GASTOS_MAP.put("Alimentación", 1);
        GASTOS_MAP.put("Transporte",   2);
        GASTOS_MAP.put("Ocio",         3);
        GASTOS_MAP.put("Educación",    4);
        GASTOS_MAP.put("Salud",        5);
        GASTOS_MAP.put("Otros",        6);

        // Inicialización de categorías de ingreso
        INGRESOS_MAP.put("Salario",     1);
        INGRESOS_MAP.put("Venta",       2);
        INGRESOS_MAP.put("Premio",      3);
        INGRESOS_MAP.put("Interés",     4);
        INGRESOS_MAP.put("Otro",        5);
    }

    /**
     * Devuelve un arreglo con los nombres de categorías de gasto.
     */
    public static String[] obtenerCategoriasGastos() {
        return GASTOS_MAP.keySet().toArray(new String[0]);
    }

    /**
     * Devuelve un arreglo con los nombres de categorías de ingreso.
     */
    public static String[] obtenerCategoriasIngresos() {
        return INGRESOS_MAP.keySet().toArray(new String[0]);
    }

    /**
     * Obtiene el ID de una categoría de gasto dado su nombre.
     * @param nombre Nombre de la categoría
     * @return ID o -1 si no existe
     */
    public static int getIdCategoriaGasto(String nombre) {
        Integer id = GASTOS_MAP.get(nombre);
        return (id != null) ? id : -1;
    }

    /**
     * Obtiene el ID de una categoría de ingreso dado su nombre.
     * @param nombre Nombre de la categoría
     * @return ID o -1 si no existe
     */
    public static int getIdCategoriaIngreso(String nombre) {
        Integer id = INGRESOS_MAP.get(nombre);
        return (id != null) ? id : -1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Ejemplo de debug: mostrar nombre e ID de la categoría seleccionada
        String nombre = e.getActionCommand();
        int idGasto = getIdCategoriaGasto(nombre);
        int idIngreso = getIdCategoriaIngreso(nombre);
        System.out.println("Categoría seleccionada: " + nombre +
                           " (Gasto ID=" + idGasto +
                           ", Ingreso ID=" + idIngreso + ")");
    }
}
