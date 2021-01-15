/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.prueba.dto;

/**
 *
 * @author l
 */
public class Usuario {
    
    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String clave;
    private String direccion;
    private String foto;

    public Usuario() {
    }

    public Usuario(Integer idUsuario, String nombre, String apellido, String telefono, String correo, String clave, String direccion, String foto) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.clave = clave;
        this.direccion = direccion;
        this.foto = foto;
    }
    
    

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

   
    public String getApellido() {
        return apellido;
    }

   
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

   
    public String getTelefono() {
        return telefono;
    }

   
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    
    public String getClave() {
        return clave;
    }

    
    public void setClave(String clave) {
        this.clave = clave;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    
    public String getFoto() {
        return foto;
    }

    
    public void setFoto(String foto) {
        this.foto = foto;
    }

}
