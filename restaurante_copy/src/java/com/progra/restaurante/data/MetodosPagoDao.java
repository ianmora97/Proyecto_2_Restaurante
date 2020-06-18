/*
 * UNIVERSIDAD NACIONAL DE COSTA RICA
 * Proyecto de programaci�n 4 
 * Integrantes:
 * David Aguilar Rojas
 * Ian Mora Rodriguez
 * I Ciclo lectivo 2020 
 */
package com.progra.restaurante.data;

import com.progra.restaurante.logic.MetodosPago;
import com.progra.restaurante.logic.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class MetodosPagoDao {

    public static ArrayList<MetodosPago> getMetodosPago() throws Exception {
        String SQL = "select * from metodos_pago;";
        ArrayList<MetodosPago> list = new ArrayList<>();

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                MetodosPago metodoPago = new MetodosPago();
                metodoPago.setIdMetodoPago(resultado.getInt("id_metodo_pago"));
                metodoPago.setNombre(resultado.getString("nombre"));
                list.add(metodoPago);
            }
            con.close();
            resultado.close();
            st.close();

            return list;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public static MetodosPago getMetodoPago(String nombre) throws Exception {
        String SQL = "select * from metodos_pago where nombre = ?;";
        MetodosPago metodoPago = null;

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            st.setString(1, nombre);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                metodoPago = new MetodosPago();
                metodoPago.setIdMetodoPago(resultado.getInt("id_metodo_pago"));
                metodoPago.setNombre(resultado.getString("nombre"));
            }
            con.close();
            resultado.close();
            st.close();

            return metodoPago;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
}
