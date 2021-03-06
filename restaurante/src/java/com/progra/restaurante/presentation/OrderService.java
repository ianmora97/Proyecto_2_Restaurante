/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.presentation;

import com.google.gson.Gson;

import com.progra.restaurante.data.Model;
import com.progra.restaurante.logic.Categoria;
import com.progra.restaurante.logic.Cliente;
import com.progra.restaurante.logic.MetodosPago;
import com.progra.restaurante.logic.Orden;
import com.progra.restaurante.logic.Platillo;
import com.progra.restaurante.logic.Ubicacion;
import com.progra.restaurante.logic.Usuario;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    "/api/restaurante/GetCartSession", "/api/restaurante/decreseQuant", "/api/restaurante/saveOrder",
    "/api/restaurante/getDishInCart", "/api/restaurante/updateCart", "/api/restaurante/getPaymentMethods"})
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
            case "/api/restaurante/updateCart":
                this.doUpdateOptions(request, response);
                break;
            case "/api/restaurante/getPaymentMethods":
                this.doGetPaymentMethods(request, response);
                break;
            case "/api/restaurante/saveOrder":
                this.doSaveOrder(request, response);
                break;
        }
    }

    protected void doSaveOrder(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(true);
            Orden order = (Orden) session.getAttribute("order");
            Gson gson = new Gson();

            if (order.getPlatilloseleccionadoCollection().size() <= 0) {
                throw new Exception("Error");
            }
            BufferedReader reader = request.getReader();
            String firstName = reader.readLine();
            String lastName = reader.readLine();
            String email = reader.readLine();
            String telephone = reader.readLine();
            String OpSelected = reader.readLine();

            String tipo_entrega = reader.readLine();
            String fecha = reader.readLine();
            ArrayList<String> postCode = gson.fromJson(reader.readLine(), ArrayList.class);

            Usuario real = com.progra.restaurante.data.Model.instance().getUsuarioByEmail(email);
            if (real == null) {
                real = new Usuario();
                real.setUsuarioCorreo(email);
                real.setUsername(firstName);
                real.setContrasena(lastName);
                real.setCliente(new Cliente());
                real.getCliente().setUsuarioCorreo(email);
                real.getCliente().setNombre(firstName);
                real.getCliente().setApellidos(lastName);
                real.getCliente().setTelefono(lastName);
                real.setRol(1);

                if (com.progra.restaurante.data.Model.instance().insertUser(real)) {
                    com.progra.restaurante.data.Model.instance().insertCliente(real.getCliente());
                }
            }
            Ubicacion ubicacion = null;
            if (postCode.size() == 1) {
                ubicacion = com.progra.restaurante.data.Model.instance().getUbicacionById(Integer.parseInt(postCode.get(0)));
            } else {
                ubicacion = new Ubicacion(1, postCode.get(0), postCode.get(1), postCode.get(2));
                ubicacion.setCodigoPostal(Integer.parseInt(postCode.get(3)));
                if (!com.progra.restaurante.data.Model.instance().insertDireccion(ubicacion, real)) {
                    throw new Exception();
                }
                ubicacion = com.progra.restaurante.data.Model.instance().getUbicionByAddress(ubicacion.getDireccion());
            }
            order.setIdUbicacion(ubicacion);
            MetodosPago metodo = com.progra.restaurante.data.Model.instance().getMetodoPago(OpSelected);
            order.setUsuarioCorreo(real);
            order.setMetodoPago(metodo);
            order.setTipoEntrega(Integer.parseInt(tipo_entrega));

            if (fecha.equals("ASAP")) {
                order.setAsap(1);
            } else {
                order.setAsap(0);
                SimpleDateFormat formatDMA = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                Date sameDate = formatDMA.parse(fecha);
                Calendar cal = Calendar.getInstance();
                cal.setTime(sameDate);
                order.setFechaEntrega(sameDate);
            }

            if (com.progra.restaurante.data.Model.instance().insertOrder(order)) {
                session.removeAttribute("order");
            } else {
                throw new Exception();
            }
            response.setStatus(201);

        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }

    protected void doGetPaymentMethods(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        try {
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();

            ArrayList<MetodosPago> metodos = com.progra.restaurante.data.Model.instance().getMetodosPago();
            //Salida de la aplicacion
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.write(gson.toJson(metodos));
            response.setStatus(201); // ok with content

        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }

    protected void doUpdateOptions(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            BufferedReader reader = request.getReader();

            String tipo_entrega = reader.readLine();
            String fecha = reader.readLine();

            HttpSession session = request.getSession(true);

            Orden order = (Orden) session.getAttribute("order");
            order.setTipoEntrega(Integer.parseInt(tipo_entrega));
            if (order.getPlatilloseleccionadoCollection().size() <= 0) {
                throw new Exception("Error");
            }
            if (fecha.equals("ASAP")) {
                order.setAsap(1);
            } else {
                order.setAsap(0);
                SimpleDateFormat formatDMA = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                Date sameDate = formatDMA.parse(fecha);

                Calendar cal = Calendar.getInstance();
                cal.setTime(sameDate);
                order.setFechaEntrega(sameDate);
            }
            response.setStatus(201);

        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }

    protected void doGetDishInCart(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        try {
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
//Se parsea en un int la posicion del platillo que se desea modificar. 
            Platillo platilloAux = gson.fromJson(reader.readLine(), Platillo.class);
//            Se trae la orden de la sesion
            if (session.getAttribute("order") == null) {
                session.setAttribute("order", new Orden());
            }
            Orden order = (Orden) session.getAttribute("order");
//Se trae el platillo con respecto al que se encuentra dentro de la peticion 

            Platillo platillo = com.progra.restaurante.data.Model.instance().findPlatillo(platilloAux.getNombrePlatillo());

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
            }
            Orden order = (Orden) session.getAttribute("order");

            //Salida de la aplicacion
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.write(gson.toJson(order));
            response.setStatus(200); // ok with content

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
            Platillo platilloInOrder = gson.fromJson(reader.readLine(), Platillo.class);

            Platillo platillo = com.progra.restaurante.data.Model.instance().getPlatilloToCart(opciones, nombrePlatillo, Integer.parseInt(cantidadPlatillo));

            HttpSession session = request.getSession(true);
            if (session.getAttribute("order") == null) {
                session.setAttribute("order", new Orden());
            }
            //Codigo para agregar el carrito a la orden y agregarselo a la orden.
            Orden order = (Orden) session.getAttribute("order");
            if (platilloInOrder != null && !platillo.equals(platilloInOrder)) {
                ArrayList<Platillo> platillosOrden = order.getPlatilloseleccionadoCollection();
                for (int i = 0; i < platillosOrden.size(); i++) {
                    if (platillosOrden.get(i).equals(platilloInOrder)) {
                        order.getPlatilloseleccionadoCollection().get(i).setCantidad(order.getPlatilloseleccionadoCollection().get(i).getCantidad() - platillo.getCantidad());
                        this.removeDishByQuantity(order.getPlatilloseleccionadoCollection(), i);
                    }
                }
            }
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
                    this.removeDishByQuantity(order.getPlatilloseleccionadoCollection(), i);
                    order.calculateTotal();
                    break;
                }
            }

            session.setAttribute("order", order);
            //Codigo para salida de la aplicacion
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.write(gson.toJson(order));
            response.setStatus(200); // ok withcontent

        } catch (Exception e) {
            response.setStatus(status(e));
        }
    }

    protected void removeDishByQuantity(ArrayList<Platillo> platillos, int pos) {
        if (platillos.get(pos).getCantidad() <= 0) {
            platillos.remove(pos);
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
