/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.data;

import com.progra.restaurante.logic.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author david
 */
public class ClienteDao {

    public static boolean insertClient(Cliente cliente) throws Exception {
        String SQL = "insert into cliente values (?,?,?,?); ";
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, cliente.getUsuarioCorreo());
            st.setString(2, cliente.getNombre());
            st.setString(3, cliente.getApellidos());
            st.setString(4, cliente.getTelefono());
            
            int resultado = st.executeUpdate();
            con.close();
            st.close();
            return resultado != 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

}
