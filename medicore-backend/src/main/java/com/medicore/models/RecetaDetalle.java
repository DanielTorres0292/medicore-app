package com.medicore.models;

public class RecetaDetalle {
    private int idMedicamento;
    private String dosis;
    private int dias;
    private int cantidadSolicitada;

    public RecetaDetalle() {}

    public int getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(int idMedicamento) { this.idMedicamento = idMedicamento; }

    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }

    public int getDias() { return dias; }
    public void setDias(int dias) { this.dias = dias; }

    public int getCantidadSolicitada() { return cantidadSolicitada; }
    public void setCantidadSolicitada(int cantidadSolicitada) { this.cantidadSolicitada = cantidadSolicitada; }
}