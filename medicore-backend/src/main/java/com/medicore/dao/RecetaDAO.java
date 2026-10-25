package com.medicore.dao;

import com.medicore.config.ConexionBD;
import com.medicore.models.Receta;
import com.medicore.models.RecetaDetalle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RecetaDAO {

    public boolean registrar(Receta receta) {
        String sqlReceta = "INSERT INTO recetas (id_consulta, estado) VALUES (?, 'Pendiente')";
        String sqlDetalle = "INSERT INTO receta_detalles (id_receta, id_medicamento, dosis, dias, cantidad_solicitada) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion()) {
            conn.setAutoCommit(false); // Desactiva auto-guardado para manejar la transacción

            try (PreparedStatement stmtReceta = conn.prepareStatement(sqlReceta, Statement.RETURN_GENERATED_KEYS)) {
                stmtReceta.setInt(1, receta.getIdConsulta());
                stmtReceta.executeUpdate();

                try (ResultSet rs = stmtReceta.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idRecetaGenerado = rs.getInt(1);

                        // Preparar la inserción de todos los medicamentos de la receta
                        try (PreparedStatement stmtDetalle = conn.prepareStatement(sqlDetalle)) {
                            for (RecetaDetalle detalle : receta.getDetalles()) {
                                stmtDetalle.setInt(1, idRecetaGenerado);
                                stmtDetalle.setInt(2, detalle.getIdMedicamento());
                                stmtDetalle.setString(3, detalle.getDosis());
                                stmtDetalle.setInt(4, detalle.getDias());
                                stmtDetalle.setInt(5, detalle.getCantidadSolicitada());
                                stmtDetalle.addBatch(); // Agrega al bloque de inserción
                            }
                            stmtDetalle.executeBatch(); // Ejecuta todas las inserciones juntas
                        }
                    }
                }
                conn.commit(); // Confirma que todo se guardó correctamente
                return true;

            } catch (SQLException ex) {
                conn.rollback(); // Si algo falla, revierte todo para no dejar datos a medias
                System.err.println("Error en la transacción de receta: " + ex.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }
}