/*
 * UNIVERSIDAD NACIONAL DE COSTA RICA
 * Proyecto de programaci�n 4 
 * Integrantes:
 * David Aguilar Rojas
 * Ian Mora Rodriguez
 * I Ciclo lectivo 2020 
 */
package com.progra.restaurante.presentation;

/**
 *
 * @author david
 */
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

@Path("/reservation")
public class reservacionRest {

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void reservation(ArrayList<String> array) {
        try {
            String firstName = array.get(0);
            String lastName = array.get(1);
            String email = array.get(2);
            String telephone = array.get(3);
            String fecha = array.get(4);
            String cantidadPersonas = array.get(5);

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
                usuario.getCliente().setTelefono(telephone);
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
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @POST
    @Path("/list")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public ArrayList<Reservacion> listReservacion(Usuario usuario) {
        try {
            return Model.instance().getReservation(usuario);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public ArrayList<Reservacion> listReservaciones() {
        try {
            return com.progra.restaurante.data.reservationDao.getReservacionesAll();
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }
    @POST
    @Path("/delete")
    @Consumes({MediaType.APPLICATION_JSON})
    public void delete(ArrayList<String> reser) {
        try {
            for (String r : reser) {
                com.progra.restaurante.data.reservationDao.deleteReservacion(Integer.parseInt(r));
            }
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }
}
