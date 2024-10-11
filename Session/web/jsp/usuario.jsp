<%-- 
    Document   : usuario
    Created on : 10/10/2024, 01:59:32 AM
    Author     : carla
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%
    // Verificar si el usuario está logueado y si no mandarlo a login
    if (session == null || session.getAttribute("matricula") == null) {
        response.sendRedirect("login.jsp");
    }

    // Obtener el nombre de usuario de la sesión
    String matricula = (String) session.getAttribute("matricula");

    // Tiempo de sesión en segundos
    int tiempoSesionSegundos = session.getMaxInactiveInterval();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Página de Usuario</title>
        <script>
            var tiempoSesion = <%= tiempoSesionSegundos%>;

            function iniciarCronometro() {
                var cronometro = document.getElementById("cronometro");

                // Actualizar el cronómetro
                var intervalo = setInterval(function () {
                    if (tiempoSesion > 0) {
                        tiempoSesion--;
                        var minutos = Math.floor(tiempoSesion / 60);
                        var segundos = tiempoSesion % 60;

                        // Mostrar el tiempo en un string
                        cronometro.innerHTML = "Tiempo restante de sesión: " + minutos + " min " + (segundos < 10 ? "0" : "") + segundos + " seg";
                    } else {
                        clearInterval(intervalo);
                        cronometro.innerHTML = "La sesión ha expirado";
                        // Redirigir al login cuando el tiempo de sesion acabe
                        setTimeout(function () {
                            window.location.href = 'login.jsp';
                        }, 1000);
                    }
                }, 1000);
            }
        </script>
    </head>
    <body onload="iniciarCronometro()">
        <h1>Bienvenido, <%= matricula%>!</h1>
        <p>Esta es tu página de usuario.</p>
        <div id="cronometro">Tiempo restante de sesión: </div>
    </body>
</html>