package com.gofinance.integrador.model;

public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String fechaNac;
    private String password;
    private String email;

    public Usuario(String nombre, String apellido, String telefono, String fechaNac, String password,
            String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.fechaNac = fechaNac;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario \nid: " + id + "\nnombre: " + nombre + "\napellido: " + apellido + "\ntelefono: " + telefono
                + "\nfecha nacimiento: " + fechaNac + "\npassword: " + password + //
                "\nemail: " + email;
    }

}
