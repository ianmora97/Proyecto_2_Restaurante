/*
 * UNIVERSIDAD NACIONAL DE COSTA RICA
 * Proyecto de programaci�n 4 
 * Integrantes:
 * David Aguilar Rojas
 * Ian Mora Rodriguez
 * I Ciclo lectivo 2020 
 */
package com.progra.restaurante.data;

import com.progra.restaurante.logic.Mesa;
import com.progra.restaurante.logic.Reservacion;
import com.progra.restaurante.logic.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author david
 */
public class reservationDao {

    public static Mesa getMesaLibre(int cantidadPersonas) throws Exception {
        String SQL = "select id_mesa, min(cap_max) cap_max, cap_min, estado from mesa where estado = 'L' and cap_min <= ? and cap_max >= ?;";
        Mesa mesa = null;

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setInt(1, cantidadPersonas);
            st.setInt(2, cantidadPersonas);

            ResultSet resultado = st.executeQuery();

            if (resultado.next()) {
                mesa = new Mesa();
                mesa.setIdMesa(resultado.getInt("id_mesa"));
                mesa.setCapMax(resultado.getInt("cap_max"));
                mesa.setCapMin(resultado.getInt("cap_min"));
                mesa.setEstado(resultado.getString("estado"));
            } else {
                con.close();
                resultado.close();
                st.close();
                throw new Exception();
            }
            con.close();
            resultado.close();
            st.close();

            return mesa;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public static boolean insertReservation(Usuario usuario, Mesa mesa, Date fecha, int cantidadPersonas) throws Exception {
        String SQL = "insert into reservacion (usuario_correo,mesa_id_mesa,fecha,cantidad_personas) values (?,?,?,?); ";
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, usuario.getUsuarioCorreo());
            st.setInt(2, mesa.getIdMesa());
            st.setDate(3, new java.sql.Date(fecha.getTime()));
            st.setInt(4, cantidadPersonas);

            int resultado = st.executeUpdate();
            if (resultado != 0) {
                SQL = "update mesa set estado = 'O' where id_mesa =?;";
                st = con.prepareStatement(SQL);
                st.setInt(1, mesa.getIdMesa());
                st.executeUpdate();
                if (st.executeUpdate() == 0) {
                    throw new Exception();
                }
            }

            con.close();
            st.close();

            return resultado != 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static boolean deleteReservacion(int id) throws Exception {
        String SQL;
        try {
            SQL = "select mesa_id_mesa FROM reservacion WHERE id_reservacion = ?;";

            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            st.setInt(1, id);
            ResultSet resultado = st.executeQuery();
            int mesa_id = 0;
            while(resultado.next()){
                mesa_id = resultado.getInt("mesa_id_mesa");
            }

            SQL = "DELETE FROM reservacion WHERE id_reservacion = ?;";
            st = con.prepareStatement(SQL);
            st.setInt(1, id);
            int r = st.executeUpdate();
            if(r != 0){
                SQL = "update mesa set estado = 'L' WHERE id_mesa = ?;";
                st = con.prepareStatement(SQL);
                st.setInt(1, mesa_id);
                r = st.executeUpdate();
            }
            
            con.close();
            st.close();
            return r != 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static ArrayList<Reservacion> getReservaciones(Usuario usuario) throws Exception {
        String SQL = "select *from reservacion where usuario_correo= ?";
        ArrayList<Reservacion>  list = new ArrayList<>();

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, usuario.getUsuarioCorreo());

            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                Reservacion reserva = new Reservacion();
                Mesa mesa = new Mesa();
                mesa.setIdMesa(resultado.getInt("mesa_id_mesa"));
                reserva.setMesaIdMesa(mesa);
                reserva.setIdReservacion(resultado.getInt("id_reservacion"));
                reserva.setCantidadPersonas(resultado.getInt("cantidad_personas"));
                reserva.setFecha(resultado.getDate("fecha"));
                reserva.setUsuarioCorreo(usuario);
                list.add(reserva);
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
    public static ArrayList<Reservacion> getReservacionesAll() throws Exception {
        String SQL = "select * from reservacion;";
        ArrayList<Reservacion>  list = new ArrayList<>();

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                Reservacion reserva = new Reservacion();
                Mesa mesa = new Mesa();
                mesa.setIdMesa(resultado.getInt("mesa_id_mesa"));
                reserva.setMesaIdMesa(mesa);
                reserva.setIdReservacion(resultado.getInt("id_reservacion"));
                reserva.setCantidadPersonas(resultado.getInt("cantidad_personas"));
                reserva.setFecha(resultado.getDate("fecha"));
                reserva.setUsuarioCorreo(com.progra.restaurante.data.UsuarioDao.getUsuarioByEmail(resultado.getString("usuario_correo")));
                
                list.add(reserva);
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
    public static ArrayList<Mesa> getMesas() throws Exception {
        String SQL = "select * from mesa;";
        ArrayList<Mesa>  list = new ArrayList<>();

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                Mesa mesa = new Mesa();
                mesa.setIdMesa(resultado.getInt("id_mesa"));
                mesa.setCapMax(resultado.getInt("cap_max"));
                mesa.setCapMin(resultado.getInt("cap_min"));
                mesa.setEstado(resultado.getString("estado"));
                list.add(mesa);
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
    public static boolean editMesa(Mesa p) throws Exception {
        String SQL = "UPDATE mesa "
                + "SET cap_max = ? , cap_min = ? , estado = ? "
                + "WHERE id_mesa = ?;";
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setInt(1, p.getCapMax());
            st.setInt(2, p.getCapMin());
            st.setString(3, p.getEstado());
            st.setInt(4, p.getIdMesa());
            int r = st.executeUpdate();
            con.close();
            st.close();
            return r != 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static boolean registrarMesa(Mesa mesa) throws Exception {
        String SQL = "insert into mesa (cap_max,cap_min,estado) "
                + "values (?,?,?);";
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setInt(1, mesa.getCapMax());
            st.setInt(2, mesa.getCapMin());
            st.setString(3, mesa.getEstado());
            int r = st.executeUpdate();
            con.close();
            st.close();

            return r != 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static boolean deleteMesa(int id) throws Exception {
        String SQL = "DELETE FROM mesa WHERE id_mesa = ?;";
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
}
