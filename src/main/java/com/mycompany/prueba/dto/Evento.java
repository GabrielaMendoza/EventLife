/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.prueba.dto;

import java.sql.Date;

public class Evento {

    private Integer idEvento;
    private String nombre;
    private String descripcion;
    private Date fecha_hora_inicio;
    private Date fecha_hora_fin;
    private String telefono;
    private String coordenada_longitud;
    private String coordenada_latitud;
    private Integer idCategoria;
    private Integer idUsuario;

    public Evento() {
    }

    public Evento(Integer idEvento, String nombre, String descripcion, Date fecha_hora_inicio, Date fecha_hora_fin, String telefono, String coordenada_longitud, String coordenada_latitud, Integer idCategoria, Integer idUsuario) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha_hora_inicio = fecha_hora_inicio;
        this.fecha_hora_fin = fecha_hora_fin;
        this.telefono = telefono;
         this.coordenada_longitud = coordenada_longitud;
        this.coordenada_latitud = coordenada_latitud;
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

    public Date getFecha_hora_inicio() {
        return fecha_hora_inicio;
    }

    public void setFecha_hora_inicio(Date fecha_hora_inicio) {
        this.fecha_hora_inicio = fecha_hora_inicio;
    }

    public Date getFecha_hora_fin() {
        return fecha_hora_fin;
    }

    public void setFecha_hora_fin(Date fecha_hora_fin) {
        this.fecha_hora_fin = fecha_hora_fin;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCoordenada_latitud() {
        return coordenada_latitud;
    }

    public void setCoordenada_latitud(String coordenada_latitud) {
        this.coordenada_latitud = coordenada_latitud;
    }

    public String getCoordenada_longitud() {
        return coordenada_longitud;
    }

    public void setCoordenada_longitud(String coordenada_longitud) {
        this.coordenada_longitud = coordenada_longitud;
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
