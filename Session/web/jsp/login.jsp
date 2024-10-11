<%-- 
    Document   : login
    Created on : 10/10/2024, 01:59:23 AM
    Author     : carla
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Iniciar seesion</title>
    </head>
    <body>
        <h2>Iniciar Sesion</h2>
        <!<!-- En action se coloca el nombre del servlet LoginServlet.java -->
        <form action ="${pageContext.request.contextPath}/loginServlet" method="post">
            <label for="username">Nombre del usuario:</label>
            <input type="text" id="matricula" name="matricula" required><br>

            <label for="password">Contraseña:</label>
            <input type="password" id="password" name="password" required><br>

            <button type="submit">Iniciar sesion</button>
        </form>
        <!<!-- Mostrar mensajes de error si existen -->
        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
        <p style = "color:red;" > <%= error%> </p>
        <%
            }
        %>
    </body>
</html>