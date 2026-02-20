
document.getElementById("formLogin").addEventListener("submit", function(event) {
    event.preventDefault();

    // Obtenemos los valores del formulario
    const correo = document.getElementById("correo_usuario").value;
    const contrasena = document.getElementById("contrasena_usuario").value;

    // Creamos el objeto a enviar
    const usuario = {
        correoUsuario: correo,
        contrasenaUsuario: contrasena
    };

    // Llamamos al endpoint del backend
    fetch("http://localhost:8081/api/usuarios/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(usuario)
    })
            .then(async response => {
                const data = await response.json();
                if (!response.ok) {
                    document.getElementById("mensaje").textContent = data.mensaje;
                    return;
                }
                return data;
            })
            .then(data => {
               if(!data) return;
       
               console.log("JS ServiciosACotizar cargado versión nueva");
               localStorage.setItem("usuarioCorreo", data.correoUsuario);
               window.location.href = "ServiciosACotizar.html";
               
            })
    .catch(error => console.error("Error:", error));
});


