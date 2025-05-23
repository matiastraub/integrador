package com.gofinance.integrador.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.gofinance.integrador.model.Transaccion;

public class TransaccionDAO {

    // Crear tabla
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS transaccion ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "fecha TEXT NOT NULL, "
                   + "nombre TEXT NOT NULL, "
                   + "descripcion TEXT, "
                   + "monto REAL NOT NULL, "
                   + "fkcategoria INTEGER NOT NULL, "
                   + "fkmetodo_pago INTEGER NOT NULL, "
                   + "fkusuario INTEGER NOT NULL, "
                   + "es_ingreso INTEGER NOT NULL DEFAULT 0)";
        try (Connection conn = DatabaseManager.connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creando la tabla: " + e.getMessage());
        }
    }

    // Insertar transacción
    public static int crearTransaccion(Transaccion transaccion) {
        int resultado = -1;
        String sql = "INSERT INTO transaccion (fecha, nombre, descripcion, monto, fkcategoria, fkmetodo_pago, fkusuario, es_ingreso) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, transaccion.getFecha());
            pstmt.setString(2, transaccion.getNombre());
            pstmt.setString(3, transaccion.getDescripcion());
            pstmt.setFloat(4, transaccion.getMonto());
            pstmt.setInt(5, transaccion.getFkCategoria());
            pstmt.setInt(6, transaccion.getFkMetodoPago());
            pstmt.setInt(7, transaccion.getFkUsuario());
            pstmt.setInt(8, transaccion.getEsIngreso());

            resultado = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar transacción: " + e.getMessage());
        }
        return resultado;
    }
    
    public static int insertarIngreso(String fecha, String nombre, String categoria, String valor) {
        String sql = "INSERT INTO transaccion (fecha, nombre, descripcion, monto, fkcategoria, fkmetodo_pago, fkusuario, es_ingreso) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fecha);
            pstmt.setString(2, nombre);
            pstmt.setString(3, categoria); // Usamos la categoría como descripción
            pstmt.setFloat(4, Float.parseFloat(valor));
            pstmt.setInt(5, 1); // fkcategoria: valor por defecto
            pstmt.setInt(6, 1); // fkmetodo_pago: valor por defecto
            pstmt.setInt(7, 1); // fkusuario: valor por defecto
            pstmt.setInt(8, 1); // es_ingreso: 1 porque es ingreso

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al insertar ingreso: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Valor numérico inválido: " + e.getMessage());
        }

        return 0;
    }


    
    public static int insertarGastoBasico(String fecha, String nombre, String categoria, String valor) {
        String sql = "INSERT INTO transaccion (fecha, nombre, descripcion, monto, fkcategoria, fkmetodo_pago, fkusuario, es_ingreso) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fecha);
            pstmt.setString(2, nombre);
            pstmt.setString(3, categoria); // usamos categoria como "descripcion"
            pstmt.setFloat(4, Float.parseFloat(valor));
            pstmt.setInt(5, 1); // fkcategoria: valor por defecto
            pstmt.setInt(6, 1); // fkmetodo_pago: valor por defecto
            pstmt.setInt(7, 1); // fkusuario: simulamos usuario 1
            pstmt.setInt(8, 0); // es_ingreso: 0 porque es gasto

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al insertar gasto básico: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Valor no válido: " + e.getMessage());
        }
        return 0;
    }


    // Obtener transacción por ID
    public static Transaccion getTransaccion(int id) {
        Transaccion transaccion = null;
        String sql = "SELECT * FROM transaccion WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                transaccion = new Transaccion(
                    rs.getString("fecha"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getFloat("monto"),
                    rs.getInt("fkcategoria"),
                    rs.getInt("fkmetodo_pago"),
                    rs.getInt("fkusuario"),
                    rs.getInt("es_ingreso")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar transacción: " + e.getMessage());
        }
        return transaccion;
    }

    // Obtener todas las transacciones de un usuario
    public static ArrayList<Transaccion> getTransaccionesPorUsuario(int idUsuario) {
        ArrayList<Transaccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM transaccion WHERE fkusuario = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Transaccion t = new Transaccion(
                    rs.getString("fecha"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getFloat("monto"),
                    rs.getInt("fkcategoria"),
                    rs.getInt("fkmetodo_pago"),
                    rs.getInt("fkusuario"),
                    rs.getInt("es_ingreso")
                );
                lista.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener transacciones: " + e.getMessage());
        }
        return lista;
    }

    // Actualizar transacción
    public static void actualizarTransaccion(int id, String fecha, String nombre, String descripcion,
                                             int monto, int fkCategoria, int fkUsuario, int esIngreso) {
        String sql = "UPDATE transaccion SET fecha = ?, nombre = ?, descripcion = ?, monto = ?, "
                   + "fkcategoria = ?, fkusuario = ?, es_ingreso = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fecha);
            pstmt.setString(2, nombre);
            pstmt.setString(3, descripcion);
            pstmt.setInt(4, monto);
            pstmt.setInt(5, fkCategoria);
            pstmt.setInt(6, fkUsuario);
            pstmt.setInt(7, esIngreso);
            pstmt.setInt(8, id);

            int filas = pstmt.executeUpdate();
            if (filas == 0) {
                System.out.println("No se encontró la transacción a actualizar.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar transacción: " + e.getMessage());
        }
    }

    // Eliminar transacción
    public static void eliminarTransaccion(int id) {
        String sql = "DELETE FROM transaccion WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int filas = pstmt.executeUpdate();
            if (filas == 0) {
                System.out.println("No se encontró la transacción a eliminar.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar transacción: " + e.getMessage());
        }
    }

    // Obtener último ID de ingreso para un usuario
    public static int getUltimoIdIngreso(int idUsuario) {
        int id = -1;
        String sql = "SELECT MAX(id) FROM transaccion WHERE es_ingreso = 1 AND fkusuario = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo último ID de ingreso: " + e.getMessage());
        }
        return id;
    }

    // Obtener último ID de gasto para un usuario
    public static int getUltimoIdGasto(int idUsuario) {
        int id = -1;
        String sql = "SELECT MAX(id) FROM transaccion WHERE es_ingreso = 0 AND fkusuario = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo último ID de gasto: " + e.getMessage());
        }
        return id;
    }

}
