/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.data;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class Conn {

    public static Connection conectar() throws Exception {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurante?user=root&password=root&useSSL=false");
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }

    }

}
