package com.dam.backend.main;

import com.dam.model.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	private static String host;
	private static int port;
	private static String database;
	private static String user;
	private static String pass;

	private static void getDatabase() {
		DatabaseConfig db = new DatabaseConfig();
		host = db.getHost();
		port = db.getPort();
		database = db.getDatabase();
		user = db.getUser();
		pass = db.getPass();
	}

	public static void main(String[] args) {
		try {
			getDatabase();
			// TODO: Cambiar base de datos a Sqlite
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection connection = DriverManager.getConnection(
					"jdbc:mysql://" + host + ":" + port + "/" + database,
					user, pass);
					Statement statement = connection.createStatement();
					// TODO: Ejemplo de conexión a base de datos
					ResultSet resultSet = statement.executeQuery("SELECT * FROM Usuario order by nombre asc;")) {

				int code;
				String lastname;
				String name;
				String tel;
				String fechaNac;
				while (resultSet.next()) {
					code = resultSet.getInt("id");
					name = resultSet.getString("nombre");
					lastname = resultSet.getString("apellido");
					tel = resultSet.getString("telefono");
					fechaNac = resultSet.getString("fechaNac");
					System.out.println(code + " - " + name + " " + lastname + " " + tel + " " + fechaNac);
				}
			} catch (SQLException exception) {
				System.out.println(exception);
			}
		} catch (ClassNotFoundException exception) {
			System.out.println(exception);
		}
	}
}
