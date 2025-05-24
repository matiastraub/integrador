package com.gofinance.integrador.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.gofinance.integrador.model.Categoria;

public class CategoriaDAO {

    // Crear tabla
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS categoria ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "nombre TEXT NOT NULL, "
                   + "descripcion TEXT)";
        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creando tabla: " + e.getMessage());
        }
    }

    // Insertar categoría
    public static int crearCategoria(Categoria categoria) {
        int resultado = -1;
        String sql = "INSERT INTO categoria (nombre, descripcion) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, categoria.getNombre());
            pstmt.setString(2, categoria.getDescripcion());

            resultado = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error insertando categoría: " + e.getMessage());
        }
        return resultado;
    }

    // Obtener categoría por ID
    public static Categoria getCategoria(int id) {
        Categoria categoria = null;
        String sql = "SELECT * FROM categoria WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                categoria = new Categoria(
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo categoría: " + e.getMessage());
        }
        return categoria;
    }

    // Obtener todas las categorías
    public static ArrayList<Categoria> getAllCategorias() {
        ArrayList<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria";
        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Categoria c = new Categoria(
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
                categorias.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo lista de categorías: " + e.getMessage());
        }
        return categorias;
    }

    // Actualizar categoría
    public static void actualizarCategoria(int id, String nombre, String descripcion) {
        String sql = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, descripcion);
            pstmt.setInt(3, id);

            int filas = pstmt.executeUpdate();
            if (filas == 0) {
                System.out.println("No se encontró la categoría a actualizar.");
            }
        } catch (SQLException e) {
            System.out.println("Error actualizando categoría: " + e.getMessage());
        }
    }

    // Eliminar categoría
    public static void eliminarCategoria(int id) {
        String sql = "DELETE FROM categoria WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int filas = pstmt.executeUpdate();
            if (filas == 0) {
                System.out.println("No se encontró la categoría a eliminar.");
            }
        } catch (SQLException e) {
            System.out.println("Error eliminando categoría: " + e.getMessage());
        }
    }
}
