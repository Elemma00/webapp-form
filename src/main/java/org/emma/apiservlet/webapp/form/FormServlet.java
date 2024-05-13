package org.emma.apiservlet.webapp.form;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

//Siempre que se quiera acceder a un servlet se debe de poner la url del servlet
//junto con su anotacion @WebServlet a la clase
//Para ejemplificar, se pone la url /registro

/*
 * Este es un ejemplo basico de un servlet en donde tanto la vista como el controlador
 * (el servlet) estan embebidos en una sola clase.*/
@WebServlet("/registro")
public class FormServlet extends HttpServlet {
    /**
     * Esta clase es un servlet que recibe los datos de un formulario y los muestra en una página HTML.
     * Se encarga de recibir los datos de un formulario y mostrarlos en una página HTML.
     * La anotación @WebServlet indica que esta clase es un servlet y que se puede acceder a ella mediante la URL /registro.
     **/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // El método setContentType establece el tipo de contenido que se va a enviar en la respuesta
        resp.setContentType("text/html");

        // Con el método getParameter obtenemos el valor de un parámetro enviado en la petición
        // En este caso, se obtienen los valores de los campos del formulario
        // y por otro lado con el método getParameterValues obtenemos los valores de los campos que son arrays
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String pais = req.getParameter("pais");
        String[] lenguajes = req.getParameterValues("lenguajes");
        String[] roles = req.getParameterValues("roles");

        String idioma = req.getParameter("idioma");
        boolean habilitar = req.getParameter("habilitar") != null && req.getParameter("habilitar").equals("on");
        String secreto = req.getParameter("secreto");

        Map<String, String> errores = new HashMap<>();
        if (username == null || username.isBlank()) {
            errores.put("username", "El campo username es requerido");
        }
        if (password == null || password.isBlank()) {
            errores.put("password", "El campo password es requerido");
        }
        if (email == null || email.isBlank() || !email.contains("@")) {
            errores.put("email", "El campo email es requerido");
        }
        if (pais == null || pais.isBlank() || pais.equals("") || pais.equals(" ")) {
            errores.put("pais", "El campo pais es requerido");
        }
        if (lenguajes == null || lenguajes.length == 0) {
            errores.put("lenguajes", "El campo lenguajes es requerido");
        }
        if (roles == null || roles.length == 0) {
            errores.put("roles", "El campo roles es requerido");
        }
        if (idioma == null) {
            errores.put("idioma", "El campo idioma es requerido");
        }
        if (errores.isEmpty()) {
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("    <head>");
                out.println("       <meta charset=\"UTF-8\">");
                out.println("       <title>Resultado Form</title>");
                out.println("    </head>");
                out.println("    <body>");
                out.println("       <h1>Resultado Form</h1>");
                out.println("     <ul>");
                out.println("       <li>Username: " + username + "</li>");
                out.println("       <li>Password: " + password + "</li>");
                out.println("       <li>Email: " + email + "</li>");
                out.println("       <li>Pais: " + pais + "</li>");
                out.println("       <li>Lenguajes: <ul>");
                Arrays.asList(lenguajes).forEach(lenguaje -> out.println("       <li>" + lenguaje + "</li>"));
                out.println("       </ul></li>");

                out.println("       <li>Roles: <ul>");
                Arrays.asList(roles).forEach(rol -> out.println("       <li>" + rol + "</li>"));
                out.println("       </ul></li>");
                out.println("       <li>Idioma: " + idioma + "</li>");
                out.println("       <li>Habilitar: " + habilitar + "</li>");
                out.println("       <li>Secreto: " + secreto + "</li>");
                out.println("     </ul>");
                out.println("     </body>");
                out.println("</html>");
            }
        } else {
            // Si hay errores, se envían de vuelta al formulario
            // el método setAttribute establece un atributo en la petición
            // el atributo errores contiene la lista de errores
            req.setAttribute("errores", errores);
            // el método getServletContext obtiene el contexto del servlet
            // el método getRequestDispatcher obtiene un objeto RequestDispatcher que se utiliza para enviar la petición a otro recurso
            // el método forward envía la petición a otro recurso que en este caso es el formulario index.jsp

            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
             /*errores.forEach(error -> out.println("       <li>" + error + "</li>"));
                out.println("<p><a href=\"/webapp-form/index.jsp\">Volver al formulario</a></p>");*/
        }
    }
}
