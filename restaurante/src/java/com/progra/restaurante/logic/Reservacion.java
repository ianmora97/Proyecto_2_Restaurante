/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.restaurante.logic;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author david
 */
@Entity
@Table(name = "reservacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservacion.findAll", query = "SELECT r FROM Reservacion r")
    , @NamedQuery(name = "Reservacion.findByIdReservacion", query = "SELECT r FROM Reservacion r WHERE r.idReservacion = :idReservacion")
    , @NamedQuery(name = "Reservacion.findByFecha", query = "SELECT r FROM Reservacion r WHERE r.fecha = :fecha")
    , @NamedQuery(name = "Reservacion.findByCantidadPersonas", query = "SELECT r FROM Reservacion r WHERE r.cantidadPersonas = :cantidadPersonas")})
public class Reservacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_reservacion")
    private Integer idReservacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad_personas")
    private int cantidadPersonas;
    @JoinColumn(name = "mesa_id_mesa", referencedColumnName = "id_mesa")
    @ManyToOne(optional = false)
    private Mesa mesaIdMesa;
    @JoinColumn(name = "usuario_correo", referencedColumnName = "usuario_correo")
    @ManyToOne(optional = false)
    private Usuario usuarioCorreo;

    public Reservacion() {
    }

    public Reservacion(Integer idReservacion) {
        this.idReservacion = idReservacion;
    }

    public Reservacion(Integer idReservacion, Date fecha, int cantidadPersonas) {
        this.idReservacion = idReservacion;
        this.fecha = fecha;
        this.cantidadPersonas = cantidadPersonas;
    }
    
    public Reservacion(Integer idReservacion, Date fecha, int cantidadPersonas, Usuario usuario) {
        this.idReservacion = idReservacion;
        this.fecha = fecha;
        this.cantidadPersonas = cantidadPersonas;
        this.usuarioCorreo = usuario;
    }
    
    public Integer getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(Integer idReservacion) {
        this.idReservacion = idReservacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public Mesa getMesaIdMesa() {
        return mesaIdMesa;
    }

    public void setMesaIdMesa(Mesa mesaIdMesa) {
        this.mesaIdMesa = mesaIdMesa;
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
        hash += (idReservacion != null ? idReservacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservacion)) {
            return false;
        }
        Reservacion other = (Reservacion) object;
        if ((this.idReservacion == null && other.idReservacion != null) || (this.idReservacion != null && !this.idReservacion.equals(other.idReservacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.progra.restaurante.logic.Reservacion[ idReservacion=" + idReservacion + " ]";
    }
    
}
