const tablaProductos = document.getElementById("tablaProductos");
const formulario = document.getElementById("productoForm");
const modal = document.getElementById("modalEditar");

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
    const stock = document.getElementById("cantidad").value;
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
            stock: parseInt(stock),
            categoriaId: parseInt(categoriaId)
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

    document.getElementById("editarNombre").value =
        producto.name;

    document.getElementById("editarPrecio").value =
        producto.price;

    document.getElementById("editarCantidad").value =
        producto.stock;

    document.getElementById("editarCategoria").value =
        producto.categoriaId;

    productoEditadoId = id;

    modal.style.display = "flex";
}
function cerrarModal(){

    modal.style.display = "none";
    productoEditadoId = null;
}
async function guardarEdicion(){

    const name =
        document.getElementById("editarNombre").value;

    const price =
        document.getElementById("editarPrecio").value;

    const stock =
        document.getElementById("editarCantidad").value;

    const categoriaId =
        document.getElementById("editarCategoria").value;

    await fetch(`${API_URL}/${productoEditadoId}`, {

        method: "PUT",

        headers:{
            "Content-Type":"application/json"
        },

        body: JSON.stringify({
            name,
            price,
            stock: parseInt(stock),
            categoriaId: parseInt(categoriaId)
        })
    });

    productoEditadoId = null;

    cerrarModal();

    obtenerProductos();
}
async function obtenerCategorias(){

    const response = await fetch(API_CATEGORIAS);

    const categorias = await response.json();

    selectCategoria.innerHTML = '<option value="">Selecciona categoria</option>';
    const selectEditarCategoria = document.getElementById("editarCategoria");
    if (selectEditarCategoria) {
        selectEditarCategoria.innerHTML = '<option value="">Selecciona categoria</option>';
    }

    categorias.forEach(categoria => {
        const optionHTML = `
            <option value="${categoria.id}">
                ${categoria.name}
            </option>
        `;
        selectCategoria.innerHTML += optionHTML;
        if (selectEditarCategoria) {
            selectEditarCategoria.innerHTML += optionHTML;
        }
    });
}

obtenerProductos();
obtenerCategorias();