/*
 * UNIVERSIDAD NACIONAL DE COSTA RICA
 * Proyecto de programaci�n 4 
 * Integrantes:
 * David Aguilar Rojas
 * Ian Mora Rodriguez
 * I Ciclo lectivo 2020 
 */
package com.progra.restaurante.logic;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author david
 */
@Entity
@Table(name = "metodos_pago", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"noombre"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetodosPago.findAll", query = "SELECT m FROM MetodosPago m")
    , @NamedQuery(name = "MetodosPago.findByIdMetodoPago", query = "SELECT m FROM MetodosPago m WHERE m.idMetodoPago = :idMetodoPago")
    , @NamedQuery(name = "MetodosPago.findByNoombre", query = "SELECT m FROM MetodosPago m WHERE m.noombre = :noombre")})
public class MetodosPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_metodo_pago", nullable = false)
    private Integer idMetodoPago;
    @Size(max = 50)
    @Column(name = "noombre", length = 50)
    private String nombre;

    public MetodosPago() {
        this.idMetodoPago = 0;
        this.nombre = "";
    }

    public MetodosPago(Integer idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public Integer getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(Integer idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMetodoPago != null ? idMetodoPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetodosPago)) {
            return false;
        }
        MetodosPago other = (MetodosPago) object;
        if ((this.idMetodoPago == null && other.idMetodoPago != null) || (this.idMetodoPago != null && !this.idMetodoPago.equals(other.idMetodoPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.progra.restaurante.logic.MetodosPago[ idMetodoPago=" + idMetodoPago + " ]";
    }

}
