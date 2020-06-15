/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.presentation;

import com.google.gson.Gson;
import com.progra.restaurante.data.Model;
import com.progra.restaurante.logic.Adicional;
import com.progra.restaurante.logic.Categoria;
import com.progra.restaurante.logic.Platillo;
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
 * @author ianmo
 */
@WebServlet(name = "AdminPanel", urlPatterns = {"/api/restaurante/ingresarAdmin", "/api/restaurante/categoriasAdmin",
    "/api/restaurante/platosAdmin", "/api/restaurante/addCategoria",
    "/api/restaurante/deleteCate",
    "/api/restaurante/editCate",
    "/api/restaurante/fillAdicionalesAdmin",
    "/api/restaurante/deletePlatos",
    "/api/restaurante/findPlato"})
public class AdminPanel extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        switch (request.getServletPath()) {
            case "/api/restaurante/ingresarAdmin":
                this.doLoginAdmin(request, response);
                break;
            case "/api/restaurante/categoriasAdmin":
                this.doCategoriaGet(request, response);
                break;
            case "/api/restaurante/platosAdmin":
                this.doPlatosGet(request, response);
                break;
            case "/api/restaurante/addCategoria":
                this.doAddCategoria(request, response);
                break;
            case "/api/restaurante/deleteCate":
                this.doDeleteCategoria(request, response);
                break;
            case "/api/restaurante/deletePlatos":
                this.doDeletePlato(request, response);
                break;
            case "/api/restaurante/editCate":
                this.doEditCategoria(request, response);
                break;
            case "/api/restaurante/fillAdicionalesAdmin":
                this.dofillAdicionales(request, response);
                break;
            case "/api/restaurante/findPlato":
                this.doFindPlato(request, response);
                break;
        }
    }

    protected void doFindPlato(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            PrintWriter out = response.getWriter();
            BufferedReader reader = request.getReader();
            String id = reader.readLine();
            Platillo plato = com.progra.restaurante.data.DishesDao.findPlatillo(Integer.parseInt(id));
            response.setContentType("application/json; charset=UTF-8");

            out.write(gson.toJson(plato));

            response.setStatus(200); // ok with content
        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }
    protected void doDeletePlato(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            BufferedReader reader = request.getReader();
            ArrayList<String> platos = gson.fromJson(reader.readLine(), ArrayList.class);
            for (String plato : platos) {
                com.progra.restaurante.data.DishesDao.deletePlato(Integer.parseInt(plato));
            }
            response.setStatus(201);
        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }
    protected void dofillAdicionales(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            PrintWriter out = response.getWriter();

            ArrayList<Adicional> adicionales = com.progra.restaurante.data.AditionalsDao.listarAdicional();
            response.setContentType("application/json; charset=UTF-8");

            out.write(gson.toJson(adicionales));

            response.setStatus(200); // ok with content
        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }
    protected void doEditCategoria(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            BufferedReader reader = request.getReader();
            String nombreO = reader.readLine();
            String nombreN = reader.readLine();
            com.progra.restaurante.data.CategoriesDao.editCategoria(nombreO, nombreN);
            response.setStatus(201);
        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }

    protected void doDeleteCategoria(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            BufferedReader reader = request.getReader();
            ArrayList<String> opciones = gson.fromJson(reader.readLine(), ArrayList.class);
            for (String opcione : opciones) {
                com.progra.restaurante.data.CategoriesDao.deleteCategoria(Integer.parseInt(opcione));
            }
            response.setStatus(201);
        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }

    protected void doAddCategoria(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            BufferedReader reader = request.getReader();
            String nombre = reader.readLine();
            com.progra.restaurante.data.CategoriesDao.registrarCategoria(new Categoria(0, nombre));
            response.setStatus(201); // ok with content
        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }

    protected void doPlatosGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            PrintWriter out = response.getWriter();

            ArrayList<Platillo> platillos = com.progra.restaurante.data.DishesDao.listarPlatillos();
            response.setContentType("application/json; charset=UTF-8");

            out.write(gson.toJson(platillos));

            response.setStatus(200); // ok with content
        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }

    protected void doCategoriaGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            PrintWriter out = response.getWriter();

            ArrayList<Categoria> categorias = Model.instance().getCategoriesAdmin();
            response.setContentType("application/json; charset=UTF-8");

            out.write(gson.toJson(categorias));

            response.setStatus(200); // ok with content
        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }

    protected void doLoginAdmin(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            PrintWriter out = response.getWriter();

            String username = reader.readLine();
            String clave = reader.readLine();

            HttpSession session = request.getSession(true);
            Usuario real = com.progra.restaurante.data.Model.instance().getUsuario(username, clave);
            if(real.getRol() == 1){ //admin
                session.setAttribute("usuario", real);

                response.setContentType("application/json; charset=UTF-8");

                out.write(gson.toJson(real));

                response.setStatus(200);
            }
            else {
                response.setStatus(404);
            }
            

        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }

    protected void doLogoutAdmin(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(true);
            session.removeAttribute("usuario");
            session.invalidate();
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
