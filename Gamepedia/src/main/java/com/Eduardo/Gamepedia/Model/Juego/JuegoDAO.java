package com.Eduardo.Gamepedia.Model.Juego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.Eduardo.Gamepedia.Model.Categoria.Categoria;
import com.Eduardo.Gamepedia.Utils.ConnectionUtil;

public class JuegoDAO extends Juego {

	public static final String GETBYID = "SELECT id,imagen,nombre,descripcion,id_categoria FROM juego WHERE id=";
	private static final String DELETE = "DELETE FROM juego WHERE id=?";
	private static final String INSERTUPDATE = "INSERT INTO juego (id, imagen,nombre,descripcion,id_categoria) "
			+ "VALUES (?,?,?,?,?) " + "ON DUPLICATE KEY UPDATE imagen=?,nombre=?,descripcion=?,id_categoria=?";
	private final static String INSERT = "INSERT INTO juego (imagen,nombre,descripcion,id_categoria) VALUES (?,?,?,?)";
	private static final String TODO = "SELECT * FROM juego";
	private static final String GETBYCATEGORIA = "SELECT juego,id,juego.imagen,juego.nombre,juego.descripcion,juego.id_categoria FROM juego,categoria WHERE id_categoria=categoria.id";

	public JuegoDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JuegoDAO(int id, String imagen, String nombre, String descripcion, int id_categoria) {
		super(id, imagen, nombre, descripcion, id_categoria);
		// TODO Auto-generated constructor stub
	}
	public JuegoDAO(String imagen, String nombre, String descripcion, int id_categoria) {
		super(imagen, nombre, descripcion, id_categoria);
		// TODO Auto-generated constructor stub
	}

	public JuegoDAO(int id) {
		super();
		Connection con = (Connection) ConnectionUtil.getConnection();
		// Stament
		if (con != null) {
			try {
				Statement st = con.createStatement();
				String q = GETBYID + id;
				ResultSet rs = st.executeQuery(q);
				while (rs.next()) {
					this.id = rs.getInt("id");
					this.imagen = rs.getString("imagen")	;
					this.nombre = rs.getString("nombre");
					this.descripcion = rs.getString("descripcion");
					this.id_categoria = rs.getInt("id_categoria");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public JuegoDAO(Juego j) {
		this.id = j.id;
		this.imagen = j.imagen;
		this.nombre = j.nombre;
		this.descripcion = j.descripcion;
		this.id_categoria = j.id_categoria;
	}

	public int save(){
        int result = -1;
        
        try {
            java.sql.Connection csql = ConnectionUtil.getConnection();
            
            if(this.id>0){
                //UPDATE
            	String nombre=this.nombre;
                String q = "UPDATE juego SET imagen=?,nombre=?,descripcion=?,id_categoria=? WHERE id ="+id;
                PreparedStatement ps = csql.prepareStatement(q);
                ps.setString(1, imagen);
                ps.setString(2, nombre);
                ps.setString(3, descripcion);
                ps.setInt(4, id_categoria);
                result= ps.executeUpdate();
                
            }else {
                //INSERT
                String q = INSERT;
                PreparedStatement ps = csql.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
                
                
                ps.setString(1, this.imagen);
                ps.setString(2, this.nombre);
                ps.setString(3, this.descripcion);
                ps.setInt(4, this.id_categoria);
                result = ps.executeUpdate();
                try(ResultSet generatedKeys = ps.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        result = generatedKeys.getInt(1); //devuelve el ultimo id insertado
                    }
                }
                this.id = result;
            }
            
        }catch (SQLException ex) {
            System.out.println("Error Guardando categoria");
        }
        
        return result;
    }

	public int eliminar() {
		int rs=0;
		Connection con = ConnectionUtil.getConnection();
		
		if (con != null) {
			try {
				PreparedStatement q=con.prepareStatement(DELETE);
				q.setInt(1, this.id);
				rs =q.executeUpdate();
				this.id=-1;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rs;

	}

	public static List<Juego> GetAllJuego() {
		List<Juego> Base = new ArrayList<Juego>();
		Connection con = ConnectionUtil.getConnection();

		if (con != null) {
			try {

				Statement st = con.createStatement();
				String q = TODO;
				ResultSet rs = st.executeQuery(q);
				while (rs.next()) {
					Juego base = new Juego();
					int id = rs.getInt("id");
					base.setId(rs.getInt("id"));
					base.setNombre(rs.getString("nombre"));
					Base.add(base);
				}

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

		return Base;
	}

}
