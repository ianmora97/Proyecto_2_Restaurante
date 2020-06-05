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

            ArrayList<Opcion> opciones = Model.instance().getOpciones();
            ArrayList<Adicional> adocionales = Model.instance().getAdicionales();
            ArrayList<Platillo> platillos = com.progra.restaurante.data.DishesDao.listarPlatillosPorCategoria(8);
            ArrayList<Categoria> categorias = com.progra.restaurante.data.CategoriesDao.listarCategoria();

//             for (int i = 0; i < categorias.size(); i++) {
//                System.out.print(categorias.get(i).getNombre()+ ": ");
//                System.out.print(categorias.get(i).getPlatilloCollection().size()+ ":\n");
//             }
//
//            for (int i = 0; i < adocionales.size(); i++) {
//                System.out.print(adocionales.get(i).getNombre() + ":\n");
//                for (int j = 0; j < adocionales.get(i).getOpcionCollection().size(); j++) {
//                    System.out.print(adocionales.get(i).getOpcionCollection().get(j).getNombre()  + '\n');
//                }
//            }
            for (int i = 0; i < platillos.size(); i++) {
                System.out.print(platillos.get(i).getNombrePlatillo() + ": ");
                System.out.print(platillos.get(i).getIdCategoria().getNombre()+ ":\n");

                for (int j = 0; j < platillos.get(i).getAdicionalCollection().size(); j++) {
                    System.out.print(platillos.get(i).getAdicionalCollection().get(j).getNombre() + '\n');
                    for (int x = 0; x < platillos.get(i).getAdicionalCollection().get(j).getOpcionCollection().size(); x++) {
                        System.out.print(platillos.get(i).getAdicionalCollection().get(j).getOpcionCollection().get(x).getNombre());
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(pruebasDavid.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
