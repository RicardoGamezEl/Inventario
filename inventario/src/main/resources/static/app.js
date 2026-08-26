const tablaProductos = document.getElementById("tablaProductos");
const formulario = document.getElementById("productoForm");
const buscarProducto = document.getElementById("buscarProducto");

const sidebar = document.querySelector(".sidebar");
const modal = document.getElementById("modalEditar");
const btnAgregarProducto = document.getElementById("btnAgregarProducto");
const modalProductos = document.getElementById("agregarProducto");

let productos = [];
let productoEditadoId = null;

const API_URL = "http://localhost:8008/productos";

const selectCategoria = document.getElementById("categoria");
const API_CATEGORIAS = "http://localhost:8008/categorias";

btnAgregarProducto.addEventListener("click", () => {
    modalProductos.style.display = "flex";
    cerrarModal();
});

document.getElementById("btnMenu").addEventListener("click",()=>{
    sidebar.classList.toggle("abierto");
});
async function obtenerProductos() {

    const response = await fetch(API_URL);
    productos = await response.json();
    mostrarProductos(productos);
}

function mostrarProductos(lista){
    tablaProductos.innerHTML = "";
    lista.forEach(producto => {
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
buscarProducto.addEventListener("input", () => {
    const texto = buscarProducto.value.toLowerCase();

    const resultados = productos.filter(producto =>
        producto.name.toLowerCase().includes(texto)
    );

    mostrarProductos(resultados);
});

formulario.addEventListener("submit", async (e) => {

    e.preventDefault();

    const name = document.getElementById("nombre").value;
    const price = parseFloat(document.getElementById("precio").value);
    const stock = parseInt(document.getElementById("cantidad").value);
    const categoriaId = parseInt(document.getElementById("categoria").value);

    const url = productoEditadoId
        ? `${API_URL}/${productoEditadoId}`
        : API_URL;

    const method = productoEditadoId
        ? "PUT"
        : "POST";

    try{
        const response = await fetch(url, {

        method: method,

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            name,
            price,
            stock,
            categoriaId
        })

    });
    if(!response.ok){
        const errores = await response.json();
        const mensaje = Object.values(errores).join(" | ");
        mostrarToast(mensaje,"error");
        return;
    }

    mostrarToast(productoEditadoId ? "Producto actualizado correctamente" : "Producto agregado correctamente", "success");

    formulario.reset();
    productoEditadoId = null;
    await obtenerProductos();

}catch(error){
    console.error("Error", error);
    mostrarToast("No se pudo conectar con el servidor","error");
}

});

async function eliminarProducto(id) {

    const confirmar = confirm(
        "¿Desea eliminar este producto?"
    );

    if (!confirmar) return;

    try {

        const response = await fetch(`${API_URL}/${id}`, {
            method: "DELETE"
        });

        if (!response.ok) {
            mostrarToast(
                "No se pudo eliminar el producto",
                "error"
            );
            return;
        }

        await obtenerProductos();

        mostrarToast(
            "Producto eliminado correctamente",
            "success"
        );

    } catch (error) {

        console.error("Error:", error);

        mostrarToast(
            "No se pudo conectar con el servidor",
            "error"
        );
    }
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
async function guardarEdicion() {

    const name = 
        document.getElementById("editarNombre").value;

    const price = parseFloat(
        document.getElementById("editarPrecio").value)  ;
    const stock = parseInt(
        document.getElementById("editarCantidad").value);

    const categoriaId = parseInt(
        document.getElementById("editarCategoria").value);
    const response = await fetch(`${API_URL}/${productoEditadoId}`,
    {   
        method: "PUT",
        headers: {
            "Content-Type":"application/json"
        },
        body: JSON.stringify({
            name,
            price,
            stock,
            categoriaId
        })

    });
    if(!response.ok){
        mostrarToast("No se pudo actualizar el producto", "error");
        return;
    }

    cerrarModal("modalEditar")
    await obtenerProductos();
    productoEditadoId = null;
    mostrarToast("Producto actualizado correctamente", "success");
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

 mostrarToast("Productos", "success");
 obtenerProductos();
 obtenerCategorias();