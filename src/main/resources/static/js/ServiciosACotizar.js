// =================================================
// PROTECCIÓN DE SESIÓN
// Verifica si hay un usuario logueado por correo
// =================================================
window.onload = function () {
    const correo = localStorage.getItem("usuarioCorreo");

    if (!correo) {
        alert("Debe iniciar sesión primero");
        window.location.replace("login.html");
    }
};


document.addEventListener("DOMContentLoaded", function () {

console.log("JS ServiciosACotizar cargado");
  const opcionCotizacion = document.getElementById("opcionCotizacion");
  const opcionVisita = document.getElementById("opcionVisita");

  const bloqueCotizacion = document.getElementById("bloqueCotizacion");
  const bloqueVisita = document.getElementById("bloqueVisita");

  opcionCotizacion.addEventListener("change", function () {
    if (this.checked) {
      bloqueCotizacion.style.display = "block";
      bloqueVisita.style.display = "none";
    }
  });

  opcionVisita.addEventListener("change", function () {
    if (this.checked) {
      bloqueVisita.style.display = "block";
      bloqueCotizacion.style.display = "none";
    }
  });
  
  const btnCotizar = document.getElementById("btnCotizar");

btnCotizar.addEventListener("click", () => {

  const checks = document.querySelectorAll(".servicioCheck");
  const serviciosSeleccionados = [];

  checks.forEach(check => {
    if (check.checked) {
      serviciosSeleccionados.push(Number(check.value));
    }
  });

  if (serviciosSeleccionados.length === 0) {
    alert("Debe seleccionar al menos un servicio");
    return;
  }
    const nombreProyecto = document.getElementById("nombre_proyecto_usuario").value.trim();

     if (nombreProyecto === "") {
       alert("Debe ingresar el nombre del proyecto");
       return;
     }
    const correo = localStorage.getItem("usuarioCorreo");

  // JSON a enviar al backend
  const data = {
    correoUsuario: correo,
    tipoSolicitud: "COTIZACION",
    nombreProyectoUsuario: nombreProyecto,
    servicios: serviciosSeleccionados
  };

  fetch("/serviciosACotizar/crear", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(data)
  })
  .then(response => {
    if (!response.ok) {
      return response.text().then(msg => { throw new Error(msg); }); //throw new Error("Error al guardar la solicitud");
    }
    return response.text();
  })
  .then(() => {
    // Redirigir SOLO si guardó bien
    window.location.href = "CotizacionBase.html";
  })
  .catch(error => {
    console.error(error);
    alert(error.message); //alert("No se pudo guardar la cotización");
  });
});

});

// CERRAR SESIÓN
function cerrarSesion(){
   localStorage.removeItem("usuarioCorreo");
   window.location.replace("/login.html");
}

