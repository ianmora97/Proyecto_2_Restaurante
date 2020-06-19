/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.progra.restaurante.logic.Categoria;
import com.progra.restaurante.data.Conn;

/**
 *
 * @author david
 */
public class CategoriesDao {

    public static boolean registrarCategoria(Categoria categoria) throws Exception {
        String SQL = "insert into categoria (nombre) values (?);";
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, categoria.getNombre());
            int r = st.executeUpdate();
            con.close();
            st.close();
            return r != 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public static boolean deleteCategoria(int id) throws Exception {
        String SQL = "DELETE FROM categoria WHERE id_categoria = ?;";
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

    public static boolean editCategoria(String nombre, String nombren) throws Exception {
        String SQL = "UPDATE categoria SET nombre = ? WHERE nombre = ?;";
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, nombren);
            st.setString(2, nombre);
            int r = st.executeUpdate();
            con.close();
            st.close();
            return r != 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static Categoria findCategoria(int id) throws Exception {
        String SQL = "select * from categoria where id_categoria=?;";
        Categoria categoria = new Categoria();

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setInt(1, id);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                categoria.setIdCategoria(resultado.getInt("id_categoria"));
                categoria.setNombre(resultado.getString("nombre"));
            }
            con.close();
            resultado.close();
            st.close();
            return categoria;

        } catch (SQLException ex) {
            System.out.println(ex);
            return categoria;
        }
    }
    public static ArrayList<Categoria> findCategoriaByName(String nombre) throws Exception {
        String SQL = "select * from categoria where nombre=?;";
        Categoria categoria = null;

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, nombre);
            ResultSet resultado = st.executeQuery();
            
            ArrayList<Categoria> lista = new ArrayList<>();

            while (resultado.next()) {
                categoria = new Categoria();
                categoria.setIdCategoria(resultado.getInt("id_categoria"));
                categoria.setNombre(resultado.getString("nombre"));
                lista.add(categoria);
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

    public static ArrayList<Categoria> getListaCategorias() throws Exception {
        String SQL = "select * from categoria order by 1;";
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            ResultSet resultado = st.executeQuery();

            ArrayList<Categoria> lista = new ArrayList<>();
            Categoria categoria;

            while (resultado.next()) {
                categoria = new Categoria();
                categoria.setIdCategoria(resultado.getInt("id_categoria"));
                categoria.setNombre(resultado.getString("nombre"));
                lista.add(categoria);
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

    public static ArrayList<Categoria> listarCategoria() throws Exception {
        String SQL = "select * from categoria;";
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            ResultSet resultado = st.executeQuery();

            ArrayList<Categoria> lista = new ArrayList<>();
            Categoria categoria;

            while (resultado.next()) {
                categoria = new Categoria();

                categoria.setIdCategoria(resultado.getInt("id_categoria"));
                categoria.setNombre(resultado.getString("nombre"));
                categoria.setPlatilloCollection(com.progra.restaurante.data.DishesDao.listarPlatillosPorCategoria(resultado.getInt("id_categoria")));

                lista.add(categoria);
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
