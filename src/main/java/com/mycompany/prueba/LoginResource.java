/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.prueba;

import com.mycompany.prueba.dto.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * REST Web Service
 *
 * @author l
 */
@Path("login")
public class LoginResource {

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class.getName());
    @Context
    private UriInfo context;
    @Resource(name = "proyectoDS")
    private DataSource ds;

    public LoginResource() {
    }

    @Produces("application/json; charset=utf-8")

    public Response loginReUsuario(Usuario u) {
        if (u.getNombre().isEmpty() || u.getNombre().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("se necesita un nombre").build();
        } else if (u.getNombre().length() > 45) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
        }
        if (u.getApellido().isEmpty() || u.getApellido().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("se necesita un apellido").build();
        } else if (u.getApellido().length() > 45) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();

        } else {
            try {
                Connection con = this.ds.getConnection();
                String sql = "SELECT * FROM usuario WHERE nombre LIKE ? OR apellido LIKE ? OR telefono LIKE ? OR correo LIKE ? OR clave LIKE ? OR direccion LIKE ?";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(2, u.getNombre());
                stm.setString(3, u.getApellido());
                stm.setString(4, u.getTelefono());
                stm.setString(5, u.getCorreo());
                String claveSHA = DigestUtils.sha256Hex(u.getClave());
                stm.setString(6, claveSHA);
                stm.setString(7, u.getDireccion());
                stm.execute();
                return Response.status(Response.Status.CREATED).entity("{}").build();

            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "Error al registrar login", ex);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();

            }
        }
    }
         @Produces("application/json; charset=utf-8")

        public Response loginUsuario(Usuario u) {
        if (u.getCorreo().isEmpty() || u.getCorreo().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("se necesita un nombre").build();
        } else if (u.getCorreo().length() > 45) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
        }
        if (u..isEmpty() || u.getApellido().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("se necesita un apellido").build();
        } else if (u.getApellido().length() > 45) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();

        } else {
            try {
                Connection con = this.ds.getConnection();
                String sql = "SELECT * FROM usuario WHERE correo LIKE ? OR clave LIKE ? ";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(5, u.getCorreo());
                String claveSHA = DigestUtils.sha256Hex(u.getClave());
                stm.setString(6, claveSHA);
                stm.execute();
                return Response.status(Response.Status.CREATED).entity("{}").build();

            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "Error al ingresar", ex);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();

            }
        }
        
}
}