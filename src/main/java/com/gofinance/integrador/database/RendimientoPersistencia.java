package com.gofinance.integrador.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;


public class RendimientoPersistencia {

    private DatabaseManager dbManager;

    public RendimientoPersistencia(){
        dbManager = new DatabaseManager();
    }

    public double ObtenerTotalGastos (int idUsuario) {

        String query = "SELECT monto FROM transacciones WHERE fecha >= date('now', '-30 days') AND esIngreso = 0 AND fkUsuario = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rlst = null;
        double totalGasto = 0;

        try {
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idUsuario);
            rlst = pstmt.executeQuery();

            
            while (rlst.next()) {
                totalGasto += rlst.getDouble("monto");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }     finally {
            try {
                if (rlst != null) rlst.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return totalGasto;
}

    public double obtenerTotalIngresos (int idUsuario) {

        String query = "SELECT monto FROM transacciones WHERE fecha >= date('now', '-30 days') AND esIngreso = 1 AND fkUsuario = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rlst = null;
        double totalIngreso = 0;

        try {
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idUsuario);
            rlst = pstmt.executeQuery();

            while (rlst.next()) {
                totalIngreso += rlst.getDouble("monto");
            }
        }catch (Exception e) {
            e.printStackTrace();
             }     finally {
            try {
                if (rlst != null) rlst.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return totalIngreso;
}

public Map<String, Double> obtenerGastosPorCategoria(int idUsuario) {

    String query = "SELECT categoria, SUM(monto) FROM transacciones WHERE fkUsuario = ? AND esIngreso = 0 GROUP BY categoria";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rlst = null;
      try {
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idUsuario);
            rlst = pstmt.executeQuery();
            Map<String, Double> gastosPorCategoria = new HashMap<>();


            while (rlst.next()) {
                String categoria = rlst.getString("categoria");
                double totalCategoria = rlst.getDouble("SUM(monto)");
                gastosPorCategoria.put(categoria, totalCategoria);
            }
        }catch (Exception e) {
            e.printStackTrace();
             }     finally {
            try {
                if (rlst != null) rlst.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return gastosPorCategoria;
    }
}