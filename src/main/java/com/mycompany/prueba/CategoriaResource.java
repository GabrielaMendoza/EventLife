/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.prueba;

import com.mycompany.prueba.dto.Categoria;
import com.mycompany.prueba.dto.ErrorApi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author l
 */
@Path("categoria")
public class CategoriaResource {

    private static final Logger LOG = Logger.getLogger(CategoriaResource.class.getName());

    @Context
    private UriInfo context;

    @Resource(name = "proyectoDS")
    private DataSource ds;

    public CategoriaResource() {
    }

    @GET
    @Produces("application/json; charset=utf-8")
    public Response getCategoria(@QueryParam("q") String query) {
        try {
            ArrayList<Categoria> lista = new ArrayList<>();
            Connection con = this.ds.getConnection();
            ResultSet rs = null;
            if (query == null) {
                String sql = "SELECT * FROM categoria";
                Statement stm = con.createStatement();

                rs = stm.executeQuery(sql);
            } else {
                String sql = "SELECT * FROM categoria WHERE nombre LIKE ?";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, "%" + query + "%");
            }
            while (rs.next()) {
                Integer idC = rs.getInt("idCategoria");
                String nom = rs.getString("nombre");
                String des = rs.getString("descripcion");

                Categoria c = new Categoria(idC, nom, des);
                lista.add(c);
            }
            return Response.status(Response.Status.OK).entity(lista).build();

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al consultar por categoria", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Produces("application/json; charset=utf-8")

    @Path("{idC}")

    public Response getUsuarioPorId(@PathParam("idC") Integer idCategoria) {
        try {
            Connection con = this.ds.getConnection();
            String sql = "SELECT * FROM categoria WHERE idCategoria =?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idCategoria);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                Integer idC = rs.getInt("idCategoria");
                String nom = rs.getString("nombre");
                String des = rs.getString("descripcion");

                Categoria c = new Categoria(idC, nom, des);

                return Response.status(Response.Status.OK).entity(c).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorApi("Error al obtener categoria ")).build();
            }

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al consultar categoria por id", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();

        }
    }

    @POST
    @Consumes("application/json; charset=utf-8")
     @Produces("application/json; charset=utf-8")
    public Response postCategoria(Categoria c) {
        try {
            if (c.getNombre().isEmpty() || c.getNombre().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita un nombre").build();
            } else if (c.getNombre().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            }
            if (c.getDescripcion().isEmpty() || c.getDescripcion().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita una descripcion").build();
            } else if (c.getDescripcion().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            }
            else {
                Connection con = this.ds.getConnection();
                String sql = "INSERT INTO categoria(nombre, descripcion)VALUE(?,?)";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, c.getNombre());
                stm.setString(2, c.getDescripcion());
                stm.execute();
                return Response.status(Response.Status.CREATED).entity("{}").build();
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al registrar categoria", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();

        }
    }

    @PUT
    @Consumes("application/json; charset=utf-8")
     @Produces("application/json; charset=utf-8")
    public Response putCategoria(Categoria c) {
        try {
            if (c.getNombre().isEmpty() || c.getNombre().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita un nombre").build();
            } else if (c.getNombre().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            }
            if (c.getDescripcion().isEmpty() || c.getDescripcion().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita una descripcion").build();
            } else if (c.getDescripcion().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            } else {
                Connection con = this.ds.getConnection();
                String sql = "UPDATE categoria SET  nombre = ?, descripcion = ? WHERE idCategoria = ?";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, c.getNombre());
                stm.setString(2, c.getDescripcion());
                stm.setInt(3, c.getIdCategoria());
                stm.execute();
                return Response.status(Response.Status.OK).entity("{}").build();
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al editar categoria", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();

        }
    }
     @Produces("application/json; charset=utf-8")
     @DELETE
    @Path("{idC}")
    public Response deleteCategoria(@PathParam("idC") Integer idCategoria) {
        try {
            Connection con = this.ds.getConnection();
            String sql = "DELETE FROM categoria WHERE idCategoria = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idCategoria);
            stm.execute();
            int afectados = stm.getUpdateCount();
            if (afectados == 0) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorApi("Error al eliminar ")).build();
            } else {
                return Response.status(Response.Status.OK).entity("{}").build();
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al eliminar categoria", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();

        }
    }
}