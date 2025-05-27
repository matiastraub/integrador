package com.gofinance.integrador.model;

public class Transaccion {
    private int id; // NUEVO: identificador único de la transacción
    private String fecha;
    private String nombre;
    private String descripcion;
    private double monto;
    private int fkCategoria;
    private int fkMetodoPago;
    private int fkUsuario;
    private int esIngreso;

    public Transaccion() {
    }

    public Transaccion(String fecha, String nombre, String descripcion, double monto,
                       int fkCategoria, int fkMetodoPago, int fkUsuario, int esIngreso) {
        this.fecha = fecha;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.monto = monto;
        this.fkCategoria = fkCategoria;
        this.fkMetodoPago = fkMetodoPago;
        this.fkUsuario = fkUsuario;
        this.esIngreso = esIngreso;
    }

    // ✅ Getter y setter del ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Resto de getters y setters
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public int getFkCategoria() {
        return fkCategoria;
    }

    public void setFkCategoria(int fkCategoria) {
        this.fkCategoria = fkCategoria;
    }

    public int getFkMetodoPago() {
        return fkMetodoPago;
    }

    public void setFkMetodoPago(int fkMetodoPago) {
        this.fkMetodoPago = fkMetodoPago;
    }

    public int getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(int fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    public int getEsIngreso() {
        return esIngreso;
    }

    public void setEsIngreso(int esIngreso) {
        this.esIngreso = esIngreso;
    }
}
