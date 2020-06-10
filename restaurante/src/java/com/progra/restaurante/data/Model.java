/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.data;

import com.progra.restaurante.logic.*;
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
    public Usuario getUsuario(String u, String c) throws Exception {
        return com.progra.restaurante.data.UsuarioDao.getUsuario(u,c);
    }
    public ArrayList<Categoria> getCategories() throws Exception {
        return categorias;
    }
    public ArrayList<Categoria> getCategoriesAdmin() throws Exception {
        return com.progra.restaurante.data.CategoriesDao.getListaCategorias();
    }
    public ArrayList<Opcion> getOpciones() throws Exception {
        return opciones;
    }

    public ArrayList<Adicional> getAdicionales() throws Exception {
        return adicionales;
    }

    public Adicional getAdicionalesPorPlatillo(String nombrePlatillo) throws Exception {
        for (int i = 0; i < platillos.size(); i++) {
            String nombreSinEspacios = platillos.get(i).getNombrePlatillo().replace(" ", "");
            if (nombreSinEspacios.equals(nombrePlatillo)) {
                return adicionales.get(i);
            }
        }
        return null;
    }
    public ArrayList<Platillo> getPlatosEO() throws Exception {
        return com.progra.restaurante.data.DishesDao.listarPlatillos();
    }
    
    public ArrayList<Platillo> getPlatillos() throws Exception {
        return platillos;
    }

    public Platillo findPlatillo(String nombrePlatillo) throws Exception {
        for(Platillo platillo: platillos){
            if(platillo.getNombrePlatillo().equals(nombrePlatillo)){
                return platillo;
            }
        }
       return null;
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

    public Platillo getPlatilloToCart(ArrayList<String> opSeleccionadas, String nombrePlatillo, int Cantidad) throws Exception {
        Platillo platillo = new Platillo();

        for (int i = 0; i < platillos.size(); i++) {
            String nombresDePlatillo = platillos.get(i).getNombrePlatillo();
            nombresDePlatillo = nombresDePlatillo.replace(" ", "");
            nombrePlatillo = nombrePlatillo.replace(" ", "");
           
            if (nombresDePlatillo.equals(nombrePlatillo)) {
                platillo = platillos.get(i).copy(platillos.get(i));
                break;
            }
        }
        ArrayList<Opcion> opcionesList = new ArrayList<>();

        for (int i = 0; i < opciones.size(); i++) {
            for (int j = 0; j < opSeleccionadas.size(); j++) {
                if (opciones.get(i).getNombre().equals(opSeleccionadas.get(j))) {
                    opcionesList.add(opciones.get(i));
                }
            }
        }

        elminarOpciones(platillo.getAdicionalCollection());
        agregarOpcionesSelect(platillo.getAdicionalCollection(), opcionesList);
        eliminarAdiSinOpciones(platillo.getAdicionalCollection());
        platillo.setCantidad(Cantidad);
        return platillo;

    }

    private void elminarOpciones(ArrayList<Adicional> adicionalesSelect) {

        for (int i = 0; i < adicionalesSelect.size(); i++) {
            adicionalesSelect.get(i).setOpcionCollection(new ArrayList<Opcion>());
        }
    }

    private void agregarOpcionesSelect(ArrayList<Adicional> adicionalesSelect, ArrayList<Opcion> opcionesList) {
        for (int i = 0; i < opcionesList.size(); i++) {
            boolean estaEnPlatillo = false;
            for (int j = 0; j < adicionalesSelect.size() && estaEnPlatillo == false; j++) {
                if (opcionesList.get(i).getIdAdicional().getIdAdicional().equals(adicionalesSelect.get(j).getIdAdicional())) {
                    adicionalesSelect.get(j).getOpcionCollection().add(opcionesList.get(i));
                    opcionesList.remove(i);
                    i--;
                    estaEnPlatillo = true;
                }
            }
        }
    }

    private void eliminarAdiSinOpciones(ArrayList<Adicional> adicionalesSelect) {

        for (int i = 0; i < adicionalesSelect.size(); i++) {
            if (adicionalesSelect.get(i).getOpcionCollection().isEmpty()) {
                adicionalesSelect.remove(i);
                i--;
            }
        }
    }

}
