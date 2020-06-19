/*
 * UNIVERSIDAD NACIONAL DE COSTA RICA
 * Proyecto de programaci�n 4 
 * Integrantes:
 * David Aguilar Rojas
 * Ian Mora Rodriguez
 * I Ciclo lectivo 2020 
 */
package com.progra.restaurante.presentation;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.progra.restaurante.logic.*;
import java.util.ArrayList;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.POST;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author david
 */
@Path("/order")
public class orderRest {

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String addToCart(ArrayList<String> configuracion) {
        try {
            Gson gson = new Gson();

            String nombrePlatillo = configuracion.get(0);
            String cantidadPlatillo = configuracion.get(1);
            ArrayList<String> opciones = gson.fromJson(configuracion.get(2), ArrayList.class);
            Platillo platilloInOrder = gson.fromJson(configuracion.get(3), Platillo.class);
            Platillo platillo = com.progra.restaurante.data.Model.instance().getPlatilloToCart(opciones, nombrePlatillo, Integer.parseInt(cantidadPlatillo));
            Orden order = gson.fromJson(configuracion.get(4), Orden.class);

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
            return gson.toJson(order);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    protected void removeDishByQuantity(ArrayList<Platillo> platillos, int pos) {
        if (platillos.get(pos).getCantidad() <= 0) {
            platillos.remove(pos);
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

    @POST
    @Path("/decreseQuant")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String decreseQuant(ArrayList<String> array) {
        try {
            Gson gson = new Gson();
            Orden order = gson.fromJson(array.get(0), Orden.class);
            Platillo platillo = gson.fromJson(array.get(1), Platillo.class);

            for (int i = 0; i < order.getPlatilloseleccionadoCollection().size(); i++) {
                if (order.getPlatilloseleccionadoCollection().get(i).equals(platillo)) {
                    order.getPlatilloseleccionadoCollection().get(i).setCantidad(order.getPlatilloseleccionadoCollection().get(i).getCantidad() - 1);
                    this.removeDishByQuantity(order.getPlatilloseleccionadoCollection(), i);
                    order.calculateTotal();
                    break;
                }
            }
            return gson.toJson(order);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @POST
    @Path("/getPlatillo")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String getDishInCart(ArrayList<String> array) {
        try {
            Gson gson = new Gson();
            Orden order = gson.fromJson(array.get(0), Orden.class);
            Platillo platilloAux = gson.fromJson(array.get(1), Platillo.class);

            Platillo platillo = com.progra.restaurante.data.Model.instance().findPlatillo(platilloAux.getNombrePlatillo());
            return gson.toJson(platillo);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @POST
    @Path("/checkOut")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String checkOut(ArrayList<String> array) {
        try {
            Gson gson = new Gson();
            Orden order = gson.fromJson(array.get(0), Orden.class);
            String seleccionada = gson.fromJson(array.get(1), String.class);
            String fecha = gson.fromJson(array.get(2), String.class);

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
            return gson.toJson(order);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @GET
    @Path("/getOrder")
    @Produces({MediaType.APPLICATION_JSON})
    public String createOrder() {
        Gson gson = new Gson();
        Orden orden = new Orden();

        return gson.toJson(orden);
    }
}
