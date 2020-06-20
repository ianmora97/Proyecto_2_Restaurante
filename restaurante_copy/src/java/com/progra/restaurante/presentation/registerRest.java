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
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import com.progra.restaurante.data.Model;
import com.progra.restaurante.logic.*;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

@Path("/register")
public class registerRest {

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void register(Usuario usuario) {
        try {
            if (Model.instance().insertUser(usuario)) {
                Model.instance().insertCliente(usuario.getCliente());
            }
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public void update(Usuario usuario) {
        try {
            if (Model.instance().updateUser(usuario)) {
                Model.instance().updateClient(usuario.getCliente());
            }
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }
}
