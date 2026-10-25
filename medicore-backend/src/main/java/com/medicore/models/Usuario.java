package com.medicore.models;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String password;
    private String rol;
    private int intentosFallidos;
    private boolean bloqueado;

    // Constructor vacío
    public Usuario() {}

    // Constructor completo
    public Usuario(int id, String nombre, String correo, String password, String rol, int intentosFallidos, boolean bloqueado) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
        this.intentosFallidos = intentosFallidos;
        this.bloqueado = bloqueado;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public int getIntentosFallidos() { return intentosFallidos; }
    public void setIntentosFallidos(int intentosFallidos) { this.intentosFallidos = intentosFallidos; }

    public boolean isBloqueado() { return bloqueado; }
    public void setBloqueado(boolean bloqueado) { this.bloqueado = bloqueado; }
}