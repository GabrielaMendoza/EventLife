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
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

    @POST
    @Consumes("application/json; charset=utf-8")
    @Produces("application/json; charset=utf-8")
    public Response loginUsuario(Usuario u) {
        if (u.getCorreo().isEmpty() || u.getCorreo().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("se necesita un correo").build();
        } else if (u.getClave().isEmpty() || u.getClave().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("se necesita una clave").build();
        } else {
            try {

                Connection con = this.ds.getConnection();
                String sql = "SELECT * FROM usuario WHERE correo = ? AND clave = ?";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, u.getCorreo());
                String claveSHA = DigestUtils.sha256Hex(u.getClave());
                stm.setString(2, claveSHA);
                ResultSet rs = stm.executeQuery();
                if (rs.next()) {
                    Integer idU = rs.getInt("idUsuario");
                    String nom = rs.getString("nombre");
                    String ape = rs.getString("apellido");
                    String tel = rs.getString("telefono");
                    String corr = rs.getString("correo");
                    String direc = rs.getString("direccion");
                    String foto = rs.getString("foto");

                    Usuario usu = new Usuario(idU, nom, ape, tel, corr, null, direc, foto);
                    return Response.status(Response.Status.OK).entity(usu).build();
                }else{
                    return Response.status(Response.Status.BAD_REQUEST).entity(new Error("Error de usuario o contraseña")).build();
                }
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "Error al ingresar", ex);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Error(ex.getMessage())).build();

            }
        }
    }

}