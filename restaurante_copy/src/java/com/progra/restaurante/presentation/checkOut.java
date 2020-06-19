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
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.progra.restaurante.data.Model;
import com.progra.restaurante.logic.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.ws.rs.DELETE;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.POST;

@Path("/chekOut")
public class checkOut {

    @GET
    @Path("/paymentMethods")
    @Produces({MediaType.APPLICATION_JSON})
    public ArrayList<MetodosPago> doGetPaymentMethods() {
        try {
            return Model.instance().getMetodosPago();
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void chekOut(ArrayList<String> array) {
        try {

            Gson gson = new Gson();
            Orden order = gson.fromJson(array.get(0), Orden.class);
            String firstName = gson.fromJson(array.get(1), String.class);
            String lastName = gson.fromJson(array.get(2), String.class);
            String email = gson.fromJson(array.get(3), String.class);
            String telephone = gson.fromJson(array.get(4), String.class);
            String OpSelected = array.get(5);
            String tipo_entrega = gson.fromJson(array.get(6), String.class);
            String fecha = gson.fromJson(array.get(7), String.class);
            ArrayList<String> postCode = gson.fromJson(array.get(8), ArrayList.class);

            if (order.getPlatilloseleccionadoCollection().size() <= 0) {
                throw new Exception("Error");
            }

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
                ubicacion = new Ubicacion(1, postCode.get(0), postCode.get(1), postCode.get(2),0);
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

            if (!com.progra.restaurante.data.Model.instance().insertOrder(order)) {
                throw new Exception();
            }
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @POST
    @Path("/addressBook")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public ArrayList<Ubicacion> doGetAddressBook(Usuario usuario) {
        try {
            return com.progra.restaurante.data.Model.instance().getUbicionesUsario(usuario);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }
}
