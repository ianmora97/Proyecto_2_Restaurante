/*
 * UNIVERSIDAD NACIONAL DE COSTA RICA
 * Proyecto de programaci�n 4 
 * Integrantes:
 * David Aguilar Rojas
 * Ian Mora Rodriguez
 * I Ciclo lectivo 2020 
 */
package com.progra.restaurante.presentation;

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
import java.util.ArrayList;
import javax.ws.rs.DELETE;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.POST;

/**
 *
 * @author ianmo
 */
@Path("/platosAdmin")
public class platosAdmin {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public ArrayList<Platillo> get() {
        try {
            return Model.instance().getPlatillos();
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void add(Platillo platillo) {
        try {
            com.progra.restaurante.data.DishesDao.registrarPlatillo(platillo);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public void update(Platillo platillo) {
        try {
            com.progra.restaurante.data.DishesDao.editPlatillo(platillo);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @POST
    @Path("/delete")
    @Consumes({MediaType.APPLICATION_JSON})
    public void delete(ArrayList<String> platos) {
        try {
            for (String plato : platos) {
                com.progra.restaurante.data.DishesDao.deletePlato(Integer.parseInt(plato));
            }
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }
    @POST
    @Path("/findPlatos")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Platillo buscar(String id) {
        try {
            return com.progra.restaurante.data.DishesDao.findPlatillo(Integer.parseInt(id));
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }
}
