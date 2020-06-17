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
import com.progra.restaurante.logic.Mesa;
import com.progra.restaurante.logic.Reservacion;
import com.progra.restaurante.logic.Usuario;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
@WebServlet(name = "reservationService", urlPatterns = {"/api/restaurante/reservation", "/api/restaurante/reservation/list"})
public class reservationService extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        switch (request.getServletPath()) {
            case "/api/restaurante/reservation":
                this.doReservationAction(request, response);
                break;
            case "/api/restaurante/reservation/list":
                this.doGetListReservation(request, response);
                break;
        }

    }

    protected void doGetListReservation(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {

            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            HttpSession session = request.getSession(true);
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            ArrayList<Reservacion> reservaciones = com.progra.restaurante.data.Model.instance().getReservation(usuario);
            
            //Salida de la aplicacion
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.write(gson.toJson(reservaciones));
            response.setStatus(200); // ok with content

        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }

    protected void doReservationAction(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {

            BufferedReader reader = request.getReader();
            Gson gson = new Gson();

            String firstName = reader.readLine();
            String lastName = reader.readLine();
            String email = reader.readLine();
            String telephone = reader.readLine();
            String fecha = reader.readLine();
            String cantidadPersonas = reader.readLine();

            SimpleDateFormat formatDMA = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date date = formatDMA.parse(fecha);

            Usuario usuario = com.progra.restaurante.data.Model.instance().getUsuarioByEmail(email);

            if (usuario == null) {
                usuario = new Usuario();
                usuario.setUsuarioCorreo(email);
                usuario.setUsername(firstName);
                usuario.setContrasena(lastName);
                usuario.setCliente(new Cliente());
                usuario.getCliente().setUsuarioCorreo(email);
                usuario.getCliente().setNombre(firstName);
                usuario.getCliente().setApellidos(lastName);
                usuario.getCliente().setTelefono(lastName);
                usuario.setRol(1);

                if (com.progra.restaurante.data.Model.instance().insertUser(usuario)) {
                    com.progra.restaurante.data.Model.instance().insertCliente(usuario.getCliente());
                }
            }
            Mesa mesa = com.progra.restaurante.data.Model.instance().getMesaLibre(Integer.parseInt(cantidadPersonas));
            if (mesa != null && mesa.getIdMesa() != 0) {
                com.progra.restaurante.data.Model.instance().insertReservation(usuario, mesa, date, Integer.parseInt(cantidadPersonas));
            } else {
                throw new Exception();
            }

            response.setStatus(201);

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
