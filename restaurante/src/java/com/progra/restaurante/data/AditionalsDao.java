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
public class AditionalsDao {

    public static boolean registrarAdicional(Adicional adicional) throws Exception {
        String SQL = "insert into adicional (id_platillo,nombre,tipo) "
                + "values (?,?,?);";

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, adicional.getIdPlatillo().getNombrePlatillo());
            st.setString(2, adicional.getNombre());
            st.setInt(2, adicional.getTipo());

            return st.executeUpdate() != 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public static ArrayList<Opcion> llenarOpciones(int id_adicional) throws Exception {
        String SQL = "select * from opcion where id_adicional = ?;";
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setInt(1, id_adicional);

            ResultSet resultado = st.executeQuery();

            ArrayList<Opcion> lista = new ArrayList<>();
            Opcion opcion;

            while (resultado.next()) {
                opcion = new Opcion();

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

    public static Adicional findAdicional(int id) throws Exception {
        String SQL = "select * from adicional where id_adicional=?;";
        Adicional adicional = new Adicional();

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setInt(1, id);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                adicional.setIdAdicional(resultado.getInt("id_adicional"));
                adicional.setNombre(resultado.getString("nombre"));
                adicional.setTipo(resultado.getInt("tipo"));
                ArrayList<Opcion> opciones = llenarOpciones(resultado.getInt("id_adicional"));
                adicional.setOpcionCollection(opciones);

            }
            con.close();
            resultado.close();
            st.close();
            return adicional;

        } catch (SQLException ex) {
            System.out.println(ex);
            return adicional;
        }
    }

    public static ArrayList<Adicional> listarAdicional() throws Exception {
        String SQL = "select * from adicional;";
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            ResultSet resultado = st.executeQuery();

            ArrayList<Adicional> lista = new ArrayList<>();
            Adicional adicional;

            while (resultado.next()) {
                adicional = new Adicional();
                
                adicional.setIdAdicional(resultado.getInt("id_adicional"));
                adicional.setNombre(resultado.getString("nombre"));
                adicional.setTipo(resultado.getInt("tipo"));
                ArrayList<Opcion> opciones = llenarOpciones(resultado.getInt("id_adicional"));
                adicional.setOpcionCollection(opciones);

                lista.add(adicional);
            }
            return lista;

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

    }
}
