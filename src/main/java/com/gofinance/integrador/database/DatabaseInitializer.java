package com.gofinance.integrador.database;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

	 public static void crearTablas() {
	        String tablaUsuarios = """
	            CREATE TABLE IF NOT EXISTS usuario (
	                id INTEGER PRIMARY KEY AUTOINCREMENT,
	                nombre TEXT,
	                apellido TEXT,
	                telefono TEXT,
	                fechaNac TEXT,
	                password TEXT,
	                email TEXT UNIQUE
	            );
	            """;

	        String tablaGastos = """
	            CREATE TABLE IF NOT EXISTS gastos (
	                id INTEGER PRIMARY KEY AUTOINCREMENT,
	                fecha TEXT,
	                nombre TEXT,
	                categoria TEXT,
	                valor TEXT
	            );
	            """;

	        try (Connection con = DatabaseManager.connect();
	             Statement stmt = con.createStatement()) {

	            stmt.execute(tablaUsuarios);
	            stmt.execute(tablaGastos);
	            System.out.println("Tablas creadas correctamente.");

	        } catch (Exception e) {
	            System.out.println("Error al crear tablas: " + e.getMessage());
	        }
	    }

	public static void inicializarTablas() {
		 	UsuarioDAO.crearTablaSiNoExiste();
	        CategoriaDAO.createTable();
	        TransaccionDAO.createTable();
	        // Agregar los DAO's necesarios
	}
}
