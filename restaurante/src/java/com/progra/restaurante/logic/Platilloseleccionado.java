/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.logic;

import java.io.Serializable;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author david
 */
@Entity
@Table(name = "platilloseleccionado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Platilloseleccionado.findAll", query = "SELECT p FROM Platilloseleccionado p")
    , @NamedQuery(name = "Platilloseleccionado.findByIdplatilloSeleccionado", query = "SELECT p FROM Platilloseleccionado p WHERE p.idplatilloSeleccionado = :idplatilloSeleccionado")
    , @NamedQuery(name = "Platilloseleccionado.findByCantidad", query = "SELECT p FROM Platilloseleccionado p WHERE p.cantidad = :cantidad")})
public class Platilloseleccionado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_platilloSeleccionado")
    private Integer idplatilloSeleccionado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    @JoinColumn(name = "id_orden", referencedColumnName = "id_orden")
    @ManyToOne(optional = false)
    private Orden idOrden;
    @JoinColumn(name = "id_platillo", referencedColumnName = "id_platillo")
    @ManyToOne(optional = false)
    private Platillo idPlatillo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idplatilloSeleccionado")
    private Collection<Seleccionada> seleccionadaCollection;

    public Platilloseleccionado() {
    }

    public Platilloseleccionado(Integer idplatilloSeleccionado) {
        this.idplatilloSeleccionado = idplatilloSeleccionado;
    }

    public Platilloseleccionado(Integer idplatilloSeleccionado, int cantidad) {
        this.idplatilloSeleccionado = idplatilloSeleccionado;
        this.cantidad = cantidad;
    }

    public Integer getIdplatilloSeleccionado() {
        return idplatilloSeleccionado;
    }

    public void setIdplatilloSeleccionado(Integer idplatilloSeleccionado) {
        this.idplatilloSeleccionado = idplatilloSeleccionado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Orden getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Orden idOrden) {
        this.idOrden = idOrden;
    }

    public Platillo getIdPlatillo() {
        return idPlatillo;
    }

    public void setIdPlatillo(Platillo idPlatillo) {
        this.idPlatillo = idPlatillo;
    }

    @XmlTransient
    public Collection<Seleccionada> getSeleccionadaCollection() {
        return seleccionadaCollection;
    }

    public void setSeleccionadaCollection(Collection<Seleccionada> seleccionadaCollection) {
        this.seleccionadaCollection = seleccionadaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idplatilloSeleccionado != null ? idplatilloSeleccionado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Platilloseleccionado)) {
            return false;
        }
        Platilloseleccionado other = (Platilloseleccionado) object;
        if ((this.idplatilloSeleccionado == null && other.idplatilloSeleccionado != null) || (this.idplatilloSeleccionado != null && !this.idplatilloSeleccionado.equals(other.idplatilloSeleccionado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.progra.restaurante.logic.Platilloseleccionado[ idplatilloSeleccionado=" + idplatilloSeleccionado + " ]";
    }
    
}
