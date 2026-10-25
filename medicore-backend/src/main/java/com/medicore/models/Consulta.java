package com.medicore.models;

public class Consulta {
    private int id;
    private String expedientePaciente;
    private int idMedico;
    private double temperatura;
    private String presion;
    private double peso;
    private int pulso;
    private String diagnostico;
    private String tratamiento;

    public Consulta() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getExpedientePaciente() { return expedientePaciente; }
    public void setExpedientePaciente(String expedientePaciente) { this.expedientePaciente = expedientePaciente; }

    public int getIdMedico() { return idMedico; }
    public void setIdMedico(int idMedico) { this.idMedico = idMedico; }

    public double getTemperatura() { return temperatura; }
    public void setTemperatura(double temperatura) { this.temperatura = temperatura; }

    public String getPresion() { return presion; }
    public void setPresion(String presion) { this.presion = presion; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public int getPulso() { return pulso; }
    public void setPulso(int pulso) { this.pulso = pulso; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }
}