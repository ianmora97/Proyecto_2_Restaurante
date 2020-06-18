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

/**
 *
 * @author david
 */
public class OptionDao {

    public static boolean registrarOpcion(Opcion opcion) throws Exception {
        String SQL = "insert into opcion (id_adicional,nombre,precio) "
                + "values (?,?,?);";

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setInt(1, opcion.getIdAdicional().getIdAdicional());
            st.setString(2, opcion.getNombre());
            st.setDouble(3, opcion.getPrecio());

            con.close();
            st.close();
            return st.executeUpdate() != 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public static Opcion findOpcion(int id) throws Exception {
        String SQL = "select * from opcion where id_opcion=?;";
        Opcion opcion = new Opcion();

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setInt(1, id);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                Adicional a = new Adicional();
                a.setIdAdicional(resultado.getInt("id_adicional"));
                opcion.setIdAdicional(a);
                opcion.setIdOpcion(resultado.getInt("id_opcion"));
                opcion.setNombre(resultado.getString("nombre"));
                opcion.setPrecio(resultado.getDouble("precio"));

            }
            con.close();
            resultado.close();
            st.close();
            return opcion;

        } catch (SQLException ex) {
            System.out.println(ex);
            return opcion;
        }
    }

    public static Opcion findOpcionPorNombre(int nombre) throws Exception {
        String SQL = "select * from opcion where nombre=?;";
        Opcion opcion = new Opcion();

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setInt(1, nombre);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                Adicional a = new Adicional();
                a.setIdAdicional(resultado.getInt("id_adicional"));
                opcion.setIdAdicional(a);
                opcion.setIdOpcion(resultado.getInt("id_opcion"));
                opcion.setNombre(resultado.getString("nombre"));
                opcion.setPrecio(resultado.getDouble("precio"));
            }
            con.close();
            resultado.close();
            st.close();
            return opcion;

        } catch (SQLException ex) {
            System.out.println(ex);
            return opcion;
        }
    }

    public static ArrayList<Opcion> listarOpcion() throws Exception {
        String SQL = "select * from opcion;";
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            ResultSet resultado = st.executeQuery();

            ArrayList<Opcion> lista = new ArrayList<>();
            Opcion opcion;

            while (resultado.next()) {
                opcion = new Opcion();
                Adicional a = new Adicional();
                a.setIdAdicional(resultado.getInt("id_adicional"));
                opcion.setIdAdicional(a);
                opcion.setIdOpcion(resultado.getInt("id_opcion"));
                opcion.setNombre(resultado.getString("nombre"));
                opcion.setPrecio(resultado.getDouble("precio"));

                lista.add(opcion);
            }

            con.close();
            resultado.close();
            st.close();

            return lista;

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

    }

}
