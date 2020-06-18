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
@Path("/ordenAdmin")
public class OrdenesAdmin {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public ArrayList<Categoria> get() {
        try {
            return Model.instance().getCategoriesAdmin();
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void add(Categoria categoria) {
        try {
            com.progra.restaurante.data.CategoriesDao.registrarCategoria(categoria);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public void update(ArrayList<String> categorias) {
        try {
            Model.instance().updateCategoria(categorias.get(0), categorias.get(1));
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @POST
    @Path("/delete")
    @Consumes({MediaType.APPLICATION_JSON})
    public void delete(ArrayList<String> categorias) {
        try {
            for (String categoria : categorias) {
                com.progra.restaurante.data.CategoriesDao.deleteCategoria(categoria);
            }
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }
    @POST
    @Path("/buscar")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public ArrayList<Categoria> buscar(Categoria categoria) {
        try {
            return com.progra.restaurante.data.CategoriesDao.findCategoriaByName(categoria.getNombre());
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }
}
