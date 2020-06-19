/*
 * UNIVERSIDAD NACIONAL DE COSTA RICA
 * Proyecto de programaci�n 4 
 * Integrantes:
 * David Aguilar Rojas
 * Ian Mora Rodriguez
 * I Ciclo lectivo 2020 
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
public class OrderDao {

    public static boolean registerOrder(Orden order) throws Exception {

        String SQL = "insert into orden (usuario_correo,id_metodo_pago,id_ubicacion,fecha_entrega,estatus,tipo_entrega,asap,total) "
                + "values (?,?,?,?,?,?,?,?);";

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setString(1, order.getUsuarioCorreo().getUsuarioCorreo());
            st.setInt(2, order.getMetodoPago().getIdMetodoPago());
            st.setInt(3, order.getIdUbicacion().getIdUbicacion());
            st.setDate(4, new java.sql.Date(order.getFechaEntrega().getTime()));
            st.setString(5, order.getEstatus());
            st.setInt(6, order.getTipoEntrega());
            st.setInt(7, order.getAsap());
            st.setDouble(8, order.getTotal());
            st.executeUpdate();
            String SQL2 = "select max(id_orden) id_orden from orden;";
            PreparedStatement st2 = con.prepareStatement(SQL2);
            st2.executeQuery();
            ResultSet resultado = st2.getResultSet();
            int id_orden = -1;
            if (resultado.next()) {
                id_orden = resultado.getInt("id_orden");
            }
            registerSelectedDishes(order.getPlatilloseleccionadoCollection(), id_orden, con);

            con.close();
            st.close();

            return st.executeUpdate() != 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    private static boolean registerSelectedDishes(ArrayList<Platillo> platillos, int idOrder, Connection con) throws Exception {

        try {

            for (Platillo platillo : platillos) {
                String SQL = "insert into platilloSeleccionado (id_platillo,id_orden,cantidad) "
                        + "values (?,?,?);";
                PreparedStatement st = con.prepareStatement(SQL);
                st.setInt(1, platillo.getIdPlatillo());
                st.setInt(2, idOrder);
                st.setInt(3, platillo.getCantidad());
                st.executeUpdate();
                st.close();
                String SQL2 = "select max(id_platilloSeleccionado) from platilloSeleccionado;";
                PreparedStatement st2 = con.prepareStatement(SQL2);
                st2.executeQuery();
                ResultSet resultado = st2.getResultSet();
                int id_platillo = -1;
                if (resultado.next()) {
                    id_platillo = resultado.getInt(1);
                }
                registerSelectedAditionals(platillo.getAdicionalCollection(), id_platillo, con);

            }
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    private static boolean registerSelectedAditionals(ArrayList<Adicional> aditionals, int idPlatillo, Connection con) throws Exception {

        try {
            for (Adicional adicional : aditionals) {
                String SQL = "insert into adicional_seleccionada (id_platilloSeleccionado,id_adicional) "
                        + "values (?,?);";
                PreparedStatement st = con.prepareStatement(SQL);
                st.setInt(1, idPlatillo);
                st.setInt(2, adicional.getIdAdicional());
                st.executeUpdate();
                st.close();
                String SQL2 = "select max(id_adicional_seleccionada) from adicional_seleccionada;";
                PreparedStatement st2 = con.prepareStatement(SQL2);
                st2.executeQuery();
                ResultSet resultado = st2.getResultSet();
                int id_adicional_seleccionada = -1;
                if (resultado.next()) {
                    id_adicional_seleccionada = resultado.getInt(1);
                }
                registerSelectedOptions(adicional.getOpcionCollection(), id_adicional_seleccionada, con);
            }

            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    private static boolean registerSelectedOptions(ArrayList<Opcion> opciones, int id_adicional_seleccionada, Connection con) throws Exception {

        try {
            for (Opcion opcion : opciones) {
                String SQL = "insert into opcionesSeleccionadas (id_adicional_seleccionada,id_opcion) "
                        + "values (?,?);";
                PreparedStatement st = con.prepareStatement(SQL);
                st.setInt(1, id_adicional_seleccionada);
                st.setInt(2, opcion.getIdOpcion());
                st.executeUpdate();
                st.close();
            }

            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static ArrayList<OrdenCliente> getOrdersNameId() throws Exception {
        String SQL = "select c.nombre,c.apellidos, o.id_orden, o.estatus from orden o, cliente c where o.usuario_correo = c.usuario_correo";
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            ResultSet resultado = st.executeQuery();

            ArrayList<OrdenCliente> lista = new ArrayList<>();
            OrdenCliente oc;

            while (resultado.next()) {
                oc = new OrdenCliente();
                oc.setNombre(resultado.getString("nombre"));
                oc.setApellidos(resultado.getString("apellidos"));
                oc.setId(resultado.getInt("id_orden"));
                oc.setStatus(resultado.getString("estatus"));
                lista.add(oc);
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
    public static ArrayList<OrdenRecu> getOrders(int id) throws Exception {
        String SQL = "select cli.nombre as nombre_cliente, orn.estatus, orn.tipo_entrega, ub.direccion,pla.nombre_platillo,"
                + "adi.nombre as nombre_adicional ,op.nombre as nombre_opcion, orn.total " +
            "from orden orn, platilloSeleccionado p, adicional_seleccionada adS, opcionesSeleccionadas opS, platillo pla, adicional adi, opcion op, " +
            "usuario u, cliente cli, ubicacion ub, metodos_pago met " +
            "where orn.id_orden = p.id_orden and " +
            "p.id_platilloSeleccionado = adS.id_platilloSeleccionado and " +
            "adS.id_adicional_seleccionada = opS.id_adicional_seleccionada and " +
            "p.id_platillo = pla.id_platillo and " +
            "adS.id_adicional = adi.id_adicional and " +
            "opS.id_opcion = op.id_opcion and " +
            "u.usuario_correo = orn.usuario_correo and " +
            "u.usuario_correo = cli.usuario_correo and " +
            "orn.id_ubicacion= ub.id_ubicacion and " +
            "orn.id_metodo_pago = met.id_metodo_pago and orn.id_orden=?;";
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            st.setInt(1, id);
            ResultSet resultado = st.executeQuery();
            
            ArrayList<OrdenRecu> lista = new ArrayList<>();
            OrdenRecu oc;

            while (resultado.next()) {
                oc = new OrdenRecu();
                oc.setNombre(resultado.getString("nombre_cliente"));
                oc.setEstatus(resultado.getString("estatus"));
                oc.setTipoEntrega(resultado.getInt("tipo_entrega"));
                oc.setDireccion(resultado.getString("direccion"));
                oc.setNombrePlatillo(resultado.getString("nombre_platillo"));
                oc.setNombreAdicional(resultado.getString("nombre_adicional"));
                oc.setNombreOpcion(resultado.getString("nombre_opcion"));
                oc.setTotal(resultado.getDouble("total"));
                lista.add(oc);
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
    public static boolean setStatus(String s, int id) throws Exception {
        String SQL = "update orden set estatus = ? where id_orden = ?;";
        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            st.setString(1, s);
            st.setInt(2, id);
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
