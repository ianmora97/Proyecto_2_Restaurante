/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.data;

import static com.progra.restaurante.data.AditionalsDao.llenarOpciones;
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
public class DishesDao {

    public static boolean registrarPlatillo(Platillo platillo) throws Exception {
        String SQL = "insert into platillo (nombre_platillo,id_categoria,descripcion,precio) "
                + "values (?,?,?,?);";
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, platillo.getNombrePlatillo());
            st.setInt(2, platillo.getIdCategoria().getIdCategoria());
            st.setString(3, platillo.getDescripcion());
            st.setDouble(4, platillo.getPrecio());

            con.close();
            st.close();

            return st.executeUpdate() != 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static boolean deletePlato(int id) throws Exception {
        String SQL = "DELETE FROM platillo WHERE id_platillo = ?;";
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setInt(1, id);
            int r = st.executeUpdate();
            con.close();
            st.close();
            return r != 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static ArrayList<Adicional> adicionalesPlatillo(int id_platillo) throws Exception {
        String SQL = "select * from adicional where id_platillo=?;";

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            st.setInt(1, id_platillo);

            ResultSet resultado = st.executeQuery();

            ArrayList<Adicional> lista = new ArrayList<>();
            Adicional adicional;

            while (resultado.next()) {
                adicional = new Adicional();

                adicional.setIdAdicional(resultado.getInt("id_adicional"));
                adicional.setNombre(resultado.getString("nombre"));
                adicional.setTipo(resultado.getInt("tipo"));
                adicional.setRequerida(resultado.getInt("requerida"));

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

    public static Platillo findPlatillo(int id) throws Exception {
        String SQL = "select * from platillo where id_platillo=?;";
        Platillo platillo = new Platillo();

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setInt(1, id);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                platillo.setIdPlatillo(resultado.getInt("id_platillo"));
                platillo.setNombrePlatillo(resultado.getString("nombre_platillo"));
                platillo.setDescripcion(resultado.getString("descripcion"));
                platillo.setPrecio(resultado.getDouble("precio"));
                Categoria categoria = Model.instance().findCategoria(resultado.getInt("id_categoria"));
                platillo.setIdCategoria(categoria);
                ArrayList<Adicional> adicionales = adicionalesPlatillo(resultado.getInt("id_platillo"));
                platillo.setAdicionalCollection(adicionales);
            }
            con.close();
            resultado.close();
            st.close();
            return platillo;

        } catch (SQLException ex) {
            System.out.println(ex);
            return platillo;
        }
    }
    public static Platillo findPlatillo(String id) throws Exception {
        String SQL = "select * from platillo where nombre=?;";
        Platillo platillo = new Platillo();

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, id);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                platillo.setIdPlatillo(resultado.getInt("id_platillo"));
                platillo.setNombrePlatillo(resultado.getString("nombre_platillo"));
                platillo.setDescripcion(resultado.getString("descripcion"));
                platillo.setPrecio(resultado.getDouble("precio"));
                Categoria categoria = Model.instance().findCategoria(resultado.getInt("id_categoria"));
                platillo.setIdCategoria(categoria);
                ArrayList<Adicional> adicionales = adicionalesPlatillo(resultado.getInt("id_platillo"));
                platillo.setAdicionalCollection(adicionales);
            }
            con.close();
            resultado.close();
            st.close();
            return platillo;

        } catch (SQLException ex) {
            System.out.println(ex);
            return platillo;
        }
    }
    public static ArrayList<Platillo> listarPlatillos() throws Exception {
        String SQL = "select * from platillo;";

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            ResultSet resultado = st.executeQuery();
            ArrayList<Platillo> lista = new ArrayList<>();
            while (resultado.next()) {
                Platillo platillo = new Platillo();
                platillo.setIdPlatillo(resultado.getInt("id_platillo"));
                platillo.setNombrePlatillo(resultado.getString("nombre_platillo"));
                platillo.setDescripcion(resultado.getString("descripcion"));
                platillo.setPrecio(resultado.getDouble("precio"));
                Categoria categoria = com.progra.restaurante.data.CategoriesDao.findCategoria(resultado.getInt("id_categoria"));
                platillo.setIdCategoria(categoria);
                ArrayList<Adicional> adicionales = adicionalesPlatillo(resultado.getInt("id_platillo"));
                platillo.setAdicionalCollection(adicionales);

                lista.add(platillo);
            }
            con.close();
            resultado.close();
            st.close();
            return lista;

        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }

    }

    public static ArrayList<Platillo> listarPlatillosPorCategoria(int id_categoria) throws Exception {
        String SQL = "select * from platillo where id_categoria= ?;";

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            st.setInt(1, id_categoria);
            ResultSet resultado = st.executeQuery();

            ArrayList<Platillo> lista = new ArrayList<>();
            while (resultado.next()) {
                Platillo platillo = new Platillo();
                platillo.setIdPlatillo(resultado.getInt("id_platillo"));
                platillo.setNombrePlatillo(resultado.getString("nombre_platillo"));
                platillo.setDescripcion(resultado.getString("descripcion"));
                platillo.setPrecio(resultado.getDouble("precio"));
                Categoria categoria = com.progra.restaurante.data.CategoriesDao.findCategoria(resultado.getInt("id_categoria"));
                platillo.setIdCategoria(categoria);
                ArrayList<Adicional> adicionales = adicionalesPlatillo(resultado.getInt("id_platillo"));
                platillo.setAdicionalCollection(adicionales);

                lista.add(platillo);
            }
            con.close();
            resultado.close();
            st.close();
            return lista;

        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }

    }

}
