package com.gofinance.integrador.model;

public class Gasto {

    private String fecha;
    private String nombre;
    private String categoria;
    private String valor;

    public Gasto(String fecha, String nombre, String categoria, String valor) {
        this.fecha = fecha;
        this.nombre = nombre;
        this.categoria = categoria;
        this.valor = valor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return fecha + " | " + nombre + " | " + categoria + " | " + valor + "€";
    }
}
