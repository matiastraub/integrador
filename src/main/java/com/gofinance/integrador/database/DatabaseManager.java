package com.gofinance.integrador.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.sqlite.SQLiteConfig;

public class DatabaseManager {

    private static String driver;
    private static String url;

    static {
        Properties prop = new Properties();
        try (InputStream is = DatabaseManager.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (is != null) {
                prop.load(is);
                driver = prop.getProperty("DRIVER");
                url = prop.getProperty("URL");
            } else {
                System.out.println("No se encontró el archivo db.properties en el classpath.");
            }
        } catch (IOException e) {
            System.out.println("Error al leer db.properties: " + e.getMessage());
        }
    }


    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(driver);

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        return DriverManager.getConnection(url, config.toProperties());
    }

    public static Connection connect() {
        try {
            return getConnection();
        } catch (Exception e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
            return null;
        }
    }
} 
