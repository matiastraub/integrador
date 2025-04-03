package database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {

    private static String url;

    private static final String PATH = "src/db.properties";
    private static final String DRIVE = "jdbc:sqlite:";

    public static Connection connect() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(PATH));
            url = prop.getProperty("URL");
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(DRIVE + url);
            } catch (SQLException e) {
                System.out.println("Fallo de conexión: " + e.getMessage());
            }
            return conn;
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error Cargando propiedades: " + e.getMessage());

        }
        return null;
    }
}
