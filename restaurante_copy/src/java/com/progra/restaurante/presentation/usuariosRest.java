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
@Path("/usuariosAdmin")
public class usuariosRest {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public ArrayList<Cliente> get() {
        try {
            return com.progra.restaurante.data.ClienteDao.getClientes();
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @POST
    @Path("/add")
    @Consumes({MediaType.APPLICATION_JSON})
    public void add(Cliente c) {
        try {
            com.progra.restaurante.data.UsuarioDao.insertUser(c.getUsuario());
            com.progra.restaurante.data.ClienteDao.insertClient(c);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public void update(Cliente cliente) {
        try {
            com.progra.restaurante.data.ClienteDao.editCliente(cliente);
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
    public ArrayList<OrdenRecu> buscar(String id) {
        try {
            return com.progra.restaurante.data.OrderDao.getOrders(Integer.parseInt(id));
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }
}
