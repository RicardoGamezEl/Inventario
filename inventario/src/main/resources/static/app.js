const tablaProductos = document.getElementById("tablaProductos");
const formulario = document.getElementById("productoForm");

let productoEditadoId = null;

const API_URL = "http://localhost:8008/productos";

const selectCategoria = document.getElementById("categoria");
const API_CATEGORIAS = "http://localhost:8008/categorias";

async function obtenerProductos() {

    const response = await fetch(API_URL);
    const productos = await response.json();

    tablaProductos.innerHTML = "";

    productos.forEach(producto => {

        tablaProductos.innerHTML += `
            <tr>
                <td>${producto.stock}</td>
                <td>${producto.name}</td>
                <td>${producto.price}</td>
                <td>${producto.categoria}</td>
                <td>
                    <button onclick="editarProducto(${producto.id})">
                        Editar
                    </button>

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
    const categoriaId = document.getElementById("categoria").value;

    const url = productoEditadoId
        ? `${API_URL}/${productoEditadoId}`
        : API_URL;

    const method = productoEditadoId
        ? "PUT"
        : "POST";

    await fetch(url, {

        method: method,

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            name,
            price,
            categoriaId
        })

    });

    formulario.reset();

    productoEditadoId = null;

    obtenerProductos();
});

async function eliminarProducto(id) {

    await fetch(`${API_URL}/${id}`, {
        method: "DELETE"
    });

    obtenerProductos();
}

async function editarProducto(id) {

    const response = await fetch(`${API_URL}/${id}`);

    const producto = await response.json();

    document.getElementById("nombre").value =
        producto.name;

    document.getElementById("precio").value =
        producto.price;

    document.getElementById("categoria").value =
        producto.categoriaId;

    productoEditadoId = id;
}

async function obtenerCategorias(){

    const response = await fetch(API_CATEGORIAS);

    const categorias = await response.json();

    selectCategoria.innerHTML = "";

    categorias.forEach(categoria => {

        selectCategoria.innerHTML += `
            <option value="${categoria.id}">
                ${categoria.name}
            </option>
        `;
    });
}

obtenerProductos();
obtenerCategorias();