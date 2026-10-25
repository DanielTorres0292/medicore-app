package com.medicore.dao;

import com.medicore.config.ConexionBD;
import com.medicore.models.Paciente;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    // HU2: Registrar un nuevo paciente
    public boolean registrar(Paciente paciente) {
        String sql = "INSERT INTO pacientes (expediente, nombres, apellidos, fecha_nacimiento, telefono, correo, seguro_medico) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getExpediente());
            stmt.setString(2, paciente.getNombres());
            stmt.setString(3, paciente.getApellidos());
            stmt.setDate(4, Date.valueOf(paciente.getFechaNacimiento()));
            stmt.setString(5, paciente.getTelefono());
            stmt.setString(6, paciente.getCorreo());
            stmt.setString(7, paciente.getSeguroMedico());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar paciente: " + e.getMessage());
            return false;
        }
    }

    // HU13: Buscar pacientes por nombre, apellido o expediente
    public List<Paciente> buscar(String criterio) {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT expediente, nombres, apellidos, fecha_nacimiento, telefono, correo, seguro_medico FROM pacientes WHERE nombres LIKE ? OR apellidos LIKE ? OR expediente LIKE ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String parametro = "%" + criterio + "%";
            stmt.setString(1, parametro);
            stmt.setString(2, parametro);
            stmt.setString(3, parametro);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Paciente(
                            rs.getString("expediente"),
                            rs.getString("nombres"),
                            rs.getString("apellidos"),
                            rs.getDate("fecha_nacimiento").toLocalDate(),
                            rs.getString("telefono"),
                            rs.getString("correo"),
                            rs.getString("seguro_medico")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar pacientes: " + e.getMessage());
        }
        return lista;
    }
}