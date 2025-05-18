package com.gofinance.integrador.database;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseManager {

    private static final String DB_NAME = "integrador.db";

    // Ruta donde se guardará una copia persistente
    private static String getRutaPersistente() {
        String userHome = System.getProperty("user.home");
        String sep = System.getProperty("file.separator");
        return userHome + sep + ".gofinance" + sep + DB_NAME;
    }

    public static Connection connect() {
        Connection conn = null;
        InputStream propInput = null;
        InputStream dbInput = null;
        FileOutputStream dbOutput = null;

        try {
            // 1. Leer propiedades desde resources
            propInput = DatabaseManager.class.getClassLoader().getResourceAsStream("db.properties");
            if (propInput == null) {
                System.err.println("❌ No se encontró el archivo db.properties");
                return null;
            }

            Properties prop = new Properties();
            prop.load(propInput);
            String rutaDbEnResources = prop.getProperty("URL"); // data/integrador.db

            // 2. Obtener archivo físico de destino
            String dbPathFinal = getRutaPersistente();
            File dbArchivoDestino = new File(dbPathFinal);

            // 3. Si no existe, copiar desde resources
            if (!dbArchivoDestino.exists()) {
                System.out.println("Copiando base de datos a " + dbPathFinal);

                dbInput = DatabaseManager.class.getClassLoader().getResourceAsStream(rutaDbEnResources);
                if (dbInput == null) {
                    System.err.println("No se encontró el archivo embebido: " + rutaDbEnResources);
                    return null;
                }

                File directorioDestino = dbArchivoDestino.getParentFile();
                if (!directorioDestino.exists()) {
                    directorioDestino.mkdirs();
                }

                dbOutput = new FileOutputStream(dbArchivoDestino);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = dbInput.read(buffer)) != -1) {
                    dbOutput.write(buffer, 0, bytesRead);
                }

                System.out.println("Base de datos copiada correctamente.");
            }

            // 4. Conectar a la base de datos
            String url = "jdbc:sqlite:" + dbPathFinal;
            conn = DriverManager.getConnection(url);

        } catch (Exception e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (propInput != null) propInput.close();
                if (dbInput != null) dbInput.close();
                if (dbOutput != null) dbOutput.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar streams: " + e.getMessage());
            }
        }

        return conn;
    }
}
