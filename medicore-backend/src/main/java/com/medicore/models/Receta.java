package com.medicore.models;

import java.util.List;

public class Receta {
    private int idConsulta;
    private List<RecetaDetalle> detalles;

    public Receta() {}

    public int getIdConsulta() { return idConsulta; }
    public void setIdConsulta(int idConsulta) { this.idConsulta = idConsulta; }

    public List<RecetaDetalle> getDetalles() { return detalles; }
    public void setDetalles(List<RecetaDetalle> detalles) { this.detalles = detalles; }
}