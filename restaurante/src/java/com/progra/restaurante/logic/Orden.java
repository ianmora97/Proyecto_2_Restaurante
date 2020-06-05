/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author david
 */
@Entity
@Table(name = "orden")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orden.findAll", query = "SELECT o FROM Orden o")
    , @NamedQuery(name = "Orden.findByIdOrden", query = "SELECT o FROM Orden o WHERE o.idOrden = :idOrden")
    , @NamedQuery(name = "Orden.findByFechaEntrega", query = "SELECT o FROM Orden o WHERE o.fechaEntrega = :fechaEntrega")
    , @NamedQuery(name = "Orden.findByEstatus", query = "SELECT o FROM Orden o WHERE o.estatus = :estatus")
    , @NamedQuery(name = "Orden.findByTipoEntrega", query = "SELECT o FROM Orden o WHERE o.tipoEntrega = :tipoEntrega")
    , @NamedQuery(name = "Orden.findByAsap", query = "SELECT o FROM Orden o WHERE o.asap = :asap")
    , @NamedQuery(name = "Orden.findByTotal", query = "SELECT o FROM Orden o WHERE o.total = :total")})
public class Orden implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_orden")
    private Integer idOrden;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_entrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrega;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "estatus")
    private String estatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo_entrega")
    private int tipoEntrega;
    @Basic(optional = false)
    @NotNull
    @Column(name = "asap")
    private int asap;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private Double total;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOrden")
    private ArrayList<Platilloseleccionado> platilloseleccionadoCollection;
    @JoinColumn(name = "id_ubicacion", referencedColumnName = "id_ubicacion")
    @ManyToOne(optional = false)
    private Ubicacion idUbicacion;
    @JoinColumn(name = "usuario_correo", referencedColumnName = "usuario_correo")
    @ManyToOne(optional = false)
    private Usuario usuarioCorreo;

    public Orden() {
    }

    public Orden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public Orden(Integer idOrden, Date fechaEntrega, String estatus, int tipoEntrega, int asap) {
        this.idOrden = idOrden;
        this.fechaEntrega = fechaEntrega;
        this.estatus = estatus;
        this.tipoEntrega = tipoEntrega;
        this.asap = asap;
    }

    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
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

    public int getAsap() {
        return asap;
    }

    public void setAsap(int asap) {
        this.asap = asap;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @XmlTransient
    public ArrayList<Platilloseleccionado> getPlatilloseleccionadoCollection() {
        return platilloseleccionadoCollection;
    }

    public void setPlatilloseleccionadoCollection(ArrayList<Platilloseleccionado> platilloseleccionadoCollection) {
        this.platilloseleccionadoCollection = platilloseleccionadoCollection;
    }

    public Ubicacion getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Ubicacion idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public Usuario getUsuarioCorreo() {
        return usuarioCorreo;
    }

    public void setUsuarioCorreo(Usuario usuarioCorreo) {
        this.usuarioCorreo = usuarioCorreo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrden != null ? idOrden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orden)) {
            return false;
        }
        Orden other = (Orden) object;
        if ((this.idOrden == null && other.idOrden != null) || (this.idOrden != null && !this.idOrden.equals(other.idOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.progra.restaurante.logic.Orden[ idOrden=" + idOrden + " ]";
    }
    
}
