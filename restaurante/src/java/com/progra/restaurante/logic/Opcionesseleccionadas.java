/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.logic;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author david
 */
@Entity
@Table(name = "opcionesseleccionadas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Opcionesseleccionadas.findAll", query = "SELECT o FROM Opcionesseleccionadas o")
    , @NamedQuery(name = "Opcionesseleccionadas.findByIdOpcion", query = "SELECT o FROM Opcionesseleccionadas o WHERE o.idOpcion = :idOpcion")})
public class Opcionesseleccionadas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_opcion")
    private Integer idOpcion;
    @JoinColumn(name = "id_opcion", referencedColumnName = "id_opcion", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Opcion opcion;
    @JoinColumn(name = "id_adicionalSeleccionado", referencedColumnName = "id_adicional")
    @ManyToOne(optional = false)
    private Seleccionada idadicionalSeleccionado;

    public Opcionesseleccionadas() {
    }

    public Opcionesseleccionadas(Integer idOpcion) {
        this.idOpcion = idOpcion;
    }

    public Integer getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(Integer idOpcion) {
        this.idOpcion = idOpcion;
    }

    public Opcion getOpcion() {
        return opcion;
    }

    public void setOpcion(Opcion opcion) {
        this.opcion = opcion;
    }

    public Seleccionada getIdadicionalSeleccionado() {
        return idadicionalSeleccionado;
    }

    public void setIdadicionalSeleccionado(Seleccionada idadicionalSeleccionado) {
        this.idadicionalSeleccionado = idadicionalSeleccionado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOpcion != null ? idOpcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Opcionesseleccionadas)) {
            return false;
        }
        Opcionesseleccionadas other = (Opcionesseleccionadas) object;
        if ((this.idOpcion == null && other.idOpcion != null) || (this.idOpcion != null && !this.idOpcion.equals(other.idOpcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.progra.restaurante.logic.Opcionesseleccionadas[ idOpcion=" + idOpcion + " ]";
    }
    
}
