/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.data;

import com.progra.restaurante.logic.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ianmo
 */
public class UsuarioDao {
    public static Usuario getUsuario(String user, String clave) throws Exception {
        String SQL = "select * from usuario where username=? and contrasena=?;";
        Usuario usuario = new Usuario();

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, user);
            st.setString(2, clave);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                usuario.setUsuarioCorreo(resultado.getString("usuario_correo"));
                usuario.setUsername(resultado.getString("username"));
                usuario.setContrasena(resultado.getString("contrasena"));
                usuario.setRol(resultado.getInt("rol"));
            }
            con.close();
            resultado.close();
            st.close();
            return usuario;

        } catch (SQLException ex) {
            System.out.println(ex);
            return usuario;
        }
    }
}
