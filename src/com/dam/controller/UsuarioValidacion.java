package com.dam.controller;

import java.util.regex.Pattern;

public class UsuarioValidacion {

    private static final int MIN_STR = 2;
    private static final int MAX_STR = 30;
    private static final String STR_REGEX = "^[A-Za-z]{" + MIN_STR + "," + MAX_STR + "}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String TEL_REGEX = "^\\d{10,15}$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";

    public static boolean esValidoNombre(String name) {
        return name != null && Pattern.matches(STR_REGEX, name);
    }

    public static boolean esValidoEmail(String email) {
        return email != null && Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean esValidoTel(String phoneNumber) {
        return phoneNumber != null && Pattern.matches(TEL_REGEX, phoneNumber);
    }

    public static boolean esValidoPassword(String password) {
        return password != null && Pattern.matches(PASSWORD_REGEX, password);
    }

    public static boolean esValidoFechaNac(String dob) {
        // Basic check: Ensure it's in YYYY-MM-DD format
        return dob != null && dob.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    public static void main(String[] args) {
        // Test
        System.out.println(esValidoNombre("John")); // true
        System.out.println(esValidoNombre("J")); // false
        System.out.println(esValidoEmail("john.doe@example.com")); // true
        System.out.println(esValidoTel("1234567890")); // true
        System.out.println(esValidoPassword("StrongP@ss1")); // true
        System.out.println(esValidoFechaNac("1995-06-25")); // true
    }
}
