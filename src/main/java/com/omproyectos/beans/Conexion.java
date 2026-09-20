/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.omproyectos.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Conexion {
    
    public static Connection getConexion(){

    /*    
                String conexionUrl = "jdbc:sqlserver://127.0.0.1:1433;databaseName=CDB" +
                             ";user=usuarioCDB" +
                             ";password=Obafgkme101" +
                             ";trustServerCertificate=true";
*/
       //usuarioCDB,Obafgkme101
       //omartin,Obafgkme123
        String conexionUrl = "jdbc:sqlserver://127.0.0.1:1433;databaseName=CDB" +
                             ";user=omartin" +
                             ";password=Obafgkme123" +
                             ";trustServerCertificate=true";
        
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(conexionUrl);
            return con;
        }catch(SQLException ex){
            System.out.println(ex.toString());
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}