/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "mesa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mesa.findAll", query = "SELECT m FROM Mesa m")
    , @NamedQuery(name = "Mesa.findByIdMesa", query = "SELECT m FROM Mesa m WHERE m.idMesa = :idMesa")
    , @NamedQuery(name = "Mesa.findByCapMax", query = "SELECT m FROM Mesa m WHERE m.capMax = :capMax")
    , @NamedQuery(name = "Mesa.findByCapMin", query = "SELECT m FROM Mesa m WHERE m.capMin = :capMin")
    , @NamedQuery(name = "Mesa.findByEstado", query = "SELECT m FROM Mesa m WHERE m.estado = :estado")})
public class Mesa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_mesa")
    private Integer idMesa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cap_max")
    private int capMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cap_min")
    private int capMin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "estado")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mesaIdMesa")
    private ArrayList<Reservacion> reservacionCollection;

    public Mesa() {
    }

    public Mesa(Integer idMesa) {
        this.idMesa = idMesa;
    }

    public Mesa(Integer idMesa, int capMax, int capMin, String estado) {
        this.idMesa = idMesa;
        this.capMax = capMax;
        this.capMin = capMin;
        this.estado = estado;
    }

    public Integer getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Integer idMesa) {
        this.idMesa = idMesa;
    }

    public int getCapMax() {
        return capMax;
    }

    public void setCapMax(int capMax) {
        this.capMax = capMax;
    }

    public int getCapMin() {
        return capMin;
    }

    public void setCapMin(int capMin) {
        this.capMin = capMin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public ArrayList<Reservacion> getReservacionCollection() {
        return reservacionCollection;
    }

    public void setReservacionCollection(ArrayList<Reservacion> reservacionCollection) {
        this.reservacionCollection = reservacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMesa != null ? idMesa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mesa)) {
            return false;
        }
        Mesa other = (Mesa) object;
        if ((this.idMesa == null && other.idMesa != null) || (this.idMesa != null && !this.idMesa.equals(other.idMesa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.progra.restaurante.logic.Mesa[ idMesa=" + idMesa + " ]";
    }
    
}
