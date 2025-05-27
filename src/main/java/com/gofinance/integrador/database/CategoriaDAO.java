package com.gofinance.integrador.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gofinance.integrador.model.Categoria;

/**
 * DAO para operaciones CRUD básicas sobre la tabla 'categoria'.
 */
public class CategoriaDAO {

    /**
     * Crea la tabla 'categoria' si no existe.
     */
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS categoria ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "nombre TEXT NOT NULL UNIQUE)";
        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creando la tabla 'categoria': " + e.getMessage());
        }
    }

    /**
     * Inserta una nueva categoría en la base de datos.
     * @param nombre Nombre de la categoría
     * @return El ID generado de la nueva categoría, o -1 si falla
     */
    public static int insertarCategoria(String nombre) {
        String sql = "INSERT INTO categoria (nombre) VALUES (?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, nombre);
            int filas = pstmt.executeUpdate();
            if (filas == 0) {
                throw new SQLException("No se insertó ninguna fila.");
            }
            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar categoría: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Devuelve el ID de la categoría dado su nombre.
     * @param nombreCategoria Nombre de la categoría
     * @return ID de la categoría
     * @throws SQLException Si no existe o hay error de BD
     */
    public static int getIdPorNombre(String nombreCategoria) throws SQLException {
        String sql = "SELECT id FROM categoria WHERE nombre = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreCategoria);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    throw new SQLException("Categoría no encontrada: " + nombreCategoria);
                }
            }
        }
    }
    /** Devuelve categorías por tipo: 'GASTO' o 'INGRESO' */
    public static List<Categoria> obtenerPorTipo(String tipo) throws SQLException {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM categoria WHERE tipo = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tipo);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Categoria(rs.getInt("id"), rs.getString("nombre")));
                }
            }
        }
        return lista;
    }

    /**
     * Recupera todas las categorías existentes.
     * @return Lista de objetos Categoria
     * @throws SQLException Si hay error de BD
     */
    public static List<Categoria> obtenerTodas() throws SQLException {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM categoria ORDER BY nombre";
        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Categoria cat = new Categoria(rs.getInt("id"), rs.getString("nombre"));
                lista.add(cat);
            }
        }
        return lista;
    }
}
