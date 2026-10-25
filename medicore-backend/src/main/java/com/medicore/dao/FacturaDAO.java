package com.medicore.dao;

import com.medicore.config.ConexionBD;
import com.medicore.models.Pago;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FacturaDAO {

    // HU11: Registrar pago y actualizar saldo de la factura
    public boolean registrarPago(Pago pago) {
        String sqlPago = "INSERT INTO pagos (id_factura, monto, metodo) VALUES (?, ?, ?)";
        String sqlFactura = "UPDATE facturas SET saldo = saldo - ?, estado = IF(saldo <= 0, 'Pagada', 'Pendiente de pago') WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion()) {
            conn.setAutoCommit(false); // Iniciar transacción

            try (PreparedStatement stmtPago = conn.prepareStatement(sqlPago);
                 PreparedStatement stmtFactura = conn.prepareStatement(sqlFactura)) {

                // 1. Insertar el abono
                stmtPago.setInt(1, pago.getIdFactura());
                stmtPago.setDouble(2, pago.getMonto());
                stmtPago.setString(3, pago.getMetodo());
                stmtPago.executeUpdate();

                // 2. Descontar el saldo
                stmtFactura.setDouble(1, pago.getMonto());
                stmtFactura.setInt(2, pago.getIdFactura());
                stmtFactura.executeUpdate();

                conn.commit(); // Confirmar cambios
                return true;

            } catch (SQLException ex) {
                conn.rollback();
                System.err.println("Error en transacción de pago: " + ex.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }
}