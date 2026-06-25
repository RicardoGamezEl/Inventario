const API_URL = "http://localhost:8008/categorias";
const tablaCategorias = document.getElementById("tablaCategorias");
const formulario = document.getElementById("categoriaForm");
let categoriaEditadaId = null;

async function obtenerCategorias() {
    const response = await fetch(API_URL);
    const categorias = await response.json();
    
    tablaCategorias.innerHTML = "";

    categorias.forEach(categoria =>{
        tablaCategorias.innerHTML += `
            <tr>
                <td>${categoria.id}</td>
                <td>${categoria.name}</td>

                <td>
                    <button onclick ="editarCategoria(${categoria.id})">Editar</button>
                </td>
                <td>
                    <button onclick ="eliminarCategoria(${categoria.id})">Eliminar</button>
                </td>
            </tr>
        `;
    });
}
formulario.addEventListener("submit", async(e)=>{
    e.preventDefault();
    const nombre = document.getElementById("nombreCategoria").value;
    await fetch(API_URL,{
        method: "POST",
        headers:{
            "Content-Type": "aplication/json"
        },
        body: JSON.stringify({
            name: nombre
        })
    });
    formulario.reset();
    obtenerCategorias();
});

async function editarCategoria(id) {
    const response = await fetch(`${API_CATEGORIAS}/${id}`);

    const categoria = await response.json();

    document.getElementById("editarNombreCategoria").value = categoria.name;
    categoriaEditadaId = id;

    document.getElementById("modalCategoria").style.display = "flex";   
}

async function guardarEdicionCategoria() {
    const nombre = document.getElementById("editarNombreCategoria").value;

    await fetch(`${API_CATEGORIAS}/${categoriaEditadaId}`,{
        method: "PUT",
        headers:{
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: nombre
        })
    });
    cerrarModalCategoria();
    obtenerCategorias();
    
}

function cerrarModalCategoria(){
    document.getElementById("modalCategoria").style.display = "none";
}

async function eliminarCategoria(id) {
    const confirmar = confirm("¿Deseas eliminar esta categoria?");

    if(!confirmar) return;

    try{
        await fetch(`${API_CATEGORIAS}/${id}`,
            {
                method: "DELETE"
            }
        );
        obtenerCategorias();
    }
    catch(error){
        alert(
            "No puedes eliminar una categoria que tiene productos asociados."
        );
    }
}