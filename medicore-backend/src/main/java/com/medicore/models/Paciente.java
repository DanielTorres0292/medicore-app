package com.medicore.models;

import java.time.LocalDate;

public class Paciente {
    private String expediente;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String correo;
    private String seguroMedico;

    public Paciente() {}

    public Paciente(String expediente, String nombres, String apellidos, LocalDate fechaNacimiento, String telefono, String correo, String seguroMedico) {
        this.expediente = expediente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correo = correo;
        this.seguroMedico = seguroMedico;
    }

    public String getExpediente() { return expediente; }
    public void setExpediente(String expediente) { this.expediente = expediente; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getSeguroMedico() { return seguroMedico; }
    public void setSeguroMedico(String seguroMedico) { this.seguroMedico = seguroMedico; }
}