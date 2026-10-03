package com.omproyectos.apirest_2.resources;

import com.omproyectos.beans.Concepto;
import com.omproyectos.beans.Conexion;
import com.omproyectos.beans.Usuario;
import com.omproyectos.beans.Deporte;
import com.omproyectos.beans.Categoria;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;

/**
 *
 * @author 
 */
@Path("rest")
public class JakartaEE8Resource {
    
   @GET 
   @Path("listar")
    
    @Produces(MediaType.APPLICATION_JSON)
   
     public List<Concepto> getConceptos(@QueryParam("queFecha") String queFecha) {

         List<Concepto> totalConceptos = new ArrayList<>();
         
         String queFecha_1;
         String query;
         queFecha_1 = queFecha.replace("-", "/");

         if (queFecha_1.length() == 10){
              query =  "  select \n"
                    + " fecemi as fecha, \n"
                    + " 0 as CodConcepto, \n"
                    + " 'Saldo Anterior' as Descripcion, \n"
                    + " case \n" 
                    + "	when salean >= 0 then 'IN' \n"
                    + " else 'EG' \n"
                    + " end as TipoMovimiento, \n"
                    + " salean as totalConcepto \n"
                    + " from cpHis_Caja \n"
                    + " where convert(varchar(10),fecemi,103) = '" + queFecha_1 + "' \n"
                    + " union \n"
                    + " select \n"  
                    + "  distinct fecha, codcon CodConcepto,\n"
                    + "  (select cpConceptos.descripcion from cpConceptos where codcon = cpConceptos.id) as Descripcion,\n"
                    + "  (select cpConceptos.tipo from cpConceptos where codcon = cpConceptos.id) as TipoMovimiento,\n"
                    + "  sum(import) totalConcepto\n"
                    + "  from cpHis_Movi\n"
                    + "  where convert(varchar(10),fecha,103) = '" + queFecha_1 + "' \n"
                    + "  and anulo <> 'S'\n"
                    + "  group by codcon, fecha, numcaj \n"
                    + "  order by CodConcepto";
         } else {
              query = " select \n" +
                      " distinct codcon CodConcepto,\n" +
                      " (select cpConceptos.descripcion from cpConceptos where codcon = cpConceptos.id) as Descripcion,\n" +
                      " (select cpConceptos.tipo from cpConceptos where codcon = cpConceptos.id) as TipoMovimiento,\n" +
                      " sum(import) totalConcepto\n" +
                      " from cpHis_Movi\n" +
                      " where substring(convert(varchar(10),fecha,103),4,7) = '" + queFecha_1 + "' \n" +
                      " and anulo <> 'S'\n" +
                      " group by codcon\n" +
                      " order by codcon";
         }
             
        try {
            Statement stmt = Conexion.getConexion().createStatement();
            
 //           String queFecha;
 //            queFecha = "12/06/2024";
            
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Concepto concepto = new Concepto();
                concepto.setCodConcepto(rs.getInt("CodConcepto"));
                concepto.setDescripcion(rs.getString("Descripcion"));
                concepto.setTipo(rs.getString("TipoMovimiento"));
                concepto.setTotalConcepto(rs.getDouble("totalConcepto"));
                totalConceptos.add(concepto);
            }
            return totalConceptos;
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println(e.toString());
            return null;
        }
//        return totalConceptos;
    }

     @GET 
     @Path("usuario")
     @Produces(MediaType.APPLICATION_JSON)
 //    public Response getUsuarios(@QueryParam("usuario") String usuario, 
//		                 @QueryParam("password") String password)
//     {
     public List<Usuario> getUsuarios(@QueryParam("usuario") String usuario, 
		                 @QueryParam("password") String password)
     {    
         
           List<Usuario> detalleUsuario = new ArrayList<>();
         
	try {
                    
  //          int resultado = 0;
  //          String result;
            
            Statement stmt = Conexion.getConexion().createStatement();   
            ResultSet rs = stmt.executeQuery("declare @resultado bit \n"
                                + "  declare @estado int \n"
                                + "  EXEC sp_CDB_LoginUsuario '" + usuario + "','" + password +"',@Result = @resultado output, @Estado = @estado output; \n"
                                + "  select @resultado as salida, @estado as estado \n");

            while (rs.next()) {
                Usuario user = new Usuario();
                user.setNombreUsuario(usuario);
                user.setExiste(rs.getInt("salida"));
                user.setEstado(rs.getInt("estado"));
//                resultado = (rs.getInt("salida"));
                detalleUsuario.add(user);
            }
//            if (resultado == 1){
//                result = "Existe";
//            } else {
//                result = "Usuario/Password Incorrecto";
//            }
//            return Response.ok(result).build();
             return detalleUsuario;
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println(e.toString());
            return null;
        }
    }
     
     @GET 
     @Path("deporte")
     @Produces(MediaType.APPLICATION_JSON)

     public List<Deporte> getDeportes()
             
     {    
         
           List<Deporte> detalleDeporte = new ArrayList<>();
         
	try {
                    
  //          int resultado = 0;
  //          String result;
            
            Statement stmt = Conexion.getConexion().createStatement();   
            ResultSet rs = stmt.executeQuery("SELECT a.idDeporte as idDeporte \n" +
                          " ,b.Descripcion as descripcion\n" +
                          " ,count(*) as totalDeporte\n" +
                          " FROM cpSocios a\n" +
                          " inner join cpDeportes b on a.idDeporte = b.idDeporte\n" +
                          " where a.estado = 'Alta' and a.idDeporte <> 0 and a.idDeporte is not null\n" +
                          " group by a.idDeporte, b.Descripcion\n" +
                          " order by b.Descripcion");

            while (rs.next()) {
                Deporte deporte = new Deporte();
                deporte.setIdDeporte(rs.getInt("idDeporte"));
                deporte.setDescripcion(rs.getString("descripcion"));
                deporte.setTotalDeporte(rs.getInt("totalDeporte"));
                detalleDeporte.add(deporte);
            }
            return detalleDeporte;
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println(e.toString());
            return null;
        }
    }

     @GET 
     @Path("categoria")
     @Produces(MediaType.APPLICATION_JSON)

     public List<Categoria> getCategoria()
             
     {    
         
           List<Categoria> detalleCategoria = new ArrayList<>();
         
	try {
                    
  //          int resultado = 0;
  //          String result;
            
            Statement stmt = Conexion.getConexion().createStatement();   
            ResultSet rs = stmt.executeQuery("SELECT a.categoria as categoria \n" +
                          " ,b.descripcion as descripcion\n" +
                          " ,count(*) as cantidad\n" +
                          " FROM cpSocios a\n" +
                          " inner join cpCategorias b on a.categoria = b.id\n" +
                          " where a.estado = 'Alta' and a.categoria <> 0 and a.categoria is not null\n" +
                          " group by a.categoria, b.Descripcion\n" +
                          " order by b.Descripcion");

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setCategoria(rs.getInt("categoria"));
                categoria.setDescripcion(rs.getString("descripcion"));
                categoria.setCantidad(rs.getInt("cantidad"));
                
                detalleCategoria.add(categoria);
            }
            return detalleCategoria;
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println(e.toString());
            return null;
        }
    }
     
     
     
}
