/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.prueba;

import com.mycompany.prueba.dto.Evento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
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

/**
 * REST Web Service
 *
 * @author l
 */
@Path("evento")
public class EventoResource {

    private static final Logger LOG = Logger.getLogger(CategoriaResource.class.getName());
    @Context
    private UriInfo context;
    @Resource(name = "proyectoDS")
    private DataSource ds;

    public EventoResource() {
    }

    @GET
    @Produces("application/json; charset=utf-8")
    public Response getEvento(@QueryParam("q") String query) {
        System.out.println("El parametro recibido es " + query);
        try {
            ArrayList<Evento> lista = new ArrayList<>();
            Connection con = this.ds.getConnection();
            ResultSet rs = null;
            if (query == null) {
                String sql = "SELECT * FROM evento";
                Statement stm = con.createStatement();
                rs = stm.executeQuery(sql);
            } else {
                String sql = "SELECT * FROM evento WHERE nombre LIKE ?";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, "%" + query + "%");
            }
            while (rs.next()) {
                Integer idE = rs.getInt("idEvento");
                String nom = rs.getString("nombre");
                String des = rs.getString("descripcion");
                String feInicio = rs.getString(" fecha_hora_inicio");
                String feFin = rs.getString(" fecha_hora_fin");
                String tel = rs.getString("telefono");
                String coLo = rs.getString("coordenada_longitud");
                String coLati = rs.getString("coordenada_latitud");
                Integer idC = rs.getInt("idCategoria");
                Integer idU = rs.getInt("idUsuario");

                Evento e = new Evento(idE, nom, des, feInicio, feFin, tel, coLo, coLati, idC, idU);
                lista.add(e);
            }
            return Response.status(Response.Status.OK).entity(lista).build();

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al consultar por evento", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Produces("application/json; charset=utf-8")

    @Path("{idE}")

    public Response getEventoPorId(@PathParam("idE") Integer idEvento) {
        try {
            Connection con = this.ds.getConnection();
            String sql = "SELECT * FROM evento WHERE idEvento =?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idEvento);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                Integer idE = rs.getInt("idEvento");
                String nom = rs.getString("nombre");
                String des = rs.getString("descripcion");
                String feInicio = rs.getString("fecha_hora_inicio");
                String feFin = rs.getString("fecha_hora_fin");
                String tel = rs.getString("telefono");
                String coLo = rs.getString("coordenada_longitud");
                String coLati = rs.getString("coordenada_latitud");
                Integer idC = rs.getInt("idCategoria");
                Integer idU = rs.getInt("idUsuario");

                Evento e = new Evento(idE, nom, des, feInicio, feFin, tel, coLo, coLati, idC, idU);
                return Response.status(Response.Status.OK).entity(e).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al consultar evento por id", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();

        }
    }

    @POST
    @Consumes("application/json; charset=utf-8")
    public Response postEvento(Evento e) {
        try {
            if (e.getNombre().isEmpty() || e.getNombre().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita un nombre").build();
            } else if (e.getNombre().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            }
            if (e.getDescripcion().isEmpty() || e.getDescripcion().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita un descripcion").build();
            } else if (e.getDescripcion().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            } else {
                Connection con = this.ds.getConnection();
                String sql = "INSERT INTO evento(idEvento, nombre, descripcion, fecha_hora_inicio, fecha_hora_fin, telefono, coordenada_longitud,oordenada_latitud, idCategoria, idUsuario)VALUE(?,?,?,?,?,?,?,?,?)";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setInt(1, e.getIdEvento());
                stm.setString(2, e.getNombre());
                stm.setString(3, e.getDescripcion());
                stm.setString(4, e.getFecha_hora_inicio());
                stm.setString(5, e.getFecha_hora_fin());
                stm.setString(6, e.getTelefono());
                stm.setString(7, e.getCoordenada_longitud());
                stm.setString(8, e.getCoordenada_latitud());
                stm.setInt(9, e.getIdCategoria());
                stm.setInt(10, e.getIdUsuario());
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
    public Response putEvento(Evento e) {
        try {
            if (e.getNombre().isEmpty() || e.getNombre().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita un nombre").build();
            } else if (e.getNombre().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            }
            if (e.getDescripcion().isEmpty() || e.getDescripcion().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("se necesita un descripcion").build();
            } else if (e.getDescripcion().length() > 45) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Logitud maxima de 45 caracteres").build();
            } else {
                Connection con = this.ds.getConnection();
                String sql = "UPDATE evento SET  nombre = ?, descripcion = ?, fecha_hora_inicio=?, fecha_hora_fin =? telefono = ?, coordenada_longitud=?,coordenada_latitud=?, idCategoria=?, idUsuario =?  WHERE idEvento = ?";
                PreparedStatement stm = con.prepareStatement(sql);

                stm.setString(1, e.getNombre());
                stm.setString(2, e.getDescripcion());
                stm.setString(3, e.getFecha_hora_inicio());
                stm.setString(4, e.getFecha_hora_fin());
                stm.setString(5, e.getTelefono());
                stm.setString(6, e.getCoordenada_longitud());
                stm.setString(7, e.getCoordenada_latitud());
                stm.setInt(8, e.getIdCategoria());
                stm.setInt(9, e.getIdUsuario());
                stm.setInt(10, e.getIdEvento());
                stm.execute();

                return Response.status(Response.Status.OK).build();
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al editar", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();

        }
    }

    @DELETE
    @Path("{idE}")
    public Response deleteEvento(@PathParam("idE") Integer idEvento) {
        try {
            Connection con = this.ds.getConnection();
            String sql = "DELETE FROM evento WHERE idEvento = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idEvento);
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

    @GET
    @Path("{idE}/items")
    @Produces("application/json; charset=utf-8")
    public Response getItemsPorEvento(@QueryParam("idE") Integer idE) {
        try {
            Connection con = this.ds.getConnection();
            String sql = "SELECT * FROM items WHERE idEvento =?";
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al eliminar", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();

        }
    }
}
