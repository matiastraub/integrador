package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.DatabaseConfig;

public class Main {

	public static void main(String[] args) {
		try {
			// TODO: Testing database connection
			DatabaseConfig db = new DatabaseConfig();
			Class.forName("org.sqlite.JDBC");
			try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + db.getUrl());
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery("SELECT * FROM usuario order by nombre asc;")) {
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
