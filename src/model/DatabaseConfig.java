package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConfig {

	private String database;
	private String host;
	private String pass;
	private String user;
	private int port;
	private String url;

	private final String PATH = "src/db.properties";

	public DatabaseConfig() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(PATH));
			database = prop.getProperty("DATABASE");
			host = prop.getProperty("HOST");
			pass = prop.getProperty("PASS");
			user = prop.getProperty("USER");
			port = Integer.parseInt(prop.getProperty("PORT"));
			url = prop.getProperty("URL");
		} catch (IOException e) {
			System.out.println("Error al cargar el archivo de propiedades: " + e.getMessage());
		} catch (NumberFormatException e) {
			System.out.println("Error al convertir el puerto a entero: " + e.getMessage());
		}
	}

	public String getUrl() {
		return url;
	}

	public String getDatabase() {
		return database;
	}

	public String getHost() {
		return host;
	}

	public String getPass() {
		return pass;
	}

	public String getUser() {
		return user;
	}

	public int getPort() {
		return port;
	}

}
