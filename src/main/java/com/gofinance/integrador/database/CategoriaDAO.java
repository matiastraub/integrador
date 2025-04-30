package com.gofinance.integrador.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.gofinance.integrador.model.Categoria;

public class CategoriaDAO {
    /*
     * private String nombre;
     * private String descripcion;
     * 
     */
    // Create tabla
    public static void createTable() {
        String sql = "CREATE TABLE categoria (\"id\" INTEGER NOT NULL UNIQUE,\"nombre\"	TEXT NOT NULL,\"descripcion\"	TEXT);";
        try {
            Connection conn = DatabaseManager.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("❌ Error creando tabla: " + e.getMessage());
        }
    }

    // Insertar categoría
    public static int crearCategoria(Categoria categoria) {
        int resp = -1;
        String sql = "INSERT INTO categoria (nombre, descripcion) VALUES (?,?)";
        try {
            Connection conn = DatabaseManager.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, categoria.getNombre());
            pstmt.setString(2, categoria.getDescripcion());
            resp = pstmt.executeUpdate();
            System.out.println("✔️ Categoría se ha añadido con éxito!");
        } catch (SQLException e) {
            System.out.println("❌ Error de inserción: " + e.getMessage());
        }
        return resp;
    }

    // Obtener categoría por Id
    public static Categoria getCategoria(int id) {
        String sql = "SELECT * FROM categoria WHERE id = ?";
        Categoria categoria = null;
        try {
            Connection conn = DatabaseManager.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                categoria = new Categoria(rs.getString("nombre"),
                        rs.getString("descripcion"));
            } else {
                System.out.println("⚠️ Categoría no encontrada");
            }
        } catch (SQLException e) {
            System.out.println("❌  Fetch error: " + e.getMessage());
        }
        return categoria;
    }

    // Obtener todos las categorías
    public static ArrayList<Categoria> getAllCategorias() {
        ArrayList<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria";
        try {
            Connection conn = DatabaseManager.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                categorias.add(new Categoria(rs.getString("nombre"),
                        rs.getString("descripcion")));
            }
        } catch (SQLException e) {
            System.out.println("❌ Fetch error: " + e.getMessage());
        }
        return categorias;
    }

    // Actualizar categoría
    public static void updateCategoria(int id, String nombre, String descripcion) {
        String sql = "UPDATE categoria SET nombre = ?, descripcion = ?  WHERE id = ?";
        try {
            Connection conn = DatabaseManager.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setString(2, descripcion);
            pstmt.setInt(3, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✔️ Categoría actualizado con éxito!");
            } else {
                System.out.println("⚠️ Categoría no encontrada.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error de actualización: " + e.getMessage());
        }
    }

    // Borrar categoría
    public static void deleteCategoria(int id) {
        String sql = "DELETE FROM categoria WHERE id = ?";
        try {
            Connection conn = DatabaseManager.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✔️ Categoría borrada con éxito!");
            } else {
                System.out.println("⚠️ Categoría no encontrada.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Delete error: " + e.getMessage());
        }
    }

}
