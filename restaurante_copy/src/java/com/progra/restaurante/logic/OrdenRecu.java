/*
 * UNIVERSIDAD NACIONAL DE COSTA RICA
 * Proyecto de programaci�n 4 
 * Integrantes:
 * David Aguilar Rojas
 * Ian Mora Rodriguez
 * I Ciclo lectivo 2020 
 */
package com.progra.restaurante.logic;

/**
 *
 * @author ianmo
 */
public class OrdenRecu {
    String nombre;
    String estatus;
    int tipoEntrega;
    String direccion;
    String nombrePlatillo;
    String nombreAdicional;
    String nombreOpcion;
    double total;

    public OrdenRecu() {
    }

    public OrdenRecu(String nombre, String estatus, int tipoEntrega, String direccion, String nombrePlatillo, String nombreAdicional, String nombreOpcion, double total) {
        this.nombre = nombre;
        this.estatus = estatus;
        this.tipoEntrega = tipoEntrega;
        this.direccion = direccion;
        this.nombrePlatillo = nombrePlatillo;
        this.nombreAdicional = nombreAdicional;
        this.nombreOpcion = nombreOpcion;
        this.total = total;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public int getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(int tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombrePlatillo() {
        return nombrePlatillo;
    }

    public void setNombrePlatillo(String nombrePlatillo) {
        this.nombrePlatillo = nombrePlatillo;
    }

    public String getNombreAdicional() {
        return nombreAdicional;
    }

    public void setNombreAdicional(String nombreAdicional) {
        this.nombreAdicional = nombreAdicional;
    }

    public String getNombreOpcion() {
        return nombreOpcion;
    }

    public void setNombreOpcion(String nombreOpcion) {
        this.nombreOpcion = nombreOpcion;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
}
