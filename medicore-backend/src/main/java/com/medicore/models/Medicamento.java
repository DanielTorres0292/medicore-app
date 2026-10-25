package com.medicore.models;

import java.time.LocalDate;

public class Medicamento {
    private int id;
    private String nombre;
    private int existencia;
    private int stockMinimo;
    private String lote;
    private LocalDate caducidad;
    private double precio;

    public Medicamento() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getExistencia() { return existencia; }
    public void setExistencia(int existencia) { this.existencia = existencia; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public LocalDate getCaducidad() { return caducidad; }
    public void setCaducidad(LocalDate caducidad) { this.caducidad = caducidad; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}