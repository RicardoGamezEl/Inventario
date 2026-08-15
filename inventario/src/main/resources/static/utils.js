function mostrarToast(mensaje,tipo = "info"){
    const container = document.getElementById("toastContainer");
    const toast = document.createElement("div");

    toast.classList.add("toast", tipo);
    toast.textContent = mensaje;
    container.appendChild(toast);

    setTimeout(()=>{
        toast.remove();
    }, 3000);
}
function cerrarModal(idModal){
    const modal = document.getElementById(idModal);
    if(modal){
        modal.style.display = "none";
    }
}