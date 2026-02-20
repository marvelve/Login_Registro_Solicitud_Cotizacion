document.getElementById("formRegistro").addEventListener("submit", function(event) {
    event.preventDefault();

    const usuario = {
        nombreUsuario: document.getElementById("nombre_usuario").value,
        correoUsuario: document.getElementById("correo_usuario").value,
        contrasenaUsuario: document.getElementById("contrasena_usuario").value,
        celularUsuario: document.getElementById("celular_usuario").value
       // , rol: { idRol: 1 } // Rol "Cliente"
    };

     fetch("http://localhost:8081/api/usuarios/guardar", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(usuario)
    })
    .then(async response => {
        const msg = document.getElementById("mensaje");

        if (!response.ok) {
            const errorText = await response.text();
            msg.textContent = errorText; // ← aquí se mostrará "El correo ya está registrado"
            msg.style.color = "red";
            throw new Error(errorText);
        }

        return response.json();
    })
    .then(data => {
        const msg = document.getElementById("mensaje");
        msg.textContent = "Registro exitoso, redirigiendo al login...";
        msg.style.color = "green";

        setTimeout(() => {
            window.location.href = "login.html";
        }, 2000);
    })
    .catch(error => console.error(error));
});