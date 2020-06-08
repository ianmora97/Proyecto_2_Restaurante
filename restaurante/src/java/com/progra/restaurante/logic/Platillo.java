/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.logic;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author david
 */
@Entity
@Table(name = "platillo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Platillo.findAll", query = "SELECT p FROM Platillo p")
    , @NamedQuery(name = "Platillo.findByIdPlatillo", query = "SELECT p FROM Platillo p WHERE p.idPlatillo = :idPlatillo")
    , @NamedQuery(name = "Platillo.findByNombrePlatillo", query = "SELECT p FROM Platillo p WHERE p.nombrePlatillo = :nombrePlatillo")
    , @NamedQuery(name = "Platillo.findByDescripcion", query = "SELECT p FROM Platillo p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Platillo.findByPrecio", query = "SELECT p FROM Platillo p WHERE p.precio = :precio")})
public class Platillo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_platillo")
    private Integer idPlatillo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_platillo")
    private String nombrePlatillo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio")
    private double precio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPlatillo")
    private ArrayList<Adicional> adicionalCollection;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
    @ManyToOne(optional = false)
    private Categoria idCategoria;
    private int cantidad;

    public Platillo() {
        this.idPlatillo = 0;
        this.nombrePlatillo = "";
        this.descripcion = "";
        this.precio = 0.0;
        this.adicionalCollection = new ArrayList<>();
        this.cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Platillo(Integer idPlatillo) {
        this.idPlatillo = idPlatillo;
    }

    public Platillo(Integer idPlatillo, String nombrePlatillo, String descripcion, double precio) {
        this.idPlatillo = idPlatillo;
        this.nombrePlatillo = nombrePlatillo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.adicionalCollection = new ArrayList<>();
    }

    public Integer getIdPlatillo() {
        return idPlatillo;
    }

    public void setIdPlatillo(Integer idPlatillo) {
        this.idPlatillo = idPlatillo;
    }

    public String getNombrePlatillo() {
        return nombrePlatillo;
    }

    public void setNombrePlatillo(String nombrePlatillo) {
        this.nombrePlatillo = nombrePlatillo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @XmlTransient
    public ArrayList<Adicional> getAdicionalCollection() {
        return adicionalCollection;
    }

    public void setAdicionalCollection(ArrayList<Adicional> adicionalCollection) {
        this.adicionalCollection = adicionalCollection;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlatillo != null ? idPlatillo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Platillo)) {
            return false;
        }
        Platillo other = (Platillo) object;
        if ((this.idPlatillo == null && other.idPlatillo != null) || (this.idPlatillo != null && !this.idPlatillo.equals(other.idPlatillo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.progra.restaurante.logic.Platillo[ idPlatillo=" + idPlatillo + " ]";
    }

    public Platillo copy(Platillo platillo) {
        Platillo platilloCopy = new Platillo();

        platilloCopy.setIdPlatillo(platillo.getIdPlatillo());
        platilloCopy.setNombrePlatillo(platillo.getNombrePlatillo());
        platilloCopy.setDescripcion(platillo.getDescripcion());
        platilloCopy.setCantidad(platillo.getCantidad());
        platilloCopy.setIdCategoria(platillo.getIdCategoria());
        platilloCopy.setPrecio(platillo.getPrecio());

        ArrayList<Adicional> adicionalList = new ArrayList<>();
        for (Adicional ad : platillo.getAdicionalCollection()) {
            Adicional adicional = ad.copy(ad);
            adicionalList.add(adicional);
        }
        platilloCopy.setAdicionalCollection(adicionalList);

        return platilloCopy;
    }

}
