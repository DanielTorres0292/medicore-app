package com.medicore.dao;

import com.medicore.config.ConexionBD;
import com.medicore.models.Consulta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConsultaDAO {

    public boolean registrar(Consulta consulta) {
        String sql = "INSERT INTO consultas (expediente_paciente, id_medico, temperatura, presion, peso, pulso, diagnostico, tratamiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, consulta.getExpedientePaciente());
            stmt.setInt(2, consulta.getIdMedico());
            stmt.setDouble(3, consulta.getTemperatura());
            stmt.setString(4, consulta.getPresion());
            stmt.setDouble(5, consulta.getPeso());
            stmt.setInt(6, consulta.getPulso());
            stmt.setString(7, consulta.getDiagnostico());
            stmt.setString(8, consulta.getTratamiento());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar la consulta: " + e.getMessage());
            return false;
        }
    }
}