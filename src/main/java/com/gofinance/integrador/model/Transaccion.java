package com.gofinance.integrador.model;

public class Transaccion {
    private String fecha;
    private String nombre;
    private String descripcion;
    private float monto;
    private int fkCategoria;
    private int fkMetodoPago;
    private int fkUsuario;
    private int esIngreso;
    
    public Transaccion() {
    }
    
    public Transaccion(String fecha, String nombre, String descripcion, float monto, int fkCategoria, int fkMetodoPago,
            int fkUsuario,
            int esIngreso) {
        this.fecha = fecha;
        this.fkCategoria = fkCategoria;
        this.fkUsuario = fkUsuario;
        this.fkMetodoPago = fkMetodoPago;
        this.monto = monto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.esIngreso = esIngreso;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getMonto() {
        return monto;
    }

    public int getFkCategoria() {
        return fkCategoria;
    }

    public int getFkMetodoPago() {
        return fkMetodoPago;
    }

    public int getFkUsuario() {
        return fkUsuario;
    }

    public int getEsIngreso() {
        return esIngreso;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public void setFkCategoria(int fkCategoria) {
        this.fkCategoria = fkCategoria;
    }

    public void setFkMetodoPago(int fkMetodoPago) {
        this.fkMetodoPago = fkMetodoPago;
    }

    public void setFkUsuario(int fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEsIngreso(int esIngreso) {
        this.esIngreso = esIngreso;
    }

}
