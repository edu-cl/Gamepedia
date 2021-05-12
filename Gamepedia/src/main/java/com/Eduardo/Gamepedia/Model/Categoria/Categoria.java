package com.Eduardo.Gamepedia.Model.Categoria;

import java.util.ArrayList;
import java.util.List;

import com.Eduardo.Gamepedia.Model.Juego.Juego;

public class Categoria {

	protected int id;
	protected String nombre;
	protected List<Juego> juegos;
	
	

	public Categoria() {
		this(-1,"");
	}

	
	public Categoria(int id, String nombre, List<Juego> juegos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.juegos = juegos;
	}
	public Categoria(String nombre) {
		super();
		this.id = -1;
		this.nombre = nombre;
		this.juegos = juegos;
	} 

	
	public Categoria(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
		juegos=new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Juego> getJuegos() {
		return juegos;
	}


	public void setJuegos(List<Juego> juegos) {
		this.juegos = juegos;
	}


	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + "]";
	}

}
