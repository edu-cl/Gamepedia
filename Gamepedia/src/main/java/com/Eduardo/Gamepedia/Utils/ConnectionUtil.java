package com.Eduardo.Gamepedia.Utils;


import java.lang.System.Logger;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.Eduardo.Gamepedia.Model.Connection.Connection;

public class ConnectionUtil {
	
	private static java.sql.Connection con=null;
	
    public static java.sql.Connection connect(Connection c) throws ClassNotFoundException, SQLException{
        java.sql.Connection conn = null;
        
        if(c==null){
            conn = null;
        }else if(c.getTipo().equals("H2")){
            conn = DriverManager.getConnection("jdbc:h2:./" + c.getDb() + ";USER=" + c.getUsuario() + ";PASSWORD=" + c.getContraseña());
            compruebaestructura(conn);
        }else{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn=DriverManager.getConnection("jdbc:mysql://"+c.getHost()+"/"+c.getDb()+
                    "?useLegacyDatetimeCode=false&serverTimezone=UTC",c.getUsuario(),c.getContraseña());
        }
        
        return conn; 
    }
    
    public static java.sql.Connection getConnection(){
        if(con == null){
            Connection c = new Connection();
            c.loadDataXML();
            try {
                con=connect(c);
            } catch (ClassNotFoundException ex) {
            	
            } catch (SQLException ex) {
                System.out.println("Falta la base de datos");
                System.exit(0);
            }
        }
        return con;
    }
    
    public static void closeConnection(){
        if(con!=null){
            try {
            	con.close();
            } catch (SQLException ex) {
                System.out.println("Error en la conexion");
            }
        }
    }
    
    private static void compruebaestructura(java.sql.Connection conn) {
        String tabla1, tabla2;
        tabla1="CREATE TABLE IF NOT EXISTS categoria(" +
                "id int(11) NOT NULL," +
                "nombre text NOT NULL" +
                ");";
        tabla2="CREATE TABLE IF NOT EXISTS juego(" +
                "id int(11) NOT NULL," +
                "imagen text DEFAULT NULL," +
                "nombre text NOT NULL," +
                "descripcion text NOT NULL," +
                "id_categoria int(11) NOT NULL," +
                "CONSTRAINT juego_categoria FOREIGN KEY (id_categoria) REFERENCES categoria (id)" +
                "ON DELETE CASCADE ON UPDATE CASCADE" +
                ");";
        try {
            PreparedStatement t1 = conn.prepareStatement(tabla1);
            PreparedStatement t2 = conn.prepareStatement(tabla2);
            t1.executeUpdate();
            t2.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
