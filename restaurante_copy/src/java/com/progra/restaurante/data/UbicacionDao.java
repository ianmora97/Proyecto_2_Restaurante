/*
 * UNIVERSIDAD NACIONAL DE COSTA RICA
 * Proyecto de programaci�n 4 
 * Integrantes:
 * David Aguilar Rojas
 * Ian Mora Rodriguez
 * I Ciclo lectivo 2020 
 */
package com.progra.restaurante.data;

import com.progra.restaurante.logic.Ubicacion;
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
public class UbicacionDao {

    public static Ubicacion getUbicion(int id_ubicacion) throws Exception {
        String SQL = "select * from ubicacion where id_ubicacion = ?;";
        Ubicacion ubicacion = null;

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setInt(1, id_ubicacion);

            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                ubicacion = new Ubicacion();
                ubicacion.setIdUbicacion(resultado.getInt("id_ubicacion"));
                ubicacion.setCodigoPostal(resultado.getInt("codigo_postal"));
                ubicacion.setCanton(resultado.getString("canton"));
                ubicacion.setDireccion(resultado.getString("direccion"));
                ubicacion.setProvincia(resultado.getString("provincia"));
            }
            con.close();
            resultado.close();
            st.close();

            return ubicacion;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
        public static Ubicacion getUbicionByAddress(String address) throws Exception {
        String SQL = "select * from ubicacion where direccion = ?;";
        Ubicacion ubicacion = null;

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, address);

            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                ubicacion = new Ubicacion();
                ubicacion.setIdUbicacion(resultado.getInt("id_ubicacion"));
                ubicacion.setCodigoPostal(resultado.getInt("codigo_postal"));
                ubicacion.setCanton(resultado.getString("canton"));
                ubicacion.setDireccion(resultado.getString("direccion"));
                ubicacion.setProvincia(resultado.getString("provincia"));
            }
            con.close();
            resultado.close();
            st.close();

            return ubicacion;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
    public static boolean editUbicacion(Ubicacion ubicacion) throws Exception {
        String SQL = "UPDATE ubicacion "
                + "SET provincia = ?,canton = ?,codigo_postal=?,direccion=? "
                + "WHERE id_ubicacion = ?;";
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, ubicacion.getProvincia());
            st.setString(2, ubicacion.getCanton());
            st.setInt(3, ubicacion.getCodigoPostal());
            st.setString(4, ubicacion.getDireccion());
            st.setInt(5, ubicacion.getIdUbicacion());
            
            int r = st.executeUpdate();
            con.close();
            st.close();
            return r != 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static Ubicacion getUbicionById(int id) throws Exception {
        String SQL = "select u.id_ubicacion,u.codigo_postal,u.canton,u.direccion,u.provincia from direccion_propia d "
                + "inner join ubicacion u on d.id_ubicacion = u.id_ubicacion where u.id_ubicacion = ?;";
        Ubicacion ubicacion = null;

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setInt(1, id);

            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                ubicacion = new Ubicacion();
                ubicacion.setIdUbicacion(resultado.getInt("id_ubicacion"));
                ubicacion.setCodigoPostal(resultado.getInt("codigo_postal"));
                ubicacion.setCanton(resultado.getString("canton"));
                ubicacion.setDireccion(resultado.getString("direccion"));
                ubicacion.setProvincia(resultado.getString("provincia"));
            }
            con.close();
            resultado.close();
            st.close();

            return ubicacion;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }

    }
    public static ArrayList<Ubicacion> getUbicionesUsuario(Usuario usuario) throws Exception {
        String SQL = "select u.id_ubicacion,u.codigo_postal,u.canton,u.direccion,u.provincia from direccion_propia d inner join ubicacion u on d.id_ubicacion = u.id_ubicacion where usuario_correo= ?;";
        Ubicacion ubicacion = null;

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, usuario.getUsuarioCorreo());
            ArrayList<Ubicacion> list = new ArrayList<>();
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                ubicacion = new Ubicacion();
                ubicacion.setIdUbicacion(resultado.getInt("id_ubicacion"));
                ubicacion.setCodigoPostal(resultado.getInt("codigo_postal"));
                ubicacion.setCanton(resultado.getString("canton"));
                ubicacion.setDireccion(resultado.getString("direccion"));
                ubicacion.setProvincia(resultado.getString("provincia"));
                list.add(ubicacion);
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

    public static boolean insertUbicacionUsario(Ubicacion ubicacion, Usuario usuario) throws Exception {
        String SQL = "insert into ubicacion (direccion,provincia,canton,codigo_postal) values(?,?,?,?);";

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, ubicacion.getDireccion());
            st.setString(1, ubicacion.getProvincia());
            st.setString(1, ubicacion.getCanton());
            st.setInt(1, ubicacion.getCodigoPostal());

            int update = st.executeUpdate();

            if (update != 0) {
                SQL = "select u.id_ubicacion from direccion_propia d inner join ubicacion u on d.id_ubicacion = u.id_ubicacion where u.direccion =? and usuario_correo= ?; ";
                st = con.prepareStatement(SQL);
                st.setString(1, ubicacion.getDireccion());
                st.setString(2, usuario.getUsuarioCorreo());

                ResultSet resultado = st.executeQuery();
                int id_ubicacion = -1;
                if (resultado.next()) {
                    id_ubicacion = resultado.getInt("id_ubicacion");
                }
                SQL = "insert into direccion_propia (id_ubicacion,usuario_correo) values(?,?);";
                st = con.prepareStatement(SQL);
                st.setInt(1, id_ubicacion);
                st.setString(2, usuario.getUsuarioCorreo());
                resultado.close();
            }
            con.close();
            st.close();

            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }

    }
    public static boolean insertUbicacion(Ubicacion ubicacion, Usuario usuario) throws Exception {
        String SQL = "insert into ubicacion (direccion,provincia,canton,codigo_postal) values(?,?,?,?);";

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, ubicacion.getDireccion());
            st.setString(2, ubicacion.getProvincia());
            st.setString(3, ubicacion.getCanton());
            st.setInt(4, ubicacion.getCodigoPostal());

            int update = st.executeUpdate();

            if (update != 0) {
                SQL = "select id_ubicacion from ubicacion where direccion =?;";
                st = con.prepareStatement(SQL);
                st.setString(1, ubicacion.getDireccion());

                ResultSet resultado = st.executeQuery();
                int id_ubicacion = -1;
                if (resultado.next()) {
                    id_ubicacion = resultado.getInt("id_ubicacion");
                }
                SQL = "insert into direccion_propia (id_ubicacion,usuario_correo) values(?,?);";
                st = con.prepareStatement(SQL);
                st.setInt(1, id_ubicacion);
                st.setString(2, usuario.getUsuarioCorreo());
                update = st.executeUpdate();
                resultado.close();
            }
            con.close();
            st.close();

            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }

    }
}
