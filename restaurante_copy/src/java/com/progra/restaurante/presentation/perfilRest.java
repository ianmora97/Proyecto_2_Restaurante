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
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.progra.restaurante.data.Model;
import com.progra.restaurante.logic.*;
import java.util.ArrayList;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.POST;

@Path("/perfil")
public class perfilRest {

    @POST
    @Path("/getDireccion")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Ubicacion direccion(String id) {
        try {
            return Model.instance().getUbicacionById(Integer.parseInt(id));
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @POST
    @Path("/edidAddre")
    @Consumes({MediaType.APPLICATION_JSON})
    public void editAddress(Ubicacion ubicacion) {
        try {
            Model.instance().updateDireccion(ubicacion);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @POST
    @Path("/ingresarDireccion")
    @Consumes({MediaType.APPLICATION_JSON})
    public void insertAddress(ArrayList<String> array) {
        try {
            Gson gson = new Gson();
            Usuario usuario = gson.fromJson(array.get(0), Usuario.class);
            Ubicacion ubicacion = gson.fromJson(array.get(1), Ubicacion.class);
            Model.instance().insertDireccion(ubicacion, usuario);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }
}
