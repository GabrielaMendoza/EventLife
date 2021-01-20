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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * REST Web Service
 *
 * @author l
 */
@Stateless
@Path("usuario")
public class UsuarioResource {

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class.getName());

    @Context
    private UriInfo context;
    @Resource(name = "proyectoDS")
    private DataSource ds;

    public UsuarioResource() {
    }

    @GET
    @Produces("application/json; charset=utf-8")
    public Response getUsuario(@QueryParam("q") String query) {
        System.out.println("El parametro recibido es " + query);
        try {
            ArrayList<Usuario> lista = new ArrayList<>();
            Connection con = this.ds.getConnection();
            ResultSet rs = null;
            if (query == null) {
                String sql = "SELECT * FROM usuario";
                Statement stm = con.createStatement();

                rs = stm.executeQuery(sql);
            } else {
                String sql = "SELECT * FROM usuario WHERE nombre LIKE ?";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, "%" + query + "%");
            }
            while (rs.next()) {
                Integer idU = rs.getInt("idUsuario");
                String nom = rs.getString("nombre");
                String ape = rs.getString("apellido");
                String tel = rs.getString("telefono");
                String corr = rs.getString("correo");
                String clave = rs.getString("clave");
                String direc = rs.getString("direccion");
                String foto = rs.getString("foto");

                Usuario u = new Usuario(idU, nom, ape, tel, corr, null, direc, foto);

                lista.add(u);
            }
            return Response.status(Response.Status.OK).entity(lista).build();

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al consultar por usuario", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Produces("application/json; charset=utf-8")

    @Path("{idU}")

    public Response getUsuarioPorId(@PathParam("idU") Integer idUsuario) {
        try {
            Connection con = this.ds.getConnection();
            String sql = "SELECT * FROM usuario WHERE idUsuario =?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idUsuario);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                Integer idU = rs.getInt("idUsuario");
                String nom = rs.getString("nombre");
                String ape = rs.getString("apellido");
                String tel = rs.getString("telefono");
                String corr = rs.getString("correo");
                String clave = rs.getString("clave");
                String direc = rs.getString("direccion");
                String foto = rs.getString("foto");

                Usuario u = new Usuario(idU, nom, ape, tel, corr, null, direc, foto);
                return Response.status(Response.Status.OK).entity(u).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al consultar usuario por id", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();

        }
    }

    @POST
    @Consumes("application/json; charset=utf-8")
    public Response postUsuario(Usuario u) {
        try {
            if (u.getNombre().isEmpty()|| u.getNombre().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita un nombre").build();
            } else if (u.getNombre().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            }  if (u.getApellido().isEmpty()|| u.getApellido().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita un apellido").build();
            } else if (u.getApellido().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            } if (u.getCorreo().isEmpty()|| u.getCorreo().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita un correo").build();
            } else if (u.getCorreo().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            } if (u.getTelefono().isEmpty()|| u.getTelefono().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita numero de telefono").build();
            } else if (u.getTelefono().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            } if (u.getClave().isEmpty()|| u.getClave().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita una clave").build();
            } else if (u.getClave().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            }  else {
                Connection con = this.ds.getConnection();
                String sql = "INSERT INTO usuario(idUsuario, nombre, apellido, telefono, correo, clave, direccion, foto)VALUE(?,?,?,?,?,?,?,?)";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setInt(1, u.getIdUsuario());
                stm.setString(2, u.getNombre());
                stm.setString(3, u.getApellido());
                stm.setString(4, u.getTelefono());
                stm.setString(5, u.getCorreo());
                String claveSHA = DigestUtils.sha256Hex(u.getClave());
                stm.setString(6, claveSHA);
                stm.setString(7, u.getDireccion());
                stm.setString(8, u.getFoto());
                stm.execute();
                return Response.status(Response.Status.CREATED).build();
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al registrar nombre", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();

        }
    }

    @PUT
    @Consumes("application/json; charset=utf-8")
    public Response putUsuario(Usuario u) {
        try {
            if (u.getNombre().isEmpty() || u.getNombre().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita un nombre").build();
            } else if (u.getNombre().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            }  if (u.getApellido().isEmpty()|| u.getApellido().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita un apellido").build();
            } else if (u.getApellido().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            } if (u.getCorreo().isEmpty()|| u.getCorreo().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita un correo").build();
            } else if (u.getCorreo().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            } if (u.getClave().isEmpty()|| u.getClave().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita una clave").build();
            } else if (u.getClave().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            } else {
                Connection con = this.ds.getConnection();
                String sql = "UPDATE usuario SET  nombre = ?, apellido = ?, telefono = ?, correo = ?, clave = ?, direccion = ?, foto= ? WHERE idUsuario = ?";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, u.getNombre());
                stm.setString(2, u.getApellido());
                stm.setString(3, u.getTelefono());
                stm.setString(4, u.getCorreo());
                String claveSHA = DigestUtils.sha256Hex(u.getClave());
                stm.setString(5, claveSHA);
                stm.setString(6, u.getDireccion());
                stm.setString(7, u.getFoto());
                stm.setInt(8, u.getIdUsuario());
                stm.execute();
                return Response.status(Response.Status.OK).build();
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al editar", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();

        }
    }

    @DELETE
    @Path("{idU}")
    public Response deleteUsuario(@PathParam("idU") Integer idUsuario) {
        try {
            Connection con = this.ds.getConnection();
            String sql = "DELETE FROM usuario WHERE idUsuario = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idUsuario);
            stm.execute();
            int afectados = stm.getUpdateCount();
            if (afectados == 0) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.status(Response.Status.OK).build();
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al eliminar", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();

        }
    }
}
