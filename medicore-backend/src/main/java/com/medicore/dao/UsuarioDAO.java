package com.medicore.dao;

import com.medicore.config.ConexionBD;
import com.medicore.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public Usuario autenticar(String correo, String password) {
        String sql = "SELECT id, nombre, correo, password, rol, intentos_fallidos, bloqueado FROM usuarios WHERE correo = ? AND password = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            rs.getString("password"),
                            rs.getString("rol"),
                            rs.getInt("intentos_fallidos"),
                            rs.getBoolean("bloqueado")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
        }
        return null; // Retorna null si las credenciales son incorrectas
    }

    // NUEVO MÉTODO: Obtener todos los usuarios de la base de datos (para la vista de Administrador)
    public List<Usuario> obtenerTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, correo, password, rol, intentos_fallidos, bloqueado FROM usuarios";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("password"),
                        rs.getString("rol"),
                        rs.getInt("intentos_fallidos"),
                        rs.getBoolean("bloqueado")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la lista de usuarios: " + e.getMessage());
        }
        return lista;
    }

    // Método para actualizar los datos de un usuario existente
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, correo = ?, rol = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getRol());
            stmt.setInt(4, usuario.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar un usuario por su ID
    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Propaga el mensaje de error del Trigger de la base de datos si es el Admin Maestro
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean registrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, password, rol) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getPassword());
            stmt.setString(4, usuario.getRol());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }
}