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
import com.progra.restaurante.logic.Categoria;
import com.progra.restaurante.logic.Platillo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ianmo
 */
@WebServlet(name = "Admin", urlPatterns = {"/api/restaurante/sendEdit",
"/api/restaurante/ingresarPlatos"})
public class Servlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        switch (request.getServletPath()) {
            case "/api/restaurante/sendEdit":
                this.doEditPlatillo(request, response);
                break;
            case "/api/restaurante/ingresarPlatos":
                this.doIngresarPlatillo(request, response);
                break;
        }
    }
    protected void doIngresarPlatillo(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            BufferedReader reader = request.getReader();
            
            String nombre = reader.readLine();
            String precio = reader.readLine();
            String descripcion = reader.readLine();
            String categoria = reader.readLine();
            Platillo plato = new Platillo(0, nombre, descripcion, Double.parseDouble(precio));
            Categoria cat = com.progra.restaurante.data.CategoriesDao.findCategoria(Integer.parseInt(categoria));
            plato.setIdCategoria(cat);
            com.progra.restaurante.data.DishesDao.registrarPlatillo(plato);
            response.setStatus(201);
        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }
    protected void doEditPlatillo(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            BufferedReader reader = request.getReader();
            
            String nombre = reader.readLine();
            String precio = reader.readLine();
            String descripcion = reader.readLine();
            String categoria = reader.readLine();
            String id = reader.readLine();
            com.progra.restaurante.data.DishesDao.editPlatillo(id, nombre, precio, descripcion, categoria);
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
