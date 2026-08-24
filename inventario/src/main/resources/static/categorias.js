const API_URL = "http://localhost:8008/categorias";

const tablaCategorias = document.getElementById("tablaCategorias");
const formulario = document.getElementById("categoriaForm");

const sidebar = document.querySelector(".sidebar");
const btnNvaCategoria = document.getElementById("btnNuevaCategoria");
const modalCategoria = document.getElementById("nuevaCategoria")
let categoriaEditadaId = null;

btnNvaCategoria.addEventListener("click",()=>{
    modalCategoria.style.display = "flex";
})
async function obtenerCategorias() {
    const response = await fetch(API_URL);
    const categorias = await response.json();
    tablaCategorias.innerHTML = "";
    categorias.forEach(categoria => {
        tablaCategorias.innerHTML += `
            <tr>
                <td>${categoria.id}</td>
                <td>${categoria.name}</td>
                <td>
                    <div class="acciones">
                        <button onclick="editarCategoria(${categoria.id})">
                            Editar
                        </button>
                        <button onclick="eliminarCategoria(${categoria.id})">
                            Eliminar
                        </button>
                    </div>
                </td>
            </tr>
        `;
    });
}
document.getElementById("btnMenu").addEventListener("click",()=>{
    sidebar.classList.toggle("abierto");
})

formulario.addEventListener("submit", async (e) => {

    e.preventDefault();

    const nombre = 
        document.getElementById("nombreCategoria").value;

    const response = await fetch(API_URL, {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            name: nombre
        })

    });
    if(!response.ok){
        mostrarToast("No se pudo crear la categoria", "error");
        return;
    }

    formulario.reset();
    await obtenerCategorias();
    mostrarToast("Categoria creada correctamente", "success");

});

async function editarCategoria(id) {

    const response = await fetch(`${API_URL}/${id}`);

    const categoria = await response.json();

    document.getElementById("editarNombreCategoria").value =
        categoria.name;

    categoriaEditadaId = id;

    document.getElementById("modalCategoria").style.display = "flex";

}

async function guardarEdicionCategoria() {

    const nombre =
        document.getElementById("editarNombreCategoria").value;

    try{
        const response = await fetch(`${API_URL}/${categoriaEditadaId}`, {

        method: "PUT",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            name: nombre
        })

    });
    if(!response.ok){
        const error = await response.json();
        const mensaje = Object.values(error).join(" | ");
        mostrarToast(mensaje,"error");
        return;
    }
        mostrarToast(
            "Categoría actualizada correctamente",
            "success"
        );
    cerrarModal("modalCategoria");
    await obtenerCategorias();
    categoriaEditadaId = null;
}catch(error){
    console.error("error", error);
    mostrarToast("No se pudo conectar al servidor", "error");
}
}

async function eliminarCategoria(id) {

    const confirmar = confirm("¿Deseas eliminar esta categoría?");

    if (!confirmar){
        return;
    }

    try{
        const respose = await fetch(
            `${API_URL}/${id}`,
            {
                method:"DELETE"
            }
        );
        if(!respose.ok){
            mostrarToast("No puedes eliminar una categoria que tiene productos asociados","error");
            return;
        }
        await obtenerCategorias();
        mostrarToast("Categoria eliminada correctamente", "success");
    }catch(error){
        console.error("Error", error);
        mostrarToast("No se pudo conectar al servidor", "error");
    }

}

mostrarToast("Caregorias","success");
obtenerCategorias();