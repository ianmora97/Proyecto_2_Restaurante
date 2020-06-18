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
import com.google.gson.Gson;

@Path("/categorias")
public class categorias {

    @POST
    @Produces({MediaType.APPLICATION_JSON, "text/json"})
    public String get() {
        try {
           Gson gson = new Gson();
           ArrayList<Categoria> categorias= Model.instance().getCategories();
            return gson.toJson(categorias);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

}
