/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.logic;

import java.io.Serializable;
import java.util.Collection;
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
@Table(name = "adicional")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adicional.findAll", query = "SELECT a FROM Adicional a")
    , @NamedQuery(name = "Adicional.findByIdAdicional", query = "SELECT a FROM Adicional a WHERE a.idAdicional = :idAdicional")
    , @NamedQuery(name = "Adicional.findByNombre", query = "SELECT a FROM Adicional a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Adicional.findByTipo", query = "SELECT a FROM Adicional a WHERE a.tipo = :tipo")})
public class Adicional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_adicional")
    private Integer idAdicional;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo")
    private int tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAdicional")
    private ArrayList<Opcion> opcionCollection;
    @JoinColumn(name = "id_platillo", referencedColumnName = "id_platillo")
    @ManyToOne(optional = false)
    private Platillo idPlatillo;

    public Adicional() {
    }

    public Adicional(Integer idAdicional) {
        this.idAdicional = idAdicional;
    }

    public Adicional(Integer idAdicional, String nombre, int tipo) {
        this.idAdicional = idAdicional;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Integer getIdAdicional() {
        return idAdicional;
    }

    public void setIdAdicional(Integer idAdicional) {
        this.idAdicional = idAdicional;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public ArrayList<Opcion> getOpcionCollection() {
        return opcionCollection;
    }

    public void setOpcionCollection(ArrayList<Opcion> opcionCollection) {
        this.opcionCollection = opcionCollection;
    }

    public Platillo getIdPlatillo() {
        return idPlatillo;
    }

    public void setIdPlatillo(Platillo idPlatillo) {
        this.idPlatillo = idPlatillo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAdicional != null ? idAdicional.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adicional)) {
            return false;
        }
        Adicional other = (Adicional) object;
        if ((this.idAdicional == null && other.idAdicional != null) || (this.idAdicional != null && !this.idAdicional.equals(other.idAdicional))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.progra.restaurante.logic.Adicional[ idAdicional=" + idAdicional + " ]";
    }
    
}
