package com.gofinance.integrador.database;

public class DatabaseInit {

    public static void inicializarTablas() {
        UsuarioDAO.crearTablaSiNoExiste();
        CategoriaDAO.createTable();
        TransaccionDAO.createTable();
        // Agregar los DAO's necesarios
    }
}
