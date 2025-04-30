package com.gofinance.integrador.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.gofinance.integrador.model.Transaccion;

public class TransaccionDAO {

    /*
     * private Date fecha;
     * private String nombre;
     * private String descripcion;
     * private float monto;
     * private int fkCategoria;
     * private int fkUsuario;
     * private int esIngreso;
     * 
     */
    // Create tabla
    public static void createTable() {
        String sql = "CREATE TABLE transaccion (id	INTEGER NOT NULL UNIQUE,nombre	TEXT NOT NULL,descripcion	TEXT,monto	REAL NOT NULL,fkcategoria INTEGER NOT NULL,fkmetodo_pago INTEGER NOT NULL,fkusuario	INTEGER NOT NULL,\"es_ingreso\"\t INTEGER NOT NULL DEFAULT 0);";
        try (Connection conn = DatabaseManager.connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creando tabla: " + e.getMessage());
        }
    }

    // Insertar Transaccion
    public static int crearTransaccion(Transaccion transaccion) {
        int resp = -1;
        String sql = "INSERT INTO transaccion (fecha,nombre, descripcion,monto,fkcategoria,fkmetodo_pago,fkusuario,es_ingreso) VALUES (?,?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, transaccion.getFecha());
            pstmt.setString(2, transaccion.getNombre());
            pstmt.setString(3, transaccion.getDescripcion());
            pstmt.setFloat(4, transaccion.getMonto());
            pstmt.setInt(5, transaccion.getFkCategoria());
            pstmt.setInt(6, transaccion.getFkMetodoPago());
            pstmt.setInt(7, transaccion.getFkUsuario());
            pstmt.setInt(8, transaccion.getEsIngreso());
            resp = pstmt.executeUpdate();
            System.out.println("✔️ Transacción se ha añadido con éxito!");
        } catch (SQLException e) {
            System.out.println("❌ Error de inserción: " + e.getMessage());
        }
        return resp;
    }

    // Obtener Transacción por Id
    public static Transaccion getTransaccion(int id) {
        String sql = "SELECT * FROM transaccion WHERE id = ?";
        Transaccion transaccion = null;
        try (Connection conn = DatabaseManager.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                transaccion = new Transaccion(rs.getString("fecha"), rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getFloat("monto"), rs.getInt("fkcategoria"), rs.getInt("fkmetodo_pago"), rs.getInt("fkuser"),
                        rs.getInt("es_ingreso"));
            } else {
                System.out.println("⚠️ Transacción no encontrada");
            }
        } catch (SQLException e) {
            System.out.println("❌ Fetch error: " + e.getMessage());
        }
        return transaccion;
    }

    // Obtener todos las transacciones
    public static ArrayList<Transaccion> getAllTransactions() {
        ArrayList<Transaccion> transacciones = new ArrayList<>();
        String sql = "SELECT * FROM transacciones";
        try {
            Connection conn = DatabaseManager.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {

                transacciones.add(new Transaccion(rs.getString("fecha"), rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getFloat("monto"), rs.getInt("fkcategoria"), rs.getInt("fkmetodo_pago"), rs.getInt("fkuser"),
                        rs.getInt("es_ingreso")));
            }
        } catch (SQLException e) {
            System.out.println("❌ Fetch error: " + e.getMessage());
        }
        return transacciones;
    }

    // Actualizar transacción
    public static void updateUsuario(int id, String fecha, String nombre, String descripcion, int fkCategoria,
            int fkUsuario,
            int esIngreso) {
        String sql = "UPDATE usuario SET fecha = ?, nombre = ?, descripcion = ?, fkcategoria= ?, fkusuario = ?,es_ingreso = ?  WHERE id = ?";
        try {
            Connection conn = DatabaseManager.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fecha);
            pstmt.setString(2, nombre);
            pstmt.setString(3, descripcion);
            pstmt.setInt(4, fkCategoria);
            pstmt.setInt(5, fkUsuario);
            pstmt.setInt(6, esIngreso);
            pstmt.setInt(7, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✔️ Transacción actualizado con éxito!");
            } else {
                System.out.println("⚠️ Transacción no encontrada.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error de actualización: " + e.getMessage());
        }
    }

    // Borrar transacción
    public static void deleteTransaccion(int id) {
        String sql = "DELETE FROM transaccion WHERE id = ?";
        try {
            Connection conn = DatabaseManager.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✔️ Transacción borrada con éxito!");
            } else {
                System.out.println("⚠️ Transacción no encontrada.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Delete error: " + e.getMessage());
        }
    }
}
