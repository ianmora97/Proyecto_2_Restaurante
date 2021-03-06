/*
 * UNIVERSIDAD NACIONAL DE COSTA RICA
 * Proyecto de programaci�n 4 
 * Integrantes:
 * David Aguilar Rojas
 * Ian Mora Rodriguez
 * I Ciclo lectivo 2020 
 */
package com.progra.restaurante.presentation;

import com.google.gson.Gson;
import com.progra.restaurante.logic.Cliente;
import com.progra.restaurante.logic.Ubicacion;
import com.progra.restaurante.logic.Usuario;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author david
 */
@WebServlet(name = "loginSession", urlPatterns = {"/api/restaurante/login", "/api/restaurante/register",
    "/api/restaurante/logOut", "/api/restaurante/getUser", "/api/restaurante/sendEditUbicacion",
    "/api/restaurante/getAddressBook","/api/restaurante/getDireccion","/api/restaurante/ingresarDireccion"})

public class loginSession extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (request.getServletPath()) {
            case "/api/restaurante/register":
                this.doRegisterAction(request, response);
                break;
            case "/api/restaurante/login":
                this.doLoginAction(request, response);
                break;
            case "/api/restaurante/logOut":
                this.doLogoutCliente(request, response);
                break;
            case "/api/restaurante/getUser":
                this.doUserGet(request, response);
                break;
            case "/api/restaurante/getAddressBook":
                this.doGetAddressBook(request, response);
                break;
            case "/api/restaurante/getDireccion":
                this.doGetDireccion(request, response);
                break;
            case "/api/restaurante/sendEditUbicacion":
                this.doSendEditDireccion(request, response);
                break;
            case "/api/restaurante/ingresarDireccion":
                this.doIngresarDireccion(request, response);
                break;
        }
    }
    protected void doIngresarDireccion(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            BufferedReader reader = request.getReader();
            
            String provincia = reader.readLine();
            String canton = reader.readLine();
            String codigo = reader.readLine();
            String direccion = reader.readLine();
            String id = reader.readLine();

            Ubicacion u = new Ubicacion(0, direccion, provincia, canton);
            u.setDireccion(direccion);
            u.setCodigoPostal(Integer.parseInt(codigo));
            HttpSession session = request.getSession(true);
            com.progra.restaurante.data.Model.instance().insertDireccion(u,(Usuario)session.getAttribute("usuario"));
            
            response.setStatus(201);
        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }
    protected void doSendEditDireccion(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            BufferedReader reader = request.getReader();
            
            String provincia = reader.readLine();
            String canton = reader.readLine();
            String codigo = reader.readLine();
            String direccion = reader.readLine();
            String id = reader.readLine();
            
            if(!com.progra.restaurante.data.Model.instance().updateDireccion(provincia, canton, codigo, direccion, id)){
                throw new Exception();
            }
            response.setContentType("application/json; charset=UTF-8");

            response.setStatus(200);

        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }
    protected void doGetDireccion(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {

            BufferedReader reader = request.getReader();
            String id = reader.readLine();
            Gson gson = new Gson();

            Ubicacion u = com.progra.restaurante.data.Model.instance().getUbicacionById(Integer.parseInt(id));
            
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.write(gson.toJson(u));
            response.setStatus(201); // ok with content

        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }
    protected void doGetAddressBook(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {

            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            HttpSession session = request.getSession(true);
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            ArrayList<Ubicacion> ubicaciones = com.progra.restaurante.data.Model.instance().getUbicionesUsario(usuario);
           
            //Salida de la aplicacion
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.write(gson.toJson(ubicaciones));
            response.setStatus(201); // ok with content

        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }
    
    protected void doRegisterAction(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            BufferedReader reader = request.getReader();

            String firstName = reader.readLine();
            String lastName = reader.readLine();
            String email = reader.readLine();
            String password = reader.readLine();
            String confirmPass = reader.readLine();
            String telephone = reader.readLine();

            Usuario usuario = new Usuario(email, firstName, password, 0);
            if (com.progra.restaurante.data.Model.instance().insertUser(usuario) == true) {
                Cliente cliente = new Cliente(email, firstName, lastName, telephone);
                if (com.progra.restaurante.data.Model.instance().insertCliente(cliente) == false) {
                    throw new Exception("Error");
                }
            }

            response.setStatus(201);

        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }

    protected void doLogoutCliente(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(true);
            session.removeAttribute("usuario");
            session.removeAttribute("order");
            session.invalidate();
            response.setStatus(201);
        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }

    protected void doLoginAction(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();

            String username = reader.readLine();
            String clave = reader.readLine();

            HttpSession session = request.getSession(true);
            Usuario real = com.progra.restaurante.data.Model.instance().getUsuario(username, clave);
            if (real == null) {
                throw new Exception("Usuario no encontrado");
            }
            session.setAttribute("usuario", real);

            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.write(gson.toJson(real));
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }

    protected void doUserGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            Usuario real = null;
            HttpSession session = request.getSession(true);
            real = (Usuario) session.getAttribute("usuario");

            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.write(gson.toJson(real));

            response.setStatus(200);

        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }

    protected int status(Exception e) {
        if (e.getMessage().startsWith("404")) {
            return 404;
        }
        if (e.getMessage().startsWith("406")) {
            return 406;
        }
        return 400;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        processRequest(request, response);
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
