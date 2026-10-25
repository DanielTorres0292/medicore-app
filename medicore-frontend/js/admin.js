const API_URL = "http://localhost:7070/api";
let editandoId = null; // Variable para saber si estamos editando o creando

document.addEventListener('DOMContentLoaded', () => {
    const usuarioSesion = JSON.parse(localStorage.getItem('usuarioSesion'));
    if (usuarioSesion && usuarioSesion.nombre) {
        document.getElementById('nombre-admin-header').textContent = `${usuarioSesion.nombre} · Administradora`;
    } else {
        window.location.href = 'index.html';
    }
    
    cargarUsuarios();
});

function switchTab(tabName) {
    document.getElementById('tab-usuarios').style.display = tabName === 'usuarios' ? 'block' : 'none';
    document.getElementById('tab-reportes').style.display = tabName === 'reportes' ? 'block' : 'none';

    document.querySelectorAll('.sidebar nav button').forEach(btn => btn.classList.remove('active'));
    document.getElementById(`nav-${tabName}`).classList.add('active');
}

function toggleFormulario() {
    const panel = document.getElementById('panel-nuevo-usuario');
    const isHidden = panel.style.display === 'none';
    panel.style.display = isHidden ? 'block' : 'none';
    
    if (isHidden) {
        // Modo Registro Limpio
        document.getElementById('titulo-form').textContent = 'Registrar personal';
        document.getElementById('form-usuario').reset();
        document.getElementById('campo-password').style.display = 'block';
        document.getElementById('password').required = true;
        editandoId = null;
    }
}

async function cargarUsuarios() {
    try {
        const res = await fetch(`${API_URL}/usuarios`);
        if (res.ok) {
            const usuarios = await res.json();
            renderTablaUsuarios(usuarios);
        } else {
            console.error("Error al obtener la lista de usuarios.");
        }
    } catch (error) {
        console.error("Error de conexión:", error);
    }
}

function renderTablaUsuarios(usuarios) {
    const tbody = document.getElementById('tabla-usuarios-body');
    tbody.innerHTML = usuarios.map(u => `
        <tr>
            <td style="font-weight: 500;">${u.nombre}</td>
            <td style="color: var(--text-muted);">${u.rol}</td>
            <td><span class="badge badge-normal">Activo</span></td>
            <td>
                <button class="btn btn-secondary" style="padding: 4px 10px; font-size: 12px; margin-right: 6px;" onclick='prepararEdicion(${JSON.stringify(u)})'>Editar</button>
                <button class="btn" style="padding: 4px 10px; font-size: 12px; background-color: var(--badge-danger-bg); color: var(--badge-danger-text); border: none;" onclick="eliminarUsuario(${u.id})">Eliminar</button>
            </td>
        </tr>
    `).join('');
}

// Cargar datos en el formulario para Editar
function prepararEdicion(usuario) {
    editandoId = usuario.id;
    document.getElementById('titulo-form').textContent = 'Editar usuario';
    document.getElementById('nombre').value = usuario.nombre;
    document.getElementById('correo').value = usuario.correo;
    document.getElementById('rol').value = usuario.rol;
    
    // Ocultar contraseña al editar ya que no es obligatorio cambiarla
    document.getElementById('campo-password').style.display = 'none';
    document.getElementById('password').required = false;

    document.getElementById('panel-nuevo-usuario').style.display = 'block';
}

// Guardar cambios (POST para crear, PUT para actualizar)
async function guardarUsuario(e) {
    e.preventDefault();
    
    const payload = {
        nombre: document.getElementById('nombre').value,
        correo: document.getElementById('correo').value,
        rol: document.getElementById('rol').value
    };

    let url = `${API_URL}/usuarios`;
    let method = 'POST';

    if (editandoId) {
        url = `${API_URL}/usuarios/${editandoId}`;
        method = 'PUT';
    } else {
        payload.password = document.getElementById('password').value;
    }

    try {
        const res = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            alert(editandoId ? "Usuario actualizado exitosamente." : "Usuario registrado exitosamente.");
            toggleFormulario();
            cargarUsuarios();
        } else {
            const errorMsg = await res.text();
            alert("Error: " + errorMsg);
        }
    } catch (err) {
        alert("Error de conexión con la API.");
    }
}

// Eliminar usuario con confirmación
async function eliminarUsuario(id) {
    if (!confirm("¿Estás seguro de que deseas eliminar este usuario?")) return;

    try {
        const res = await fetch(`${API_URL}/usuarios/${id}`, {
            method: 'DELETE'
        });

        if (res.ok) {
            alert("Usuario eliminado correctamente.");
            cargarUsuarios();
        } else {
            const errorMsg = await res.text();
            alert("No se pudo eliminar: " + errorMsg);
        }
    } catch (err) {
        alert("Error de conexión con el servidor.");
    }
}

function cerrarSesion() {
    localStorage.removeItem('usuarioSesion');
    window.location.href = 'index.html';
}