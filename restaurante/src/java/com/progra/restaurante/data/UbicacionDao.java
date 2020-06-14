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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author david
 */
public class UbicacionDao {

    public static Ubicacion getUbicion(int codigoPostal) throws Exception {
        String SQL = "select * from ubicacion where codigo_postal = ?;";
        Ubicacion ubicacion = null;

        try {
            Connection con = Conn.conectar();
            PreparedStatement st = con.prepareStatement(SQL);

            st.setInt(1, codigoPostal);

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
}
