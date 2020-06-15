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
import com.progra.restaurante.logic.Orden;
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
 * @author
 */
@WebServlet(name = "OrderService", urlPatterns = {"/api/restaurante/categorias/get", "/api/restaurante/AddToCart",
    "/api/restaurante/GetCartSession", "/api/restaurante/decreseQuant", "/api/restaurante/getDishInCart"})
public class OrderService extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (request.getServletPath()) {

            case "/api/restaurante/categorias/get":
                this.doCategoriaGet(request, response);
                break;
            case "/api/restaurante/AddToCart":
                this.doAddToCart(request, response);
                break;
            case "/api/restaurante/GetCartSession":
                this.doGetCart(request, response);
                break;
            case "/api/restaurante/decreseQuant":
                this.doDecreaseQuantDish(request, response);
                break;
            case "/api/restaurante/getDishInCart":
                this.doGetDishInCart(request, response);
                break;

        }
    }

    protected void doGetDishInCart(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        try {
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
//Se parsea en un int la posicion del platillo que se desea modificar. 
            int index = Integer.parseInt(reader.readLine());
//            Se trae la orden de la sesion
            Orden order = (Orden) session.getAttribute("order");
//Se trae el platillo con respecto al que se encuentra dentro de la peticion 
            String nombrePlatillo = order.getPlatilloseleccionadoCollection().get(index).getNombrePlatillo();

            Platillo platillo = com.progra.restaurante.data.Model.instance().findPlatillo(nombrePlatillo);

            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.write(gson.toJson(platillo));
            response.setStatus(200); // ok with content

        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }

//esta funcion no ha sido terminada por lo cual tiene problemitaas de programacion
    protected void doGetCart(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        try {
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();

            if (session.getAttribute("order") == null) {
                session.setAttribute("order", new Orden());
            } else {
                //Codigo para agregar el carrito a la orden y agregarselo a la orden.
                Orden order = (Orden) session.getAttribute("order");
                session.setAttribute("order", order);
                //Codigo para salida de la aplicacion
                response.setContentType("application/json; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.write(gson.toJson(order));
                response.setStatus(200); // ok with content
            }
        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }

    protected void doAddToCart(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();

            String nombrePlatillo = reader.readLine();
            String cantidadPlatillo = reader.readLine();
            ArrayList<String> opciones = gson.fromJson(reader.readLine(), ArrayList.class);

            Platillo platillo = com.progra.restaurante.data.Model.instance().getPlatilloToCart(opciones, nombrePlatillo, Integer.parseInt(cantidadPlatillo));

            HttpSession session = request.getSession(true);
            if (session.getAttribute("order") == null) {
                session.setAttribute("order", new Orden());
            } else {
                //Codigo para agregar el carrito a la orden y agregarselo a la orden.
                Orden order = (Orden) session.getAttribute("order");
                if (!this.searchAlreadyExists(platillo, order)) {
                    order.getPlatilloseleccionadoCollection().add(platillo);
                    order.calculateTotal();
                }
                session.setAttribute("order", order);
                //Codigo para salida de la aplicacion
                response.setContentType("application/json; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.write(gson.toJson(order));
                response.setStatus(200); // ok with content
            }

        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }

    protected boolean searchAlreadyExists(Platillo platillo, Orden order) {
        ArrayList<Platillo> platillosOrden = order.getPlatilloseleccionadoCollection();

        for (int i = 0; i < platillosOrden.size(); i++) {
            if (platillosOrden.get(i).equals(platillo)) {
                platillosOrden.get(i).setCantidad(platillosOrden.get(i).getCantidad() + platillo.getCantidad());
                order.calculateTotal();
                return true;
            }
        }
        return false;
    }

    protected void doDecreaseQuantDish(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            HttpSession session = request.getSession(true);

            Platillo platillo = gson.fromJson(reader.readLine(), Platillo.class);

            Orden order = (Orden) session.getAttribute("order");

            for (int i = 0; i < order.getPlatilloseleccionadoCollection().size(); i++) {
                if (order.getPlatilloseleccionadoCollection().get(i).equals(platillo)) {
                    order.getPlatilloseleccionadoCollection().get(i).setCantidad(order.getPlatilloseleccionadoCollection().get(i).getCantidad() - 1);
                    if (order.getPlatilloseleccionadoCollection().get(i).getCantidad() == 0) {
                        order.getPlatilloseleccionadoCollection().remove(i);
                    }
                    order.calculateTotal();
                    break;
                }
            }

            session.setAttribute("order", order);
            //Codigo para salida de la aplicacion
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.write(gson.toJson(order));
            response.setStatus(200); // ok with content

        } catch (Exception e) {
            response.setStatus(status(e));
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
