package com.gofinance.integrador.model;

public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String fechaNac;
    private String password;
    private String email;

    // Constructor completo (con id) para recuperar desde la base de datos
    public Usuario(int id, String nombre, String apellido, String telefono, String fechaNac, String password, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.fechaNac = fechaNac;
        this.password = password;
        this.email = email;
    }

    // Constructor sin id para registro (cuando aún no existe en la base de datos)
    public Usuario(String nombre, String apellido, String telefono, String fechaNac, String password, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.fechaNac = fechaNac;
        this.password = password;
        this.email = email;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Para depuración
    @Override
    public String toString() {
        return "Usuario \n" +
               "id: " + id + "\n" +
               "nombre: " + nombre + "\n" +
               "apellido: " + apellido + "\n" +
               "telefono: " + telefono + "\n" +
               "fecha nacimiento: " + fechaNac + "\n" +
               "password: " + password + "\n" +
               "email: " + email;
    }
}
