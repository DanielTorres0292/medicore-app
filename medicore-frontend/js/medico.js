const API_URL = "http://localhost:7070/api";
let consultaIdActual = null;
let recetaItems = [];

// Manejo de pestañas
function switchTab(tabName) {
    document.getElementById('tab-consulta').style.display = tabName === 'consulta' ? 'block' : 'none';
    document.getElementById('tab-receta').style.display = tabName === 'receta' ? 'block' : 'none';
    document.getElementById('tab-historial').style.display = tabName === 'historial' ? 'block' : 'none';

    document.querySelectorAll('.sidebar nav button').forEach(btn => btn.classList.remove('active'));
    document.getElementById(`nav-${tabName}`).classList.add('active');

    if (tabName === 'historial') cargarHistorial();
}

// Guardar Consulta médica vía POST
async function guardarConsulta(e) {
    e.preventDefault();
    const payload = {
        expedientePaciente: "EXP-0012", // Estático por demo
        idMedico: 2,
        temperatura: parseFloat(document.getElementById('temp').value),
        presion: document.getElementById('presion').value,
        peso: parseFloat(document.getElementById('peso').value),
        pulso: parseInt(document.getElementById('pulso').value),
        diagnostico: document.getElementById('diagnostico').value,
        tratamiento: document.getElementById('tratamiento').value
    };

    try {
        const res = await fetch(`${API_URL}/consultas`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            alert("Consulta guardada con éxito en MySQL.");
            consultaIdActual = 1; // ID asignado
        } else {
            alert("Error al guardar la consulta.");
        }
    } catch (err) {
        alert("Error de conexión con el backend.");
    }
}

// Agregar medicamento a la tabla temporal de recetas
function agregarMedicamentoFila() {
    const select = document.getElementById('select-medicamento');
    const idMed = select.value;
    const nombreMed = select.options[select.selectedIndex].text;

    if (!idMed) return;

    recetaItems.push({
        idMedicamento: parseInt(idMed),
        nombre: nombreMed,
        dosis: "1 cada 8 horas",
        dias: 3,
        cantidadSolicitada: 1
    });

    renderTablaReceta();
    select.value = "";
}

function renderTablaReceta() {
    const tbody = document.getElementById('tabla-receta-body');
    tbody.innerHTML = recetaItems.map((item, index) => `
        <tr>
            <td>${item.nombre}</td>
            <td><input type="text" class="form-control" value="${item.dosis}" onchange="recetaItems[${index}].dosis = this.value"></td>
            <td><input type="number" class="form-control" value="${item.dias}" style="width: 80px;" onchange="recetaItems[${index}].dias = parseInt(this.value)"></td>
            <td><button class="btn btn-secondary" onclick="eliminarItemReceta(${index})">Eliminar</button></td>
        </tr>
    `).join('');
}

function eliminarItemReceta(index) {
    recetaItems.splice(index, 1);
    renderTablaReceta();
}

// Emitir Receta al Backend vía POST
async function emitirRecetaFinal() {
    if (recetaItems.length === 0) {
        alert("Agregue al menos un medicamento a la receta.");
        return;
    }

    const payload = {
        idConsulta: consultaIdActual || 1,
        detalles: recetaItems
    };

    try {
        const res = await fetch(`${API_URL}/recetas`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            alert("Receta emitida y enviada a farmacia.");
            recetaItems = [];
            renderTablaReceta();
            switchTab('consulta');
        } else {
            alert("Error al emitir la receta.");
        }
    } catch (err) {
        alert("Error al conectar con la API.");
    }
}