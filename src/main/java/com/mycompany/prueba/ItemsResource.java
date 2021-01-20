/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.prueba;

import com.mycompany.prueba.dto.Items;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author l
 */
@Path("items")
public class ItemsResource {

    private static final Logger LOG = Logger.getLogger(ItemsResource.class.getName());

    @Context
    private UriInfo context;

    @Resource(name = "proyectoDS")
    private DataSource ds;

    @GET
    @Produces("application/json; charset=utf-8")
    public Response getItems(@QueryParam("q") String query) {
        try {
            ArrayList<Items> lista = new ArrayList<>();
            Connection con = this.ds.getConnection();
            ResultSet rs = null;
            if (query == null) {
                String sql = "SELECT * FROM items";
                Statement stm = con.createStatement();

                rs = stm.executeQuery(sql);
            } else {
                String sql = "SELECT * FROM items WHERE nombre LIKE ?";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, "%" + query + "%");
            }
            while (rs.next()) {
                Integer idI = rs.getInt("idItems");
                String nom = rs.getString("nombre");
                String des = rs.getString("descripcion");
                String foto = rs.getString("foto");
                Integer pre = rs.getInt("precio");
                Integer idE = rs.getInt("idEvento");

                Items i = new Items(idI, nom, des, foto, pre, idE);
                lista.add(i);
            }
            return Response.status(Response.Status.OK).entity(lista).build();

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al consultar por items", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Produces("application/json; charset=utf-8")

    @Path("{idI}")

    public Response getUsuarioPorId(@PathParam("idI") Integer idItems) {
        try {
            Connection con = this.ds.getConnection();
            String sql = "SELECT * FROM items WHERE idItems =?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idItems);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                Integer idI = rs.getInt("idItems");
                String nom = rs.getString("nombre");
                String des = rs.getString("descripcion");
                String foto = rs.getString("foto");
                Integer pre = rs.getInt("precio");
                Integer idE = rs.getInt("idEvento");

                Items i = new Items(idI, nom, des, foto, pre, idE);

                return Response.status(Response.Status.OK).entity(i).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al consultar items por id", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();

        }
    }

    @POST
    @Consumes("application/json; charset=utf-8")
    public Response postItems(Items i) {
        try {
            if (i.getNombre().isEmpty() || i.getNombre().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita un nombre").build();
            } else if (i.getNombre().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            }
            if (i.getDescripcion().isEmpty() || i.getDescripcion().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita una descripcion").build();
            } else if (i.getDescripcion().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            } else {
                Connection con = this.ds.getConnection();
                String sql = "INSERT INTO items(idItems, nombre, descripcion, foto, precio,IdEvento)VALUE(?,?,?,?,?,?)";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setInt(1, i.getIdItems());
                stm.setString(2, i.getNombre());
                stm.setString(3, i.getDescripcion());
                stm.setString(4, i.getFoto());
                stm.setInt(5, i.getPrecio());
                stm.setInt(6, i.getIdEvento());
                stm.execute();
                return Response.status(Response.Status.CREATED).build();
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al registrar items", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();

        }
    }

    @PUT
    @Consumes("application/json; charset=utf-8")
    public Response putItems(Items i) {
        try {
            if (i.getNombre().isEmpty() || i.getNombre().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita un nombre").build();
            } else if (i.getNombre().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            }
            if (i.getDescripcion().isEmpty() || i.getDescripcion().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita una descripcion").build();
            } else if (i.getDescripcion().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            } else {
                Connection con = this.ds.getConnection();
                String sql = "UPDATE items SET  nombre = ?, descripcion = ?,foto = ?, precio = ?,IdEvento=? WHERE idItems = ?";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, i.getNombre());
                stm.setString(2, i.getDescripcion());
                stm.setString(3, i.getFoto());
                stm.setInt(4, i.getPrecio());
                stm.setInt(5, i.getIdEvento());
                stm.setInt(6, i.getIdItems());
                stm.execute();
                return Response.status(Response.Status.OK).build();
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al editar items", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();

        }
    }

    @DELETE
    @Path("{idI}")
    public Response deleteItems(@PathParam("idI") Integer idItems) {
        try {
            Connection con = this.ds.getConnection();
            String sql = "DELETE FROM items WHERE idItems = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idItems);
            stm.execute();
            int afectados = stm.getUpdateCount();
            if (afectados == 0) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.status(Response.Status.OK).build();
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al eliminar items", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();

        }
    }
}
