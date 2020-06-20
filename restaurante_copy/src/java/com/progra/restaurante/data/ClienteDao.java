/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.data;

import com.progra.restaurante.logic.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
     public static boolean updateClient(Cliente cliente) throws Exception {
        String SQL = "update cliente set nombre =?,apellidos =?,telefono =? where usuario_correo = ?;";

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, cliente.getNombre());
            st.setString(2, cliente.getApellidos());
            st.setString(3, cliente.getTelefono());
            st.setString(4, cliente.getUsuarioCorreo());

            int resultado = st.executeUpdate();

            con.close();
            st.close();

            return resultado != 0;

        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static Cliente createClient(String usuarioCorreo) throws Exception {
        String SQL = "select *from cliente where usuario_correo = ?;";
        Cliente cliente = new Cliente();
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            st.setString(1, usuarioCorreo);
            ResultSet result = st.executeQuery();

            while (result.next()) {
                cliente.setNombre(result.getString("nombre"));
                cliente.setApellidos(result.getString("apellidos"));
                cliente.setTelefono(result.getString("telefono"));
                cliente.setUsuarioCorreo(result.getString("usuario_correo"));
            }
            result.close();
            con.close();
            st.close();
            return cliente;

        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
}
