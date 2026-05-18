const tablaProductos = document.getElementById("tablaProductos");
const formulario = document.getElementById("productoForm");

const API_URL = "http://localhost:8008/productos";

async function obtenerProductos() {

    const response = await fetch(API_URL);

    const productos = await response.json();

    tablaProductos.innerHTML = "";

    productos.forEach(producto => {

        tablaProductos.innerHTML += `
            <tr>
                <td>${producto.id}</td>
                <td>${producto.name}</td>
                <td>${producto.price}</td>
                <td>
                    <button onclick="eliminarProducto(${producto.id})">
                        Eliminar
                    </button>
                </td>
            </tr>
        `;
    });
}

formulario.addEventListener("submit", async (e) => {

    e.preventDefault();

    const name = document.getElementById("nombre").value;

    const price = document.getElementById("precio").value;

    await fetch(API_URL, {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            name,
            price
        })

    });

    formulario.reset();

    obtenerProductos();

});

async function eliminarProducto(id) {

    await fetch(`${API_URL}/${id}`, {
        method: "DELETE"
    });

    obtenerProductos();
}

obtenerProductos();