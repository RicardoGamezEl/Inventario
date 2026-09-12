const tablaProductos = document.getElementById("tablaProductos");
const formulario = document.getElementById("productoForm");
const buscarProducto = document.getElementById("buscarProducto");

const sidebar = document.querySelector(".sidebar");
const modal = document.getElementById("modalEditar");
const btnAgregarProducto = document.getElementById("btnAgregarProducto");
const modalProductos = document.getElementById("agregarProducto");
const filtroCategoria = document.getElementById("filtroCategoria");

let productos = [];
let categorias = [];
let productoEditadoId = null;

const API_URL = "http://localhost:8008/productos";

const selectCategoria = document.getElementById("categoria");
const API_CATEGORIAS = "http://localhost:8008/categorias";

function actualizarDashboard(){
    const totalProductos = productos.length;
    const stockTotal = productos.reduce((total, producto) => total + producto.stock, 0);
    const valorInventario = productos.reduce((total, producto) => total + (producto.price * producto.stock), 0);
    const productosStockBajo = productos.filter(producto => producto.stock <= 5);
    const listaStockBajo = document.getElementById("listaStockBajo");

    listaStockBajo.innerHTML = "";

    if(productosStockBajo.length === 0){
        listaStockBajo.innerHTML = `
        <p class='sin-stock-bajo'>
        No hay productos con stock bajo
        </p>
        `;
    }
    else{
        productosStockBajo.forEach(producto => {
            listaStockBajo.innerHTML += `
            <div class="producto-stock-bajo">
                <span>${producto.name}</span>
                <span>Stock: ${producto.stock}</span>
            </div>
            `;
        });
    }

    document.getElementById("totalProductos").textContent = totalProductos;
    document.getElementById("stockTotal").textContent = stockTotal;
    document.getElementById("totalCategorias").textContent = categorias.length;
    document.getElementById("valorInventario").textContent = formatearMoneda(valorInventario);
}

btnAgregarProducto.addEventListener("click", () => {
    modalProductos.style.display = "flex";
});

document.getElementById("btnMenu").addEventListener("click",()=>{
    sidebar.classList.toggle("abierto");
});
async function obtenerProductos() {
    try{
        const response = await fetch(API_URL);
        
        if(!response.ok){
            mostrarToast("No se pudo obtener los productos", "error");
            return;
        }
        productos = await response.json();
        mostrarProductos(productos);
        actualizarDashboard();
    } catch(error){
        console.error("Error:", error);
        mostrarToast("No se pudo conectar con el servidor", "error");
    }
}

function mostrarProductos(lista){
    tablaProductos.innerHTML = "";

    if(lista.length === 0){
        tablaProductos.innerHTML = `
            <tr>
                <td colspan="5" class="sin-productos">
                    No se encontraron productos
                </td>
            </tr>
        `;
        return;
    }

    lista.forEach(producto => {
        tablaProductos.innerHTML += `
            <tr>
                <td>${producto.stock}</td>
                <td>${producto.name}</td>
                <td>${formatearMoneda(producto.price)}</td>
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

function filtrarProductos(){
    const texto = buscarProducto.value.toLowerCase();
    const categoriaId = filtroCategoria.value;

    const resultados = productos.filter(producto =>{
        const coincideNombre = producto.name.toLowerCase().includes(texto);
        
        const coincideCategoria = categoriaId === "" || producto.categoriaId == categoriaId;

        return coincideNombre && coincideCategoria;
    });
    mostrarProductos(resultados);
}

buscarProducto.addEventListener(
    "input",
    filtrarProductos
);

filtroCategoria.addEventListener(
    "change",
    filtrarProductos
);

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

    mostrarToast(productoEditadoId ? "Producto actualizado correctamente" : 
        "Producto agregado correctamente", "success");

    formulario.reset();
    productoEditadoId = null;
    await obtenerProductos();
    cerrarModal("agregarProducto");

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
    try{
        const response = await fetch(`${API_URL}/${id}`);
        if(!response.ok){
            mostrarToast("No se pudo obtener el producto", "error");
            return;
        }

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
    }catch(error){
        console.error("Error:", error);
        mostrarToast("No se pudo conectar con el servidor", "error");
    }
}
async function guardarEdicion() {

    const name = 
        document.getElementById("editarNombre").value;

    const price = parseFloat(
        document.getElementById("editarPrecio").value);
    const stock = parseInt(
        document.getElementById("editarCantidad").value);
    const categoriaId = parseInt(
        document.getElementById("editarCategoria").value);
    try{
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
    }catch(error){
        console.error("Error", error);
        mostrarToast("No se pudo conectar con el servidor", "error");
    } 
}

async function obtenerCategorias(){
    try{
    const response = await fetch(API_CATEGORIAS);
        if(!response.ok){
            mostrarToast("No se pudo obtener las categorias", "error");
            return;
        }
    categorias = await response.json();

    filtroCategoria.innerHTML = '<option value = "">Todas las categorías</option>';

    const selectEditarCategoria = document.getElementById("editarCategoria");
    
    if (selectEditarCategoria) {
        selectEditarCategoria.innerHTML = '<option value="">Selecciona categoria</option>';
    }

    selectCategoria.innerHTML = '<option value="">Selecciona categoria</option>';

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
        
        filtroCategoria.innerHTML += optionHTML;
    });
    } catch(error){
        console.error("Error:", error);
        mostrarToast("No se pudo conectar con el servidor", "error");
    }

    actualizarDashboard();
}


 obtenerProductos();
 obtenerCategorias();