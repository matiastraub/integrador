package com.gofinance.integrador.database;

import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseManager {

    private static final String DRIVE = "jdbc:sqlite:";

    public static Connection connect() {
        Properties prop = new Properties();
        try {
            // Cargar el archivo desde el classpath (funciona dentro y fuera del IDE)
            InputStream input = DatabaseManager.class.getClassLoader().getResourceAsStream("db.properties");
            if (input == null) {
                System.err.println("⚠️ No se encontró el archivo db.properties en el classpath.");
                return null;
            }

            prop.load(input);
            String relativePath = prop.getProperty("URL");

            // Obtener ruta real al archivo de base de datos
            URL resourceUrl = DatabaseManager.class.getClassLoader().getResource(relativePath);
            if (resourceUrl == null) {
                System.err.println("⚠️ No se encontró el archivo de base de datos: " + relativePath);
                return null;
            }

            String decodedPath = URLDecoder.decode(resourceUrl.getPath(), StandardCharsets.UTF_8);
            Connection conn = DriverManager.getConnection(DRIVE + decodedPath);
            return conn;

        } catch (Exception e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
