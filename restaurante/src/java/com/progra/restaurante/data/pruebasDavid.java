/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.data;

import com.progra.restaurante.logic.Usuario;
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
            //            ArrayList<String> opSeleccionadas = new ArrayList<>();
            //            opSeleccionadas.add("Salsa Ranch");
            //            opSeleccionadas.add("Salsa Tomate");
            //
            //            Platillo platillo = com.progra.restaurante.data.Model.instance().getPlatilloToCart(opSeleccionadas, "Casado de pollo", 1); //            for (int i = 0; i < platillos.size(); i++) {
            //
            //            System.out.print(platillo.getNombrePlatillo() + ": ");
            //            System.out.print(platillo.getIdCategoria().getNombre() + ":\n");
            //
            //            for (int j = 0; j < platillo.getAdicionalCollection().size(); j++) {
            //                System.out.print(platillo.getAdicionalCollection().get(j).getNombre() + '\n');
            //                for (int x = 0; x < platillo.getAdicionalCollection().get(j).getOpcionCollection().size(); x++) {
            //                    System.out.print(platillo.getAdicionalCollection().get(j).getOpcionCollection().get(x).getNombre());
            //                }
            //            }
//
//            String fecha = "19-06-2020 19:55";
//            SimpleDateFormat diaMesAnio = new SimpleDateFormat("dd-MM-yyyy HH:mm");
//            Date sameDate = diaMesAnio.parse(fecha);
//
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(sameDate);
                Usuario usuario = com.progra.restaurante.data.Model.instance().getUsuario("pulplix", "123");

            System.out.println(usuario.getCliente().getNombre());

        } catch (Exception ex) {
            Logger.getLogger(pruebasDavid.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
