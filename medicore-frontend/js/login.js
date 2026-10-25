const API_URL = "http://localhost:7070/api";

document.getElementById('login-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const correo = document.getElementById('correo').value;
    const password = document.getElementById('password').value;
    const errorBox = document.getElementById('error-box');

    try {
        const res = await fetch(`${API_URL}/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ correo, password })
        });

        if (res.ok) {
            const usuario = await res.json();
            
            // Guardar sesión en el navegador para usarla en las otras pantallas
            localStorage.setItem('usuarioSesion', JSON.stringify(usuario));

            // Redirección basada en el rol de MySQL
            switch (usuario.rol) {
                case 'Médico':
                    window.location.href = 'medico.html';
                    break;
                case 'Farmacia':
                    window.location.href = 'farmacia.html';
                    break;
                case 'Facturación':
                    window.location.href = 'facturacion.html';
                    break;
                case 'Administrador':
                    window.location.href = 'admin.html';
                    break;
                case 'Recepción':
                    window.location.href = 'recepcion.html';
                    break;
                default:
                    mostrarError("Rol no reconocido por el sistema.");
            }
        } else {
            mostrarError("Credenciales incorrectas. Verifique su correo y contraseña.");
        }
    } catch (error) {
        mostrarError("Error al conectar con el servidor. Verifique que Javalin esté en ejecución.");
    }
});

function mostrarError(mensaje) {
    const errorBox = document.getElementById('error-box');
    errorBox.textContent = mensaje;
    errorBox.style.display = 'block';
}