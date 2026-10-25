package com.medicore;

import com.medicore.dao.PacienteDAO;
import com.medicore.models.Paciente;
import java.util.List;

public class TestConexion {
    public static void main(String[] args) {
        PacienteDAO pacienteDAO = new PacienteDAO();

        // Probamos buscar a la paciente Ana
        List<Paciente> resultados = pacienteDAO.buscar("Ana");

        System.out.println("----------------------------------------");
        System.out.println("RESULTADOS DE BÚSQUEDA DE PACIENTES:");
        for (Paciente p : resultados) {
            System.out.println("Expediente: " + p.getExpediente() + " | Nombre: " + p.getNombres() + " " + p.getApellidos() + " | Teléfono: " + p.getTelefono());
        }
        System.out.println("----------------------------------------");
    }
}