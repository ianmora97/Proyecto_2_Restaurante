/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.presentation;

import com.google.gson.Gson;

import com.progra.restaurante.data.Model;
import com.progra.restaurante.logic.Categoria;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 
 */
@WebServlet(name = "OrderService", urlPatterns = {"/api/restaurante/categorias/get"})
public class OrderService extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (request.getServletPath()) {

            case "/api/restaurante/categorias/get":
                this.doCategoriaGet(request, response);
                break;
        }
    }

    protected void doCategoriaGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            PrintWriter out = response.getWriter();

            ArrayList<Categoria> categorias = Model.instance().getCategories();
            response.setContentType("application/json; charset=UTF-8");
            out.write(gson.toJson(categorias));
            
            response.setStatus(200); // ok with content
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
