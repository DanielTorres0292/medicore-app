const API_URL = "http://localhost:7070/api";

document.addEventListener('DOMContentLoaded', () => {
    cargarInventario();
});

// Cambiar de pestañas en Farmacia
function switchTab(tabName) {
    document.getElementById('tab-inventario').style.display = tabName === 'inventario' ? 'block' : 'none';
    document.getElementById('tab-entrada').style.display = tabName === 'entrada' ? 'block' : 'none';

    document.querySelectorAll('.sidebar nav button').forEach(btn => btn.classList.remove('active'));
    document.getElementById(`nav-${tabName}`).classList.add('active');

    if (tabName === 'inventario') {
        cargarInventario();
    }
}

// Obtener inventario desde el backend (HU9)
async function cargarInventario() {
    try {
        const res = await fetch(`${API_URL}/inventario`);
        if (res.ok) {
            const medicamentos = await res.json();
            renderTablaInventario(medicamentos);
        } else {
            console.error("Error al obtener el inventario.");
        }
    } catch (error) {
        console.error("Error de conexión:", error);
    }
}

// Renderizar la tabla aplicando insignias de estado (Stock bajo / Normal)
function renderTablaInventario(medicamentos) {
    const tbody = document.getElementById('tabla-inventario-body');
    
    tbody.innerHTML = medicamentos.map(m => {
        let badgeClass = "badge-normal";
        let badgeText = "Disponible";

        if (m.existencia <= m.stockMinimo) {
            badgeClass = "badge-bajo";
            badgeText = "Stock Bajo";
        }

        return `
            <tr>
                <td style="font-weight: 500;">${m.nombre}</td>
                <td>${m.existencia}</td>
                <td>${m.stockMinimo}</td>
                <td>${m.lote}</td>
                <td>${m.caducidad}</td>
                <td>$${m.precio.toFixed(2)}</td>
                <td><span class="badge ${badgeClass}">${badgeText}</span></td>
            </tr>
        `;
    }).join('');
}

// Registrar nuevo medicamento / lote (HU15)
async function registrarMedicamento(e) {
    e.preventDefault();

    const payload = {
        nombre: document.getElementById('nombre').value,
        existencia: parseInt(document.getElementById('existencia').value),
        stockMinimo: parseInt(document.getElementById('stockMinimo').value),
        lote: document.getElementById('lote').value,
        caducidad: document.getElementById('caducidad').value,
        precio: parseFloat(document.getElementById('precio').value)
    };

    try {
        const res = await fetch(`${API_URL}/inventario`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            alert("Medicamento registrado exitosamente en el inventario.");
            document.getElementById('form-medicamento').reset();
            switchTab('inventario');
        } else {
            alert("Error al registrar el medicamento.");
        }
    } catch (error) {
        alert("Error de conexión con el servidor.");
    }
}