package com.gofinance.integrador.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gofinance.integrador.model.Transaccion;

/**
 * DAO para operaciones CRUD sobre la tabla 'transaccion', compatible con
 * obtención dinámica de fkcategoria mediante CategoriaDAO.
 */
public class TransaccionDAO {

    /**
     * Crea la tabla 'transaccion' si no existe.
     */
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
        try (Connection conn = DatabaseManager.connect();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creando la tabla 'transaccion': " + e.getMessage());
        }
    }

    /**
     * Inserta una transacción genérica.
     * 
     * @param transaccion Objeto Transaccion con los datos completos.
     * @return número de filas afectadas o -1 si falla.
     */
    public static int crearTransaccion(Transaccion transaccion) {
        int resultado = -1;
        String sql = "INSERT INTO transaccion (fecha, nombre, descripcion, monto, fkcategoria, fkmetodo_pago, fkusuario, es_ingreso) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, transaccion.getFecha());
            pstmt.setString(2, transaccion.getNombre());
            pstmt.setString(3, transaccion.getDescripcion());
            pstmt.setFloat(4, (float) transaccion.getMonto());
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

    /**
     * Inserta un ingreso usando el nombre de categoría para obtener su ID.
     */
    public static int insertarIngreso(String fecha, String nombre, String categoriaNombre, String descripcion,
            float monto, int fkMetodoPago, int idUsuario) {
        int filas = 0;
        try {
            int idCategoria = CategoriaDAO.getIdPorNombre(categoriaNombre);
            Transaccion transaccion = new Transaccion(
                    fecha,
                    nombre,
                    descripcion,
                    monto,
                    idCategoria,
                    fkMetodoPago,
                    idUsuario,
                    1);
            filas = crearTransaccion(transaccion);
        } catch (SQLException e) {
            System.out.println("Error al insertar ingreso: " + e.getMessage());
        }
        return filas;
    }

    /**
     * Inserta un gasto usando el nombre de categoría para obtener su ID.
     */
    public static int insertarGasto(String fecha, String nombre, String categoriaNombre, String descripcion,
            float monto, int fkMetodoPago, int idUsuario) {
        int filas = 0;
        try {
            int idCategoria = CategoriaDAO.getIdPorNombre(categoriaNombre);
            Transaccion transaccion = new Transaccion(
                    fecha,
                    nombre,
                    descripcion,
                    monto,
                    idCategoria,
                    fkMetodoPago,
                    idUsuario,
                    0);
            filas = crearTransaccion(transaccion);
        } catch (SQLException e) {
            System.out.println("Error al insertar gasto: " + e.getMessage());
        }
        return filas;
    }

    /**
     * Recupera una transacción por su ID.
     */
    public static Transaccion getTransaccion(int id) {
        Transaccion transaccion = null;
        String sql = "SELECT * FROM transaccion WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Transaccion t = new Transaccion(
                        rs.getString("fecha"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getFloat("monto"),
                        rs.getInt("fkcategoria"),
                        rs.getInt("fkmetodo_pago"),
                        rs.getInt("fkusuario"),
                        rs.getInt("es_ingreso"));
                t.setId(rs.getInt("id"));
                transaccion = t;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar transacción: " + e.getMessage());
        }
        return transaccion;
    }

    /**
     * Recupera todas las transacciones de un usuario.
     */
    public static List<Transaccion> getTransaccionesPorUsuario(int idUsuario) {
        List<Transaccion> lista = new ArrayList<Transaccion>();
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
                        rs.getInt("es_ingreso"));
                t.setId(rs.getInt("id"));
                lista.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener transacciones: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza los campos de una transacción.
     */
    public static void actualizarTransaccion(int id, String fecha, String nombre, String descripcion,
            float monto, int fkCategoria, int fkUsuario, int esIngreso) {
        String sql = "UPDATE transaccion SET fecha = ?, nombre = ?, descripcion = ?, monto = ?, "
                + "fkcategoria = ?, fkusuario = ?, es_ingreso = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fecha);
            pstmt.setString(2, nombre);
            pstmt.setString(3, descripcion);
            pstmt.setFloat(4, monto);
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

    /**
     * Elimina una transacción por su ID.
     */
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

    /**
     * Obtiene el último ID de ingreso de un usuario.
     */
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

    /**
     * Obtiene el último ID de gasto de un usuario.
     */
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
