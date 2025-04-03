package database;

import java.sql.*;
import java.util.ArrayList;
import model.Usuario;

public class UsuarioDAO {

    // Create tabla
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS usuario (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre TEXT, apellido TEXT, telefono TEXT, fechaNac TEXT, password TEXT, email TEXT)";
        try (Connection conn = DatabaseManager.connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creando tabla: " + e.getMessage());
        }
    }

    // Insertar Usuario
    public static void insertUsuario(Usuario user) {
        String sql = "INSERT INTO usuario (nombre, apellido,telefono, fechaNac,password,email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getNombre());
            pstmt.setString(2, user.getApellido());
            pstmt.setString(3, user.getTelefono());
            pstmt.setString(4, user.getFechaNac());
            pstmt.setString(5, user.getPassword());
            pstmt.setString(6, user.getEmail());
            pstmt.executeUpdate();
            System.out.println("Usuario añadido con éxito!");
        } catch (SQLException e) {
            System.out.println("Error de inserción: " + e.getMessage());
        }
    }

    // Obtener usuario por Id
    public static Usuario getUsuario(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        Usuario usuario = null;
        try (Connection conn = DatabaseManager.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"),
                        rs.getString("telefono"), rs.getString("fechaNac"), rs.getString("password"),
                        rs.getString("email"));
            } else {
                System.out.println("Usuario no encontrado");
            }
        } catch (SQLException e) {
            System.out.println("Fetch error: " + e.getMessage());

        }
        return usuario;
    }

    // Obtener todos los usuarios
    public static ArrayList<Usuario> getAllUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection conn = DatabaseManager.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {

                usuarios.add(new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"),
                        rs.getString("telefono"), rs.getString("fechaNac"), rs.getString("password"),
                        rs.getString("email")));
            }
        } catch (SQLException e) {
            System.out.println("Fetch error: " + e.getMessage());
        }
        return usuarios;
    }

    // Actualizar Usuario
    public static void updateUsuario(int id, String newNombre, String newApellido, String newTelefono,
            String newFechaNac,
            String newPassword, String newEmail) {
        String sql = "UPDATE usuario SET nombre = ?, apellido = ?, telefono= ?, fechaNac = ?, password = ?, email = ?  WHERE id = ?";
        try (Connection conn = DatabaseManager.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newNombre);
            pstmt.setString(2, newApellido);
            pstmt.setString(3, newTelefono);
            pstmt.setString(4, newFechaNac);
            pstmt.setString(5, newPassword);
            pstmt.setString(6, newEmail);
            pstmt.setInt(7, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Usuario actualizado con éxito!");
            } else {
                System.out.println("Usuario no encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Error de actualización: " + e.getMessage());
        }
    }

    // Borrar Usuario
    public static void deleteUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conn = DatabaseManager.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Usuario borrado con éxito!");
            } else {
                System.out.println("Usuario no encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Delete error: " + e.getMessage());
        }
    }
}
