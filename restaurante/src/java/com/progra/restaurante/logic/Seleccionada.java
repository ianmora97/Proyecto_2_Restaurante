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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author david
 */
@Entity
@Table(name = "seleccionada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Seleccionada.findAll", query = "SELECT s FROM Seleccionada s")
    , @NamedQuery(name = "Seleccionada.findByIdAdicional", query = "SELECT s FROM Seleccionada s WHERE s.idAdicional = :idAdicional")})
public class Seleccionada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_adicional")
    private Integer idAdicional;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idadicionalSeleccionado")
    private Collection<Opcionesseleccionadas> opcionesseleccionadasCollection;
    @JoinColumn(name = "id_adicional", referencedColumnName = "id_adicional", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Adicional adicional;
    @JoinColumn(name = "id_platilloSeleccionado", referencedColumnName = "id_platilloSeleccionado")
    @ManyToOne(optional = false)
    private Platilloseleccionado idplatilloSeleccionado;

    public Seleccionada() {
    }

    public Seleccionada(Integer idAdicional) {
        this.idAdicional = idAdicional;
    }

    public Integer getIdAdicional() {
        return idAdicional;
    }

    public void setIdAdicional(Integer idAdicional) {
        this.idAdicional = idAdicional;
    }

    @XmlTransient
    public Collection<Opcionesseleccionadas> getOpcionesseleccionadasCollection() {
        return opcionesseleccionadasCollection;
    }

    public void setOpcionesseleccionadasCollection(Collection<Opcionesseleccionadas> opcionesseleccionadasCollection) {
        this.opcionesseleccionadasCollection = opcionesseleccionadasCollection;
    }

    public Adicional getAdicional() {
        return adicional;
    }

    public void setAdicional(Adicional adicional) {
        this.adicional = adicional;
    }

    public Platilloseleccionado getIdplatilloSeleccionado() {
        return idplatilloSeleccionado;
    }

    public void setIdplatilloSeleccionado(Platilloseleccionado idplatilloSeleccionado) {
        this.idplatilloSeleccionado = idplatilloSeleccionado;
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
        if (!(object instanceof Seleccionada)) {
            return false;
        }
        Seleccionada other = (Seleccionada) object;
        if ((this.idAdicional == null && other.idAdicional != null) || (this.idAdicional != null && !this.idAdicional.equals(other.idAdicional))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.progra.restaurante.logic.Seleccionada[ idAdicional=" + idAdicional + " ]";
    }
    
}
