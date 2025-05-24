package com.gofinance.integrador.controller;

public class UsuarioValidacion {

    public boolean esValidoNombre(String nombre) {
        if (nombre == null || nombre.length() < 2 || nombre.length() > 30) {
            return false;
        }

        for (int i = 0; i < nombre.length(); i++) {
            char c = nombre.charAt(i);
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    public boolean esValidoEmail(String email) {
        if (email == null || email.length() < 5) {
            return false;
        }

        int atIndex = email.indexOf('@');
        int dotIndex = email.lastIndexOf('.');

        return atIndex > 0 && dotIndex > atIndex;
    }

    public boolean esValidoTel(String telefono) {
        if (telefono == null || telefono.length() != 9) {
            return false;
        }

        for (int i = 0; i < telefono.length(); i++) {
            char c = telefono.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    public boolean esValidoPassword(String password) {
        if (password == null || password.length() < 8 || password.length() > 20) {
            return false;
        }

        boolean tieneMayus = false;
        boolean tieneMinus = false;
        boolean tieneNumero = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (Character.isUpperCase(c)) {
                tieneMayus = true;
            } else if (Character.isLowerCase(c)) {
                tieneMinus = true;
            } else if (Character.isDigit(c)) {
                tieneNumero = true;
            }
        }

        return tieneMayus && tieneMinus && tieneNumero;
    }

    public boolean esValidoFechaNac(String fecha) {
        if (fecha == null || fecha.length() != 10) {
            return false;
        }

        if (fecha.charAt(4) != '-' || fecha.charAt(7) != '-') {
            return false;
        }

        try {
            int anio = Integer.parseInt(fecha.substring(0, 4));
            int mes = Integer.parseInt(fecha.substring(5, 7));
            int dia = Integer.parseInt(fecha.substring(8, 10));

            return (anio > 1900 && anio < 2100) &&
                   (mes >= 1 && mes <= 12) &&
                   (dia >= 1 && dia <= 31); // no validamos días exactos por mes

        } catch (NumberFormatException e) {
            return false;
        }
    }
}
