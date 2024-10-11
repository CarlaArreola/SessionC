/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import configuration.ConnectionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author carla
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/loginServlet"})
public class LoginServlet extends HttpServlet {

    Connection conn;
    PreparedStatement ps;
    Statement statement;
    ResultSet rs;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);//No crear una nueva sesion
        if (session != null) {
            //Invalidar la sesion actual
            session.invalidate();
        }
        //Redirigir al usuario a la pagina de login
        response.sendRedirect("/http_session/jsp/login.jsp");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Obtener la matricula y password del fromulario
        String matricula = request.getParameter("matricula");
        String password = request.getParameter("password");
        System.out.println(password);
        //Operaciones con la base de datos
        try {
            ConnectionBD conexion = new ConnectionBD();
            conn = conexion.getConnectionBD();
            //Consultar a la base de datos
            String sql = "Select password from autenticacion where matricula = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, matricula);

            rs = ps.executeQuery();
            if (rs.next()) {
                String hashPssword = rs.getString("password");
                System.out.println(hashPssword);
                if (BCrypt.checkpw(password, hashPssword)) {
                    //Crear una sesion
                    HttpSession session = request.getSession();
                    //Guardar el nombre del usuario en la sesion
                    session.setAttribute("matricula", matricula);
                    // Establecer la duración de la sesión
                    session.setMaxInactiveInterval(10); // 10 segundos
                    //Redirigar a la pagina de bienvenida
                    response.sendRedirect("/http_session/jsp/usuario.jsp");
                } else {
                    request.setAttribute("error", "Credenciales incorrrectas");
                    request.getRequestDispatcher("/http_session/jsp/login.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Usuario no encontrado");
                request.getRequestDispatcher("/http_session/jsp/login.jsp").forward(request, response);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
