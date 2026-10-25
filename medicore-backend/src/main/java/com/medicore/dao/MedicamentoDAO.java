package com.medicore.dao;

import com.medicore.config.ConexionBD;
import com.medicore.models.Medicamento;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDAO {

    // HU9: Consultar el inventario completo
    public List<Medicamento> obtenerInventario() {
        List<Medicamento> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, existencia, stock_minimo, lote, caducidad, precio FROM medicamentos";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Medicamento med = new Medicamento();
                med.setId(rs.getInt("id"));
                med.setNombre(rs.getString("nombre"));
                med.setExistencia(rs.getInt("existencia"));
                med.setStockMinimo(rs.getInt("stock_minimo"));
                med.setLote(rs.getString("lote"));
                med.setCaducidad(rs.getDate("caducidad").toLocalDate());
                med.setPrecio(rs.getDouble("precio"));
                lista.add(med);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener inventario: " + e.getMessage());
        }
        return lista;
    }

    // HU15: Registrar entrada de un nuevo medicamento
    public boolean registrarEntrada(Medicamento med) {
        String sql = "INSERT INTO medicamentos (nombre, existencia, stock_minimo, lote, caducidad, precio) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, med.getNombre());
            stmt.setInt(2, med.getExistencia());
            stmt.setInt(3, med.getStockMinimo());
            stmt.setString(4, med.getLote());
            stmt.setDate(5, Date.valueOf(med.getCaducidad()));
            stmt.setDouble(6, med.getPrecio());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar medicamento: " + e.getMessage());
            return false;
        }
    }
}
