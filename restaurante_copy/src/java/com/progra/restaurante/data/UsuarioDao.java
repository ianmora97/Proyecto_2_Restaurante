/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.data;

import com.progra.restaurante.logic.Usuario;
import com.progra.restaurante.logic.trio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ianmo
 */
public class UsuarioDao {

    public static Usuario getUsuario(String user, String clave) throws Exception {
        String SQL = "select * from usuario where username=? and contrasena=?;";
        Usuario usuario = null;

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, user);
            st.setString(2, clave);
            ResultSet resultado = st.executeQuery();

            if (resultado.next()) {
                usuario = new Usuario();
                usuario.setUsuarioCorreo(resultado.getString("usuario_correo"));
                usuario.setUsername(resultado.getString("username"));
                usuario.setContrasena(resultado.getString("contrasena"));
                usuario.setRol(resultado.getInt("rol"));
                usuario.setCliente(com.progra.restaurante.data.ClienteDao.createClient(resultado.getString("usuario_correo")));
            }
            con.close();
            resultado.close();
            st.close();

            return usuario;

        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public static boolean insertUser(Usuario usuario) throws Exception {
        String SQL = "insert into usuario values (?,?,?,?);";

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, usuario.getUsuarioCorreo());
            st.setString(2, usuario.getUsername());
            st.setString(3, usuario.getContrasena());
            st.setInt(4, usuario.getRol());

            int resultado = st.executeUpdate();

            con.close();
            st.close();

            return resultado != 0;

        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public static Usuario getUsuarioByEmail(String correo) throws Exception {
        String SQL = "select *from usuario where usuario_correo = ?;";
        Usuario usuario = null;
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, correo);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                usuario = new Usuario();
                usuario.setUsuarioCorreo(resultado.getString("usuario_correo"));
                usuario.setUsername(resultado.getString("username"));
                usuario.setContrasena(resultado.getString("contrasena"));
                usuario.setRol(resultado.getInt("rol"));
                usuario.setCliente(com.progra.restaurante.data.ClienteDao.createClient(resultado.getString("usuario_correo")));
            }

            con.close();
            st.close();
            resultado.close();
            
            return usuario;

        } catch (SQLException ex) {
            System.out.println(ex);
            return usuario;
        }
    }
    public static trio getStats() throws Exception {
        String SQL = "select count(rol) as privilegiado from usuario where rol = 1;";
        trio r = new trio();
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                r.setP(result.getInt("privilegiado"));
            }
            
            SQL = "select count(rol) as no_privilegiado from usuario where rol = 0;";
            st = con.prepareStatement(SQL);
            result = st.executeQuery();
            while (result.next()) {
                r.setNop(result.getInt("no_privilegiado"));
            }
            
            SQL = "select sum(total) as total from orden;";
            st = con.prepareStatement(SQL);
            result = st.executeQuery();
            while (result.next()) {
                r.setTotal(result.getInt("total"));
            }
            
            result.close();
            con.close();
            st.close();
            return r;

        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
}
