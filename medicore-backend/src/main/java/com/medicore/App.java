package com.medicore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medicore.dao.*;
import com.medicore.models.*;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import io.javalin.plugin.bundled.CorsPluginConfig;

import java.util.List;

public class App {

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(CorsPluginConfig.CorsRule::anyHost);
            });
            // Se agrega el parámetro 'false' requerido por Javalin 6.x
            config.jsonMapper(new JavalinJackson(mapper, false));
        }).start(7070);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PacienteDAO pacienteDAO = new PacienteDAO();
        ConsultaDAO consultaDAO = new ConsultaDAO();
        RecetaDAO recetaDAO = new RecetaDAO();
        MedicamentoDAO medicamentoDAO = new MedicamentoDAO();
        FacturaDAO facturaDAO = new FacturaDAO();

        System.out.println("==================================================");
        System.out.println(" SERVIDOR MEDICORE EN EJECUCIÓN: http://localhost:7070");
        System.out.println("==================================================");

        // ENDPOINT: Ruta raíz de verificación del estado del servidor
        app.get("/", ctx -> ctx.result("Servidor API de MediCore en línea"));

        // ENDPOINT: Autenticar usuario (Login por rol y credenciales)
        app.post("/api/login", ctx -> {
            Usuario loginReq = ctx.bodyAsClass(Usuario.class);
            Usuario user = usuarioDAO.autenticar(loginReq.getCorreo(), loginReq.getPassword());

            if (user != null) {
                ctx.status(200).json(user);
            } else {
                ctx.status(401).result("Credenciales incorrectas");
            }
        });

        // ENDPOINT: Buscar pacientes por nombre o expediente
        app.get("/api/pacientes", ctx -> {
            String q = ctx.queryParamAsClass("q", String.class).getOrDefault("");
            List<Paciente> lista = pacienteDAO.buscar(q);
            ctx.json(lista);
        });

        // ENDPOINT: Registrar un nuevo paciente en el sistema
        app.post("/api/pacientes", ctx -> {
            Paciente nuevoPaciente = ctx.bodyAsClass(Paciente.class);
            boolean guardado = pacienteDAO.registrar(nuevoPaciente);

            if (guardado) {
                ctx.status(201).result("Paciente registrado con éxito");
            } else {
                ctx.status(400).result("Error al registrar paciente");
            }
        });

        // ENDPOINT: Registrar Consulta Médica con signos vitales y diagnóstico (HU6)
        app.post("/api/consultas", ctx -> {
            Consulta nuevaConsulta = ctx.bodyAsClass(Consulta.class);
            boolean guardado = consultaDAO.registrar(nuevaConsulta);

            if (guardado) {
                ctx.status(201).result("Consulta guardada exitosamente");
            } else {
                ctx.status(400).result("Error al guardar la consulta");
            }
        });

        // ENDPOINT: Emitir Receta Electrónica enlazada a una consulta y farmacia (HU7)
        app.post("/api/recetas", ctx -> {
            Receta nuevaReceta = ctx.bodyAsClass(Receta.class);
            boolean guardado = recetaDAO.registrar(nuevaReceta);

            if (guardado) {
                ctx.status(201).result("Receta emitida y enviada a farmacia");
            } else {
                ctx.status(400).result("Error al emitir la receta");
            }
        });

        // ENDPOINT: Obtener el inventario completo de medicamentos y existencias (HU9)
        app.get("/api/inventario", ctx -> {
            List<Medicamento> inventario = medicamentoDAO.obtenerInventario();
            ctx.json(inventario);
        });

        // ENDPOINT: Registrar entrada de nuevos lotes y medicamentos a la farmacia (HU15)
        app.post("/api/inventario", ctx -> {
            Medicamento nuevoMed = ctx.bodyAsClass(Medicamento.class);
            boolean guardado = medicamentoDAO.registrarEntrada(nuevoMed);

            if (guardado) {
                ctx.status(201).result("Medicamento registrado exitosamente");
            } else {
                ctx.status(400).result("Error al registrar medicamento");
            }
        });

        // ENDPOINT: Registrar pagos (efectivo/tarjeta) y actualizar saldos de facturación (HU11)
        app.post("/api/pagos", ctx -> {
            Pago nuevoPago = ctx.bodyAsClass(Pago.class);
            boolean procesado = facturaDAO.registrarPago(nuevoPago);

            if (procesado) {
                ctx.status(201).result("Pago registrado y saldo actualizado exitosamente");
            } else {
                ctx.status(400).result("Error al procesar el pago");
            }
        });

        // ENDPOINT: Obtener la lista general de usuarios para poblar la tabla del panel de administración
        app.get("/api/usuarios", ctx -> {
            try {
                // Se utiliza la instancia 'usuarioDAO' para obtener todos los usuarios registrados en la base de datos
                List<Usuario> listaUsuarios = usuarioDAO.obtenerTodos();
                ctx.status(200).json(listaUsuarios);
            } catch (Exception e) {
                ctx.status(500).result("Error al obtener los usuarios: " + e.getMessage());
            }
        });

        // ENDPOINT: Actualizar un usuario existente
        app.put("/api/usuarios/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Usuario usuarioActualizado = ctx.bodyAsClass(Usuario.class);
            usuarioActualizado.setId(id);
            boolean actualizado = usuarioDAO.actualizar(usuarioActualizado);

            if (actualizado) {
                ctx.status(200).result("Usuario actualizado con éxito");
            } else {
                ctx.status(400).result("Error al actualizar el usuario");
            }
        });

        // ENDPOINT: Eliminar un usuario (Protegido por el Trigger de la BD)
        app.delete("/api/usuarios/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            try {
                boolean eliminado = usuarioDAO.eliminar(id);
                if (eliminado) {
                    ctx.status(200).result("Usuario eliminado con éxito");
                } else {
                    ctx.status(400).result("No se pudo eliminar el usuario");
                }
            } catch (Exception e) {
                // Captura el error lanzado por el Trigger si se intenta borrar al Admin Maestro
                ctx.status(400).result(e.getMessage());
            }
        });

        // ENDPOINT: Registrar un nuevo usuario desde el panel de administración
        app.post("/api/usuarios", ctx -> {
            Usuario nuevoUsuario = ctx.bodyAsClass(Usuario.class);
            boolean guardado = usuarioDAO.registrar(nuevoUsuario);

            if (guardado) {
                ctx.status(201).result("Usuario registrado exitosamente");
            } else {
                ctx.status(400).result("Error al registrar el usuario");
            }
        });

    }
}