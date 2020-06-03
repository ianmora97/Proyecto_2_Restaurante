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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "ubicacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ubicacion.findAll", query = "SELECT u FROM Ubicacion u")
    , @NamedQuery(name = "Ubicacion.findByIdUbicacion", query = "SELECT u FROM Ubicacion u WHERE u.idUbicacion = :idUbicacion")
    , @NamedQuery(name = "Ubicacion.findByDireccion", query = "SELECT u FROM Ubicacion u WHERE u.direccion = :direccion")
    , @NamedQuery(name = "Ubicacion.findByProvincia", query = "SELECT u FROM Ubicacion u WHERE u.provincia = :provincia")
    , @NamedQuery(name = "Ubicacion.findByCanton", query = "SELECT u FROM Ubicacion u WHERE u.canton = :canton")
    , @NamedQuery(name = "Ubicacion.findByCodigoPostal", query = "SELECT u FROM Ubicacion u WHERE u.codigoPostal = :codigoPostal")})
public class Ubicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ubicacion")
    private Integer idUbicacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "provincia")
    private String provincia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "canton")
    private String canton;
    @Column(name = "codigo_postal")
    private Integer codigoPostal;
    @JoinTable(name = "direccion_propia", joinColumns = {
        @JoinColumn(name = "id_ubicacion", referencedColumnName = "id_ubicacion")}, inverseJoinColumns = {
        @JoinColumn(name = "usuario_correo", referencedColumnName = "usuario_correo")})
    @ManyToMany
    private Collection<Usuario> usuarioCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUbicacion")
    private Collection<Orden> ordenCollection;

    public Ubicacion() {
    }

    public Ubicacion(Integer idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public Ubicacion(Integer idUbicacion, String direccion, String provincia, String canton) {
        this.idUbicacion = idUbicacion;
        this.direccion = direccion;
        this.provincia = provincia;
        this.canton = canton;
    }

    public Integer getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Integer idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    @XmlTransient
    public Collection<Orden> getOrdenCollection() {
        return ordenCollection;
    }

    public void setOrdenCollection(Collection<Orden> ordenCollection) {
        this.ordenCollection = ordenCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUbicacion != null ? idUbicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ubicacion)) {
            return false;
        }
        Ubicacion other = (Ubicacion) object;
        if ((this.idUbicacion == null && other.idUbicacion != null) || (this.idUbicacion != null && !this.idUbicacion.equals(other.idUbicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.progra.restaurante.logic.Ubicacion[ idUbicacion=" + idUbicacion + " ]";
    }
    
}
