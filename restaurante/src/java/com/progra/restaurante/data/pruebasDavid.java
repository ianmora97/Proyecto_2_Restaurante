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
public class pruebasDavid {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {

//            ArrayList<Opcion> opciones = Model.instance().getOpciones();
//            ArrayList<Adicional> adocionales = Model.instance().getAdicionales();
//            ArrayList<Platillo> platillos = com.progra.restaurante.data.DishesDao.listarPlatillosPorCategoria(8);
//            ArrayList<Categoria> categorias = com.progra.restaurante.data.CategoriesDao.listarCategoria();
//
            //                System.out.print(platillos.get(i).getNombrePlatillo() + ": ");
            //                System.out.print(platillos.get(i).getIdCategoria().getNombre()+ ":\n");
            //
            //                for (int j = 0; j < platillos.get(i).getAdicionalCollection().size(); j++) {
            //                    System.out.print(platillos.get(i).getAdicionalCollection().get(j).getNombre() + '\n');
            //                    for (int x = 0; x < platillos.get(i).getAdicionalCollection().get(j).getOpcionCollection().size(); x++) {
            //                        System.out.print(platillos.get(i).getAdicionalCollection().get(j).getOpcionCollection().get(x).getNombre());
            //                    }
            //                }
            //            }
            
            ArrayList<String> opSeleccionadas = new ArrayList<>();
            opSeleccionadas.add("Salsa Ranch");
            opSeleccionadas.add("Salsa Tomate");

            Platillo platillo = com.progra.restaurante.data.Model.instance().getPlatilloToCart(opSeleccionadas, "Casado de pollo", 1); //            for (int i = 0; i < platillos.size(); i++) {

            System.out.print(platillo.getNombrePlatillo() + ": ");
            System.out.print(platillo.getIdCategoria().getNombre() + ":\n");

            for (int j = 0; j < platillo.getAdicionalCollection().size(); j++) {
                System.out.print(platillo.getAdicionalCollection().get(j).getNombre() + '\n');
                for (int x = 0; x < platillo.getAdicionalCollection().get(j).getOpcionCollection().size(); x++) {
                    System.out.print(platillo.getAdicionalCollection().get(j).getOpcionCollection().get(x).getNombre());
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(pruebasDavid.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
