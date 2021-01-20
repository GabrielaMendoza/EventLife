/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.prueba.dto;

import java.util.Date;

public class Evento {

    private Integer idEvento;
    private String nombre;
    private String descripcion;
    private String fechaHoraInicio;
    private String fechaHoraFin;
    private String telefono;
    private String coordenadaLongitud;
    private String coordenadaLatitud;
    private Integer idCategoria;
    private Integer idUsuario;

    public Evento() {
    }

    public Evento(Integer idEvento, String nombre, String descripcion, String fecha_hora_inicio, String fecha_hora_fin, String telefono, String coordenada_longitud, String coordenada_latitud, Integer idCategoria, Integer idUsuario) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaHoraInicio = fecha_hora_inicio;
        this.fechaHoraFin = fecha_hora_fin;
        this.telefono = telefono;
         this.coordenadaLongitud = coordenada_longitud;
        this.coordenadaLatitud = coordenada_latitud;
        this.idCategoria = idCategoria;
        this.idUsuario = idUsuario;
       
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(String fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public String getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(String fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCoordenadaLatitud() {
        return coordenadaLatitud;
    }

    public void setCoordenadaLatitud(String coordenadaLatitud) {
        this.coordenadaLatitud = coordenadaLatitud;
    }

    public String getCoordenadaLongitud() {
        return coordenadaLongitud;
    }

    public void setCoordenadaLongitud(String coordenadaLongitud) {
        this.coordenadaLongitud = coordenadaLongitud;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

}
