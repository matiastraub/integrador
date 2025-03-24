package com.dam.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConfig {

	private String database;
	private String host;
	private String pass;
	private String user;
	private int port;
	private final String PATH = "src/com/dam/model/db.properties";

	public DatabaseConfig() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(PATH));
			database = prop.getProperty("DATABASE");
			host = prop.getProperty("HOST");
			pass = prop.getProperty("PASS");
			user = prop.getProperty("USER");
			port = Integer.parseInt(prop.getProperty("PORT"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
