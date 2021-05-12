package com.Eduardo.Gamepedia.Model.Categoria;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.Eduardo.Gamepedia.Model.Juego.Juego;
import com.Eduardo.Gamepedia.Model.Juego.JuegoDAO;
import com.Eduardo.Gamepedia.Utils.ConnectionUtil;

import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoriaDAO extends Categoria {

	private final static String GETBYID = "SELECT id,nombre FROM categoria WHERE id=";
	private final static String INSERTUPDATE = "INSERT INTO categoria (id, nombre)" + "VALUES (?,?) "
			+ "ON DUPLICATE KEY UPDATE nombre=?";
	private final static String INSERT = "INSERT INTO categoria (nombre) VALUES (?)";
	private final static String DELETE = "DELETE FROM categoria WHERE id=?";

	private final static String JUEGOs = "SELECT * FROM juego,categoria WHERE id_categoria=categoria.id";
	private final static String CATEGORIA = "SELECT * FROM categoria";

	private final static String JUEGOS = "SELECT * FROM juego WHERE id_categoria=";

	public CategoriaDAO() {
		super();

	}

	public CategoriaDAO(int id, String nombre, List<Juego> juegos) {
		super(id, nombre, juegos);

	}

	public CategoriaDAO(int id, String nombre) {
		super(id, nombre);
	}
	

	
	public CategoriaDAO(String nombre) {
		super(nombre);
		// TODO Auto-generated constructor stub
	}

	public CategoriaDAO(Categoria c) {
		this.id = c.getId();
		this.nombre = c.nombre;
		this.juegos = c.juegos;
	}
	
	// Trae categoria por un id
	public CategoriaDAO(int id) {
		super();
		Connection con = (Connection) ConnectionUtil.getConnection();
		if (con != null) {
			try {
				Statement st = con.createStatement();
				String q = GETBYID + id;
				ResultSet rs = st.executeQuery(q);
				while (rs.next()) {
					this.id = rs.getInt("id");
					this.nombre = rs.getString("nombre");
				}
				this.juegos=CategoriaDAO.buscarJuegosPorCategoria(this.id);
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}
	public int save(){
        int result = -1;
        
        try {
            java.sql.Connection csql = ConnectionUtil.getConnection();
            
            if(this.id>=0){
                //UPDATE
            	String nombre=this.nombre;
                String q = "UPDATE categoria SET nombre =? WHERE id = "+id;
                PreparedStatement ps = csql.prepareStatement(q);
                ps.setString(1, nombre);
                result= ps.executeUpdate();
                
            }else {
                //INSERT
                String q = INSERT;
                PreparedStatement ps = csql.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
                
                ps.setString(1, this.nombre);
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

	public static List<Categoria> GetAllCategoria() {
		List<Categoria> Base = new ArrayList<Categoria>();
		Connection con = ConnectionUtil.getConnection();

		if (con != null) {
			try {

				Statement st = con.createStatement();
				String q = CATEGORIA;
				ResultSet rs = st.executeQuery(q);
				while (rs.next()) {
					Categoria base = new Categoria();
					int id = rs.getInt("id");
					base.setId(rs.getInt("id"));
					base.setNombre(rs.getString("nombre"));
					if (buscarJuegosPorCategoria(id) == null) {
						base.setJuegos(new ArrayList<Juego>());
					} else {
						base.setJuegos(buscarJuegosPorCategoria(id));
					}
					Base.add(base);
				}

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

		return Base;
	}

	public static List<Juego> buscarJuegosPorCategoria(int id) {
		List<Juego> juegoss = new ArrayList<Juego>();
		Connection con = ConnectionUtil.getConnection();
		if (con != null) {
			try {
				Statement st = con.createStatement();
				String q = JUEGOS + id;
				ResultSet rs = st.executeQuery(q);
				while (rs.next()) {
					Juego añadir = new Juego();
					añadir.setId(rs.getInt("id"));
					añadir.setImagen(rs.getString("imagen"));
					añadir.setNombre(rs.getString("nombre"));
					añadir.setDescripcion((rs.getString("descripcion")));
					añadir.setId_categoria(rs.getInt("id_categoria"));
					juegoss.add(añadir);

				}

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

		return juegoss;
	}

	public List<Juego> getMiJuegos() {

		// como soy lazy
		if (juegos == null) {
			// invoco al SELECT mis libros
			juegos = buscarJuegosPorCategoria(this.id);
		}
		return juegos;
	}
	

	public int eliminar() {
		int rs = 0;
		Connection con = ConnectionUtil.getConnection();

		if (con != null) {
			try {
				PreparedStatement q = con.prepareStatement(DELETE);
				q.setInt(1, this.id);
				rs = q.executeUpdate();
				this.id = -1;
				this.nombre = "";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rs;
	}


}
