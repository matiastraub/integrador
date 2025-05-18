package com.gofinance.integrador.database;

import java.sql.*;
import java.util.ArrayList;
import com.gofinance.integrador.model.Usuario;

public class UsuarioDAO {

    static final String TABLA = "usuario";

    public static void crearTablaSiNoExiste() {
        Connection con = null;
        Statement stmt = null;

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLA + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre TEXT, "
                + "apellido TEXT, "
                + "telefono TEXT, "
                + "fechaNac TEXT, "
                + "password TEXT, "
                + "email TEXT UNIQUE)";

        try {
            con = DatabaseManager.connect();
            stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("❌ Error al crear tabla usuario: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("❌ Error al cerrar recursos: " + e.getMessage());
            }
        }
    }

    public int crearUsuario(Usuario user) {
        int res = 0;
        Connection con = null;
        PreparedStatement pstmt = null;

        String sql = "INSERT INTO " + TABLA + " (nombre, apellido, telefono, fechaNac, password, email) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            con = DatabaseManager.connect();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getNombre());
            pstmt.setString(2, user.getApellido());
            pstmt.setString(3, user.getTelefono());
            pstmt.setString(4, user.getFechaNac());
            pstmt.setString(5, user.getPassword());
            pstmt.setString(6, user.getEmail());

            res = pstmt.executeUpdate();
            System.out.println("Usuario insertado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return res;
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        Usuario usuario = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM " + TABLA + " WHERE email = ?";

        try {
            con = DatabaseManager.connect();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("fechaNac"),
                    rs.getString("password"),
                    rs.getString("email"));
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar usuario por email: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return usuario;
    }

    public boolean validarLogin(String email, String password) {
        boolean valido = false;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM " + TABLA + " WHERE email = ? AND password = ?";

        try {
            con = DatabaseManager.connect();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();
            valido = rs.next();

        } catch (SQLException e) {
            System.out.println("Error de validación: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return valido;
    }

    public ArrayList<Usuario> obtenerTodos() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM " + TABLA;

        try {
            con = DatabaseManager.connect();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                usuarios.add(new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("fechaNac"),
                    rs.getString("password"),
                    rs.getString("email")));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener usuarios: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return usuarios;
    }

    public int actualizarUsuario(int id, Usuario nuevo) {
        int res = 0;
        Connection con = null;
        PreparedStatement pstmt = null;

        String sql = "UPDATE " + TABLA + " SET nombre=?, apellido=?, telefono=?, fechaNac=?, password=?, email=? WHERE id=?";

        try {
            con = DatabaseManager.connect();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nuevo.getNombre());
            pstmt.setString(2, nuevo.getApellido());
            pstmt.setString(3, nuevo.getTelefono());
            pstmt.setString(4, nuevo.getFechaNac());
            pstmt.setString(5, nuevo.getPassword());
            pstmt.setString(6, nuevo.getEmail());
            pstmt.setInt(7, id);

            res = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return res;
    }

    public int eliminarUsuario(int id) {
        int res = 0;
        Connection con = null;
        PreparedStatement pstmt = null;

        String sql = "DELETE FROM " + TABLA + " WHERE id=?";

        try {
            con = DatabaseManager.connect();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);

            res = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return res;
    }
}
