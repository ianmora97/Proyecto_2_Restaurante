/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.data;

import com.progra.restaurante.logic.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class Model {

    private static Model uniqueInstance;

    public static Model instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Model();
        }
        return uniqueInstance;
    }

    private ArrayList<Categoria> categorias;

    private ArrayList<Opcion> opciones;

    private ArrayList<Adicional> adicionales;

    private ArrayList<Platillo> platillos;

    private Model() {
        try {
            categorias = com.progra.restaurante.data.CategoriesDao.listarCategoria();
            opciones = com.progra.restaurante.data.OptionDao.listarOpcion();
            adicionales = com.progra.restaurante.data.AditionalsDao.listarAdicional();
            platillos = com.progra.restaurante.data.DishesDao.listarPlatillos();

        } catch (Exception ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Categoria> getCategories() throws Exception {
        return categorias;
    }

    public ArrayList<Opcion> getOpciones() throws Exception {
        return opciones;
    }

    public ArrayList<Adicional> getAdicionales() throws Exception {
        return adicionales;
    }

    public ArrayList<Platillo> getPlatillos() throws Exception {
        return platillos;
    }

    public Platillo findPlatillo(int id_platillo) throws Exception {
        return com.progra.restaurante.data.DishesDao.findPlatillo(id_platillo);
    }

    public Categoria findCategoria(int id_categoria) throws Exception {
        return com.progra.restaurante.data.CategoriesDao.findCategoria(id_categoria);
    }

    public Opcion findOpciones(int id_opcion) throws Exception {
        return com.progra.restaurante.data.OptionDao.findOpcion(id_opcion);
    }

    public Adicional findAdicional(int id_adicional) throws Exception {
        return com.progra.restaurante.data.AditionalsDao.findAdicional(id_adicional);
    }

}
