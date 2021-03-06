/*
 * UNIVERSIDAD NACIONAL DE COSTA RICA
 * Proyecto de programaci�n 4 
 * Integrantes:
 * David Aguilar Rojas
 * Ian Mora Rodriguez
 * I Ciclo lectivo 2020 
 */
package com.progra.restaurante.data;

import com.progra.restaurante.logic.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class OrderDao {

    public static boolean registerOrder(Orden order) throws Exception {

        String SQL = "insert into orden (usuario_correo,id_metodo_pago,id_ubicacion,fecha_entrega,estatus,tipo_entrega,asap,total) "
                + "values (?,?,?,?,?,?,?,?);";

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, order.getUsuarioCorreo().getUsuarioCorreo());
            st.setInt(2, order.getMetodoPago().getIdMetodoPago());
            st.setInt(3, order.getIdUbicacion().getIdUbicacion());
            st.setDate(4, new java.sql.Date(order.getFechaEntrega().getTime()));
            st.setString(5, order.getEstatus());
            st.setInt(6, order.getTipoEntrega());
            st.setInt(7, order.getAsap());
            st.setDouble(8, order.getTotal());
            st.executeUpdate();
            String SQL2 = "select max(id_orden) id_orden from orden;";
            PreparedStatement st2 = con.prepareStatement(SQL2);
            st2.executeQuery();
            ResultSet resultado = st2.getResultSet();
            int id_orden = -1;
            if (resultado.next()) {
                id_orden = resultado.getInt("id_orden");
            }
            registerSelectedDishes(order.getPlatilloseleccionadoCollection(), id_orden, con);

            con.close();
            st.close();

            return st.executeUpdate() != 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    private static boolean registerSelectedDishes(ArrayList<Platillo> platillos, int idOrder, Connection con) throws Exception {

        try {

            for (Platillo platillo : platillos) {
                String SQL = "insert into platilloSeleccionado (id_platillo,id_orden,cantidad) "
                        + "values (?,?,?);";
                PreparedStatement st = con.prepareStatement(SQL);
                st.setInt(1, platillo.getIdPlatillo());
                st.setInt(2, idOrder);
                st.setInt(3, platillo.getCantidad());
                st.executeUpdate();
                st.close();
                String SQL2 = "select max(id_platilloSeleccionado) from platilloSeleccionado;";
                PreparedStatement st2 = con.prepareStatement(SQL2);
                st2.executeQuery();
                ResultSet resultado = st2.getResultSet();
                int id_platillo = -1;
                if (resultado.next()) {
                    id_platillo = resultado.getInt(1);
                }
                registerSelectedAditionals(platillo.getAdicionalCollection(), id_platillo, con);

            }
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    private static boolean registerSelectedAditionals(ArrayList<Adicional> aditionals, int idPlatillo, Connection con) throws Exception {

        try {
            for (Adicional adicional : aditionals) {
                String SQL = "insert into adicional_seleccionada (id_platilloSeleccionado,id_adicional) "
                        + "values (?,?);";
                PreparedStatement st = con.prepareStatement(SQL);
                st.setInt(1, idPlatillo);
                st.setInt(2, adicional.getIdAdicional());
                st.executeUpdate();
                st.close();
                String SQL2 = "select max(id_adicional_seleccionada) from adicional_seleccionada;";
                PreparedStatement st2 = con.prepareStatement(SQL2);
                st2.executeQuery();
                ResultSet resultado = st2.getResultSet();
                int id_adicional_seleccionada = -1;
                if (resultado.next()) {
                    id_adicional_seleccionada = resultado.getInt(1);
                }
                registerSelectedOptions(adicional.getOpcionCollection(), id_adicional_seleccionada, con);
            }

            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    private static boolean registerSelectedOptions(ArrayList<Opcion> opciones, int id_adicional_seleccionada, Connection con) throws Exception {

        try {
            for (Opcion opcion : opciones) {
                String SQL = "insert into opcionesSeleccionadas (id_adicional_seleccionada,id_opcion) "
                        + "values (?,?);";
                PreparedStatement st = con.prepareStatement(SQL);
                st.setInt(1, id_adicional_seleccionada);
                st.setInt(2, opcion.getIdOpcion());
                st.executeUpdate();
                st.close();
            }

            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
}
